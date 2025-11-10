package com.truonggiahung.yte.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.support.Repositories;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.truonggiahung.yte.models.Appointment;
import com.truonggiahung.yte.models.ERole;
import com.truonggiahung.yte.models.User;
import com.truonggiahung.yte.payload.request.AppointmentRequest;
import com.truonggiahung.yte.repository.UserRepository;
import com.truonggiahung.yte.services.AppointmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final UserRepository userRepository;

    // ✅ Đặt lịch khám
    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestBody AppointmentRequest request,Principal principal) {

        // Lấy user đang đăng nhập (bệnh nhân)
        User patient = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user."));

        // Lấy bác sĩ
        User doctor = userRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bác sĩ."));

        LocalDateTime time = LocalDateTime.parse(request.getTime());
        Appointment appointment = appointmentService.bookAppointment(patient, doctor, time);

        return ResponseEntity.ok(appointment);
    }

    //Xem lịch của user
    @GetMapping("/my-schedule")
    public ResponseEntity<List<Appointment>> getMyAppointments(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user."));
        
        boolean isDoctor = user.getRoles().stream()
        .anyMatch(role -> role.getName()== ERole.Doctor);//kiểm tra user đăng nhập có phải bác sĩ hay không

        if(isDoctor){
                return ResponseEntity.ok(appointmentService.getAppointmentsByDoctor(user));//lấy lịch bác sĩ
        }

        return ResponseEntity.ok(appointmentService.getAppointmentsByPatient(user));// lấy lịch user
    }

    @GetMapping("/doctor-schedule/{doctorId}")
    public ResponseEntity<List<Appointment>> getDoctorAppointment(@PathVariable Long doctorId) {

        User doctor = userRepository.findById(doctorId) 
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bác sĩ."));

        return ResponseEntity.ok(appointmentService.getAppointmentsByDoctor(doctor));
    }
    

    //Hủy lịch khám
    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id, Principal principal) {
        User requester = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user."));
        appointmentService.cancelAppointment(id, requester);
        return ResponseEntity.ok("Đã hủy lịch thành công");
    }

    //Xác nhận lịch khám (chỉ Bác sĩ được xác nhận)
    @PreAuthorize("hasRole('Doctor')")
    @PutMapping("/{id}/confirm")
    public ResponseEntity<?> confirmAppointment(@PathVariable Long id, Principal principal) {
        User doctor = userRepository.findByUsername(principal.getName())
        .orElseThrow(() -> new RuntimeException("Không tìm thấy user."));
    appointmentService.confirmAppointment(id, doctor);
    return ResponseEntity.ok("Lịch khám đã được xác nhận.");
    }
}
