package com.moringaschool.schoolsystem.models;

public class AcademicYear {
    private String academicYear;
    private String startDate;
    private String endDate;


    public AcademicYear(String academicYear, String startDate, String endDate) {
        this.academicYear = academicYear;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
