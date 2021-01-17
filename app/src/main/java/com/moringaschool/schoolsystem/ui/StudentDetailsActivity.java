package com.moringaschool.schoolsystem.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.schoolsystem.R;
import com.moringaschool.schoolsystem.models.Student;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.studentName) TextView mStudentName;
    @BindView(R.id.studentEmail) TextView mStudentEmail;
    @BindView(R.id.studentAdm) TextView mStudentAdm;
    @BindView(R.id.studentSex) TextView mStudentSex;
    @BindView(R.id.studentGrade) TextView mStudentGrade;
    @BindView(R.id.studentCategory) TextView mStudentCategory;
    @BindView(R.id.studentLocation) TextView mStudentLocation;
    @BindView(R.id.parentName) TextView mParentName;
    @BindView(R.id.parentPhone1) TextView mParentPhone1;
    @BindView(R.id.parentPhone2) TextView mParentPhone2;
    @BindView(R.id.feesButton) Button mFeesButton;
    @BindView(R.id.examsButton) Button mExamsButton;
    @BindView(R.id.attendaceButton) Button mAttendanceButton;

    private DatabaseReference UsersRef;
    private FirebaseAuth mAuth;
    private String currentUserID="";
    String studentId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        Intent intent = new Intent();
        studentId = intent.getStringExtra("visit_user_id");
        fetchStudentInfo();
    }

    @Override
    public void onClick(View view) {

    }

    public void fetchStudentInfo() {
        Intent intent = new Intent();
        studentId = intent.getStringExtra("visit_user_id");
        UsersRef.child(studentId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String studentName = dataSnapshot.child("name").getValue().toString();
                    String studentEmail = dataSnapshot.child("email").getValue().toString();
                    String studentClass = dataSnapshot.child("grade").getValue().toString();
                    String studentAdm = dataSnapshot.child("admissionNo").getValue().toString();
                    String studentSex = dataSnapshot.child("sex").getValue().toString();
                    String studentCategory = dataSnapshot.child("category").getValue().toString();
                    String studentLocation = dataSnapshot.child("location").getValue().toString();
                    String parentName = dataSnapshot.child("parentName").getValue().toString();
                    String parentPhone1 = dataSnapshot.child("phone1").getValue().toString();
                    String parentPhone2 = dataSnapshot.child("phone2").getValue().toString();

                    Student student = dataSnapshot.getValue(Student.class);

                    assert student != null;
                    mStudentName.setText(student.getName());
                    mStudentEmail.setText(student.getEmail());
                    mStudentGrade.setText(student.getGrade());
                    mStudentAdm.setText(student.getAdmissionNo());
                    mStudentSex.setText(student.getSex());
                    mStudentCategory.setText(student.getCategory());
                    mStudentLocation.setText(student.getLocation());
                    mParentName.setText(student.getParentName());
                    mParentPhone1.setText(student.getPhone1());
                    if (!student.getPhone2().trim().isEmpty()) {
                        mParentPhone2.setText(student.getPhone2());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}