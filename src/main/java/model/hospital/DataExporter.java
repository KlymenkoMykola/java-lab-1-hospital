package model.hospital;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DataExporter {
    private final FileHandler fileHandler;

    public DataExporter(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public void exportDoctors(List<Doctor> doctors, String filePath, Comparator<Doctor> comparator) throws IOException {
        List<Doctor> sortedDoctors = new ArrayList<>(doctors);
        if (comparator != null) {
            sortedDoctors.sort(comparator);
        }

        StringBuilder csvData = new StringBuilder();
        csvData.append("id,name,specialization\n");

        for (Doctor doc : sortedDoctors) {
            csvData.append(doc.getId()).append(",")
                    .append(doc.getName()).append(",")
                    .append(doc.getSpecialization().name()).append("\n");
        }

        fileHandler.writeToFile(filePath, csvData.toString());
    }

    public List<Doctor> importDoctors(String filePath) throws IOException {
        String content = fileHandler.readFromFile(filePath);
        List<Doctor> doctors = new ArrayList<>();

        String[] lines = content.split("\n");
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(",");
            if (parts.length == 3) {
                String id = parts[0];
                String name = parts[1];
                Specialization spec = Specialization.valueOf(parts[2]);
                doctors.add(new Doctor(id, name, spec));
            }
        }
        return doctors;
    }
}
