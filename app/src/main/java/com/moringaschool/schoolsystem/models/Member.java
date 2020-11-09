package com.moringaschool.schoolsystem.models;

public class Member {
    private boolean isMember;

    public Member() {

    }

    public Member(boolean isMember) {
        this.isMember = isMember;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }
}
