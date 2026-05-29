package com.devtracker.userapp.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Standardized structure returned by controllers when error intercepts occur
 */
public record ErrorResponse(
    LocalDateTime timestamp,
    int status,
    String message,
    Map<String, String> fieldErrors
) {}
