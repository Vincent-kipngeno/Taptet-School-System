package com.moringaschool.schoolsystem.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.moringaschool.schoolsystem.R;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_academic_year);

        ButterKnife.bind(this);
    }

    public void createYearWithTermDatesAndFeeStructures (){
        String academicYear = editAcademicYear.getText().toString();
        String term1StartDate = editTerm1StartDate.getText().toString();
        String term1EndDate = editTerm1EndDate.getText().toString();
        String term2StartDate = editTerm2StartDate.getText().toString();
        String term2EndDate = editTerm2EndDate.getText().toString();
        String term3StartDate = editTerm3StartDate.getText().toString();
        String term3EndDate = editTerm3EndDate.getText().toString();
    }

    public void termDatesPicker (EditText editText) {

    }
}