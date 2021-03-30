package com.moringaschool.schoolsystem.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.schoolsystem.R;
import com.moringaschool.schoolsystem.models.AcademicYear;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.moringaschool.schoolsystem.Constants.BOARDER;
import static com.moringaschool.schoolsystem.Constants.Class_1;
import static com.moringaschool.schoolsystem.Constants.Class_2;
import static com.moringaschool.schoolsystem.Constants.Class_3;
import static com.moringaschool.schoolsystem.Constants.Class_4;
import static com.moringaschool.schoolsystem.Constants.Class_5;
import static com.moringaschool.schoolsystem.Constants.Class_6;
import static com.moringaschool.schoolsystem.Constants.Class_7;
import static com.moringaschool.schoolsystem.Constants.Class_8;
import static com.moringaschool.schoolsystem.Constants.DAY;
import static com.moringaschool.schoolsystem.Constants.PRE_PR1_1;
import static com.moringaschool.schoolsystem.Constants.PRE_PR1_2;
import static com.moringaschool.schoolsystem.Constants.TERM_1;
import static com.moringaschool.schoolsystem.Constants.TERM_2;
import static com.moringaschool.schoolsystem.Constants.TERM_3;

public class AddNewAcademicYearActivity extends AppCompatActivity {
    @BindView(R.id.edit_academic_year) EditText editAcademicYear;
    @BindView(R.id.edit_term1_StartDate) EditText editTerm1StartDate;
    @BindView(R.id.edit_term1_EndDate) EditText editTerm1EndDate;
    @BindView(R.id.edit_term2_StartDate) EditText editTerm2StartDate;
    @BindView(R.id.edit_term2_EndDate) EditText editTerm2EndDate;
    @BindView(R.id.edit_term3_StartDate) EditText editTerm3StartDate;
    @BindView(R.id.edit_term3_EndDate) EditText editTerm3EndDate;

    @BindView(R.id.edit_pp1_boarders_term1_fee) EditText editPp1BoardersTerm1Fee;
    @BindView(R.id.edit_pp1_boarders_term2_fee) EditText editPp1BoardersTerm2Fee;
    @BindView(R.id.edit_pp1_boarders_term3_fee) EditText editPp1BoardersTerm3Fee;
    @BindView(R.id.edit_pp2_boarders_term1_fee) EditText editPp2BoardersTerm1Fee;
    @BindView(R.id.edit_pp2_boarders_term2_fee) EditText editPp2BoardersTerm2Fee;
    @BindView(R.id.edit_pp2_boarders_term3_fee) EditText editPp2BoardersTerm3Fee;
    @BindView(R.id.edit_class1_boarders_term1_fee) EditText editClass1BoardersTerm1Fee;
    @BindView(R.id.edit_class1_boarders_term2_fee) EditText editClass1BoardersTerm2Fee;
    @BindView(R.id.edit_class1_boarders_term3_fee) EditText editClass1BoardersTerm3Fee;
    @BindView(R.id.edit_class2_boarders_term1_fee) EditText editClass2BoardersTerm1Fee;
    @BindView(R.id.edit_class2_boarders_term2_fee) EditText editClass2BoardersTerm2Fee;
    @BindView(R.id.edit_class2_boarders_term3_fee) EditText editClass2BoardersTerm3Fee;
    @BindView(R.id.edit_class3_boarders_term1_fee) EditText editClass3BoardersTerm1Fee;
    @BindView(R.id.edit_class3_boarders_term2_fee) EditText editClass3BoardersTerm2Fee;
    @BindView(R.id.edit_class3_boarders_term3_fee) EditText editClass3BoardersTerm3Fee;
    @BindView(R.id.edit_class4_boarders_term1_fee) EditText editClass4BoardersTerm1Fee;
    @BindView(R.id.edit_class4_boarders_term2_fee) EditText editClass4BoardersTerm2Fee;
    @BindView(R.id.edit_class4_boarders_term3_fee) EditText editClass4BoardersTerm3Fee;
    @BindView(R.id.edit_class5_boarders_term1_fee) EditText editClass5BoardersTerm1Fee;
    @BindView(R.id.edit_class5_boarders_term2_fee) EditText editClass5BoardersTerm2Fee;
    @BindView(R.id.edit_class5_boarders_term3_fee) EditText editClass5BoardersTerm3Fee;
    @BindView(R.id.edit_class6_boarders_term1_fee) EditText editClass6BoardersTerm1Fee;
    @BindView(R.id.edit_class6_boarders_term2_fee) EditText editClass6BoardersTerm2Fee;
    @BindView(R.id.edit_class6_boarders_term3_fee) EditText editClass6BoardersTerm3Fee;
    @BindView(R.id.edit_class7_boarders_term1_fee) EditText editClass7BoardersTerm1Fee;
    @BindView(R.id.edit_class7_boarders_term2_fee) EditText editClass7BoardersTerm2Fee;
    @BindView(R.id.edit_class7_boarders_term3_fee) EditText editClass7BoardersTerm3Fee;
    @BindView(R.id.edit_class8_boarders_term1_fee) EditText editClass8BoardersTerm1Fee;
    @BindView(R.id.edit_class8_boarders_term2_fee) EditText editClass8BoardersTerm2Fee;
    @BindView(R.id.edit_class8_boarders_term3_fee) EditText editClass8BoardersTerm3Fee;

