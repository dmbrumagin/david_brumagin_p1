package dev.brumagin.app.entities;

/**
 * The three allowed expense status types
 * PENDING - Has not been reviewed
 * APPROVED - The expense has been approved
 * DENIED - The expense has been denied
 *
 * Approved and Denied expenses cannot be edited or deleted
 * Employees with expenses cannot be edited or deleted
 */
public enum ExpenseStatus {
    PENDING,
    APPROVED,
    DENIED
}
