package com.moringaschool.schoolsystem.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.moringaschool.schoolsystem.R;
import com.moringaschool.schoolsystem.databinding.ActivityAddNewExamResultsBinding;

public class AddNewExamResults extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private ActivityAddNewExamResultsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddNewExamResultsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        fillClassSpinner();

    }

    public void fillClassSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.class_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.classSpinner.setAdapter(adapter);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public void onClick(View v) {

    }
}