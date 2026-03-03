package model.hospital;

import java.util.Objects;

public class Doctor {
    private String id;
    private String name;
    private Specialization specialization;

    public Doctor(String id, String name, Specialization specialization){
    this.id = id;
    this.name = name;
    this.specialization = specialization;
    }

    public boolean isSpecializationMatch(Specialization requiredSpecialization){
    if (requiredSpecialization == null) {
        return false;
    }
    return this.specialization == requiredSpecialization;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Specialization getSpecialization() { return specialization; }
    public void setId(Specialization specialization) { this.specialization = specialization; }

    @Override
    public boolean equals(Object o){
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(id, doctor.id);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id);
    }
}
