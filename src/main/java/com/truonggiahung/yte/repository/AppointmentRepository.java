package com.truonggiahung.yte.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.truonggiahung.yte.models.Appointment;
import com.truonggiahung.yte.models.AppointmentStatus;
import com.truonggiahung.yte.models.User;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByPatient(User patient);

    List<Appointment> findAllByDoctor(User doctor);

    List<Appointment> findAllByPatientAndStatus(User patient, AppointmentStatus status);

    boolean existsByDoctorAndAppointmentTime(User doctor, LocalDateTime appointmentTime);
}
