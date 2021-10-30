package com.moringaschool.schoolsystem.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.schoolsystem.R;
import com.moringaschool.schoolsystem.models.ExamDetails;
import com.moringaschool.schoolsystem.models.ExamResult;
import com.moringaschool.schoolsystem.models.Student;


public class FirebaseEditResultsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    View mView;
    EditText studentNameText, engEdit, compEdit, engTotEdit, kisEdit, insEdit, kisTotEdit, matEdit, sciEdit, sstEdit, creEdit, sreTotEdit, total;
    Button save, edit;
    Context mContext;
    ExamResult result;

    DatabaseReference DatabaseRef = FirebaseDatabase.getInstance().getReference();

    DatabaseReference ExamsRef = DatabaseRef.child("Exams");
    DatabaseReference OverallExamsRef = ExamsRef.child("OverallExams");
    DatabaseReference StudentsExamsRef = ExamsRef.child("StudentsExams");
    DatabaseReference ClassExamsRef = ExamsRef.child("ClassExams");
    DatabaseReference YearDetailsRef = DatabaseRef.child("Years");
    DatabaseReference UsersRef = DatabaseRef.child("Users");
    DatabaseReference currentAcademicYearRef = YearDetailsRef.child("CurrentAcademicYear");

    public FirebaseEditResultsViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        studentNameText = itemView.findViewById(R.id.resultStudentName);
        engEdit = itemView.findViewById(R.id.eng_result_edit);
        compEdit = itemView.findViewById(R.id.comp_result_edit);
        engTotEdit = itemView.findViewById(R.id.eng_tot_result_edit);
        kisEdit = itemView.findViewById(R.id.kis_result_edit);
        insEdit = itemView.findViewById(R.id.ins_result_edit);
        kisTotEdit = itemView.findViewById(R.id.kis_tot_result_edit);
        matEdit = itemView.findViewById(R.id.math_result_edit);
        sciEdit = itemView.findViewById(R.id.sci_result_edit);
        sstEdit = itemView.findViewById(R.id.sst_result_edit);
        creEdit = itemView.findViewById(R.id.cre_result_edit);
        sreTotEdit = itemView.findViewById(R.id.sreTot_result_edit);
        total = itemView.findViewById(R.id.total_result_edit);
        save = itemView.findViewById(R.id.save_result_button);
        edit = itemView.findViewById(R.id.edit_result_button);
        mContext = itemView.getContext();


        save.setOnClickListener(this);
        edit.setOnClickListener(this);
    }

    public void bindRestaurant(ExamResult result, Boolean isEdit) {
        this.result = result;

        if (isEdit){
            save.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);

            UsersRef.child(result.getStudentId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.exists())
                    {

                        Student student = dataSnapshot.getValue(Student.class);

                        assert student != null;

                        studentNameText.setText(student.getName());
                        engEdit.setText(result.getEng());
                        compEdit.setText(result.getComp());
                        engTotEdit.setText(result.pairSubjectsTotal(result.getEng(), result.getComp()));
                        kisEdit.setText(result.getKis());
                        insEdit.setText(result.getIns());
                        kisTotEdit.setText(result.pairSubjectsTotal(result.getKis(), result.getIns()));
                        matEdit.setText(result.getMat());
                        sciEdit.setText(result.getSci());
                        sstEdit.setText(result.getSst());
                        creEdit.setText(result.getCre());
                        sreTotEdit.setText(result.pairSubjectsTotal(result.getSst(), result.getCre()));
                        total.setText(result.getTotal());

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else {
            save.setVisibility(View.VISIBLE);
            edit.setVisibility(View.GONE);

            UsersRef.child(result.getStudentId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.exists())
                    {

                        Student student = dataSnapshot.getValue(Student.class);

                        assert student != null;

                        studentNameText.setText(student.getName());

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }

    @Override
    public void onClick(View v) {
        if (v == save){
            String eng = engEdit.getText().toString();
            String comp = compEdit.getText().toString();
            String kis = kisEdit.getText().toString();
            String ins = insEdit.getText().toString();
            String mat = matEdit.getText().toString();
            String sci = matEdit.getText().toString();
            String sst = sstEdit.getText().toString();
            String cre = creEdit.getText().toString();

            boolean isValid = validateInputs(eng, comp, kis, ins, mat, sci, sst, cre);

            if (!isValid){
                validateInputs(eng, comp, kis, ins, mat, sci, sst, cre);
            }
            else{

                currentAcademicYearRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists())
                        {
                            String currentAcademicYearId = dataSnapshot.child("AcademicYearId").getValue().toString();
                            String currentAcademicTerm = dataSnapshot.child("Term").getValue().toString();

                            DatabaseReference ExamsRef = OverallExamsRef.child(currentAcademicYearId).child(currentAcademicTerm).child("ExamResults");

                            DatabaseReference examRef = ExamsRef.push();
                            String examId = examRef.getKey();

                            ExamResult newResult = new ExamResult(result.getStudentClass(), result.getStudentId(), examId, result.getExamTypeId(), result.getDateDone(), Integer.parseInt(eng),  Integer.parseInt(comp), Integer.parseInt(kis), Integer.parseInt(ins), Integer.parseInt(mat), Integer.parseInt(sci), Integer.parseInt(sst), Integer.parseInt(cre));

                            ExamsRef.child(examId).setValue(newResult).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(mContext, "Exam result added Successfully...", Toast.LENGTH_SHORT).show();

                                        save.setVisibility(View.GONE);
                                        edit.setVisibility(View.VISIBLE);
                                    }
                                    else
                                    {
                                        Toast.makeText(mContext, "Error adding exam result. Retry .", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                        else {
                            Toast.makeText(mContext, "Kindly Create and Start academic year First", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        }
        else if (v == edit){

        }

    }

    public boolean validateInputs(String eng, String comp, String kis, String ins, String mat, String sci, String sst, String cre){

        if (eng==null || eng.trim().isEmpty()){
            engEdit.setError("Required Field");
            return false;
        }
        else if (comp==null || comp.trim().isEmpty()){
            compEdit.setError("Required Field");
            return false;
        }
        else if (kis==null || kis.trim().isEmpty()){
            kisEdit.setError("Required Field");
            return false;
        }
        else if (ins==null || ins.trim().isEmpty()){
            insEdit.setError("Required Field");
            return false;
        }
        else if (mat==null || mat.trim().isEmpty()){
            matEdit.setError("Required Field");
            return false;
        }
        else if (sci==null || sci.trim().isEmpty()){
            sciEdit.setError("Required Field");
            return false;
        }
        else if (sst==null || sst.trim().isEmpty()){
            sstEdit.setError("Required Field");
            return false;
        }
        else if (cre==null || cre.trim().isEmpty()){
            creEdit.setError("Required Field");
            return false;
        }
        else{
            return true;
        }

    }
}
