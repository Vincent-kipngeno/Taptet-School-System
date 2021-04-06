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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.schoolsystem.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeesFragment extends Fragment {

    @BindView(R.id.school_fee_statement_recyclerview) RecyclerView schoolFeeStatementList;

    private FeesViewModel feesViewModel;

    private DatabaseReference UsersRef;
    private FirebaseAuth mAuth;
    private String currentUserID="";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        feesViewModel = ViewModelProviders.of(this).get(FeesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_fees, container, false);

        ButterKnife.bind(this, root);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        schoolFeeStatementList.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }
}
