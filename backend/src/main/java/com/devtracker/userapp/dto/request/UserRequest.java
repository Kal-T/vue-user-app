package com.devtracker.userapp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * Request DTO record enclosing payload inputs for user actions
 */
public record UserRequest(
    @NotBlank(message = "First name is required")
    String name,

    @NotBlank(message = "Last name is required")
    String lastName,

    @NotBlank(message = "Email is required")
    @Email(message = "Provide a valid email address")
    String email,

    @NotNull(message = "Role is required")
    @Pattern(regexp = "[AU]", message = "Role must be either 'A' (Admin) or 'U' (User)")
    String role,

    String address
) {}
