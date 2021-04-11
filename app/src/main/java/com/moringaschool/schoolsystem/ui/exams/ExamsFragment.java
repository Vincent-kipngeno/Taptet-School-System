package com.moringaschool.schoolsystem.ui.exams;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.moringaschool.schoolsystem.R;

import butterknife.BindView;

public class ExamsFragment extends Fragment {

    private ExamsViewModel mViewModel;
    @BindView(R.id.academic_years_list_exams) RecyclerView yearsList;
    @BindView(R.id.add_year_button_exams) Button addYear;
    @BindView(R.id.noYears_layout_exams) LinearLayout noYearsLayout;


    public static ExamsFragment newInstance() {
        return new ExamsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exams_fragment, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ExamsViewModel.class);
        // TODO: Use the ViewModel
    }

}