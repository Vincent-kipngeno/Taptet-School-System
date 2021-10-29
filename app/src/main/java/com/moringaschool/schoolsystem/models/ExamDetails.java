package com.moringaschool.schoolsystem.models;

import java.util.Objects;

public class ExamDetails {
    public String examName, examClass, examTypeId;
    public long dateDone;
    public int yearPublished;

    public ExamDetails( ) {

    }

    public ExamDetails(String examName, String examClass, String examTypeId, long dateDone, int yearPublished) {
        this.examName = examName;
        this.examClass = examClass;
        this.examTypeId = examTypeId;
        this.dateDone = dateDone;
        this.yearPublished = yearPublished;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamClass() {
        return examClass;
    }

    public void setExamClass(String examClass) {
        this.examClass = examClass;
    }

    public String getExamTypeId() {
        return examTypeId;
    }

    public void setExamTypeId(String examTypeId) {
        this.examTypeId = examTypeId;
    }

    public long getDateDone() {
        return dateDone;
    }

    public void setDateDone(long dateDone) {
        this.dateDone = dateDone;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExamDetails)) return false;
        ExamDetails that = (ExamDetails) o;
        return yearPublished == that.yearPublished &&
                examName.equals(that.examName) &&
                examClass.equals(that.examClass) &&
                examTypeId.equals(that.examTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(examName, examClass, examTypeId, yearPublished);
    }
}
