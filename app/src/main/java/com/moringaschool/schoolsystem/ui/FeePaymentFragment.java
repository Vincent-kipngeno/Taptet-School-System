package com.moringaschool.schoolsystem.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moringaschool.schoolsystem.R;

public class FeePaymentFragment extends Fragment {

    private static final String ARG_STUDENT_UID = "param1";

    private String mStudentUid;

    public FeePaymentFragment() {
        // Required empty public constructor
    }


    public static FeePaymentFragment newInstance(String studentUid) {
        FeePaymentFragment fragment = new FeePaymentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STUDENT_UID, studentUid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStudentUid = getArguments().getString(ARG_STUDENT_UID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fee_payment, container, false);
    }
}