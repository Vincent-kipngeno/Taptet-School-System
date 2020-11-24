package com.moringaschool.schoolsystem.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.schoolsystem.R;
import com.moringaschool.schoolsystem.models.AcademicYear;
import com.moringaschool.schoolsystem.models.FeeStructure;
import com.moringaschool.schoolsystem.ui.calendar.CalendarFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AcademicCalendarDetailsActivity extends AppCompatActivity {
    @BindView(R.id.editTerm1_startDate) TextView mStartDate1;
    @BindView(R.id.editTerm1_endDate) TextView mEndDate1;
    @BindView(R.id.editTerm2_startDate) TextView mStartDate2;
    @BindView(R.id.editTerm2_endDate) TextView mEndDate2;
    @BindView(R.id.editTerm3_startDate) TextView mStartDate3;
    @BindView(R.id.editTerm3_endDate) TextView mEndDate3;
    @BindView(R.id.school_fee_structure) RecyclerView mFeeStructureGrid;

    private DatabaseReference YearDetailsRef, YearFeeStructureRef;
    private FirebaseAuth mAuth;
    private String currentUserID="";
    private String academicYearId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_calendar_details);

        Intent intent = new Intent();
        academicYearId = intent.getStringExtra("academicYearId");

        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(intent.getStringExtra("academicYearName") + " AY");

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        YearDetailsRef = FirebaseDatabase.getInstance().getReference().child("YearDetails").child(academicYearId);
        YearFeeStructureRef = YearDetailsRef.child("FeeStructure");

        mFeeStructureGrid.setLayoutManager(new LinearLayoutManager(this));

        fillTermDates();
    }

    public void fillTermDates() {
        YearDetailsRef.child("TermDates").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    mStartDate1.setText(dataSnapshot.child("Term1").child("startDate").getValue().toString());
                    mStartDate2.setText(dataSnapshot.child("Term2").child("startDate").getValue().toString());
                    mStartDate3.setText(dataSnapshot.child("Term3").child("startDate").getValue().toString());
                    mEndDate1.setText(dataSnapshot.child("Term1").child("endDate").getValue().toString());
                    mEndDate2.setText(dataSnapshot.child("Term2").child("endDate").getValue().toString());
                    mEndDate3.setText(dataSnapshot.child("Term3").child("endDate").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void fillFeesStructureRecyclerView() {
        FirebaseRecyclerOptions<FeeStructure> options =
                new FirebaseRecyclerOptions.Builder<FeeStructure>()
                        .setQuery(YearFeeStructureRef, FeeStructure.class)
                        .build();


        FirebaseRecyclerAdapter<FeeStructure, AcademicCalendarDetailsActivity.FeeStructureViewHolder> adapter =
                new FirebaseRecyclerAdapter<FeeStructure, AcademicCalendarDetailsActivity.FeeStructureViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final AcademicCalendarDetailsActivity.FeeStructureViewHolder holder, int position, @NonNull FeeStructure model)
                    {
                        final String academicYearId = getRef(position).getKey();

                        holder.className.setText(model.getClassName());
                        holder.term1Fee.setText(model.getTerm1Fee());
                        holder.term2Fee.setText(model.getTerm2Fee());
                        holder.term3Fee.setText(model.getTerm3Fee());

                    }

                    @NonNull
                    @Override
                    public AcademicCalendarDetailsActivity.FeeStructureViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
                    {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fee_structure_custom_layout, viewGroup, false);
                        return new AcademicCalendarDetailsActivity.FeeStructureViewHolder(view);
                    }
                };

        mFeeStructureGrid.setAdapter(adapter);
        adapter.startListening();
    }

    public static class  FeeStructureViewHolder extends RecyclerView.ViewHolder
    {
        TextView className, term1Fee, term2Fee, term3Fee;


        public FeeStructureViewHolder(@NonNull View itemView)
        {
            super(itemView);

            className = itemView.findViewById(R.id.edit_classTable);
            term1Fee = itemView.findViewById(R.id.edit_term1Table);
            term2Fee = itemView.findViewById(R.id.edit_term2Table);
            term3Fee = itemView.findViewById(R.id.edit_term3Table);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
        {
            fillTermDates();
            fillFeesStructureRecyclerView();
        }
    }
}