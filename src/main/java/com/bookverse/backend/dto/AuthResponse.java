package com.bookverse.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String email;
    private Long userId;

    // Manual getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Manual Builder class (in case Lombok @Builder doesn't work)
    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }

    public static class AuthResponseBuilder {
        private String token;
        private String email;
        private Long userId;

        public AuthResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public AuthResponseBuilder email(String email) {
            this.email = email;
            return this;
        }

        public AuthResponseBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public AuthResponse build() {
            AuthResponse response = new AuthResponse();
            response.setToken(this.token);
            response.setEmail(this.email);
            response.setUserId(this.userId);
            return response;
        }
    }
}
