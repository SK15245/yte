package com.truonggiahung.yte.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('Patient') or hasRole('Doctor') or hasRole('Admin')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/doctor")
    @PreAuthorize("hasRole('Doctor')")
    public String doctorAccess() {
        return "Doctor Board.";
    }

    @GetMapping("/patient")
    @PreAuthorize("hasRole('Patient')")
    public String patientAccess() {
        return "Patient Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('Admin')")
    public String adminAccess() {
        return "Admin Board.";
    }
}
