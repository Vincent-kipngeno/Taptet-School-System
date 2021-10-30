package com.moringaschool.schoolsystem.models;

import java.util.Objects;

public class StudentExamResult {
    public String studentClass, studentId, examId, examTypeId;
    public long dateDone;
    public int eng, comp, kis, ins, mat, sci, sst, cre, total;

    public StudentExamResult( ){

    }

    public StudentExamResult(String studentClass, String studentId, String examId, String examTypeId, long dateDone, int eng, int comp, int kis, int ins, int mat, int sci, int sst, int cre) {
        this.studentClass = studentClass;
        this.studentId = studentId;
        this.examId = examId;
        this.examTypeId = examTypeId;
        this.dateDone = dateDone;
        this.eng = eng;
        this.comp = comp;
        this.kis = kis;
        this.ins = ins;
        this.mat = mat;
        this.sci = sci;
        this.sst = sst;
        this.cre = cre;
        this.total = pairSubjectsTotal(eng, comp) + pairSubjectsTotal(kis, ins) + mat + sci + pairSubjectsTotal(sst, cre);
    }

    public int pairSubjectsTotal(int gram, int comp){
        float multiplier = (float)100 / (float)90;
        return Math.round((gram+comp)*multiplier);
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
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

    public int getEng() {
        return eng;
    }

    public void setEng(int eng) {
        this.eng = eng;
    }

    public int getComp() {
        return comp;
    }

    public void setComp(int comp) {
        this.comp = comp;
    }

    public int getKis() {
        return kis;
    }

    public void setKis(int kis) {
        this.kis = kis;
    }

    public int getIns() {
        return ins;
    }

    public void setIns(int ins) {
        this.ins = ins;
    }

    public int getMat() {
        return mat;
    }

    public void setMat(int mat) {
        this.mat = mat;
    }

    public int getSci() {
        return sci;
    }

    public void setSci(int sci) {
        this.sci = sci;
    }

    public int getSst() {
        return sst;
    }

    public void setSst(int sst) {
        this.sst = sst;
    }

    public int getCre() {
        return cre;
    }

    public void setCre(int cre) {
        this.cre = cre;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentExamResult)) return false;
        StudentExamResult that = (StudentExamResult) o;
        return eng == that.eng &&
                comp == that.comp &&
                kis == that.kis &&
                ins == that.ins &&
                mat == that.mat &&
                sci == that.sci &&
                sst == that.sst &&
                cre == that.cre &&
                studentClass.equals(that.studentClass) &&
                studentId.equals(that.studentId) &&
                examId.equals(that.examId) &&
                examTypeId.equals(that.examTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentClass, studentId, examId, examTypeId, eng, comp, kis, ins, mat, sci, sst, cre);
    }
}
