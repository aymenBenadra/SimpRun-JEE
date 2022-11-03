package com.sakamoto.simprunjee.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailServiceTest {

    static EmailService emailService;
    static Dotenv dotenv;

    @BeforeAll
    static void setUp() {
        emailService = EmailService.getInstance();
        dotenv = Dotenv.load();
    }

    @Test
    void testSendEmail() {
        assertTrue(emailService.sendEmail(dotenv.get("EMAIL_TEST_ADDRESS"), "Test", "This is a test email"));
        assertFalse(emailService.sendEmail("test@failed", "Test", "This is a test email"));
    }
}