import java.util.*;

public class PatientRecordSystemMenu {
    private PatientRecordSystem prs;
    private Scanner scanner;

    public PatientRecordSystemMenu() {
        prs = new PatientRecordSystem();
        scanner = new Scanner(System.in);
    }

    public void run() {
        String option;
        do {
            displayMenu();
            option = scanner.nextLine().trim().toUpperCase();
            switch (option) {
                case "1":
                    addMeasurementObservationType();
                    break;
                case "2":
                    addCategoryObservationType();
                    break;
                case "3":
                    addPatient();
                    break;
                case "4":
                    addMeasurementObservation();
                    break;
                case "5":
                    addCategoryObservation();
                    break;
                case "6":
                    displayObservationType();
                    break;
                case "7":
                    displayPatientRecord();
                    break;
                case "8":
                    saveData();
                    break;
                case "9":
                    loadData();
                    break;
                case "D":
                    displayAllData();
                    break;
                case "X":
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (!option.equals("X"));
    }

    private void displayMenu() {
        System.out.println("=====================");
        System.out.println("Patient Record System");
        System.out.println("=====================");
        System.out.println("1. Add a measurement observation type");
        System.out.println("2. Add a category observation type");
        System.out.println("3. Add a patient");
        System.out.println("4. Add a measurement observation");
        System.out.println("5. Add a category observation");
        System.out.println("6. Display details of an observation type");
        System.out.println("7. Display a patient record by the patient id");
        System.out.println("8. Save data");
        System.out.println("9. Load data");
        System.out.println("D. Display all data for inspection");
        System.out.println("X. Exit");
        System.out.print("Please enter an option (1-9 or D or X): ");
    }

    private void addMeasurementObservationType() {
        System.out.print("Enter observation type code: ");
        String code = scanner.nextLine();
        System.out.print("Enter observation type name: ");
        String name = scanner.nextLine();
        System.out.print("Enter unit: ");
        String unit = scanner.nextLine();
        try {
            prs.addMeasurementObservationType(code, name, unit);
            System.out.println("Measurement observation type added successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addCategoryObservationType() {
        System.out.print("Enter observation type code: ");
        String code = scanner.nextLine();
        System.out.print("Enter observation type name: ");
        String name = scanner.nextLine();
        System.out.print("Enter categories (separated by commas): ");
        String[] categories = scanner.nextLine().split(",");
        try {
            prs.addCategoryObservationType(code, name, Arrays.asList(categories));
            System.out.println("Category observation type added successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addPatient() {
        System.out.print("Enter patient ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter patient name: ");
        String name = scanner.nextLine();
        try {
            prs.addPatient(id, name);
            System.out.println("Patient added successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addMeasurementObservation() {
        System.out.print("Enter patient ID: ");
        String patientId = scanner.nextLine();
        System.out.print("Enter observation type code: ");
        String observationTypeCode = scanner.nextLine();
        System.out.print("Enter observation value: ");
        double value = Double.parseDouble(scanner.nextLine());
        try {
            prs.addMeasurementObservation(patientId, observationTypeCode, value);
            System.out.println("Measurement observation added successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addCategoryObservation() {
        System.out.print("Enter patient ID: ");
        String patientId = scanner.nextLine();
        System.out.print("Enter observation type code: ");
        String observationTypeCode = scanner.nextLine();
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        try {
            prs.addCategoryObservation(patientId, observationTypeCode, category);
            System.out.println("Category observation added successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void displayObservationType() {
        System.out.print("Enter observation type code: ");
        String code = scanner.nextLine();
        // Display observation type details
        MeasurementObservationType measurementType = prs.getMeasurementObservationTypes().stream()
                .filter(type -> type.getCode().equals(code))
                .findFirst().orElse(null);
        if (measurementType != null) {
            System.out.println("Measurement Observation Type:");
            System.out.println("Code: " + measurementType.getCode());
            System.out.println("Name: " + measurementType.getName());
            System.out.println("Unit: " + measurementType.getUnit());
            return;
        }
        CategoryObservationType categoryType = prs.getCategoryObservationTypes().stream()
                .filter(type -> type.getCode().equals(code))
                .findFirst().orElse(null);
        if (categoryType != null) {
            System.out.println("Category Observation Type:");
            System.out.println("Code: " + categoryType.getCode());
            System.out.println("Name: " + categoryType.getName());
            System.out.println("Categories: " + String.join(", ", categoryType.getCategories()));
            return;
        }
        System.out.println("Observation type not found.");
    }

    private void displayPatientRecord() {
        System.out.print("Enter patient ID: ");
        String patientId = scanner.nextLine();
        // Display patient record
        Patient patient = prs.getPatients().stream()
                .filter(p -> p.getId().equals(patientId))
                .findFirst().orElse(null);
        if (patient != null) {
            System.out.println("Patient Record:");
            System.out.println("ID: " + patient.getId());
            System.out.println("Name: " + patient.getName());
            System.out.println("Measurement Observations:");
            prs.getMeasurementObservations().stream()
                    .filter(observation -> observation.getPatient().getId().equals(patientId))
                    .forEach(observation -> {
                        System.out.println("Type: " + observation.getObservationType().getName() + ", Value: " + observation.getValue());
                    });
            System.out.println("Category Observations:");
            prs.getCategoryObservations().stream()
                    .filter(observation -> observation.getPatient().getId().equals(patientId))
                    .forEach(observation -> {
                        System.out.println("Type: " + observation.getObservationType().getName() + ", Category: " + observation.getCategory());
                    });
            return;
        }
        System.out.println("Patient not found.");
    }

    private void saveData() {
        try {
            prs.saveData();
            System.out.println("Data saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private void loadData() {
        try {
            prs.loadData();
            System.out.println("Data loaded successfully.");
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    private void displayAllData() {
        System.out.println("Measurement Observation Types:");
        prs.getMeasurementObservationTypes().forEach(type -> {
            System.out.println(type.getCode() + ": " + type.getName() + " (" + type.getUnit() + ")");
        });
        System.out.println("Category Observation Types:");
        prs.getCategoryObservationTypes().forEach(type -> {
            System.out.println(type.getCode() + ": " + type.getName() + " (" + String.join(", ", type.getCategories()) + ")");
        });
        System.out.println("Patients:");
        prs.getPatients().forEach(patient -> {
            System.out.println(patient.getId() + ": " + patient.getName());
        });
        System.out.println("Measurement Observations:");
        prs.getMeasurementObservations().forEach(observation -> {
            System.out.println("Patient ID: " + observation.getPatient().getId() + ", Type: " + observation.getObservationType().getName() + ", Value: " + observation.getValue());
        });
        System.out.println("Category Observations:");
        prs.getCategoryObservations().forEach(observation -> {
            System.out.println("Patient ID: " + observation.getPatient().getId() + ", Type: " + observation.getObservationType().getName() + ", Category: " + observation.getCategory());
        });
    }

    public static void main(String[] args) {
        PatientRecordSystemMenu menu = new PatientRecordSystemMenu();
        menu.run();
    }
}
