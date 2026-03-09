package model.hospital;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Hospital hospital = new Hospital("Звягельська міська лікарня");
        Scanner scanner = new Scanner(System.in);

        FileHandler fileHandler = new DefaultFileHandler();
        DataExporter dataExporter = new DataExporter(fileHandler);

        boolean running = true;

        System.out.println("Ви в системі управління лікарнею");

        while (running) {
            System.out.println("\nОберіть дію:");
            System.out.println("1. Додати лікаря");
            System.out.println("2. Додати пацієнта");
            System.out.println("3. Знайти лікарів за спеціалізацією");
            System.out.println("4. Записати пацієнта на прийом");
            System.out.println("5. Вивести інформацію про стан лікарні");
            System.out.println("6. Експортувати лікарів у CSV (сортування за ім'ям)");
            System.out.println("7. Імпортувати лікарів з CSV");
            System.out.println("0. Вийти");
            System.out.print("Ваш вибір: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Введіть ID лікаря: ");
                    String docId = scanner.nextLine();
                    System.out.print("Введіть ім'я лікаря: ");
                    String docName = scanner.nextLine();
                    System.out.println("Доступні спеціалізації: Cardiologist, Surgeon, Therapist, Dentist");
                    System.out.print("Введіть спеціалізацію: ");
                    try {
                        Specialization spec = Specialization.valueOf(scanner.nextLine());
                        Doctor newDoc = new Doctor(docId, docName, spec);
                        if (hospital.addDoctor(newDoc)) {
                            System.out.println("Лікаря успішно додано!");
                        } else {
                            System.out.println("Помилка: Лікар з таким ID вже існує.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Помилка: Невідома спеціалізація.");
                    }
                    break;

                case "2":
                    System.out.print("Введіть ID пацієнта: ");
                    String patId = scanner.nextLine();
                    System.out.print("Введіть ім'я пацієнта: ");
                    String patName = scanner.nextLine();
                    if (hospital.addPatient(new Patient(patId, patName))) {
                        System.out.println("Пацієнта успішно додано!");
                    } else {
                        System.out.println("Помилка: Пацієнт з таким ID вже існує.");
                    }
                    break;

                case "3":
                    System.out.print("Введіть спеціалізацію для пошуку: ");
                    try {
                        Specialization searchSpec = Specialization.valueOf(scanner.nextLine().toUpperCase());
                        List<Doctor> foundDoctors = hospital.findDoctorsBySpecialization(searchSpec);
                        if (foundDoctors.isEmpty()) {
                            System.out.println("Лікарів такої спеціалізації не знайдено.");
                        } else {
                            System.out.println("Знайдені лікарі:");
                            for (Doctor d : foundDoctors) {
                                System.out.println("- " + d.getName() + " (ID: " + d.getId() + ")");
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Помилка: Невідома спеціалізація.");
                    }
                    break;

                case "4":
                    System.out.print("Введіть ID пацієнта: ");
                    String pId = scanner.nextLine();
                    System.out.print("Введіть ID лікаря: ");
                    String dId = scanner.nextLine();
                    if (hospital.appointPatientToDoctor(pId, dId)) {
                        System.out.println("Запис успішно створено!");
                    } else {
                        System.out.println("Помилка: Неправильний ID, або пацієнт вже має запис.");
                    }
                    break;

                case "5":
                    System.out.println(" Актуальна інформація ");
                    System.out.println("Лікарня: " + hospital.getName());
                    System.out.println("Всього лікарів: " + hospital.getDoctors().size());
                    System.out.println("Всього пацієнтів: " + hospital.getPatients().size());
                    System.out.println("Кількість активних записів: " + hospital.getCountOfAppointedPatients());
                    break;

                case "6":
                    System.out.print("Введіть назву файлу для збереження (наприклад, doctors.csv): ");
                    String exportPath = scanner.nextLine();
                    try {
                        // Сортуємо лікарів за ім'ям перед експортом
                        dataExporter.exportDoctors(hospital.getDoctors(), exportPath, Comparator.comparing(Doctor::getName));
                        System.out.println("Дані успішно експортовано!");
                    } catch (IOException e) {
                        System.out.println("Помилка запису у файл: " + e.getMessage());
                    }
                    break;

                case "7":
                    System.out.print("Введіть назву файлу для імпорту: ");
                    String importPath = scanner.nextLine();
                    try {
                        List<Doctor> imported = dataExporter.importDoctors(importPath);
                        for (Doctor d : imported) {
                            hospital.addDoctor(d);
                        }
                        System.out.println("Успішно імпортовано лікарів: " + imported.size());
                    } catch (IOException e) {
                        System.out.println("Помилка читання файлу: " + e.getMessage());
                    }
                    break;

                case "0":
                    running = false;
                    System.out.println("Роботу завершено. До побачення!");
                    break;

                default:
                    System.out.println("Невідома команда. Спробуйте ще раз.");
            }
        }
        scanner.close();
    }
}
