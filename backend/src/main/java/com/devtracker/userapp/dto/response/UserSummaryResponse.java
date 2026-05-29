package com.devtracker.userapp.dto.response;

/**
 * Response DTO record returning computed user metric summaries
 */
public record UserSummaryResponse(
    long total,
    long adminCount,
    long userCount
) {}
