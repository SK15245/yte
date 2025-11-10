package com.truonggiahung.yte.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.truonggiahung.yte.models.Appointment;
import com.truonggiahung.yte.models.AppointmentStatus;
import com.truonggiahung.yte.models.User;
import com.truonggiahung.yte.repository.AppointmentRepository;
import com.truonggiahung.yte.services.AppointmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public Appointment bookAppointment(User patient, User doctor, LocalDateTime time) {
        // Kiểm tra trùng lịch
        boolean isBusy = appointmentRepository.existsByDoctorAndAppointmentTime(doctor, time);
        if (isBusy) {
            throw new RuntimeException("Bác sĩ đã có lịch khám vào thời gian này.");
        }

        // Tạo lịch mới
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(time);
        appointment.setStatus(AppointmentStatus.PENDING);

        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAppointmentsByPatient(User patient) {
        return appointmentRepository.findAllByPatient(patient);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctor(User doctor) {
        return appointmentRepository.findAllByDoctor(doctor);
    }

    @Override
    public void cancelAppointment(Long id, User requester) {
        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch khám."));

        // Chỉ cho phép người đặt hoặc bác sĩ hủy
        if (!appt.getPatient().equals(requester) && !appt.getDoctor().equals(requester)) {
            throw new RuntimeException("Bạn không có quyền hủy lịch này.");
        }

        appt.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appt);
    }

    @Override
    public void confirmAppointment(Long id,User requester){
        Appointment appt = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch khám.")); //kiểm tra lịch
        
        appt.setStatus(AppointmentStatus.CONFIRMED);
        appointmentRepository.save(appt);
    }
}
