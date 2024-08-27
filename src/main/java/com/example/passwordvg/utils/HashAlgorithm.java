package com.example.passwordvg.utils;

public enum HashAlgorithm {
    MD5("MD5"),
    SHA256("SHA-256");

    private final String algorithmName;

    HashAlgorithm(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    @Override
    public String toString() {
        return this.algorithmName;
    }
}
