package com.airtribe.learntrack.entity;

public class Student extends Person{

    private String batch;
    private boolean active;

    public Student() {
        super();
        this.active = true;
    }

    // student without email
    public Student(int id, String firstName, String lastName, String batch) {
        this(id, firstName, lastName, null, batch);
    }

    public Student(int id, String firstName, String lastName, String email, String batch) {
        super(id, firstName, lastName, email);
        this.batch = batch;
        this.active = true;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String getDisplayName() {
        return "Student: " + super.getDisplayName() + " (Batch: " + batch + ")";
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s %s | Email: %s | Batch: %s | Active: %s",
                getId(), getFirstName(), getLastName(),
                getEmail() == null || getEmail().isBlank() ? "N/A" : getEmail(),
                batch, active ? "Yes" : "No");
    }
}
