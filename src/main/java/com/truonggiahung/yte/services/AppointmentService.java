package com.truonggiahung.yte.services;

import java.time.LocalDateTime;
import java.util.List;

import com.truonggiahung.yte.models.Appointment;
import com.truonggiahung.yte.models.User;

public interface AppointmentService {
    Appointment bookAppointment(User patient, User doctor, LocalDateTime time);
    List<Appointment> getAppointmentsByPatient(User patient);
    List<Appointment> getAppointmentsByDoctor(User doctor);
    void cancelAppointment(Long id, User requester);
    void confirmAppointment(Long id,User requester);
}
