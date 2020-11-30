package com.moringaschool.schoolsystem.models;

import com.google.firebase.database.Exclude;

import org.parceler.Parcel;

@Parcel
public class Student {
    public String name, email, admissionNo, location, sex, category, grade, parentName, phone1, phone2, adminUser, type;

    @Exclude
    public String key;

    public Student()
    {

    }

    public Student(String name, String email, String admissionNo, String location, String sex, String category, String grade, String parentName, String phone1, String phone2, String adminUser, String type) {
        this.name = name;
        this.email = email;
        this.admissionNo = admissionNo;
        this.location = location;
        this.sex = sex;
        this.category = category;
        this.grade = grade;
        this.parentName = parentName;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.adminUser = adminUser;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdmissionNo() {
        return admissionNo;
    }

    public void setAdmissionNo(String admissionNo) {
        this.admissionNo = admissionNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
