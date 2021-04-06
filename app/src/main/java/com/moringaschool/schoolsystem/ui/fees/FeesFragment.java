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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.schoolsystem.R;
import com.moringaschool.schoolsystem.models.PaymentDetails;
import com.moringaschool.schoolsystem.models.Student;
import com.moringaschool.schoolsystem.ui.students.StudentsFragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeesFragment extends Fragment {

    @BindView(R.id.school_fee_statement_recyclerview) RecyclerView schoolFeeStatementList;

    private FeesViewModel feesViewModel;

    private DatabaseReference UsersRef, SchoolPaymentsRef, Payments, DatabaseRef, AllPaymentsRef, YearDetailsRef, currentAcademicYearRef;
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
        AllPaymentsRef = Payments.child("AllPayments");
        YearDetailsRef = DatabaseRef.child("Years");
        currentAcademicYearRef = YearDetailsRef.child("CurrentAcademicYear");

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
            protected void onBindViewHolder(@NonNull FeeStatementViewHolder holder, int position, @NonNull Integer amount) {
                final String paymentId = getRef(position).getKey();

                AllPaymentsRef.child(paymentId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            PaymentDetails paymentDetails = dataSnapshot.getValue(PaymentDetails.class);

                            if (paymentDetails.getType().equals("Fees")) {

                                UsersRef.child(paymentDetails.getTransactedBy()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Student student = dataSnapshot.getValue(Student.class);

                                        String myFormat = "HH/mm/ss"; //In which you need put here
                                        String dayFormat = "MM/dd/yy";
                                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                        holder.time.setText(sdf.format(paymentDetails.getTime()));
                                        holder.date.setText(sdf.format(paymentDetails.getTime()));
                                        holder.transactedBy.setText(student.getName());
                                        holder.transactedTo.setText(paymentDetails.getTransactedTo());
                                        holder.paidVia.setText(paymentDetails.getMode());
                                        holder.amountIn.setText(paymentDetails.getAmount());
                                        holder.amountOut.setText(R.string.amountOut);
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
            }

            @NonNull
            @Override
            public FeeStatementViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fees_statement_custom_layout, viewGroup, false);
                return new FeesFragment.FeeStatementViewHolder(view);
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

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
        {
            currentAcademicYearRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String currentAcademicYearId = dataSnapshot.child("AcademicYearId").getValue().toString();
                        String currentAcademicTerm = dataSnapshot.child("Term").getValue().toString();

                        fillFeeStatementRecyclerView(currentAcademicYearId, currentAcademicTerm);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
