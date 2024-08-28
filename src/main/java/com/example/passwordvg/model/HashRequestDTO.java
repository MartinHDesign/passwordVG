package com.example.passwordvg.model;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HashRequestDTO {
    @Pattern(regexp = "^[a-fA-F0-9]{32}$|^[a-fA-F0-9]{64}$", message = "Hash must be either 32 (MD5) or 64 (SHA-256) hexadecimal characters.")
    private String hash;
}
