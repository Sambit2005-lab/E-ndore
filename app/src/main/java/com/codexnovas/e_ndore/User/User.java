package com.codexnovas.e_ndore.User;

public class User {
    private String name;
    private String dateOfBirth;
    private int age;
    private String gender;
    private boolean hasBirthCertificate;
    private boolean hasDeathCertificate;
    private String deathLocation;
    private boolean hasNOC;
    private String nocPurpose;
    private int numOfNOC;
    private boolean isMarried;
    private String spouseName;
    private String ageGroup;

    // Default constructor (required for Firestore)
    public User() {
    }

    // Constructor with all fields
    public User(String name, String dateOfBirth, int age, String gender, boolean hasBirthCertificate,
                boolean hasDeathCertificate, String deathLocation, boolean hasNOC, String nocPurpose,
                int numOfNOC, boolean isMarried, String spouseName, String ageGroup) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.gender = gender;
        this.hasBirthCertificate = hasBirthCertificate;
        this.hasDeathCertificate = hasDeathCertificate;
        this.deathLocation = deathLocation;
        this.hasNOC = hasNOC;
        this.nocPurpose = nocPurpose;
        this.numOfNOC = numOfNOC;
        this.isMarried = isMarried;
        this.spouseName = spouseName;
        this.ageGroup = ageGroup;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public boolean isHasBirthCertificate() {
        return hasBirthCertificate;
    }

    public void setHasBirthCertificate(boolean hasBirthCertificate) {
        this.hasBirthCertificate = hasBirthCertificate;
    }

    public boolean isHasDeathCertificate() {
        return hasDeathCertificate;
    }

    public void setHasDeathCertificate(boolean hasDeathCertificate) {
        this.hasDeathCertificate = hasDeathCertificate;
    }

    public String getDeathLocation() {
        return deathLocation;
    }

    public void setDeathLocation(String deathLocation) {
        this.deathLocation = deathLocation;
    }

    public boolean isHasNOC() {
        return hasNOC;
    }

    public void setHasNOC(boolean hasNOC) {
        this.hasNOC = hasNOC;
    }

    public String getNocPurpose() {
        return nocPurpose;
    }

    public void setNocPurpose(String nocPurpose) {
        this.nocPurpose = nocPurpose;
    }

    public int getNumOfNOC() {
        return numOfNOC;
    }

    public void setNumOfNOC(int numOfNOC) {
        this.numOfNOC = numOfNOC;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }
}
