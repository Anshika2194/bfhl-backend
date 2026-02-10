package com.example.bfhl.dto;

public class ApiResponse {
    public boolean is_success;
    public String official_email;
    public Object data;

    public ApiResponse(boolean is_success, String official_email, Object data) {
        this.is_success = is_success;
        this.official_email = official_email;
        this.data = data;
    }
}
