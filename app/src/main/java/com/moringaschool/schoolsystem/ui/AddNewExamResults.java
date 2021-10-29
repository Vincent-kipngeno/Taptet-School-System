package com.moringaschool.schoolsystem.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.schoolsystem.R;
import com.moringaschool.schoolsystem.databinding.ActivityAddNewExamResultsBinding;
import com.moringaschool.schoolsystem.models.ExamDetails;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.currentTimeMillis;

public class AddNewExamResults extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private ActivityAddNewExamResultsBinding binding;
    private final String defaultSelectedClass = "Select Class";
    private static String examTypeId, examClass;
    private static long dateDone;

    private DatabaseReference ExamsRef, DatabaseRef, YearDetailsRef, UsersRef, currentAcademicYearRef, OverallExamsRef, StudentsExamsRef, ClassExamsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddNewExamResultsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        DatabaseRef = FirebaseDatabase.getInstance().getReference();

        ExamsRef = DatabaseRef.child("Exams");
        OverallExamsRef = ExamsRef.child("OverallExams");
        StudentsExamsRef = ExamsRef.child("StudentsExams");
        ClassExamsRef = ExamsRef.child("ClassExams");
        YearDetailsRef = DatabaseRef.child("Years");
        UsersRef = DatabaseRef.child("Users");
        currentAcademicYearRef = YearDetailsRef.child("CurrentAcademicYear");

        fillClassSpinner();

    }

    @Override
    public void onClick(View v) {

        if (v == binding.saveExamButton){
            addNewExamType();
        }
        else if (v == binding.editExamButton){
            editExamType();
        }

    }

    public boolean validateInputs(String examClass, String examName, String yearPublished){

        if (examClass==null || examClass.trim().isEmpty() || examClass.equals(defaultSelectedClass)){
            TextView mySpinner = (TextView) binding.classSpinner.getSelectedView();
            mySpinner.setError("Class field cannot be empty");
            Toast.makeText(AddNewExamResults.this, "Error : Class field cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (examName==null || examName.trim().isEmpty()){
            binding.examNameEdit.setError("Exam name cannot be empty");
            return false;
        }
        else if (yearPublished==null || yearPublished.trim().isEmpty()){
            binding.examYearEdit.setError("Year Published cannot be empty");
            return false;
        }
        else{
            return true;
        }

    }



    public void addNewExamType(){
        examClass = (String) binding.classSpinner.getSelectedItem();
        String examName = binding.examNameEdit.getText().toString();
        String yearPublished = binding.examYearEdit.getText().toString();
        dateDone = currentTimeMillis();

        boolean isValidInputs = validateInputs(examClass, examName, yearPublished);

        if (!isValidInputs) {
            validateInputs(examClass, examName, yearPublished);
        }
        else {

            AlertDialog.Builder builder = new AlertDialog.Builder(AddNewExamResults.this);

            builder.setMessage("You won't be able to change Class field after this. Press Ok if you would like to proceed.");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    currentAcademicYearRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {
                            if (dataSnapshot.exists())
                            {
                                String currentAcademicYearId = dataSnapshot.child("AcademicYearId").getValue().toString();
                                String currentAcademicTerm = dataSnapshot.child("Term").getValue().toString();

                                DatabaseReference ExamsTypeRef = OverallExamsRef.child(currentAcademicYearId).child(currentAcademicTerm).child("ExamTypes");

                                DatabaseReference examTypeRef = ExamsTypeRef.push();
                                examTypeId = examTypeRef.getKey();

                                ExamDetails examTypeDetails = new ExamDetails(examName, examClass, examTypeId, dateDone, Integer.parseInt(yearPublished));

                                ExamsTypeRef.child(examTypeId).setValue(examTypeDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(AddNewExamResults.this, "Exam Type added Successfully...", Toast.LENGTH_SHORT).show();

                                            binding.classSpinner.setVisibility(View.GONE);
                                            binding.classText.setVisibility(View.VISIBLE);
                                            binding.saveExamButton.setVisibility(View.GONE);
                                            binding.editExamButton.setVisibility(View.VISIBLE);
                                            fillExamsRecyclerView();
                                        }
                                        else
                                        {
                                            Toast.makeText(AddNewExamResults.this, "Error adding exam type. Retry .", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                            else {
                                Toast.makeText(AddNewExamResults.this, "Kindly Create and Start academic year First", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }

    public void editExamType(){
        String examName = binding.examNameEdit.getText().toString();
        String yearPublished = binding.examYearEdit.getText().toString();

        boolean isValidInputs = validateInputs(examClass, examName, yearPublished);

        if (!isValidInputs) {
            validateInputs(examClass, examName, yearPublished);
        }
        else {

            currentAcademicYearRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.exists())
                    {
                        String currentAcademicYearId = dataSnapshot.child("AcademicYearId").getValue().toString();
                        String currentAcademicTerm = dataSnapshot.child("Term").getValue().toString();

                        DatabaseReference ExamsTypeRef = OverallExamsRef.child(currentAcademicYearId).child(currentAcademicTerm).child("ExamTypes");

                        ExamDetails examTypeDetails = new ExamDetails(examName, examClass, examTypeId, dateDone, Integer.parseInt(yearPublished));

                        ExamsTypeRef.child(examTypeId).setValue(examTypeDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(AddNewExamResults.this, "Exam Type details updated Successfully...", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(AddNewExamResults.this, "Error updating exam type details. Retry .", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                    else {
                        Toast.makeText(AddNewExamResults.this, "Kindly Create and Start academic year First", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

    public void fillExamsRecyclerView() {

    }

    public void fillClassSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.class_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.classSpinner.setAdapter(adapter);
        int defaultSelected = adapter.getPosition(defaultSelectedClass);
        binding.classSpinner.setSelection(defaultSelected);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}