package model.hospital;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HospitalTest {
    private Hospital hospital;
    private Doctor doc1;
    private Patient pat1;

    @BeforeEach
    void setUp() {
        hospital = new Hospital("Zviahel City Hospital");
        doc1 = new Doctor("D1", "Олександр", Specialization.Surgeon);
        pat1 = new Patient("P1", "Микола");
    }

    @Test
    void addDoctor_ValidDoctor_ReturnsTrue() {
        assertTrue(hospital.addDoctor(doc1));
        assertEquals(1, hospital.getDoctors().size());
    }

    @Test
    void addDoctor_DuplicateDoctor_ReturnsFalse() {
        hospital.addDoctor(doc1);
        assertFalse(hospital.addDoctor(doc1));
        assertEquals(1, hospital.getDoctors().size());
    }

    @Test
    void addDoctor_NullDoctor_ReturnsFalse() {
        assertFalse(hospital.addDoctor(null));
        assertEquals(0, hospital.getDoctors().size());
    }

    @Test
    void findDoctorsBySpecialization_ValidSpecialization_ReturnsList() {
        hospital.addDoctor(doc1);
        hospital.addDoctor(new Doctor("D2", "Іван", Specialization.Cardiologist));

        List<Doctor> surgeons = hospital.findDoctorsBySpecialization(Specialization.Surgeon);

        assertEquals(1, surgeons.size());
        assertEquals("D1", surgeons.get(0).getId());
    }

    @Test
    void findDoctorsBySpecialization_NullSpecialization_ReturnsEmptyList() {
        hospital.addDoctor(doc1);
        List<Doctor> result = hospital.findDoctorsBySpecialization(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void appointPatient_ValidData_ReturnsTrue() {
        hospital.addDoctor(doc1);
        hospital.addPatient(pat1);

        assertTrue(hospital.appointPatientToDoctor("P1", "D1"));
        assertTrue(pat1.hasAppointment());
        assertEquals("D1", pat1.getAssignedDoctorId());
    }

    @Test
    void appointPatient_AlreadyAppointed_ReturnsFalse() {
        hospital.addDoctor(doc1);
        hospital.addPatient(pat1);

        hospital.appointPatientToDoctor("P1", "D1");
        assertFalse(hospital.appointPatientToDoctor("P1", "D1"));
    }

    @Test
    void appointPatient_InvalidDoctorId_ReturnsFalse() {
        hospital.addPatient(pat1);
        // Лікаря з ID "D99" не існує в лікарні
        assertFalse(hospital.appointPatientToDoctor("P1", "D99"));
        assertFalse(pat1.hasAppointment());
    }

    @Test
    void getCountOfAppointedPatients_ReturnsCorrectCount() {
        hospital.addDoctor(doc1);
        Patient pat2 = new Patient("P2", "Олег");

        hospital.addPatient(pat1);
        hospital.addPatient(pat2);

        hospital.appointPatientToDoctor("P1", "D1"); // Записуємо лише одного

        assertEquals(1, hospital.getCountOfAppointedPatients());
    }
}
