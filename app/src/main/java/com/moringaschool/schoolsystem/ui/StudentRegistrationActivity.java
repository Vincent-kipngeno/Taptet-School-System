package com.moringaschool.schoolsystem.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.moringaschool.schoolsystem.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentRegistrationActivity extends AppCompatActivity {
    @BindView(R.id.edit_name) EditText mStudentName;
    @BindView(R.id.edit_class) EditText mStudentClass;
    @BindView(R.id.edit_admissionNo) EditText mStudentAdmission;
    @BindView(R.id.edit_email) EditText mStudentEmail;
    @BindView(R.id.edit_location) EditText mStudentLocation;
    @BindView(R.id.edit_parentName) EditText mParentName;
    @BindView(R.id.edit_phone1) EditText mParentPhone1;
    @BindView(R.id.edit_phone2) EditText mParentPhone2;
    @BindView(R.id.radio_female) RadioButton mRadioFemale;
    @BindView(R.id.radio_male) RadioButton mRadioMale;
    @BindView(R.id.radio_day) RadioButton mRadioDay;
    @BindView(R.id.radio_border) RadioButton mRadioBoarder;
    @BindView(R.id.radio_sex) RadioGroup mRadioSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        ButterKnife.bind(this);

    }
}