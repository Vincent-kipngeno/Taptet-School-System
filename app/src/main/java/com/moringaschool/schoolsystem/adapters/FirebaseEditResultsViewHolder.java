package com.moringaschool.schoolsystem.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.schoolsystem.R;
import com.moringaschool.schoolsystem.models.ExamResult;
import com.moringaschool.schoolsystem.models.Student;


public class FirebaseEditResultsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    View mView;
    EditText studentNameText, engEdit, compEdit, engTotEdit, kisEdit, insEdit, kisTotEdit, matEdit, sciEdit, sstEdit, creEdit, sreTotEdit, total;
    Button save, edit;
    Context mContext;

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
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

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
            //examId =
        }
        else if (v == edit){

        }

    }
}
