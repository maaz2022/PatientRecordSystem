import java.io.*;
import java.util.*;

class MeasurementObservationType {
    private String code;
    private String name;
    private String unit;

    public MeasurementObservationType(String code, String name, String unit) {
        this.code = code;
        this.name = name;
        this.unit = unit;
    }

    // Getters
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }
}

class CategoryObservationType {
    private String code;
    private String name;
    private List<String> categories;

    public CategoryObservationType(String code, String name, List<String> categories) {
        this.code = code;
        this.name = name;
        this.categories = categories;
    }

    // Getters
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public List<String> getCategories() {
        return categories;
    }
}

class Patient {
    private String id;
    private String name;

    public Patient(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class MeasurementObservation {
    private Patient patient;
    private MeasurementObservationType observationType;
    private double value;

    public MeasurementObservation(Patient patient, MeasurementObservationType observationType, double value) {
        this.patient = patient;
        this.observationType = observationType;
        this.value = value;
    }

    // Getters
    public Patient getPatient() {
        return patient;
    }

    public MeasurementObservationType getObservationType() {
        return observationType;
    }

    public double getValue() {
        return value;
    }
}

class CategoryObservation {
    private Patient patient;
    private CategoryObservationType observationType;
    private String category;

    public CategoryObservation(Patient patient, CategoryObservationType observationType, String category) {
        this.patient = patient;
        this.observationType = observationType;
        this.category = category;
    }

    // Getters
    public Patient getPatient() {
        return patient;
    }

    public CategoryObservationType getObservationType() {
        return observationType;
    }

    public String getCategory() {
        return category;
    }
}

public class PatientRecordSystem {
    private List<MeasurementObservationType> measurementObservationTypes;
    private List<CategoryObservationType> categoryObservationTypes;
    private List<Patient> patients;
    private List<MeasurementObservation> measurementObservations;
    private List<CategoryObservation> categoryObservations;

    public PatientRecordSystem() {
        measurementObservationTypes = new ArrayList<>();
        categoryObservationTypes = new ArrayList<>();
        patients = new ArrayList<>();
        measurementObservations = new ArrayList<>();
        categoryObservations = new ArrayList<>();
    }

    // Methods to add observation types, patients, and observations
    public void addMeasurementObservationType(String code, String name, String unit) throws Exception {
        for (MeasurementObservationType type : measurementObservationTypes) {
            if (type.getCode().equals(code)) {
                throw new Exception("Observation type code already exists");
            }
        }
        measurementObservationTypes.add(new MeasurementObservationType(code, name, unit));
    }

    public void addCategoryObservationType(String code, String name, List<String> categories) throws Exception {
        for (CategoryObservationType type : categoryObservationTypes) {
            if (type.getCode().equals(code)) {
                throw new Exception("Observation type code already exists");
            }
        }
        categoryObservationTypes.add(new CategoryObservationType(code, name, categories));
    }

    public void addPatient(String id, String name) throws Exception {
        for (Patient patient : patients) {
            if (patient.getId().equals(id)) {
                throw new Exception("Patient ID already exists");
            }
        }
        patients.add(new Patient(id, name));
    }

    public void addMeasurementObservation(String patientId, String observationTypeCode, double value) throws Exception {
        Patient patient = findPatientById(patientId);
        MeasurementObservationType observationType = findMeasurementObservationTypeByCode(observationTypeCode);
        if (patient == null || observationType == null) {
            throw new Exception("Invalid patient ID or observation type code");
        }
        measurementObservations.add(new MeasurementObservation(patient, observationType, value));
    }

    public void addCategoryObservation(String patientId, String observationTypeCode, String category) throws Exception {
        Patient patient = findPatientById(patientId);
        CategoryObservationType observationType = findCategoryObservationTypeByCode(observationTypeCode);
        if (patient == null || observationType == null) {
            throw new Exception("Invalid patient ID or observation type code");
        }
        if (!observationType.getCategories().contains(category)) {
            throw new Exception("Invalid category for observation type");
        }
        categoryObservations.add(new CategoryObservation(patient, observationType, category));
    }

    // Helper methods to find objects
    private Patient findPatientById(String id) {
        for (Patient patient : patients) {
            if (patient.getId().equals(id)) {
                return patient;
            }
        }
        return null;
    }

    private MeasurementObservationType findMeasurementObservationTypeByCode(String code) {
        for (MeasurementObservationType type : measurementObservationTypes) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    private CategoryObservationType findCategoryObservationTypeByCode(String code) {
        for (CategoryObservationType type : categoryObservationTypes) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    // Method to save data to files
    public void saveData() throws Exception {
        saveMeasurementObservationTypes();
        saveCategoryObservationTypes();
        savePatients();
        saveMeasurementObservations();
        saveCategoryObservations();
    }

    private void saveMeasurementObservationTypes() throws Exception {
        try (PrintWriter writer = new PrintWriter("PRS-MeasurementObservationTypes.txt")) {
            for (MeasurementObservationType type : measurementObservationTypes) {
                writer.println(type.getCode() + ";" + type.getName() + ";" + type.getUnit());
            }
        }
    }

    private void saveCategoryObservationTypes() throws Exception {
        try (PrintWriter writer = new PrintWriter("PRS-CategoryObservationTypes.txt")) {
            for (CategoryObservationType type : categoryObservationTypes) {
                writer.print(type.getCode() + ";" + type.getName() + ";");
                for (String category : type.getCategories()) {
                    writer.print(category + ",");
                }
                writer.println();
            }
        }
    }

    private void savePatients() throws Exception {
        try (PrintWriter writer = new PrintWriter("PRS-Patients.txt")) {
            for (Patient patient : patients) {
                writer.println(patient.getId() + ";" + patient.getName());
            }
        }
    }

    private void saveMeasurementObservations() throws Exception {
        try (PrintWriter writer = new PrintWriter("PRS-MeasurementObservations.txt")) {
            for (MeasurementObservation observation : measurementObservations) {
                writer.println(observation.getPatient().getId() + ";" + observation.getObservationType().getCode() + ";" + observation.getValue());
            }
        }
    }

    private void saveCategoryObservations() throws Exception {
        try (PrintWriter writer = new PrintWriter("PRS-CategoryObservations.txt")) {
            for (CategoryObservation observation : categoryObservations) {
                writer.println(observation.getPatient().getId() + ";" + observation.getObservationType().getCode() + ";" + observation.getCategory());
            }
        }
    }

    // Method to load data from files
    public void loadData() throws Exception {
        loadMeasurementObservationTypes();
        loadCategoryObservationTypes();
        loadPatients();
        loadMeasurementObservations();
        loadCategoryObservations();
    }

    private void loadMeasurementObservationTypes() throws Exception {
        measurementObservationTypes.clear();
        try (Scanner scanner = new Scanner(new File("PRS-MeasurementObservationTypes.txt"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(";");
                if (parts.length == 3) {
                    measurementObservationTypes.add(new MeasurementObservationType(parts[0], parts[1], parts[2]));
                }
            }
        }
    }

    private void loadCategoryObservationTypes() throws Exception {
        categoryObservationTypes.clear();
        try (Scanner scanner = new Scanner(new File("PRS-CategoryObservationTypes.txt"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(";");
                if (parts.length >= 2) {
                    String code = parts[0];
                    String name = parts[1];
                    List<String> categories = new ArrayList<>();
                    if (parts.length > 2 && !parts[2].isEmpty()) {
                        categories.addAll(Arrays.asList(parts[2].split(",")));
                    }
                    categoryObservationTypes.add(new CategoryObservationType(code, name, categories));
                }
            }
        }
    }

    private void loadPatients() throws Exception {
        patients.clear();
        try (Scanner scanner = new Scanner(new File("PRS-Patients.txt"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(";");
                if (parts.length == 2) {
                    patients.add(new Patient(parts[0], parts[1]));
                }
            }
        }
    }

    private void loadMeasurementObservations() throws Exception {
        measurementObservations.clear();
        try (Scanner scanner = new Scanner(new File("PRS-MeasurementObservations.txt"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(";");
                if (parts.length == 3) {
                    Patient patient = findPatientById(parts[0]);
                    MeasurementObservationType observationType = findMeasurementObservationTypeByCode(parts[1]);
                    double value = Double.parseDouble(parts[2]);
                    if (patient != null && observationType != null) {
                        measurementObservations.add(new MeasurementObservation(patient, observationType, value));
                    }
                }
            }
        }
    }

    private void loadCategoryObservations() throws Exception {
        categoryObservations.clear();
        try (Scanner scanner = new Scanner(new File("PRS-CategoryObservations.txt"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(";");
                if (parts.length == 3) {
                    Patient patient = findPatientById(parts[0]);
                    CategoryObservationType observationType = findCategoryObservationTypeByCode(parts[1]);
                    String category = parts[2];
                    if (patient != null && observationType != null) {
                        categoryObservations.add(new CategoryObservation(patient, observationType, category));
                    }
                }
            }
        }
    }

    // Other getter methods for inspection
    public List<MeasurementObservationType> getMeasurementObservationTypes() {
        return measurementObservationTypes;
    }

    public List<CategoryObservationType> getCategoryObservationTypes() {
        return categoryObservationTypes;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public List<MeasurementObservation> getMeasurementObservations() {
        return measurementObservations;
    }

    public List<CategoryObservation> getCategoryObservations() {
        return categoryObservations;
    }
}
