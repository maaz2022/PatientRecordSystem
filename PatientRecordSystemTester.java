import java.util.*;

class Patient {
    private String id;
    private String name;

    public Patient(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Patient ID: " + id + ", Name: " + name;
    }
}

class ObservationType {
    private String code;
    private String name;

    public ObservationType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}

class MeasurementObservationType extends ObservationType {
    private String unit;

    public MeasurementObservationType(String code, String name, String unit) {
        super(code, name);
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}

class CategoryObservationType extends ObservationType {
    private List<String> categories;

    public CategoryObservationType(String code, String name, List<String> categories) {
        super(code, name);
        this.categories = categories;
    }

    public List<String> getCategories() {
        return categories;
    }
}

class Observation {
    private Patient patient;
    private ObservationType observationType;

    public Observation(Patient patient, ObservationType observationType) {
        this.patient = patient;
        this.observationType = observationType;
    }

    public Patient getPatient() {
        return patient;
    }

    public ObservationType getObservationType() {
        return observationType;
    }
}

class MeasurementObservation extends Observation {
    private double value;

    public MeasurementObservation(Patient patient, MeasurementObservationType observationType, double value) {
        super(patient, observationType);
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}

class CategoryObservation extends Observation {
    private String category;

    public CategoryObservation(Patient patient, CategoryObservationType observationType, String category) {
        super(patient, observationType);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}

class PatientRecordSystem {
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

    public void addMeasurementObservationType(String code, String name, String unit) throws Exception {
        if (measurementObservationTypes.stream().anyMatch(t -> t.getCode().equals(code))) {
            throw new Exception("Observation type code already exists");
        }
        measurementObservationTypes.add(new MeasurementObservationType(code, name, unit));
    }

    public void addCategoryObservationType(String code, String name, List<String> categories) throws Exception {
        if (categoryObservationTypes.stream().anyMatch(t -> t.getCode().equals(code))) {
            throw new Exception("Observation type code already exists");
        }
        categoryObservationTypes.add(new CategoryObservationType(code, name, categories));
    }

    public void addPatient(String id, String name) throws Exception {
        if (patients.stream().anyMatch(p -> p.getId().equals(id))) {
            throw new Exception("Patient ID already exists");
        }
        patients.add(new Patient(id, name));
    }

    public void addMeasurementObservation(String patientId, String observationTypeCode, double value) throws Exception {
        Patient patient = patients.stream().filter(p -> p.getId().equals(patientId)).findFirst().orElse(null);
        if (patient == null) {
            throw new Exception("Patient not found");
        }
        MeasurementObservationType observationType = measurementObservationTypes.stream().filter(t -> t.getCode().equals(observationTypeCode)).findFirst().orElse(null);
        if (observationType == null) {
            throw new Exception("Measurement observation type not found");
        }
        if (measurementObservations.stream().anyMatch(o -> o.getPatient().equals(patient) && o.getObservationType().equals(observationType))) {
            throw new Exception("Patient already has an observation of this type");
        }
        measurementObservations.add(new MeasurementObservation(patient, observationType, value));
    }

    public void addCategoryObservation(String patientId, String observationTypeCode, String category) throws Exception {
        Patient patient = patients.stream().filter(p -> p.getId().equals(patientId)).findFirst().orElse(null);
        if (patient == null) {
            throw new Exception("Patient not found");
        }
        CategoryObservationType observationType = categoryObservationTypes.stream().filter(t -> t.getCode().equals(observationTypeCode)).findFirst().orElse(null);
        if (observationType == null) {
            throw new Exception("Category observation type not found");
        }
        if (!observationType.getCategories().contains(category)) {
            throw new Exception("Invalid category for observation type");
        }
        if (categoryObservations.stream().anyMatch(o -> o.getPatient().equals(patient) && o.getObservationType().equals(observationType))) {
            throw new Exception("Patient already has an observation of this type");
        }
        categoryObservations.add(new CategoryObservation(patient, observationType, category));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Measurement Observation Types:\n");
        for (MeasurementObservationType type : measurementObservationTypes) {
            sb.append(type.getName()).append(" (Code: ").append(type.getCode()).append(")\n");
        }
        sb.append("\nCategory Observation Types:\n");
        for (CategoryObservationType type : categoryObservationTypes) {
            sb.append(type.getName()).append(" (Code: ").append(type.getCode()).append(")\n");
        }
        sb.append("\nPatients:\n");
        for (Patient patient : patients) {
            sb.append(patient.toString()).append("\n");
        }
        sb.append("\nMeasurement Observations:\n");
        for (MeasurementObservation observation : measurementObservations) {
            sb.append("Patient ID: ").append(observation.getPatient().getId()).append(", Observation Type: ")
                    .append(observation.getObservationType().getName()).append(", Value: ").append(observation.getValue()).append("\n");
        }
        sb.append("\nCategory Observations:\n");
        for (CategoryObservation observation : categoryObservations) {
            sb.append("Patient ID: ").append(observation.getPatient().getId()).append(", Observation Type: ")
                    .append(observation.getObservationType().getName()).append(", Category: ").append(observation.getCategory()).append("\n");
        }
        return sb.toString();
    }
}

public class PatientRecordSystemTester {
    public static void main(String[] args) throws Exception {
        testInit();
        testAddObservationTypes();
        testAddPatients();
        testAddObservations();
    }

    public static void testInit() {
        String test = "TEST: Create new patient record system and display it";
        System.out.println(test);
        PatientRecordSystem prs = new PatientRecordSystem();
        System.out.println(prs);
    }

    public static void testAddObservationTypes() throws Exception {
        PatientRecordSystem prs = new PatientRecordSystem();
        // add a measurement observation type
        prs.addMeasurementObservationType("T100", "Blood Pressure", "psi");
        System.out.println(prs);
        // add a measurement observation type with invalid code
        try {
            prs.addMeasurementObservationType("T100", "Height", "cm");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println(prs);
        }
        // add a category observation type
        List<String> categories = Arrays.asList("Group A", "Group B1", "Group B2");
        prs.addCategoryObservationType("T200", "blood type", categories);
        System.out.println(prs);
    }

    public static void testAddPatients() throws Exception {
        PatientRecordSystem prs = new PatientRecordSystem();
        // add a new patient
        prs.addPatient("P100", "Smith");
        System.out.println(prs);
        // add another patient
        prs.addPatient("P200", "Adams");
        System.out.println(prs);
        // invalid request
        try {
            prs.addPatient("P200", "Blake");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println(prs);
        }
    }

    public static void testAddObservations() throws Exception {
        // Create PatientRecordSystem and add patients
        PatientRecordSystem prs = new PatientRecordSystem();
        // add observation types
        prs.addMeasurementObservationType("T100", "Blood Pressure", "psi");
        List<String> categories = Arrays.asList("Group A", "Group B1", "Group B2");
        prs.addCategoryObservationType("T200", "blood type", categories);
        // add patients
        prs.addPatient("P100", "Smith");
        prs.addPatient("P200", "Adams");
        System.out.println(prs);
        // add a measurement observation to Smith’s records
        prs.addMeasurementObservation("P100", "T100", 120);
        System.out.println(prs);
        // add a category observation to Smith’s records
        prs.addCategoryObservation("P100", "T200", "Group A");
        System.out.println(prs);
        // invalid request: patient already has observation of the type
        try {
            prs.addMeasurementObservation("P100", "T100", 140);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println(prs);
        }
        // invalid request: invalid category value
        try {
            prs.addCategoryObservation("P200", "T200", "Group D");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println(prs);
        }
    }
}
