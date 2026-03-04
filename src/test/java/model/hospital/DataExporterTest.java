package model.hospital;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DataExporterTest {
    private FileHandler mockFileHandler;
    private DataExporter dataExporter;

    @BeforeEach
    void setUp() {
        mockFileHandler = Mockito.mock(FileHandler.class);
        dataExporter = new DataExporter(mockFileHandler);
    }

    @Test
    void testExportDoctors_WithSortingAndMock() throws IOException {
        List<Doctor> doctors = Arrays.asList(
                new Doctor("D2", "Вадим", Specialization.Surgeon),
                new Doctor("D3", "Олександр", Specialization.Dentist),
                new Doctor("D1", "Микола", Specialization.Cardiologist)
        );

        Comparator<Doctor> byId = Comparator.comparing(Doctor::getId);

        dataExporter.exportDoctors(doctors, "test.csv", byId);

        String expectedCsv = "id,name,specialization\n" +
                "D1,Микола,Cardiologist\n" +
                "D2,Вадим,Surgeon\n" +
                "D3,Олександр,Dentist\n";


        verify(mockFileHandler, times(1)).writeToFile("test.csv", expectedCsv);
    }

    @Test
    void testImportDoctors_WithMock() throws IOException {
        String fakeCsvContent = "id,name,specialization\n" +
                "D1,Олександр,Cardiologist\n" +
                "D2,Микола,Surgeon\n";

        when(mockFileHandler.readFromFile("test.csv")).thenReturn(fakeCsvContent);

        List<Doctor> importedDoctors = dataExporter.importDoctors("test.csv");

        assertEquals(2, importedDoctors.size());
        assertEquals("Олександр", importedDoctors.get(0).getName());
        assertEquals(Specialization.Cardiologist, importedDoctors.get(0).getSpecialization());

        verify(mockFileHandler, times(1)).readFromFile("test.csv");
    }
}
