package com.moringaschool.schoolsystem.ui.exams;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.schoolsystem.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExamsFragment extends Fragment {

    private ExamsViewModel mViewModel;
    @BindView(R.id.academic_years_list_exams) RecyclerView yearsList;
    @BindView(R.id.add_year_button_exams) Button addYear;
    @BindView(R.id.noYears_layout_exams) LinearLayout noYearsLayout;

    private DatabaseReference AcademicYearsRef;
    private FirebaseAuth mAuth;
    private String currentUserID="";


    public static ExamsFragment newInstance() {
        return new ExamsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exams_fragment, container, false);

        ButterKnife.bind(this,view);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        AcademicYearsRef = FirebaseDatabase.getInstance().getReference().child("Years").child("AcademicYears");

        yearsList.setLayoutManager(new LinearLayoutManager(getContext()));

        addYear.setOnClickListener(this);

        checkIfYearsExist();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ExamsViewModel.class);
        // TODO: Use the ViewModel
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

}