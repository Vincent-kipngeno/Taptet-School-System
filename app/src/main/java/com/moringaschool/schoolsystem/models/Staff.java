package com.moringaschool.schoolsystem.models;

import com.google.firebase.database.Exclude;

import java.util.Objects;

public class Staff {
    public String name, email, staffNumber, location, sex, category, role, idNumber, phone, tscNUmber, adminUser, type;

    @Exclude
    public String key;

    public Staff( ) {

    }

    public Staff(String name, String email, String staffNumber, String location, String sex, String category, String role, String idNumber, String phone, String tscNUmber, String adminUser, String type) {
        this.name = name;
        this.email = email;
        this.staffNumber = staffNumber;
        this.location = location;
        this.sex = sex;
        this.category = category;
        this.role = role;
        this.idNumber = idNumber;
        this.phone = phone;
        this.tscNUmber = tscNUmber;
        this.adminUser = adminUser;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTscNUmber() {
        return tscNUmber;
    }

    public void setTscNUmber(String tscNUmber) {
        this.tscNUmber = tscNUmber;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return Objects.equals(name, staff.name) &&
                Objects.equals(email, staff.email) &&
                Objects.equals(staffNumber, staff.staffNumber) &&
                Objects.equals(location, staff.location) &&
                Objects.equals(sex, staff.sex) &&
                Objects.equals(category, staff.category) &&
                Objects.equals(role, staff.role) &&
                Objects.equals(idNumber, staff.idNumber) &&
                Objects.equals(phone, staff.phone) &&
                Objects.equals(tscNUmber, staff.tscNUmber) &&
                Objects.equals(adminUser, staff.adminUser) &&
                Objects.equals(type, staff.type) &&
                Objects.equals(key, staff.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, staffNumber, location, sex, category, role, idNumber, phone, tscNUmber, adminUser, type, key);
    }
}