    @BindView(R.id.edit_pp1_days_term1_fee) EditText editPp1DaysTerm1Fee;
    @BindView(R.id.edit_pp1_days_term2_fee) EditText editPp1DaysTerm2Fee;
    @BindView(R.id.edit_pp1_days_term3_fee) EditText editPp1DaysTerm3Fee;
    @BindView(R.id.edit_pp2_days_term1_fee) EditText editPp2DaysTerm1Fee;
    @BindView(R.id.edit_pp2_days_term2_fee) EditText editPp2DaysTerm2Fee;
    @BindView(R.id.edit_pp2_days_term3_fee) EditText editPp2DaysTerm3Fee;
    @BindView(R.id.edit_class1_days_term1_fee) EditText editClass1DaysTerm1Fee;
    @BindView(R.id.edit_class1_days_term2_fee) EditText editClass1DaysTerm2Fee;
    @BindView(R.id.edit_class1_days_term3_fee) EditText editClass1DaysTerm3Fee;
    @BindView(R.id.edit_class2_days_term1_fee) EditText editClass2DaysTerm1Fee;
    @BindView(R.id.edit_class2_days_term2_fee) EditText editClass2DaysTerm2Fee;
    @BindView(R.id.edit_class2_days_term3_fee) EditText editClass2DaysTerm3Fee;
    @BindView(R.id.edit_class3_days_term1_fee) EditText editClass3DaysTerm1Fee;
    @BindView(R.id.edit_class3_days_term2_fee) EditText editClass3DaysTerm2Fee;
    @BindView(R.id.edit_class3_days_term3_fee) EditText editClass3DaysTerm3Fee;
    @BindView(R.id.edit_class4_days_term1_fee) EditText editClass4DaysTerm1Fee;
    @BindView(R.id.edit_class4_days_term2_fee) EditText editClass4DaysTerm2Fee;
    @BindView(R.id.edit_class4_days_term3_fee) EditText editClass4DaysTerm3Fee;
    @BindView(R.id.edit_class5_days_term1_fee) EditText editClass5DaysTerm1Fee;
    @BindView(R.id.edit_class5_days_term2_fee) EditText editClass5DaysTerm2Fee;
    @BindView(R.id.edit_class5_days_term3_fee) EditText editClass5DaysTerm3Fee;
    @BindView(R.id.edit_class6_days_term1_fee) EditText editClass6DaysTerm1Fee;
    @BindView(R.id.edit_class6_days_term2_fee) EditText editClass6DaysTerm2Fee;
    @BindView(R.id.edit_class6_days_term3_fee) EditText editClass6DaysTerm3Fee;
    @BindView(R.id.edit_class7_days_term1_fee) EditText editClass7DaysTerm1Fee;
    @BindView(R.id.edit_class7_days_term2_fee) EditText editClass7DaysTerm2Fee;
    @BindView(R.id.edit_class7_days_term3_fee) EditText editClass7DaysTerm3Fee;
    @BindView(R.id.edit_class8_days_term1_fee) EditText editClass8DaysTerm1Fee;
    @BindView(R.id.edit_class8_days_term2_fee) EditText editClass8DaysTerm2Fee;
    @BindView(R.id.edit_class8_days_term3_fee) EditText editClass8DaysTerm3Fee;

