package com.moringaschool.schoolsystem.ui.fees;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.moringaschool.schoolsystem.R;

public class FeesFragment extends Fragment {

    private FeesViewModel feesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        feesViewModel = ViewModelProviders.of(this).get(FeesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_fees, container, false);

        return root;
    }
}
