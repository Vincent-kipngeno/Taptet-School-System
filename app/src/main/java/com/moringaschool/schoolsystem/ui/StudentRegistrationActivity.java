package com.moringaschool.schoolsystem.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.moringaschool.schoolsystem.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentRegistrationActivity extends AppCompatActivity implements View.OnClickListener {
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
    @BindView(R.id.radio_class1) RadioGroup mRadioClass1;
    @BindView(R.id.radio_class2) RadioGroup mRadioClass2;
    @BindView(R.id.radio_class3) RadioGroup mRadioClass3;
    @BindView(R.id.radio_class4) RadioGroup mRadioClass4;
    @BindView(R.id.radio_class5) RadioGroup mRadioClass5;
    @BindView(R.id.radio_class6) RadioGroup mRadioClass6;
    @BindView(R.id.radio_class7) RadioGroup mRadioClass7;
    @BindView(R.id.radio_class8) RadioGroup mRadioClass8;
    @BindView(R.id.radio_classPre1) RadioGroup mRadioClassPre1;
    @BindView(R.id.radio_classPre2) RadioGroup mRadioClassPre2;
    @BindView(R.id.submit_studentRegistration) Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        ButterKnife.bind(this);

        mSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

    }
}