package com.moringaschool.schoolsystem.ui;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.moringaschool.schoolsystem.R;

        import butterknife.BindView;
        import butterknife.ButterKnife;

public class StaffDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.staffName) TextView mStaffName;
    @BindView(R.id.staffEmail) TextView mStaffEmail;
    @BindView(R.id.staffNo) TextView mStaffNo;
    @BindView(R.id.staffSex) TextView mStaffSex;
    @BindView(R.id.staffRole) TextView mStaffRole;
    @BindView(R.id.staffCategory) TextView mStaffCategory;
    @BindView(R.id.staffLocation) TextView mStaffLocation;
    @BindView(R.id.idNum) TextView mIdNo;
    @BindView(R.id.phone) TextView mPhone;
    @BindView(R.id.tscNo) TextView mTscNo;
    @BindView(R.id.feesButton)
    Button mFeesButton;
    @BindView(R.id.examsButton) Button mExamsButton;
    @BindView(R.id.attendaceButton) Button mAttendaceButton;

    private DatabaseReference UsersRef;
    private FirebaseAuth mAuth;
    private String currentUserID="";
    String staffId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_details);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        Intent intent = new Intent();
        staffId = intent.getStringExtra("visit_user_id");
        fetchStaffInfo();
    }

    @Override
    public void onClick(View view) {

    }

    public void fetchStaffInfo() {
        UsersRef.child(staffId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String staffName = dataSnapshot.child("name").getValue().toString();
                    String staffEmail = dataSnapshot.child("email").getValue().toString();
                    String staffRole = dataSnapshot.child("role").getValue().toString();
                    String staffNo = dataSnapshot.child("staffNo").getValue().toString();
                    String staffSex = dataSnapshot.child("sex").getValue().toString();
                    String staffCategory = dataSnapshot.child("category").getValue().toString();
                    String staffLocation = dataSnapshot.child("location").getValue().toString();
                    String idNumber = dataSnapshot.child("idNumber").getValue().toString();
                    String phone = dataSnapshot.child("phone").getValue().toString();
                    String tscNum = dataSnapshot.child("tscNumber").getValue().toString();

                    mStaffName.setText(staffName);
                    mStaffEmail.setText(staffEmail);
                    mStaffRole.setText(staffRole);
                    mStaffNo.setText(staffNo);
                    mStaffSex.setText(staffSex);
                    mStaffCategory.setText(staffCategory);
                    mStaffLocation.setText(staffLocation);
                    mIdNo.setText(idNumber);
                    mPhone.setText(phone);
                    if (!tscNum.trim().isEmpty()) {
                        mTscNo.setText(tscNum);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}