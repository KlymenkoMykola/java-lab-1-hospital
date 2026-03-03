package model.hospital;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Hospital {
    private String name;
    private List<Doctor> doctors;
    private List<Patient> patients;

    public Hospital(String name) {
        this.name = name;
        this.doctors = new ArrayList<>();
        this.patients = new ArrayList<>();
    }

    public boolean addDoctor(Doctor doctor) {
        if (doctor != null && !doctors.contains(doctor)) {
            doctors.add(doctor);
            return true;
        }
        return false;
    }

    public boolean addPatient(Patient patient) {
        if (patient != null && !patients.contains(patient)) {
            patients.add(patient);
            return true;
        }
        return false;
    }

    public List<Doctor> findDoctorsBySpecialization(Specialization specialization) {
        if (specialization == null) {
            return new ArrayList<>();
        }
        return doctors.stream()
                .filter(doc -> doc.isSpecializationMatch(specialization))
                .collect(Collectors.toList());
    }

    public Doctor getDoctorById(String id) {
        return doctors.stream()
                .filter(doc -> doc.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public boolean appointPatientToDoctor(String patientId, String doctorId) {
        Patient patient = patients.stream()
                .filter(p -> p.getId().equals(patientId))
                .findFirst()
                .orElse(null);
        Doctor doctor = getDoctorById(doctorId);

        if (patient != null && doctor != null) {
            return patient.assignToDoctor(doctorId);
        }
        return false;
    }

    public long getCountOfAppointedPatients() {
        return patients.stream()
                .filter(Patient::hasAppointment)
                .count();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Doctor> getDoctors() { return doctors; }
    public void setDoctors(List<Doctor> doctors) { this.doctors = doctors; }

    public List<Patient> getPatients() { return patients; }
    public void setPatients(List<Patient> patients) { this.patients = patients; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hospital hospital = (Hospital) o;
        return Objects.equals(name, hospital.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
