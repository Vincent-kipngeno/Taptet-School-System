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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.schoolsystem.R;
import com.moringaschool.schoolsystem.ui.students.StudentsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeesFragment extends Fragment {

    @BindView(R.id.school_fee_statement_recyclerview) RecyclerView schoolFeeStatementList;

    private FeesViewModel feesViewModel;

    private DatabaseReference UsersRef, SchoolPaymentsRef, Payments, DatabaseRef;
    private FirebaseAuth mAuth;
    private String currentUserID="";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        feesViewModel = ViewModelProviders.of(this).get(FeesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_fees, container, false);

        ButterKnife.bind(this, root);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        DatabaseRef = FirebaseDatabase.getInstance().getReference();

        Payments = DatabaseRef.child("Payments");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        SchoolPaymentsRef = Payments.child("SchoolPayments");

        schoolFeeStatementList.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

    public void fillFeeStatementRecyclerView(String academicYearId, String academicTerm) {
        DatabaseReference schoolPaymentsIds = SchoolPaymentsRef.child(academicYearId).child(academicTerm).child("Payments");

        FirebaseRecyclerOptions<Integer> options =
                new FirebaseRecyclerOptions.Builder<Integer>()
                        .setQuery(schoolPaymentsIds, Integer.class)
                        .build();

        FirebaseRecyclerAdapter<Integer, FeesFragment.FeeStatementViewHolder> adapter = new FirebaseRecyclerAdapter<Integer, FeeStatementViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FeeStatementViewHolder feeStatementViewHolder, int position, @NonNull Integer amount) {
                final String paymentId = getRef(position).getKey();
            }

            @NonNull
            @Override
            public FeeStatementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };
    }

    private static class FeeStatementViewHolder extends RecyclerView.ViewHolder
    {
        TextView time, date, transactedBy, transactedTo, paidVia, amountIn, amountOut;


        public FeeStatementViewHolder(@NonNull View itemView)
        {
            super(itemView);

            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            transactedBy = itemView.findViewById(R.id.transacted_By);
            transactedTo = itemView.findViewById(R.id.transacted_To);
            paidVia = itemView.findViewById(R.id.paid_Via);
            amountIn = itemView.findViewById(R.id.amount_in);
            amountOut = itemView.findViewById(R.id.amount_out);
        }
    }
}
