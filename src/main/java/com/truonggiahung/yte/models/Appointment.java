package com.truonggiahung.yte.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "appointments")
@Getter
@Setter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonIgnoreProperties("appointments")
    private User patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonIgnoreProperties("appointments")
    private User doctor;

    private LocalDateTime appointmentTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
