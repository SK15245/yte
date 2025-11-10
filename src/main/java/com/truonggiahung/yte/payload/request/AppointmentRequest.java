package com.truonggiahung.yte.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentRequest {
    @NotBlank
    private Long doctorId;
    
    @NotBlank
    private String time;
}
