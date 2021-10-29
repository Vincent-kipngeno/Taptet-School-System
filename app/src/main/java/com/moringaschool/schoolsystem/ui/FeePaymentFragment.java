package com.moringaschool.schoolsystem.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.schoolsystem.R;
import com.moringaschool.schoolsystem.models.PaymentDetails;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.System.currentTimeMillis;

public class FeePaymentFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.edit_amount) EditText mEditAmount;
    @BindView(R.id.edit_paidBy) EditText mEditPayer;
    @BindView(R.id.edit_summary) EditText mEditPaymentSummary;
    @BindView(R.id.submit_feePayment) Button mSubmitButton;

    private static final String ARG_STUDENT_UID = "param1";

    private String mStudentUid;
    private DatabaseReference StudentFeePaymentRef, Payments, AllPaymentsRef, SchoolPaymentsRef, ClassPaymentsRef, DatabaseRef, YearDetailsRef, UsersRef, currentAcademicYearRef;

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
        View view = inflater.inflate(R.layout.fragment_fee_payment, container, false);

        ButterKnife.bind(this, view);
        DatabaseRef = FirebaseDatabase.getInstance().getReference();

        Payments = DatabaseRef.child("Payments");
        StudentFeePaymentRef = Payments.child("StudentFeePayments");
        AllPaymentsRef = Payments.child("AllPayments");
        SchoolPaymentsRef = Payments.child("SchoolPayments");
        ClassPaymentsRef = Payments.child("ClassFeePayments");
        YearDetailsRef = DatabaseRef.child("Years");
        UsersRef = DatabaseRef.child("Users");
        currentAcademicYearRef = YearDetailsRef.child("CurrentAcademicYear");

        mSubmitButton.setOnClickListener(this);

        return view;
    }

    public boolean validateInputs (String amount, String payer, String summary) {

        if (amount==null||amount.trim().isEmpty()) {
            mEditAmount.setError("This field is required");
            return false;
        }
        else if (payer==null||payer.trim().isEmpty())
        {
            mEditPayer.setError("This field is required");
            return false;
        }
        else if (summary==null||summary.trim().isEmpty())
        {
            mEditPaymentSummary.setError("This field is required");
            return false;
        }
        else {
            return true;
        }
    }

    public void recordFeePayment () {
        String amount = mEditAmount.getText().toString();
        String payer = mEditPayer.getText().toString();
        String paymentSummary = mEditPaymentSummary.getText().toString();

        PaymentDetails paymentDetails = new PaymentDetails(currentTimeMillis(), amount, "Taptet School", mStudentUid, payer, "Cash", "Fees", paymentSummary);

        boolean isValidInputs = validateInputs(amount, payer, paymentSummary);

        if (!isValidInputs) {
            validateInputs(amount, payer, paymentSummary);
        }
        else {
            DatabaseReference paymentRef = AllPaymentsRef.push();
            String paymentPushId = paymentRef.getKey();

            AllPaymentsRef.child(paymentPushId).setValue(paymentDetails);

            currentAcademicYearRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.exists())
                    {
                        String currentAcademicYearId = dataSnapshot.child("AcademicYearId").getValue().toString();
                        String currentAcademicTerm = dataSnapshot.child("Term").getValue().toString();

                        Map<String, Object> model = new HashMap<>();
                        model.put(currentAcademicTerm+"/Payments/"+paymentPushId, paymentDetails.getAmount());

                        SchoolPaymentsRef.child(currentAcademicYearId).updateChildren(model);
                        StudentFeePaymentRef.child(paymentDetails.getTransactedBy()).child(currentAcademicYearId).updateChildren(model);

                    }
                    else {
                        Toast.makeText(getContext(), "Kindly Create and Start academic year First", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if (v==mSubmitButton) {
            recordFeePayment();
        }
    }
}