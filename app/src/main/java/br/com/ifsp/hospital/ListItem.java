package br.com.ifsp.hospital;

public class ListItem {
    private String id;
    private String name;
    private int age;
    private String gender;
    private String diagnostic;
    private String medicines;
    public ListItem(){}

    public ListItem(String id, String name, int age, String gender, String diagnostic, String medicines) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.diagnostic = diagnostic;
        this.medicines = medicines;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }
}