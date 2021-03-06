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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.schoolsystem.R;
import com.moringaschool.schoolsystem.models.Student;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.moringaschool.schoolsystem.Constants.*;

public class StudentRegistrationActivity extends AppCompatActivity implements View.OnClickListener, FragmentManager.OnBackStackChangedListener {
    @BindView(R.id.sex) TextView mSex;
    @BindView(R.id.studentType) TextView mStudentType;
    @BindView(R.id.studentClass) TextView mStudentClass;
    @BindView(R.id.edit_name) EditText mStudentName;
    @BindView(R.id.edit_admissionNo) EditText mStudentAdmission;
    @BindView(R.id.edit_email) EditText mStudentEmail;
    @BindView(R.id.edit_location) EditText mStudentLocation;
    @BindView(R.id.edit_parentName) EditText mParentName;
    @BindView(R.id.edit_phone1) EditText mParentPhone1;
    @BindView(R.id.edit_phone2) EditText mParentPhone2;
    @BindView(R.id.radio_female) RadioButton mRadioFemale;
    @BindView(R.id.radio_male) RadioButton mRadioMale;
    @BindView(R.id.radio_day) RadioButton mRadioDay;
    @BindView(R.id.radio_border) RadioButton mRadioBoarder;
    @BindView(R.id.radio_class1) RadioButton mRadioClass1;
    @BindView(R.id.radio_class2) RadioButton mRadioClass2;
    @BindView(R.id.radio_class3) RadioButton mRadioClass3;
    @BindView(R.id.radio_class4) RadioButton mRadioClass4;
    @BindView(R.id.radio_class5) RadioButton mRadioClass5;
    @BindView(R.id.radio_class6) RadioButton mRadioClass6;
    @BindView(R.id.radio_class7) RadioButton mRadioClass7;
    @BindView(R.id.radio_class8) RadioButton mRadioClass8;
    @BindView(R.id.radio_classPre1) RadioButton mRadioClassPre1;
    @BindView(R.id.radio_classPre2) RadioButton mRadioClassPre2;
    @BindView(R.id.submit_studentRegistration) Button mSubmit;

    private FirebaseAuth mAuth1;
    private FirebaseAuth mAuth2;
    private DatabaseReference StudentsRef, UsersRef, ClassRef, CurrentStudentsRef, ClassCurrentStudentsRef, DatabaseRef, CurrentAcademicYearRef, StudentFeePaymentRef;
    private String adminUid ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setTitle("Student Registration");

        shouldDisplayHomeUp();

        mAuth1 = FirebaseAuth.getInstance();
        adminUid = mAuth1.getCurrentUser().getUid();
        DatabaseRef = FirebaseDatabase.getInstance().getReference();

        StudentsRef = DatabaseRef.child("Students");
        UsersRef = DatabaseRef.child("Users");
        ClassRef = DatabaseRef.child("Classes");
        CurrentStudentsRef = DatabaseRef.child("CurrentStudents");
        ClassCurrentStudentsRef = DatabaseRef.child("ClassCurrentStudents");
        CurrentAcademicYearRef = DatabaseRef.child("CurrentAcademicYear");
        StudentFeePaymentRef = DatabaseRef.child("StudentFeePayments");

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

    public String getStudentType () {
        String type;
        if (mRadioDay.isChecked()) {
            type = DAY;
        }
        else if (mRadioBoarder.isChecked()) {
            type =  BOARDER;
        }
        else {
            type = "No";
        }
        return type;
    }

    public String getStudentClass () {
        String studentClass;
        if (mRadioClass1.isChecked()) {
            studentClass = Class_1;
        }
        else if (mRadioClass2.isChecked()) {
            studentClass =  Class_2;
        }
        else if (mRadioClass3.isChecked()) {
            studentClass =  Class_3;
        }
        else if (mRadioClass4.isChecked()) {
            studentClass =  Class_4;
        }
        else if (mRadioClass5.isChecked()) {
            studentClass =  Class_5;
        }
        else if (mRadioClass6.isChecked()) {
            studentClass =  Class_6;
        }
        else if (mRadioClass7.isChecked()) {
            studentClass =  Class_7;
        }
        else if (mRadioClass8.isChecked()) {
            studentClass =  Class_8;
        }
        else if (mRadioClassPre1.isChecked()) {
            studentClass =  PRE_PR1_1;
        }
        else if (mRadioClassPre2.isChecked()) {
            studentClass =  PRE_PR1_2;
        }
        else {
            studentClass = "No";
        }
        return studentClass;
    }

    public boolean validInputs (String name, String email, String location, String parentName, String parentPhone1, String sex, String type, String studentClass, String adm) {
        if (name == null ||name.trim().isEmpty()) {
            mStudentName.setError("This field is required");
            return false;
        }
        else if(email == null || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mStudentEmail.setError("This field is required");
            return false;
        }
        else if(location == null || location.trim().isEmpty()) {
            mStudentLocation.setError("This field is required");
            return false;
        }
        else if(parentName == null || parentName.trim().isEmpty()) {
            mParentName.setError("This field is required");
            return false;
        }
        else if(parentPhone1 == null || parentPhone1.trim().isEmpty()) {
            mParentPhone1.setError("This field is required");
            return false;
        }
        else if(adm == null || adm.trim().isEmpty()) {
            mStudentAdmission.setError("This field is required");
            return false;
        }
        else if(sex.equals("No")) {
            mSex.setError("This field is required");
            return false;
        }
        else if(type.equals("No")) {
            mStudentType.setError("This field is required");
            return false;
        }
        else if(studentClass.equals("No")) {
            mStudentClass.setError("This field is required");
            return false;
        }
        else{
            return true;
        }
    }

