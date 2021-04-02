package com.moringaschool.schoolsystem.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.moringaschool.schoolsystem.R;
import com.moringaschool.schoolsystem.models.PaymentDetails;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.System.currentTimeMillis;

public class FeePaymentFragment extends Fragment {

    @BindView(R.id.edit_amount) EditText mEditAmount;
    @BindView(R.id.edit_paidBy) EditText mEditPayer;
    @BindView(R.id.edit_summary) EditText mEditPaymentSummary;
    @BindView(R.id.submit_feePayment) Button mSubmitButton;

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
        View view = inflater.inflate(R.layout.fragment_fee_payment, container, false);

        ButterKnife.bind(this, view);

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
    }
}