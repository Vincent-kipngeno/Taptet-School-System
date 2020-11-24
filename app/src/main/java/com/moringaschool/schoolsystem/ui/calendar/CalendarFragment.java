package com.moringaschool.schoolsystem.ui.calendar;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.schoolsystem.R;
import com.moringaschool.schoolsystem.models.AcademicYear;
import com.moringaschool.schoolsystem.ui.AcademicCalendarDetailsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CalendarFragment extends Fragment {

    private CalendarViewModel calendarViewModel;

    @BindView(R.id.academic_years_list) RecyclerView yearsList;
    @BindView(R.id.add_year_button) Button addYear;
    @BindView(R.id.noYears_layout) LinearLayout noYearsLayout;

    private DatabaseReference AcademicYearsRef;
    private FirebaseAuth mAuth;
    private String currentUserID="";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel = ViewModelProviders.of(this).get(CalendarViewModel.class);
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        ButterKnife.bind(this,view);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        AcademicYearsRef = FirebaseDatabase.getInstance().getReference().child("AcademicYears");

        yearsList.setLayoutManager(new LinearLayoutManager(getContext()));

        checkIfYearsExist();

        return view;
    }

    public void checkIfYearsExist () {
        AcademicYearsRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s)
                    {
                        if (dataSnapshot.exists()) {
                            fillYearsRecyclerView();
                            noYearsLayout.setVisibility(View.INVISIBLE);
                            addYear.setEnabled(false);
                        }
                        else {
                           noYearsLayout.setVisibility(View.VISIBLE);
                           addYear.setEnabled(true);
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void fillYearsRecyclerView() {
        FirebaseRecyclerOptions<AcademicYear> options =
                new FirebaseRecyclerOptions.Builder<AcademicYear>()
                        .setQuery(AcademicYearsRef, AcademicYear.class)
                        .build();


        FirebaseRecyclerAdapter<AcademicYear, CalendarFragment.YearsViewHolder> adapter =
                new FirebaseRecyclerAdapter<AcademicYear, CalendarFragment.YearsViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final CalendarFragment.YearsViewHolder holder, int position, @NonNull AcademicYear model)
                    {
                        final String academicYearId = getRef(position).getKey();

                        holder.yearName.setText(model.getAcademicYear());
                        holder.startDate.setText(model.getStartDate());
                        holder.endDate.setText(model.getEndDate());
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                Intent yearIntent = new Intent(getContext(), AcademicCalendarDetailsActivity.class);
                                yearIntent.putExtra("academicYearId", academicYearId);
                                yearIntent.putExtra("academicYearName", model.getAcademicYear());
                                startActivity(yearIntent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public CalendarFragment.YearsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
                    {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_academic_year_layout, viewGroup, false);
                        return new CalendarFragment.YearsViewHolder(view);
                    }
                };

        yearsList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class  YearsViewHolder extends RecyclerView.ViewHolder
    {
        TextView startDate, yearName, endDate;


        public YearsViewHolder(@NonNull View itemView)
        {
            super(itemView);

            yearName = itemView.findViewById(R.id.academicYear);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
        {
            checkIfYearsExist();
        }
    }
}
