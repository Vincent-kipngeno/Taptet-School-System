package com.moringaschool.schoolsystem.ui.students;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.schoolsystem.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentsFragment extends Fragment {

    private StudentsViewModel studentsViewModel;

    @BindView(R.id.students_list) RecyclerView mAllStudents;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        studentsViewModel = ViewModelProviders.of(this).get(StudentsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_students, container, false);

        ButterKnife.bind(this,view);


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.students_fragment_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }
}