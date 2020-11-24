package com.moringaschool.schoolsystem.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.moringaschool.schoolsystem.R;

import butterknife.BindView;

public class AcademicCalendarDetailsActivity extends AppCompatActivity {
    @BindView(R.id.editTerm1_startDate) TextView mStartDate1;
    @BindView(R.id.editTerm1_endDate) TextView mEndDate1;
    @BindView(R.id.feeStructure_gridTerm1) RecyclerView mFeeStructureGrid1;

    private DatabaseReference YearDetailsRef;
    private FirebaseAuth mAuth;
    private String currentUserID="";
    private String academicYearId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_calendar_details);
        academicYearId = getIntent().getStringExtra("academicYearId");
    }
}