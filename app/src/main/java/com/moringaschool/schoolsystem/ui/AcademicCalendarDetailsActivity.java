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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.schoolsystem.R;
import com.moringaschool.schoolsystem.models.FeeStructure;
import com.moringaschool.schoolsystem.models.Student;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;
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

    private DatabaseReference YearDetailsRef, YearFeeStructureRef, currentAcademicCalendarRef, UsersRef, NewAcademicYearRef, StudentFeePaymentRef, PreviousAcademicYearRef, DatabaseRef, ClassCurrentStudentsRef, AlumniRef, ClassRef, Payments, AllPaymentsRef, SchoolPaymentsRef, ClassPaymentsRef;
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
        UsersRef = DatabaseRef.child("Users");
        currentAcademicCalendarRef = YearDetailsRef.child("CurrentAcademicYear");
        NewAcademicYearRef = YearDetailsRef.child("NewAcademicYear");
        PreviousAcademicYearRef = YearDetailsRef.child("PreviousAcademicYear");
        YearFeeStructureRef = YearDetailsRef.child("YearDetails").child(CurrentAcademicYearId).child("FeeStructure");
        ClassCurrentStudentsRef = DatabaseRef.child("ClassCurrentStudents");
        ClassRef = DatabaseRef.child("Classes");
        AlumniRef = DatabaseRef.child("Alumni");
        Payments = DatabaseRef.child("Payments");
        StudentFeePaymentRef = Payments.child("StudentFeePayments");
        AllPaymentsRef = Payments.child("AllPayments");
        SchoolPaymentsRef = Payments.child("SchoolPayments");
        ClassPaymentsRef = Payments.child("ClassFeePayments");

        mFeeStructureGrid.setLayoutManager(new LinearLayoutManager(this));

        fillTermDates();
        visibilityOfStartAndEndButtons();

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

                        updateCurrentYear(currentAcademicTerm);
                        transferCurrentStudentsToNextTerm(previousAcademicYearId, previousAcademicTerm, currentAcademicTerm);
                        transferCurrentStudentsToNextClass(previousAcademicYearId, previousAcademicTerm, currentAcademicTerm);
                        createSchoolFeeEntry(previousAcademicYearId, previousAcademicTerm, currentAcademicTerm);
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
            PreviousAcademicYearRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.exists())
                    {
                        String previousAcademicYearId = dataSnapshot.child("AcademicYearId").getValue().toString();
                        String previousAcademicTerm = dataSnapshot.child("Term").getValue().toString();
                        String currentAcademicTerm = TERM_2;

                        updateCurrentYear(currentAcademicTerm);
                        transferCurrentStudentsToNextTerm(previousAcademicYearId, previousAcademicTerm, currentAcademicTerm);
                        transferCurrentStudentsToNextClassTerm(previousAcademicYearId, previousAcademicTerm, currentAcademicTerm);
                        createSchoolFeeEntry(previousAcademicYearId, previousAcademicTerm, currentAcademicTerm);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        if (view == mEndTerm2) {

        }

        if (view == mStartTerm3) {
            PreviousAcademicYearRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.exists())
                    {
                        String previousAcademicYearId = dataSnapshot.child("AcademicYearId").getValue().toString();
                        String previousAcademicTerm = dataSnapshot.child("Term").getValue().toString();
                        String currentAcademicTerm = TERM_3;

                        updateCurrentYear(currentAcademicTerm);
                        transferCurrentStudentsToNextTerm(previousAcademicYearId, previousAcademicTerm, currentAcademicTerm);
                        transferCurrentStudentsToNextClassTerm(previousAcademicYearId, previousAcademicTerm, currentAcademicTerm);
                        createSchoolFeeEntry(previousAcademicYearId, previousAcademicTerm, currentAcademicTerm);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
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

        currentAcademicCalendarRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String yearId = dataSnapshot.child("AcademicYearId").getValue().toString();
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
                        PreviousAcademicYearRef.addValueEventListener(new ValueEventListener() {
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
                else
                {
                    NewAcademicYearRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {
                            if (dataSnapshot.exists())
                            {
                                String yearId = dataSnapshot.child("AcademicYearId").getValue().toString();

                                if ( yearId.equals(CurrentAcademicYearId)) {
                                    mStartTerm1.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
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
                    DatabaseRef.child("CurrentStudents").child(CurrentAcademicYearId).child(currentAcademicTerm).setValue(dataSnapshot).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(AcademicCalendarDetailsActivity.this, "Students transferred to the next term Successfully...", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(AcademicCalendarDetailsActivity.this, "Error transferring students.", Toast.LENGTH_SHORT).show();
                            }
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

                                        ClassCurrentStudentsRef.child(getNextClass(currentClass.getKey())).child(CurrentAcademicYearId).child(currentAcademicTerm).setValue(dataSnapshot).addOnCompleteListener(new OnCompleteListener() {
                                            @Override
                                            public void onComplete(@NonNull Task task) {
                                                if (task.isSuccessful())
                                                {
                                                    Toast.makeText(AcademicCalendarDetailsActivity.this, "Students transferred to the alumni class term Successfully...", Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                {
                                                    Toast.makeText(AcademicCalendarDetailsActivity.this, "Error transferring students to alumni class term.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                        AlumniRef.child("YearAlumni").child(CurrentAcademicYearId).child(currentAcademicTerm).setValue(dataSnapshot).addOnCompleteListener(new OnCompleteListener() {
                                            @Override
                                            public void onComplete(@NonNull Task task) {
                                                if (task.isSuccessful())
                                                {
                                                    AlumniRef.child("YearAlumni").child(CurrentAcademicYearId).child(currentAcademicTerm).child("Students").addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot)
                                                        {
                                                            if (dataSnapshot.exists())
                                                            {
                                                                for (DataSnapshot alumni : dataSnapshot.getChildren()){

                                                                    DatabaseRef.child("CurrentStudents").child(CurrentAcademicYearId).child(currentAcademicTerm).child("Students").child(alumni.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {

                                                                                DatabaseRef.child("CurrentStudents").child(CurrentAcademicYearId).child(currentAcademicTerm).child("Boarders").child(alumni.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        if (task.isSuccessful()) {

                                                                                            DatabaseRef.child("CurrentStudents").child(CurrentAcademicYearId).child(currentAcademicTerm).child("Day").child(alumni.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                    if (task.isSuccessful()) {
                                                                                                        DatabaseRef.child("CurrentStudents").child(CurrentAcademicYearId).child(currentAcademicTerm).child("Students").addValueEventListener(new ValueEventListener() {
                                                                                                            @Override
                                                                                                            public void onDataChange(DataSnapshot dataSnapshot)
                                                                                                            {
                                                                                                                if (dataSnapshot.exists())
                                                                                                                {
                                                                                                                    for (DataSnapshot studentToAddEntry: dataSnapshot.getChildren()){
                                                                                                                        createStudentFeeEntry(studentToAddEntry.getKey(), previousAcademicYearId, previousAcademicTerm, currentAcademicTerm);
                                                                                                                    }
                                                                                                                }
                                                                                                            }

                                                                                                            @Override
                                                                                                            public void onCancelled(DatabaseError databaseError) {

                                                                                                            }
                                                                                                        });
                                                                                                    }
                                                                                                }
                                                                                            });

                                                                                        }
                                                                                    }
                                                                                });
                                                                            }
                                                                        }
                                                                    });
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
                                            }
                                        });

                                        for (DataSnapshot alumniStudent : dataSnapshot.child("Students").getChildren()) {

                                            AlumniRef.child("AllAlumni").child("AllYearsAlumni").child(alumniStudent.getKey()).setValue(true).addOnCompleteListener(new OnCompleteListener() {
                                                @Override
                                                public void onComplete(@NonNull Task task) {
                                                    if (task.isSuccessful())
                                                    {
                                                        Toast.makeText(AcademicCalendarDetailsActivity.this, "Students transferred to the next class term Successfully...", Toast.LENGTH_SHORT).show();
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(AcademicCalendarDetailsActivity.this, "Error transferring students.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                        }

                                    }
                                    else if (getNextClass(currentClass.getKey()).equals("NULL")){

                                        Toast.makeText(AcademicCalendarDetailsActivity.this, "Sorry next class is   NULL ...", Toast.LENGTH_SHORT).show();

                                    }
                                    else {

                                        ClassCurrentStudentsRef.child(getNextClass(currentClass.getKey())).child(CurrentAcademicYearId).child(currentAcademicTerm).setValue(dataSnapshot).addOnCompleteListener(new OnCompleteListener() {
                                            @Override
                                            public void onComplete(@NonNull Task task) {
                                                if (task.isSuccessful())
                                                {
                                                    Toast.makeText(AcademicCalendarDetailsActivity.this, "Students transferred to the next class term Successfully...", Toast.LENGTH_SHORT).show();
                                                } else
                                                {
                                                    Toast.makeText(AcademicCalendarDetailsActivity.this, "Error transferring students.", Toast.LENGTH_SHORT).show();
                                                }
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

    public void transferCurrentStudentsToNextClassTerm (String previousAcademicYearId, String previousAcademicTerm, String currentAcademicTerm) {
        ClassRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot currentClass : dataSnapshot.getChildren()) {
                         ClassCurrentStudentsRef.child(currentClass.getKey()).child(previousAcademicYearId).child(previousAcademicTerm).addValueEventListener(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 ClassCurrentStudentsRef.child(currentClass.getKey()).child(CurrentAcademicYearId).child(currentAcademicTerm).setValue(dataSnapshot).addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) {
                                         if (task.isSuccessful())
                                         {
                                             Toast.makeText(AcademicCalendarDetailsActivity.this, "Students transferred to the next class term Successfully...", Toast.LENGTH_SHORT).show();
                                         }
                                         else {
                                             Toast.makeText(AcademicCalendarDetailsActivity.this, "Error transferring students to the next class term...", Toast.LENGTH_SHORT).show();
                                         }
                                     }
                                 });
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {

                             }
                         });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseRef.child("CurrentStudents").child(CurrentAcademicYearId).child(currentAcademicTerm).child("Students").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot studentToAddEntry: dataSnapshot.getChildren()){
                        createStudentFeeEntry(studentToAddEntry.getKey(), previousAcademicYearId, previousAcademicTerm, currentAcademicTerm);
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

    public void createStudentFeeEntry (String currentStudentId, String previousAcademicYearId, String previousAcademicTerm, String currentAcademicTerm) {

        StudentFeePaymentRef.child(currentStudentId).child(previousAcademicYearId).child(previousAcademicTerm).child("Balance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    Map<String, Object> model = new HashMap<>();
                    model.put(currentAcademicTerm+"/Payments/None", 0);
                    model.put(currentAcademicTerm+"/Balance/Arrears", Integer.parseInt(dataSnapshot.child("TotalBalance").getValue().toString()));
                    model.put(currentAcademicTerm+"/Balance/TotalBalance", 0);



                    StudentFeePaymentRef.child(currentStudentId).child(CurrentAcademicYearId).updateChildren(model).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(AcademicCalendarDetailsActivity.this, "Student Fee Entry created Successfully...", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(AcademicCalendarDetailsActivity.this, "Error creating Student Fee Entry .", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void createSchoolFeeEntry (String previousAcademicYearId, String previousAcademicTerm, String currentAcademicTerm) {

        SchoolPaymentsRef.child(previousAcademicYearId).child(previousAcademicTerm).child("Balance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Map<String, Object> model = new HashMap<>();

                if (dataSnapshot.exists())
                {
                    model.put(currentAcademicTerm+"/Payments/None", 0);
                    model.put(currentAcademicTerm+"/Balance/Arrears", Integer.parseInt(dataSnapshot.child("TotalBalance").getValue().toString()));
                    model.put(currentAcademicTerm+"/Balance/TotalBalance", 0);

                }
                else
                {
                    model.put(currentAcademicTerm+"/Payments/None", 0);
                    model.put(currentAcademicTerm+"/Balance/Arrears", 0);
                    model.put(currentAcademicTerm+"/Balance/TotalBalance", 0);
                }

                SchoolPaymentsRef.child(CurrentAcademicYearId).updateChildren(model).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(AcademicCalendarDetailsActivity.this, "School Fee Entry created Successfully...", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(AcademicCalendarDetailsActivity.this, "Error creating School Fee Entry.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void updateCurrentYear (String currentAcademicTerm) {
        Map<String, Object> currentDates = new HashMap<>();

        currentDates.put("CurrentAcademicYear/AcademicYearId", CurrentAcademicYearId);
        currentDates.put("CurrentAcademicYear/term", currentAcademicTerm);

        YearDetailsRef.updateChildren(currentDates).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task)
            {
                if (task.isSuccessful())
                {
                    NewAcademicYearRef.removeValue();
                    Toast.makeText(AcademicCalendarDetailsActivity.this, "Current academic dates updated successfully...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(AcademicCalendarDetailsActivity.this, "Error updating current academic dates.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void calculateStudentsTotalFeeBalance (String currentAcademicTerm) {
        DatabaseRef.child("CurrentStudents").child(CurrentAcademicYearId).child(currentAcademicTerm).child("Students").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot studentToCalculateBalance: dataSnapshot.getChildren()){
                        StudentFeePaymentRef.child(studentToCalculateBalance.getKey()).child(CurrentAcademicYearId).child(currentAcademicTerm).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int totalPayments = 0;
                                for (DataSnapshot amount : dataSnapshot.child("Payments").getChildren())
                                {
                                    int paymentAmount = Integer.parseInt(amount.toString());
                                    totalPayments+=paymentAmount;
                                }
                                int finalTotalPayments = totalPayments;
                                int arrears = Integer.parseInt(dataSnapshot.child("Balance").child("Arrears").getValue().toString());
                                UsersRef.child(studentToCalculateBalance.getKey()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Student student = dataSnapshot.getValue(Student.class);
                                        YearFeeStructureRef.child(student.getCategory()).child(student.getGrade()).child(currentAcademicTerm).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                int termFeeAmount = Integer.parseInt(dataSnapshot.getValue().toString());
                                                int totalBalance = (termFeeAmount - finalTotalPayments)+arrears;
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

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