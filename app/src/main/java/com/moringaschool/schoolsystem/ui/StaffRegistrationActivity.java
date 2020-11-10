package com.moringaschool.schoolsystem.ui;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.ActionBar;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import androidx.fragment.app.FragmentManager;

        import android.os.Bundle;
        import android.util.Patterns;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.RadioButton;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.FirebaseApp;
        import com.google.firebase.FirebaseOptions;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.moringaschool.schoolsystem.R;

        import java.util.HashMap;
        import java.util.Map;

        import butterknife.BindView;
        import butterknife.ButterKnife;

public class StaffRegistrationActivity extends AppCompatActivity implements View.OnClickListener, FragmentManager.OnBackStackChangedListener {
    @BindView(R.id.sex) TextView mSex;
    @BindView(R.id.staffType) TextView mStaffType;
    @BindView(R.id.edit_name) EditText mStaffName;
    @BindView(R.id.edit_staffNo) EditText mStaffNo;
    @BindView(R.id.edit_email) EditText mStaffEmail;
    @BindView(R.id.edit_location) EditText mStaffLocation;
    @BindView(R.id.edit_idNo) EditText mIdNo;
    @BindView(R.id.edit_phone) EditText mPhone;
    @BindView(R.id.edit_role) EditText mRole;
    @BindView(R.id.edit_tscNum) EditText mTscNum;
    @BindView(R.id.radio_female) RadioButton mRadioFemale;
    @BindView(R.id.radio_male) RadioButton mRadioMale;
    @BindView(R.id.radio_Subordinate) RadioButton mRadioSubordinate;
    @BindView(R.id.radio_nonSubordinate) RadioButton mRadioNonSubordinate;
    @BindView(R.id.submit_studentRegistration) Button mSubmit;

    private FirebaseAuth mAuth1;
    private FirebaseAuth mAuth2;
    private DatabaseReference StaffRef, UsersRef, TypeRef;
    private String adminUid ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_registration);

        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setTitle("Staff Registration");

        shouldDisplayHomeUp();

        mAuth1 = FirebaseAuth.getInstance();
        adminUid = mAuth1.getCurrentUser().getUid();

        StaffRef = FirebaseDatabase.getInstance().getReference().child("Staff");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        TypeRef = FirebaseDatabase.getInstance().getReference().child("Type");

        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setDatabaseUrl("https://school-system-84c86.firebaseio.com/")
                .setApiKey("AIzaSyAUM_owDsZEdn0_9yNBrrFe48yzXGBKHAI")
                .setApplicationId("school-system-84c86").build();

        try { FirebaseApp myApp = FirebaseApp.initializeApp(getApplicationContext(), firebaseOptions, "AnyAppName");
            mAuth2 = FirebaseAuth.getInstance(myApp);
        } catch (IllegalStateException e){
            mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"));
        }

        mSubmit.setOnClickListener(this);

    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp(){
        //Enable Up button only  if there are entries in the back stack
        boolean canGoBack = getSupportFragmentManager().getBackStackEntryCount()>0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canGoBack);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack.
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == mSubmit) {
            registerStudent();
        }
    }

    public String getSex () {
        String sex;
        if (mRadioFemale.isChecked()) {
            sex = "Female";
        }
        else if (mRadioMale.isChecked()) {
            sex =  "Male";
        }
        else {
            sex = "No";
        }
        return sex;
    }

    public String getStaffType () {
        String type;
        if (mRadioSubordinate.isChecked()) {
            type = "Subordinate";
        }
        else if (mRadioNonSubordinate.isChecked()) {
            type =  "NonSubordinate";
        }
        else {
            type = "No";
        }
        return type;
    }

    public boolean validInputs (String name, String email, String location, String idNo, String phone, String sex, String type, String staffNo, String tscNo, String role) {
        if (name == null ||name.trim().isEmpty()) {
            mStaffName.setError("This field is required");
            return false;
        }
        else if(email == null || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mStaffEmail.setError("This field is required");
            return false;
        }
        else if(role == null ||role.trim().isEmpty()) {
            mRole.setError("This field is required");
            return false;
        }
        else if(location == null || location.trim().isEmpty()) {
            mStaffLocation.setError("This field is required");
            return false;
        }
        else if(idNo == null || idNo.trim().isEmpty()) {
            mIdNo.setError("This field is required");
            return false;
        }
        else if(phone == null || phone.trim().isEmpty()) {
            mPhone.setError("This field is required");
            return false;
        }
        else if(staffNo == null || staffNo.trim().isEmpty()) {
            mStaffNo.setError("This field is required");
            return false;
        }
        else if(sex.equals("No")) {
            mSex.setError("This field is required");
            return false;
        }
        else if(type.equals("No")) {
            mStaffType.setError("This field is required");
            return false;
        }
        else if(tscNo == null || tscNo.trim().isEmpty()) {
            mTscNum.setError("This field is required");
            return false;
        }
        else{
            return true;
        }
    }

    public void registerStudent () {
        String name =  mStaffName.getText().toString();
        String email =  mStaffEmail.getText().toString();
        String staffNo =  mStaffNo.getText().toString();
        String idNo = mIdNo.getText().toString();
        String location =  mStaffLocation.getText().toString();
        String phone =  mPhone.getText().toString();
        String sex =  getSex();
        String type = getStaffType();
        String tscNo = mTscNum.getText().toString();
        String role = mRole.getText().toString();

        boolean isValidForm =  validInputs(name, email, location, idNo, phone, sex, type, staffNo, tscNo, role);

        if (!isValidForm){
            validInputs(name, email, location, idNo, phone, sex, type, tscNo, staffNo, role);
        }
        else {
            Map<String, Object> staff = new HashMap<String, Object>();
            staff.put("name", name);
            staff.put("email", email);
            staff.put("staffNumber", staffNo);
            staff.put("location", location);
            staff.put("sex", sex);
            staff.put("category", type);
            staff.put("role", role);
            staff.put("idNumber", idNo);
            staff.put("phone", phone);
            staff.put("tscNumber", tscNo);
            staff.put("adminUser", adminUid);
            staff.put("type", "Staff");

            mAuth2.createUserWithEmailAndPassword(email, staffNo)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (!task.isSuccessful()) {
                                String ex = task.getException().toString();
                                Toast.makeText(StaffRegistrationActivity.this, "Registration Failed"+ex,
                                        Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(StaffRegistrationActivity.this, "Registration successful",
                                        Toast.LENGTH_SHORT).show();
                                String staffUid = mAuth2.getCurrentUser().getUid();

                                UsersRef.child(staffUid).updateChildren(staff).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task)
                                    {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(StaffRegistrationActivity.this, "Staff data Saved Successfully...", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(StaffRegistrationActivity.this, "Error saving staff data.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                StaffRef.child(staffUid).setValue(true).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task)
                                    {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(StaffRegistrationActivity.this, "Staff added to staff list Successfully...", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(StaffRegistrationActivity.this, "Error saving staff to staff list.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                TypeRef.child(type).child(staffUid).setValue(true).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task)
                                    {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(StaffRegistrationActivity.this, "Staff added to type list Successfully...", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(StaffRegistrationActivity.this, "Error saving staff to type list.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                mAuth2.signOut();
                            }
                        }
                    });
        }
    }
}