    public void registerStudent () {
        String name =  mStudentName.getText().toString();
        String email =  mStudentEmail.getText().toString();
        String studentAdm =  mStudentAdmission.getText().toString();
        String parentName = mParentName.getText().toString();
        String location =  mStudentLocation.getText().toString();
        String parentPhone1 =  mParentPhone1.getText().toString();
        String parentPhone2 =  mParentPhone2.getText().toString();
        String sex =  getSex();
        String category = getStudentType();
        String studentClass = getStudentClass();
        String type = "Student";

        boolean isValidForm =  validInputs(name, email, location, parentName, parentPhone1, sex, type, studentClass, studentAdm);

        if (!isValidForm){
            validInputs(name, email, location, parentName, parentPhone1, sex, type, studentClass, studentAdm);
        }
        else {
            /*Map<String, Object> student = new HashMap<String, Object>();
            student.put("name", name);
            student.put("email", email);
            student.put("admissionNo", studentAdm);
            student.put("location", location);
            student.put("sex", sex);
            student.put("category", category);
            student.put("grade", studentClass);
            student.put("parentName", parentName);
            student.put("phone1", parentPhone1);
            student.put("phone2", parentPhone2);
            student.put("adminUser", adminUid);
            student.put("type", type);*/

            Student student = new Student(name, email, studentAdm, location, sex, category, studentClass, parentName, parentPhone1, parentPhone2, adminUid, type);

            CurrentAcademicYearRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.exists())
                    {
                        String currentAcademicYearId = dataSnapshot.child("AcademicYearId").getValue().toString();
                        String currentAcademicTerm = dataSnapshot.child("Term").getValue().toString();

                        createAndLoginUser(student, currentAcademicYearId, currentAcademicTerm);

                    }
                    else {
                        Toast.makeText(StudentRegistrationActivity.this, "Kindly Create and Start academic year First", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
    }

    public void createAndLoginUser (Student student, String currentAcademicYearId, String currentAcademicTerm) {

        mAuth2.createUserWithEmailAndPassword(student.getEmail(), student.getAdmissionNo())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (!task.isSuccessful()) {
                            String ex = task.getException().toString();
                            Toast.makeText(StudentRegistrationActivity.this, "Registration Failed"+ex,
                                    Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(StudentRegistrationActivity.this, "Registration successful",
                                    Toast.LENGTH_SHORT).show();
                            String studentUid = mAuth2.getCurrentUser().getUid();

                            UsersRef.child(studentUid).setValue(student).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(StudentRegistrationActivity.this, "Student data Saved Successfully...", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(StudentRegistrationActivity.this, "Error saving student data.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            StudentsRef.child(studentUid).setValue(true).addOnCompleteListener((OnCompleteListener<Void>) task1 -> {
                                if (task1.isSuccessful())
                                {
                                    Toast.makeText(StudentRegistrationActivity.this, "Student added to students list Successfully...", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(StudentRegistrationActivity.this, "Error saving student to students list.", Toast.LENGTH_SHORT).show();
                                }
                            });

                            Map<String, Object> currentStudentsMap = new HashMap<>();
                            currentStudentsMap.put(currentAcademicTerm+"/Students/"+studentUid, true);
                            currentStudentsMap.put(currentAcademicTerm+"/"+student.getCategory()+"/"+studentUid, true);

                            CurrentStudentsRef.child(currentAcademicYearId).updateChildren(currentStudentsMap).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(StudentRegistrationActivity.this, "Student added to class list Successfully...", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(StudentRegistrationActivity.this, "Error saving student to class list.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            Map<String, Object> ClassCurrentStudentsMap = new HashMap<>();
                            ClassCurrentStudentsMap.put(currentAcademicTerm+"/Students/"+studentUid, true);
                            ClassCurrentStudentsMap.put(currentAcademicTerm+"/"+student.getCategory()+"/"+studentUid, true);

                            ClassCurrentStudentsRef.child(student.getGrade()).child(currentAcademicYearId).updateChildren(ClassCurrentStudentsMap).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(StudentRegistrationActivity.this, "Student added to class list Successfully...", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(StudentRegistrationActivity.this, "Error saving student to class list.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            Map<String, Object> model = new HashMap<>();
                            model.put(currentAcademicTerm+"/Payments/None", 0);
                            model.put(currentAcademicTerm+"/Balance/Arrears", 0);
                            model.put(currentAcademicTerm+"/Balance/TotalBalance", 0);

                            StudentFeePaymentRef.child(studentUid).child(currentAcademicYearId).updateChildren(model).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(StudentRegistrationActivity.this, "Student added to class list Successfully...", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(StudentRegistrationActivity.this, "Error saving student to class list.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            ClassRef.child(student.getGrade()).setValue(true).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(StudentRegistrationActivity.this, "Student added to class list Successfully...", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(StudentRegistrationActivity.this, "Error saving student to class list.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            mAuth2.signOut();
                        }
                    }
                });
    }
}