package com.moringaschool.schoolsystem.models;

public class FeeStructure {
    private String className;
    private String term1Fee;
    private String term2Fee;
    private String term3Fee;

    public FeeStructure() {

    }

    public FeeStructure(String className, String term1Fee, String term2Fee, String term3Fee) {
        this.className = className;
        this.term1Fee = term1Fee;
        this.term2Fee = term2Fee;
        this.term3Fee = term3Fee;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTerm1Fee() {
        return term1Fee;
    }

    public void setTerm1Fee(String term1Fee) {
        this.term1Fee = term1Fee;
    }

    public String getTerm2Fee() {
        return term2Fee;
    }

    public void setTerm2Fee(String term2Fee) {
        this.term2Fee = term2Fee;
    }

    public String getTerm3Fee() {
        return term3Fee;
    }

    public void setTerm3Fee(String term3Fee) {
        this.term3Fee = term3Fee;
    }

}
