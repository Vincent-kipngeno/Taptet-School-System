package com.moringaschool.schoolsystem.models;

public class ExamDetails {
    private String examName, examClass, examTypeId;
    private long dateDone;
    private int yearPublished;

    public ExamDetails(String examName, String examClass, String examTypeId, long dateDone, int yearPublished) {
        this.examName = examName;
        this.examClass = examClass;
        this.examTypeId = examTypeId;
        this.dateDone = dateDone;
        this.yearPublished = yearPublished;
    }
}
