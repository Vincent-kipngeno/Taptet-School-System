package com.moringaschool.schoolsystem.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.moringaschool.schoolsystem.R;

import butterknife.BindView;

public class StudentDetailsActivity extends AppCompatActivity {

    @BindView(R.id.studentName) TextView studentName;
    @BindView(R.id.studentEmail) TextView studentEmail;
    @BindView(R.id.studentAdm) TextView studentAdm;
    @BindView(R.id.studentSex) TextView studentSex;
    @BindView(R.id.studentGrade) TextView studentGrade;
    @BindView(R.id.studentCategory) TextView studentCategory;
    @BindView(R.id.studentLocation) TextView studentLocation;
    @BindView(R.id.parentName) TextView parentName;
    @BindView(R.id.parentPhone1) TextView parentPhone1;
    @BindView(R.id.parentPhone2) TextView parentPhone2;
    @BindView(R.id.feesButton) Button feesButton;
    @BindView(R.id.examsButton) Button examsButton;
    @BindView(R.id.attendaceButton) Button attendaceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        Intent intent = new Intent();
        String studentId = intent.getStringExtra("visit_user_id");
    }
}