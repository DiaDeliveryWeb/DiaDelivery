package com.dia.delivery.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequestDto {
    @Size(min=8, max=15)
    @Pattern(regexp = "^[a-zA-Z0-9~`!@#$%^&*()-_=+\\\\[{\\\\]}\\\\\\\\|;:'\\\",<.>/?]*$")
    private String password;
}