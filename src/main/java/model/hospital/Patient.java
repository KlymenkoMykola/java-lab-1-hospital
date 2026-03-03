package model.hospital;

import java.util.Objects;

public class Patient {
    private String id;
    private String name;
    private String assignedDoctorId;

    public Patient(String id, String name) {
        this.id = id;
        this.name = name;
        this.assignedDoctorId = null;
    }

    public boolean assignToDoctor(String doctorId) {
        if (this.assignedDoctorId != null) {
            return false;
        }
        this.assignedDoctorId = doctorId;
        return true;
    }

    public boolean hasAppointment() {
        return this.assignedDoctorId != null;
    }

    public void cancelAppointment() {
        this.assignedDoctorId = null;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAssignedDoctorId() { return assignedDoctorId; }
    public void setAssignedDoctorId(String assignedDoctorId) { this.assignedDoctorId = assignedDoctorId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