    private DatabaseReference AcademicYearsRef,NewAcademicYearRef,YearDetailsRef, YearFeeStructureRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_academic_year);

        ButterKnife.bind(this);

        YearDetailsRef = FirebaseDatabase.getInstance().getReference().child("Years");

        AcademicYearsRef = YearDetailsRef.child("AcademicYears");
        NewAcademicYearRef = YearDetailsRef.child("NewAcademicYear");
        YearFeeStructureRef = YearDetailsRef.child("YearDetails");
    }

    public void createYearWithTermDatesAndFeeStructures (){
        String academicYear = editAcademicYear.getText().toString();
        String term1StartDate = termDatesPicker(editTerm1StartDate);
        String term1EndDate = termDatesPicker(editTerm1EndDate);
        String term2StartDate = termDatesPicker(editTerm2StartDate);
        String term2EndDate = termDatesPicker(editTerm2EndDate);
        String term3StartDate = termDatesPicker(editTerm3StartDate);
        String term3EndDate = termDatesPicker(editTerm3EndDate);

        String pp1BoardersTerm1Fee = editPp1BoardersTerm1Fee.getText().toString();
        String pp1BoardersTerm2Fee = editPp1BoardersTerm2Fee.getText().toString();
        String pp1BoardersTerm3Fee = editPp1BoardersTerm3Fee.getText().toString();
        String pp2BoardersTerm1Fee = editPp2BoardersTerm1Fee.getText().toString();
        String pp2BoardersTerm2Fee = editPp2BoardersTerm2Fee.getText().toString();
        String pp2BoardersTerm3Fee = editPp2BoardersTerm3Fee.getText().toString();
        String class1BoardersTerm1Fee = editClass1BoardersTerm1Fee.getText().toString();
        String class1BoardersTerm2Fee = editClass1BoardersTerm2Fee.getText().toString();
        String class1BoardersTerm3Fee = editClass1BoardersTerm3Fee.getText().toString();
        String class2BoardersTerm1Fee = editClass2BoardersTerm1Fee.getText().toString();
        String class2BoardersTerm2Fee = editClass2BoardersTerm2Fee.getText().toString();
        String class2BoardersTerm3Fee = editClass2BoardersTerm3Fee.getText().toString();
        String class3BoardersTerm1Fee = editClass3BoardersTerm1Fee.getText().toString();
        String class3BoardersTerm2Fee = editClass3BoardersTerm2Fee.getText().toString();
        String class3BoardersTerm3Fee = editClass3BoardersTerm3Fee.getText().toString();
        String class4BoardersTerm1Fee = editClass4BoardersTerm1Fee.getText().toString();
        String class4BoardersTerm2Fee = editClass4BoardersTerm2Fee.getText().toString();
        String class4BoardersTerm3Fee = editClass4BoardersTerm3Fee.getText().toString();
        String class5BoardersTerm1Fee = editClass5BoardersTerm1Fee.getText().toString();
        String class5BoardersTerm2Fee = editClass5BoardersTerm2Fee.getText().toString();
        String class5BoardersTerm3Fee = editClass5BoardersTerm3Fee.getText().toString();
        String class6BoardersTerm1Fee = editClass6BoardersTerm1Fee.getText().toString();
        String class6BoardersTerm2Fee = editClass6BoardersTerm2Fee.getText().toString();
        String class6BoardersTerm3Fee = editClass6BoardersTerm3Fee.getText().toString();
        String class7BoardersTerm1Fee = editClass7BoardersTerm1Fee.getText().toString();
        String class7BoardersTerm2Fee = editClass7BoardersTerm2Fee.getText().toString();
        String class7BoardersTerm3Fee = editClass7BoardersTerm3Fee.getText().toString();
        String class8BoardersTerm1Fee = editClass8BoardersTerm1Fee.getText().toString();
        String class8BoardersTerm2Fee = editClass8BoardersTerm2Fee.getText().toString();
        String class8BoardersTerm3Fee = editClass8BoardersTerm3Fee.getText().toString();

        String pp1DaysTerm1Fee = editPp1DaysTerm1Fee.getText().toString();
        String pp1DaysTerm2Fee = editPp1DaysTerm2Fee.getText().toString();
        String pp1DaysTerm3Fee = editPp1DaysTerm3Fee.getText().toString();
        String pp2DaysTerm1Fee = editPp2DaysTerm1Fee.getText().toString();
        String pp2DaysTerm2Fee = editPp2DaysTerm2Fee.getText().toString();
        String pp2DaysTerm3Fee = editPp2DaysTerm3Fee.getText().toString();
        String class1DaysTerm1Fee = editClass1DaysTerm1Fee.getText().toString();
        String class1DaysTerm2Fee = editClass1DaysTerm2Fee.getText().toString();
        String class1DaysTerm3Fee = editClass1DaysTerm3Fee.getText().toString();
        String class2DaysTerm1Fee = editClass2DaysTerm1Fee.getText().toString();
        String class2DaysTerm2Fee = editClass2DaysTerm2Fee.getText().toString();
        String class2DaysTerm3Fee = editClass2DaysTerm3Fee.getText().toString();
        String class3DaysTerm1Fee = editClass3DaysTerm1Fee.getText().toString();
        String class3DaysTerm2Fee = editClass3DaysTerm2Fee.getText().toString();
        String class3DaysTerm3Fee = editClass3DaysTerm3Fee.getText().toString();
        String class4DaysTerm1Fee = editClass4DaysTerm1Fee.getText().toString();
        String class4DaysTerm2Fee = editClass4DaysTerm2Fee.getText().toString();
        String class4DaysTerm3Fee = editClass4DaysTerm3Fee.getText().toString();
        String class5DaysTerm1Fee = editClass5DaysTerm1Fee.getText().toString();
        String class5DaysTerm2Fee = editClass5DaysTerm2Fee.getText().toString();
        String class5DaysTerm3Fee = editClass5DaysTerm3Fee.getText().toString();
        String class6DaysTerm1Fee = editClass6DaysTerm1Fee.getText().toString();
        String class6DaysTerm2Fee = editClass6DaysTerm2Fee.getText().toString();
        String class6DaysTerm3Fee = editClass6DaysTerm3Fee.getText().toString();
        String class7DaysTerm1Fee = editClass7DaysTerm1Fee.getText().toString();
        String class7DaysTerm2Fee = editClass7DaysTerm2Fee.getText().toString();
        String class7DaysTerm3Fee = editClass7DaysTerm3Fee.getText().toString();
        String class8DaysTerm1Fee = editClass8DaysTerm1Fee.getText().toString();
        String class8DaysTerm2Fee = editClass8DaysTerm2Fee.getText().toString();
        String class8DaysTerm3Fee = editClass8DaysTerm3Fee.getText().toString();

        boolean isValidInputs = validInputs (academicYear, term1StartDate, term1EndDate, term2StartDate, term2EndDate, term3StartDate, term3EndDate, pp1BoardersTerm1Fee, pp1BoardersTerm2Fee, pp1BoardersTerm3Fee, pp2BoardersTerm1Fee, pp2BoardersTerm2Fee, pp2BoardersTerm3Fee, class1BoardersTerm1Fee, class1BoardersTerm2Fee, class1BoardersTerm3Fee, class2BoardersTerm1Fee, class2BoardersTerm2Fee, class2BoardersTerm3Fee, class3BoardersTerm1Fee, class3BoardersTerm2Fee, class3BoardersTerm3Fee, class4BoardersTerm1Fee, class4BoardersTerm2Fee, class4BoardersTerm3Fee, class5BoardersTerm1Fee, class5BoardersTerm2Fee, class5BoardersTerm3Fee, class6BoardersTerm1Fee, class6BoardersTerm2Fee, class6BoardersTerm3Fee, class7BoardersTerm1Fee, class7BoardersTerm2Fee, class7BoardersTerm3Fee, class8BoardersTerm1Fee, class8BoardersTerm2Fee, class8BoardersTerm3Fee, pp1DaysTerm1Fee, pp1DaysTerm2Fee, pp1DaysTerm3Fee, pp2DaysTerm1Fee, pp2DaysTerm2Fee, pp2DaysTerm3Fee, class1DaysTerm1Fee, class1DaysTerm2Fee, class1DaysTerm3Fee, class2DaysTerm1Fee, class2DaysTerm2Fee, class2DaysTerm3Fee, class3DaysTerm1Fee, class3DaysTerm2Fee, class3DaysTerm3Fee, class4DaysTerm1Fee, class4DaysTerm2Fee, class4DaysTerm3Fee, class5DaysTerm1Fee, class5DaysTerm2Fee, class5DaysTerm3Fee, class6DaysTerm1Fee, class6DaysTerm2Fee, class6DaysTerm3Fee, class7DaysTerm1Fee, class7DaysTerm2Fee, class7DaysTerm3Fee, class8DaysTerm1Fee, class8DaysTerm2Fee, class8DaysTerm3Fee);

        if (!isValidInputs)
        {
            validInputs (academicYear, term1StartDate, term1EndDate, term2StartDate, term2EndDate, term3StartDate, term3EndDate, pp1BoardersTerm1Fee, pp1BoardersTerm2Fee, pp1BoardersTerm3Fee, pp2BoardersTerm1Fee, pp2BoardersTerm2Fee, pp2BoardersTerm3Fee, class1BoardersTerm1Fee, class1BoardersTerm2Fee, class1BoardersTerm3Fee, class2BoardersTerm1Fee, class2BoardersTerm2Fee, class2BoardersTerm3Fee, class3BoardersTerm1Fee, class3BoardersTerm2Fee, class3BoardersTerm3Fee, class4BoardersTerm1Fee, class4BoardersTerm2Fee, class4BoardersTerm3Fee, class5BoardersTerm1Fee, class5BoardersTerm2Fee, class5BoardersTerm3Fee, class6BoardersTerm1Fee, class6BoardersTerm2Fee, class6BoardersTerm3Fee, class7BoardersTerm1Fee, class7BoardersTerm2Fee, class7BoardersTerm3Fee, class8BoardersTerm1Fee, class8BoardersTerm2Fee, class8BoardersTerm3Fee, pp1DaysTerm1Fee, pp1DaysTerm2Fee, pp1DaysTerm3Fee, pp2DaysTerm1Fee, pp2DaysTerm2Fee, pp2DaysTerm3Fee, class1DaysTerm1Fee, class1DaysTerm2Fee, class1DaysTerm3Fee, class2DaysTerm1Fee, class2DaysTerm2Fee, class2DaysTerm3Fee, class3DaysTerm1Fee, class3DaysTerm2Fee, class3DaysTerm3Fee, class4DaysTerm1Fee, class4DaysTerm2Fee, class4DaysTerm3Fee, class5DaysTerm1Fee, class5DaysTerm2Fee, class5DaysTerm3Fee, class6DaysTerm1Fee, class6DaysTerm2Fee, class6DaysTerm3Fee, class7DaysTerm1Fee, class7DaysTerm2Fee, class7DaysTerm3Fee, class8DaysTerm1Fee, class8DaysTerm2Fee, class8DaysTerm3Fee);
        }
        else {
            DatabaseReference academicYearKeyRef = AcademicYearsRef.push();
            String academicYearPushId = academicYearKeyRef.getKey();
            AcademicYear academicYearModel = new AcademicYear(academicYear, term1StartDate, term3EndDate);
            AcademicYearsRef.child(academicYearPushId).setValue(academicYearModel);

            Map<String, Object> academicYearDetails = new HashMap<>();
            academicYearDetails.put(academicYearPushId+"/TermDates/"+TERM_1+"/startDate", term1StartDate);
            academicYearDetails.put(academicYearPushId+"/TermDates/"+TERM_1+"/endDate", term1EndDate);
            academicYearDetails.put(academicYearPushId+"/TermDates/"+TERM_2+"/startDate", term2StartDate);
            academicYearDetails.put(academicYearPushId+"/TermDates/"+TERM_2+"/endDate", term2EndDate);
            academicYearDetails.put(academicYearPushId+"/TermDates/"+TERM_3+"/startDate", term3StartDate);
            academicYearDetails.put(academicYearPushId+"/TermDates/"+TERM_3+"/endDate", term3EndDate);

            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+PRE_PR1_1+"/"+TERM_1, pp1BoardersTerm1Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+PRE_PR1_1+"/"+TERM_2, pp1BoardersTerm2Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+PRE_PR1_1+"/"+TERM_3, pp1BoardersTerm3Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+PRE_PR1_2+"/"+TERM_1, pp2BoardersTerm1Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+PRE_PR1_2+"/"+TERM_2, pp2BoardersTerm2Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+PRE_PR1_2+"/"+TERM_3, pp2BoardersTerm3Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_1+"/"+TERM_1, class1BoardersTerm1Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_1+"/"+TERM_2, class1BoardersTerm2Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_1+"/"+TERM_3, class1BoardersTerm3Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_2+"/"+TERM_1, class2BoardersTerm1Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_2+"/"+TERM_2, class2BoardersTerm2Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_2+"/"+TERM_3, class2BoardersTerm3Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_3+"/"+TERM_1, class3BoardersTerm1Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_3+"/"+TERM_2, class3BoardersTerm2Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_3+"/"+TERM_3, class3BoardersTerm3Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_4+"/"+TERM_1, class4BoardersTerm1Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_4+"/"+TERM_2, class4BoardersTerm2Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_4+"/"+TERM_3, class4BoardersTerm3Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_5+"/"+TERM_1, class5BoardersTerm1Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_5+"/"+TERM_2, class5BoardersTerm2Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_5+"/"+TERM_3, class5BoardersTerm3Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_6+"/"+TERM_1, class6BoardersTerm1Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_6+"/"+TERM_2, class6BoardersTerm2Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_6+"/"+TERM_3, class6BoardersTerm3Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_7+"/"+TERM_1, class7BoardersTerm1Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_7+"/"+TERM_2, class7BoardersTerm2Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_7+"/"+TERM_3, class7BoardersTerm3Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_8+"/"+TERM_1, class8BoardersTerm1Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_8+"/"+TERM_2, class8BoardersTerm2Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+BOARDER+"/"+Class_8+"/"+TERM_3, class8BoardersTerm3Fee);

            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+PRE_PR1_1+"/"+TERM_1, pp1DaysTerm1Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+PRE_PR1_1+"/"+TERM_2, pp1DaysTerm2Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+PRE_PR1_1+"/"+TERM_3, pp1DaysTerm3Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+PRE_PR1_2+"/"+TERM_1, pp2DaysTerm1Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+PRE_PR1_2+"/"+TERM_2, pp2DaysTerm2Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+PRE_PR1_2+"/"+TERM_3, pp2DaysTerm3Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_1+"/"+TERM_1, class1DaysTerm1Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_1+"/"+TERM_2, class1DaysTerm2Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_1+"/"+TERM_3, class1DaysTerm3Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_2+"/"+TERM_1, class2DaysTerm1Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_2+"/"+TERM_2, class2DaysTerm2Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_2+"/"+TERM_3, class2DaysTerm3Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_3+"/"+TERM_1, class3DaysTerm1Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_3+"/"+TERM_2, class3DaysTerm2Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_3+"/"+TERM_3, class3DaysTerm3Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_4+"/"+TERM_1, class4DaysTerm1Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_4+"/"+TERM_2, class4DaysTerm2Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_4+"/"+TERM_3, class4DaysTerm3Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_5+"/"+TERM_1, class5DaysTerm1Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_5+"/"+TERM_2, class5DaysTerm2Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_5+"/"+TERM_3, class5DaysTerm3Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_6+"/"+TERM_1, class6DaysTerm1Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_6+"/"+TERM_2, class6DaysTerm2Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_6+"/"+TERM_3, class6DaysTerm3Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_7+"/"+TERM_1, class7DaysTerm1Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_7+"/"+TERM_2, class7DaysTerm2Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_7+"/"+TERM_3, class7DaysTerm3Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_8+"/"+TERM_1, class8DaysTerm1Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_8+"/"+TERM_2, class8DaysTerm2Fee);
            academicYearDetails.put(academicYearPushId+"/FeeStructure/"+DAY+"/"+Class_8+"/"+TERM_3, class8DaysTerm3Fee);

            Map<String, Object> newDates = new HashMap<>();

            newDates.put("AcademicYearId", academicYearPushId);
            newDates.put("term", TERM_1);

            NewAcademicYearRef.updateChildren(newDates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    YearFeeStructureRef.updateChildren(academicYearDetails);
                }
            });

        }

    }

    public boolean validInputs (String academicYear, String term1StartDate, String term1EndDate, String term2StartDate, String term2EndDate, String term3StartDate, String term3EndDate, String pp1BoardersTerm1Fee, String pp1BoardersTerm2Fee, String pp1BoardersTerm3Fee, String pp2BoardersTerm1Fee, String pp2BoardersTerm2Fee, String pp2BoardersTerm3Fee, String class1BoardersTerm1Fee, String class1BoardersTerm2Fee, String class1BoardersTerm3Fee, String class2BoardersTerm1Fee, String class2BoardersTerm2Fee, String class2BoardersTerm3Fee, String class3BoardersTerm1Fee, String class3BoardersTerm2Fee, String class3BoardersTerm3Fee, String class4BoardersTerm1Fee, String class4BoardersTerm2Fee, String class4BoardersTerm3Fee, String class5BoardersTerm1Fee, String class5BoardersTerm2Fee, String class5BoardersTerm3Fee, String class6BoardersTerm1Fee, String class6BoardersTerm2Fee, String class6BoardersTerm3Fee, String class7BoardersTerm1Fee, String class7BoardersTerm2Fee, String class7BoardersTerm3Fee, String class8BoardersTerm1Fee, String class8BoardersTerm2Fee, String class8BoardersTerm3Fee, String pp1DaysTerm1Fee, String pp1DaysTerm2Fee, String pp1DaysTerm3Fee, String pp2DaysTerm1Fee, String pp2DaysTerm2Fee, String pp2DaysTerm3Fee, String class1DaysTerm1Fee, String class1DaysTerm2Fee, String class1DaysTerm3Fee, String class2DaysTerm1Fee, String class2DaysTerm2Fee, String class2DaysTerm3Fee, String class3DaysTerm1Fee, String class3DaysTerm2Fee, String class3DaysTerm3Fee, String class4DaysTerm1Fee, String class4DaysTerm2Fee, String class4DaysTerm3Fee, String class5DaysTerm1Fee, String class5DaysTerm2Fee, String class5DaysTerm3Fee, String class6DaysTerm1Fee, String class6DaysTerm2Fee, String class6DaysTerm3Fee, String class7DaysTerm1Fee, String class7DaysTerm2Fee, String class7DaysTerm3Fee, String class8DaysTerm1Fee, String class8DaysTerm2Fee, String class8DaysTerm3Fee) {
        if (academicYear == null || academicYear.trim().isEmpty()) {
            editAcademicYear.setError("This field is required");
            return false;
        }
        else if (term1StartDate == null || term1StartDate.trim().isEmpty()){
            editTerm1StartDate.setError("This field is required");
            return false;
        }
        else if (term1EndDate == null || term1EndDate.trim().isEmpty()){
            editTerm1EndDate.setError("This field is required");
            return false;
        }
        else if (term2StartDate == null || term2StartDate.trim().isEmpty()){
            editTerm2StartDate.setError("This field is required");
            return false;
        }
        else if (term2EndDate == null || term2EndDate.trim().isEmpty()){
            editTerm2EndDate.setError("This field is required");
            return false;
        }
        else if (term3StartDate == null || term3StartDate.trim().isEmpty()){
            editTerm3StartDate.setError("This field is required");
            return false;
        }
        else if (term3EndDate == null || term3EndDate.trim().isEmpty()){
            editTerm3EndDate.setError("This field is required");
            return false;
        }
        else if (pp1BoardersTerm1Fee == null || pp1BoardersTerm1Fee.trim().isEmpty()){
            editPp1BoardersTerm1Fee.setError("This field is required");
            return false;
        }
        else if (pp1BoardersTerm2Fee == null || pp1BoardersTerm2Fee.trim().isEmpty()){
            editPp1BoardersTerm2Fee.setError("This field is required");
            return false;
        }
        else if (pp1BoardersTerm3Fee == null || pp1BoardersTerm3Fee.trim().isEmpty()){
            editPp1BoardersTerm3Fee.setError("This field is required");
            return false;
        }
        else if (pp2BoardersTerm1Fee == null || pp2BoardersTerm1Fee.trim().isEmpty()){
            editPp2BoardersTerm1Fee.setError("This field is required");
            return false;
        }
        else if (pp2BoardersTerm2Fee == null || pp2BoardersTerm2Fee.trim().isEmpty()){
            editPp2BoardersTerm2Fee.setError("This field is required");
            return false;
        }
        else if (pp2BoardersTerm3Fee == null || pp2BoardersTerm3Fee.trim().isEmpty()){
            editPp2BoardersTerm3Fee.setError("This field is required");
            return false;
        }
        else if (class1BoardersTerm1Fee == null || class1BoardersTerm1Fee.trim().isEmpty()){
            editClass1BoardersTerm1Fee.setError("This field is required");
            return false;
        }
        else if (class1BoardersTerm2Fee == null || class1BoardersTerm2Fee.trim().isEmpty()){
            editClass1BoardersTerm2Fee.setError("This field is required");
            return false;
        }
        else if (class1BoardersTerm3Fee == null || class1BoardersTerm3Fee.trim().isEmpty()){
            editClass1BoardersTerm3Fee.setError("This field is required");
            return false;
        }
        else if (class2BoardersTerm1Fee == null || class2BoardersTerm1Fee.trim().isEmpty()){
            editClass2BoardersTerm1Fee.setError("This field is required");
            return false;
        }
        else if (class2BoardersTerm2Fee == null || class2BoardersTerm2Fee.trim().isEmpty()){
            editClass2BoardersTerm2Fee.setError("This field is required");
            return false;
        }
        else if (class2BoardersTerm3Fee == null || class2BoardersTerm3Fee.trim().isEmpty()){
            editClass2BoardersTerm3Fee.setError("This field is required");
            return false;
        }
        else if (class3BoardersTerm1Fee == null || class3BoardersTerm1Fee.trim().isEmpty()){
            editClass3BoardersTerm1Fee.setError("This field is required");
            return false;
        }
        else if (class3BoardersTerm2Fee == null || class3BoardersTerm2Fee.trim().isEmpty()){
            editClass3BoardersTerm2Fee.setError("This field is required");
            return false;
        }
        else if (class3BoardersTerm3Fee == null || class3BoardersTerm3Fee.trim().isEmpty()){
            editClass3BoardersTerm3Fee.setError("This field is required");
            return false;
        }
        else if (class4BoardersTerm1Fee == null || class4BoardersTerm1Fee.trim().isEmpty()){
            editClass4BoardersTerm1Fee.setError("This field is required");
            return false;
        }
        else if (class4BoardersTerm2Fee == null || class4BoardersTerm2Fee.trim().isEmpty()){
            editClass2BoardersTerm2Fee.setError("This field is required");
            return false;
        }
        else if (class4BoardersTerm3Fee == null || class4BoardersTerm3Fee.trim().isEmpty()){
            editClass4BoardersTerm3Fee.setError("This field is required");
            return false;
        }
        else if (class5BoardersTerm1Fee == null || class5BoardersTerm1Fee.trim().isEmpty()){
            editClass5BoardersTerm1Fee.setError("This field is required");
            return false;
        }
        else if (class5BoardersTerm2Fee == null || class5BoardersTerm2Fee.trim().isEmpty()){
            editClass5BoardersTerm2Fee.setError("This field is required");
            return false;
        }
        else if (class5BoardersTerm3Fee == null || class5BoardersTerm3Fee.trim().isEmpty()){
            editClass5BoardersTerm3Fee.setError("This field is required");
            return false;
        }
        else if (class6BoardersTerm1Fee == null || class6BoardersTerm1Fee.trim().isEmpty()){
            editClass6BoardersTerm1Fee.setError("This field is required");
            return false;
        }
        else if (class6BoardersTerm2Fee == null || class6BoardersTerm2Fee.trim().isEmpty()){
            editClass6BoardersTerm2Fee.setError("This field is required");
            return false;
        }
        else if (class6BoardersTerm3Fee == null || class6BoardersTerm3Fee.trim().isEmpty()){
            editClass6BoardersTerm3Fee.setError("This field is required");
            return false;
        }
        else if (class7BoardersTerm1Fee == null || class7BoardersTerm1Fee.trim().isEmpty()){
            editClass7BoardersTerm1Fee.setError("This field is required");
            return false;
        }
        else if (class7BoardersTerm2Fee == null || class7BoardersTerm2Fee.trim().isEmpty()){
            editClass7BoardersTerm2Fee.setError("This field is required");
            return false;
        }
        else if (class7BoardersTerm3Fee == null || class7BoardersTerm3Fee.trim().isEmpty()){
            editClass7BoardersTerm3Fee.setError("This field is required");
            return false;
        }
        else if (class8BoardersTerm1Fee == null || class8BoardersTerm1Fee.trim().isEmpty()){
            editClass8BoardersTerm1Fee.setError("This field is required");
            return false;
        }
        else if (class8BoardersTerm2Fee == null || class8BoardersTerm2Fee.trim().isEmpty()){
            editClass8BoardersTerm2Fee.setError("This field is required");
            return false;
        }
        else if (class8BoardersTerm3Fee == null || class8BoardersTerm3Fee.trim().isEmpty()){
            editClass8BoardersTerm3Fee.setError("This field is required");
            return false;
        }
        else if (pp1DaysTerm1Fee == null || pp1DaysTerm1Fee.trim().isEmpty()){
            editPp1DaysTerm1Fee.setError("This field is required");
            return false;
        }
        else if (pp1DaysTerm2Fee == null || pp1DaysTerm2Fee.trim().isEmpty()){
            editPp1DaysTerm2Fee.setError("This field is required");
            return false;
        }
        else if (pp1DaysTerm3Fee == null || pp1DaysTerm3Fee.trim().isEmpty()){
            editPp1DaysTerm3Fee.setError("This field is required");
            return false;
        }
        else if (pp2DaysTerm1Fee == null || pp2DaysTerm1Fee.trim().isEmpty()){
            editPp2DaysTerm1Fee.setError("This field is required");
            return false;
        }
        else if (pp2DaysTerm2Fee == null || pp2DaysTerm2Fee.trim().isEmpty()){
            editPp2DaysTerm2Fee.setError("This field is required");
            return false;
        }
        else if (pp2DaysTerm3Fee == null || pp2DaysTerm3Fee.trim().isEmpty()){
            editPp2DaysTerm3Fee.setError("This field is required");
            return false;
        }
        else if (class1DaysTerm1Fee == null || class1DaysTerm1Fee.trim().isEmpty()){
            editClass1DaysTerm1Fee.setError("This field is required");
            return false;
        }
        else if (class1DaysTerm2Fee == null || class1DaysTerm2Fee.trim().isEmpty()){
            editClass1DaysTerm2Fee.setError("This field is required");
            return false;
        }
        else if (class1DaysTerm3Fee == null || class1DaysTerm3Fee.trim().isEmpty()){
            editClass1DaysTerm3Fee.setError("This field is required");
            return false;
        }
        else if (class2DaysTerm1Fee == null || class2DaysTerm1Fee.trim().isEmpty()){
            editClass2DaysTerm1Fee.setError("This field is required");
            return false;
        }
        else if (class2DaysTerm2Fee == null || class2DaysTerm2Fee.trim().isEmpty()){
            editClass2DaysTerm2Fee.setError("This field is required");
            return false;
        }
        else if (class2DaysTerm3Fee == null || class2DaysTerm3Fee.trim().isEmpty()){
            editClass2DaysTerm3Fee.setError("This field is required");
            return false;
        }
        else if (class3DaysTerm1Fee == null || class3DaysTerm1Fee.trim().isEmpty()){
            editClass3DaysTerm1Fee.setError("This field is required");
            return false;
        }
        else if (class3DaysTerm2Fee == null || class3DaysTerm2Fee.trim().isEmpty()){
            editClass3DaysTerm2Fee.setError("This field is required");
            return false;
        }
        else if (class3DaysTerm3Fee == null || class3DaysTerm3Fee.trim().isEmpty()){
            editClass3DaysTerm3Fee.setError("This field is required");
            return false;
        }
        else if (class4DaysTerm1Fee == null || class4DaysTerm1Fee.trim().isEmpty()){
            editClass4DaysTerm1Fee.setError("This field is required");
            return false;
        }
        else if (class4DaysTerm2Fee == null || class4DaysTerm2Fee.trim().isEmpty()){
            editClass2DaysTerm2Fee.setError("This field is required");
            return false;
        }
        else if (class4DaysTerm3Fee == null || class4DaysTerm3Fee.trim().isEmpty()){
            editClass4DaysTerm3Fee.setError("This field is required");
            return false;
        }
        else if (class5DaysTerm1Fee == null || class5DaysTerm1Fee.trim().isEmpty()){
            editClass5DaysTerm1Fee.setError("This field is required");
            return false;
        }
        else if (class5DaysTerm2Fee == null || class5DaysTerm2Fee.trim().isEmpty()){
            editClass5DaysTerm2Fee.setError("This field is required");
            return false;
        }
        else if (class5DaysTerm3Fee == null || class5DaysTerm3Fee.trim().isEmpty()){
            editClass5DaysTerm3Fee.setError("This field is required");
            return false;
        }
        else if (class6DaysTerm1Fee == null || class6DaysTerm1Fee.trim().isEmpty()){
            editClass6DaysTerm1Fee.setError("This field is required");
            return false;
        }
        else if (class6DaysTerm2Fee == null || class6DaysTerm2Fee.trim().isEmpty()){
            editClass6DaysTerm2Fee.setError("This field is required");
            return false;
        }
        else if (class6DaysTerm3Fee == null || class6DaysTerm3Fee.trim().isEmpty()){
            editClass6DaysTerm3Fee.setError("This field is required");
            return false;
        }
        else if (class7DaysTerm1Fee == null || class7DaysTerm1Fee.trim().isEmpty()){
            editClass7DaysTerm1Fee.setError("This field is required");
            return false;
        }
        else if (class7DaysTerm2Fee == null || class7DaysTerm2Fee.trim().isEmpty()){
            editClass7DaysTerm2Fee.setError("This field is required");
            return false;
        }
        else if (class7DaysTerm3Fee == null || class7DaysTerm3Fee.trim().isEmpty()){
            editClass7DaysTerm3Fee.setError("This field is required");
            return false;
        }
        else if (class8DaysTerm1Fee == null || class8DaysTerm1Fee.trim().isEmpty()){
            editClass8DaysTerm1Fee.setError("This field is required");
            return false;
        }
        else if (class8DaysTerm2Fee == null || class8DaysTerm2Fee.trim().isEmpty()){
            editClass8DaysTerm2Fee.setError("This field is required");
            return false;
        }
        else if (class8DaysTerm3Fee == null || class8DaysTerm3Fee.trim().isEmpty()){
            editClass8DaysTerm3Fee.setError("This field is required");
            return false;
        }
        else
        {
            return true;
        }
    }

    public String termDatesPicker (EditText editText) {
        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(editText, myCalendar);
        };

        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddNewAcademicYearActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return editText.getText().toString();
    }

    private void updateLabel(EditText editText, Calendar myCalendar) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText.setText(sdf.format(myCalendar.getTime()));
    }
}