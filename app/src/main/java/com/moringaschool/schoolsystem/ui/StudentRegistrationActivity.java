package com.moringaschool.schoolsystem.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.moringaschool.schoolsystem.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentRegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.radio_sex) TextView mSex;
    @BindView(R.id.radio_studentType) TextView mStudentType;
    @BindView(R.id.radio_studentClass) TextView mStudentClass;
    @BindView(R.id.edit_name) EditText mStudentName;
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
    @BindView(R.id.radio_class1) RadioButton mRadioClass1;
    @BindView(R.id.radio_class2) RadioButton mRadioClass2;
    @BindView(R.id.radio_class3) RadioButton mRadioClass3;
    @BindView(R.id.radio_class4) RadioButton mRadioClass4;
    @BindView(R.id.radio_class5) RadioButton mRadioClass5;
    @BindView(R.id.radio_class6) RadioButton mRadioClass6;
    @BindView(R.id.radio_class7) RadioButton mRadioClass7;
    @BindView(R.id.radio_class8) RadioButton mRadioClass8;
    @BindView(R.id.radio_classPre1) RadioButton mRadioClassPre1;
    @BindView(R.id.radio_classPre2) RadioButton mRadioClassPre2;
    @BindView(R.id.submit_studentRegistration) Button mSubmit;

    private FirebaseAuth mAuth1;
    private FirebaseAuth mAuth2;

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

    public String getSex () {
        String sex;
        if (mRadioFemale.isChecked()) {
            sex = "Female";
        }
        else if (mRadioMale.isChecked()) {
            sex =  "Male";
        }
        else {
            sex = "No";
        }
        return sex;
    }

    public String getStudentType () {
        String type;
        if (mRadioDay.isChecked()) {
            type = "Day";
        }
        else if (mRadioBoarder.isChecked()) {
            type =  "Boarder";
        }
        else {
            type = "No";
        }
        return type;
    }

    public String getStudentClass () {
        String studentClass;
        if (mRadioClass1.isChecked()) {
            studentClass = "Class 1";
        }
        else if (mRadioClass2.isChecked()) {
            studentClass =  "Class 2";
        }
        else if (mRadioClass3.isChecked()) {
            studentClass =  "Class 3";
        }
        else if (mRadioClass4.isChecked()) {
            studentClass =  "Class 4";
        }
        else if (mRadioClass5.isChecked()) {
            studentClass =  "Class 5";
        }
        else if (mRadioClass6.isChecked()) {
            studentClass =  "Class 6";
        }
        else if (mRadioClass7.isChecked()) {
            studentClass =  "Class 7";
        }
        else if (mRadioClass8.isChecked()) {
            studentClass =  "Class 8";
        }
        else if (mRadioClassPre1.isChecked()) {
            studentClass =  "Pre-Primary1";
        }
        else if (mRadioClassPre2.isChecked()) {
            studentClass =  "Pre-Primary2";
        }
        else {
            studentClass = "No";
        }
        return studentClass;
    }

    public boolean validInputs (String name, String email, String location, String parentName, String parentPhone1, String sex, String type, String studentClass, String adm) {
        if (name == null ||name.trim().isEmpty()) {
            mStudentName.setError("This field is required");
            return false;
        }
        else if(email == null || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mStudentEmail.setError("This field is required");
            return false;
        }
        else if(location == null || location.trim().isEmpty()) {
            mStudentLocation.setError("This field is required");
            return false;
        }
        else if(parentName == null || parentName.trim().isEmpty()) {
            mParentName.setError("This field is required");
            return false;
        }
        else if(parentPhone1 == null || parentPhone1.trim().isEmpty()) {
            mParentPhone1.setError("This field is required");
            return false;
        }
        else if(adm == null || adm.trim().isEmpty()) {
            mStudentAdmission.setError("This field is required");
            return false;
        }
        else if(sex.equals("No")) {
            mSex.setError("This field is required");
            return false;
        }
        else if(type.equals("No")) {
            mStudentType.setError("This field is required");
            return false;
        }
        else if(studentClass.equals("No")) {
            mStudentClass.setError("This field is required");
            return false;
        }
        else{
            return true;
        }
    }

    public void registerStudent () {
        String name =  mStudentName.getText().toString();
        String email =  mStudentEmail.getText().toString();
        String studentAdm =  mStudentAdmission.getText().toString();
        String parentName = mParentName.getText().toString();
        String location =  mStudentLocation.getText().toString();
        String parentPhone1 =  mParentPhone1.getText().toString();
        String parentPhone2 =  mParentPhone2.getText().toString();
        String sex =  getSex();
        String type = getStudentType();
        String studentClass = getStudentClass();

        boolean isValidForm =  validInputs(name, email, location, parentName, parentPhone1, sex, type, studentClass, studentAdm);

        if (!isValidForm){
            validInputs(name, email, location, parentName, parentPhone1, sex, type, studentClass, studentAdm);
        }
        else {
            Map<String, Object> student = new HashMap<String, Object>();
            student.put("studentName", name);
            student.put("studentEmail", email);
            student.put("studentAdmissionNo", studentAdm);
            student.put("studentLocation", location);
            student.put("studentSex", sex);
            student.put("studentType", type);
            student.put("studentClass", studentClass);
            student.put("parentName", parentName);
            student.put("parentPhone1", parentPhone1);
            student.put("parentPhone2", parentPhone1);


        }
    }
}