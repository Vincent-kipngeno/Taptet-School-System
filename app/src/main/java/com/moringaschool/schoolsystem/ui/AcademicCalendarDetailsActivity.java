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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.schoolsystem.R;
import com.moringaschool.schoolsystem.models.FeeStructure;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.moringaschool.schoolsystem.Constants.ALUMNI;
import static com.moringaschool.schoolsystem.Constants.Class_1;
import static com.moringaschool.schoolsystem.Constants.Class_2;
import static com.moringaschool.schoolsystem.Constants.Class_3;
import static com.moringaschool.schoolsystem.Constants.Class_4;
import static com.moringaschool.schoolsystem.Constants.Class_5;
import static com.moringaschool.schoolsystem.Constants.Class_6;
import static com.moringaschool.schoolsystem.Constants.Class_7;
import static com.moringaschool.schoolsystem.Constants.Class_8;
import static com.moringaschool.schoolsystem.Constants.PRE_PR1_1;
import static com.moringaschool.schoolsystem.Constants.PRE_PR1_2;
import static com.moringaschool.schoolsystem.Constants.TERM_1;
import static com.moringaschool.schoolsystem.Constants.TERM_2;
import static com.moringaschool.schoolsystem.Constants.TERM_3;

public class AcademicCalendarDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.editTerm1_startDate) TextView mStartDate1;
    @BindView(R.id.editTerm1_endDate) TextView mEndDate1;
    @BindView(R.id.editTerm2_startDate) TextView mStartDate2;
    @BindView(R.id.editTerm2_endDate) TextView mEndDate2;
    @BindView(R.id.editTerm3_startDate) TextView mStartDate3;
    @BindView(R.id.editTerm3_endDate) TextView mEndDate3;
    @BindView(R.id.button_start1) Button mStartTerm1;
    @BindView(R.id.button_end1) Button mEndTerm1;
    @BindView(R.id.button_start2) Button mStartTerm2;
    @BindView(R.id.button_end2) Button mEndTerm2;
    @BindView(R.id.button_start3) Button mStartTerm3;
    @BindView(R.id.button_end3) Button mEndTerm3;
    @BindView(R.id.school_fee_structure) RecyclerView mFeeStructureGrid;

    private DatabaseReference YearDetailsRef, YearFeeStructureRef, currentAcademicCalendarRef, NewAcademicYearRef, PreviousAcademicYearRef, DatabaseRef, ClassCurrentStudentsRef, AlumniRef, ClassRef;
    private FirebaseAuth mAuth;
    private String currentUserID="";
    private String CurrentAcademicYearId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_calendar_details);

        Intent intent = new Intent();
        CurrentAcademicYearId = intent.getStringExtra("academicYearId");

        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(intent.getStringExtra("academicYearName") + " AY");

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        DatabaseRef = FirebaseDatabase.getInstance().getReference();

        YearDetailsRef = DatabaseRef.child("Years");
        currentAcademicCalendarRef = DatabaseRef.child("CurrentAcademicYear");
        NewAcademicYearRef = DatabaseRef.child("NewAcademicYear");
        PreviousAcademicYearRef = DatabaseRef.child("PreviousAcademicYear");
        YearFeeStructureRef = YearDetailsRef.child("YearDetails").child(CurrentAcademicYearId).child("FeeStructure");
        ClassCurrentStudentsRef = DatabaseRef.child("ClassCurrentStudents");
        ClassRef = DatabaseRef.child("Classes");
        AlumniRef = DatabaseRef.child("Alumni");

        mFeeStructureGrid.setLayoutManager(new LinearLayoutManager(this));

        fillTermDates();

        mStartTerm1.setOnClickListener(this);
        mEndTerm1.setOnClickListener(this);
        mStartTerm2.setOnClickListener(this);
        mEndTerm2.setOnClickListener(this);
        mStartTerm3.setOnClickListener(this);
        mEndTerm3.setOnClickListener(this);
    }

    public void fillTermDates() {
        YearDetailsRef.child("YearDetails").child(CurrentAcademicYearId).child("TermDates").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    mStartDate1.setText(dataSnapshot.child(TERM_1).child("startDate").getValue().toString());
                    mStartDate2.setText(dataSnapshot.child(TERM_2).child("startDate").getValue().toString());
                    mStartDate3.setText(dataSnapshot.child(TERM_3).child("startDate").getValue().toString());
                    mEndDate1.setText(dataSnapshot.child(TERM_1).child("endDate").getValue().toString());
                    mEndDate2.setText(dataSnapshot.child(TERM_2).child("endDate").getValue().toString());
                    mEndDate3.setText(dataSnapshot.child(TERM_3).child("endDate").getValue().toString());
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

    @Override
    public void onClick(View view) {

        if (view == mStartTerm1) {
            PreviousAcademicYearRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.exists())
                    {
                        String previousAcademicYearId = dataSnapshot.child("AcademicYearId").getValue().toString();
                        String previousAcademicTerm = dataSnapshot.child("Term").getValue().toString();
                        String currentAcademicTerm = TERM_1;

                        transferCurrentStudentsToNextTerm(previousAcademicYearId, previousAcademicTerm, currentAcademicTerm);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        if (view == mEndTerm1) {

        }

        if (view == mStartTerm2) {

        }

        if (view == mEndTerm2) {

        }

        if (view == mStartTerm3) {

        }

        if (view == mEndTerm3) {

        }

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

    public void visibilityOfStartAndEndButtons() {

        YearDetailsRef.child("CurrentDates").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String yearId = dataSnapshot.child("yearId").getValue().toString();
                    String term = dataSnapshot.child("term").getValue().toString();

                    if ( yearId.equals(CurrentAcademicYearId)) {

                        //End Buttons visibility
                        if (term.equals(TERM_1)) {
                            mEndTerm1.setVisibility(View.VISIBLE);
                        }
                        if (term.equals(TERM_2))
                        {
                           mEndTerm2.setVisibility(View.VISIBLE);
                        }
                        if (term.equals(TERM_3))
                        {
                            mEndTerm3.setVisibility(View.VISIBLE);
                        }

                        //Start Buttons visibility
                        YearDetailsRef.child("PreviousDates").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot)
                            {
                                if (dataSnapshot.exists())
                                {
                                    String termStart = dataSnapshot.child("term").getValue().toString();

                                    if (termStart.equals(TERM_3)) {
                                        mStartTerm1.setVisibility(View.VISIBLE);
                                    }
                                    if (termStart.equals(TERM_1))
                                    {
                                        mStartTerm2.setVisibility(View.VISIBLE);
                                    }
                                    if (termStart.equals(TERM_2))
                                    {
                                        mStartTerm3.setVisibility(View.VISIBLE);
                                    }
                                }
                                else
                                {
                                    mStartTerm1.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

    public  void transferCurrentStudentsToNextTerm (String previousAcademicYearId, String previousAcademicTerm, String currentAcademicTerm) {

        DatabaseRef.child("CurrentStudents").child(previousAcademicYearId).child(previousAcademicTerm).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    DatabaseRef.child("CurrentStudents").child(CurrentAcademicYearId).child(currentAcademicTerm).setValue(dataSnapshot).addOnCompleteListener((OnCompleteListener<Void>) task -> {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(AcademicCalendarDetailsActivity.this, "Students transferred to the next term Successfully...", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(AcademicCalendarDetailsActivity.this, "Error transferring students.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public  void transferCurrentStudentsToNextClass (String previousAcademicYearId, String previousAcademicTerm, String currentAcademicTerm) {

        ClassRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot currentClass : dataSnapshot.getChildren()){

                        ClassCurrentStudentsRef.child(currentClass.getKey()).child(previousAcademicYearId).child(previousAcademicTerm).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot)
                            {
                                if (dataSnapshot.exists())
                                {
                                    if (getNextClass(currentClass.getKey()).equals(ALUMNI)) {

                                        ClassCurrentStudentsRef.child(currentClass.getKey()).child(CurrentAcademicYearId).child(currentAcademicTerm).setValue(dataSnapshot).addOnCompleteListener((OnCompleteListener<Void>) task -> {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(AcademicCalendarDetailsActivity.this, "Students transferred to the next class term Successfully...", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(AcademicCalendarDetailsActivity.this, "Error transferring students.", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        AlumniRef.child("YearAlumni").child(CurrentAcademicYearId).child(currentAcademicTerm).setValue(dataSnapshot).addOnCompleteListener((OnCompleteListener<Void>) task -> {
                                            if (task.isSuccessful())
                                            {
                                                AlumniRef.child("YearAlumni").child(CurrentAcademicYearId).child(currentAcademicTerm).addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot)
                                                    {
                                                        if (dataSnapshot.exists())
                                                        {
                                                            for (DataSnapshot alumni : dataSnapshot.getChildren()){
                                                                DatabaseRef.child("CurrentStudents").child(CurrentAcademicYearId).child(currentAcademicTerm).child(Objects.requireNonNull(alumni.getKey())).removeValue();
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });
                                                Toast.makeText(AcademicCalendarDetailsActivity.this, "Students transferred to the next class term Successfully...", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(AcademicCalendarDetailsActivity.this, "Error transferring students.", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        AlumniRef.child("AllAlumni").setValue(dataSnapshot).addOnCompleteListener((OnCompleteListener<Void>) task -> {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(AcademicCalendarDetailsActivity.this, "Students transferred to the next class term Successfully...", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(AcademicCalendarDetailsActivity.this, "Error transferring students.", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                    else if (getNextClass(currentClass.getKey()).equals("NULL")){

                                        Toast.makeText(AcademicCalendarDetailsActivity.this, "Sorry next class is   NULL ...", Toast.LENGTH_SHORT).show();

                                    }
                                    else {

                                        ClassCurrentStudentsRef.child(currentClass.getKey()).child(CurrentAcademicYearId).child(currentAcademicTerm).setValue(dataSnapshot).addOnCompleteListener((OnCompleteListener<Void>) task -> {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(AcademicCalendarDetailsActivity.this, "Students transferred to the next class term Successfully...", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(AcademicCalendarDetailsActivity.this, "Error transferring students.", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public String getNextClass (String currentClass) {

        switch (currentClass) {
            case PRE_PR1_1:
                return PRE_PR1_2;
            case PRE_PR1_2:
                return Class_1;
            case Class_1:
                return Class_2;
            case Class_2:
                return Class_3;
            case Class_3:
                return Class_4;
            case Class_4:
                return Class_5;
            case Class_5:
                return Class_6;
            case Class_6:
                return Class_7;
            case Class_7:
                return Class_8;
            case Class_8:
                return ALUMNI;
            default:
                return "NULL";
        }
    }
}