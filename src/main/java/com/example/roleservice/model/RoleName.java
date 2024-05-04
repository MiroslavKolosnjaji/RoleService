package com.example.roleservice.model;

/**
 * @author Miroslav Kološnjaji
 */
public enum RoleName {

    ADMIN("ADMIN"),
    MODERATOR("MODERATOR"),
    USER("USER");

    private final String role;

    RoleName(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
