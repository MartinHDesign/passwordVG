package com.example.passwordvg.model;

import jakarta.validation.constraints.Pattern;

import java.util.Arrays;
import java.util.Objects;

public class HashRequestDTO {
    @Pattern(regexp = "^[a-fA-F0-9]{32}$", message = "MD5 hash must be exactly 32 hexadecimal characters.")
    private String md5;

    @Pattern(regexp = "^[a-fA-F0-9]{64}$", message = "SHA-256 hash must be exactly 64 hexadecimal characters.")
    private String sha256;

    private String error;

    public HashRequestDTO(String hash){
        if (hash.matches("^[a-fA-F0-9]{64}$")) {
            this.sha256 = hash;
        } else if (hash.matches("^[a-fA-F0-9]{32}$")) {
            this.md5 = hash;
        } else {
            error = "Not an MD5 or SHA256 hash";
        }
    }

    public String get() {
        return Arrays.asList(md5, sha256, error).stream()
                .filter(Objects::nonNull)
                .findFirst()
                .orElse("Not an MD5 or SHA256 hash");
    }
}
