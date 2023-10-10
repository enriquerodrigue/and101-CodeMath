package com.enriquerodriguez.codemath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.enriquerodriguez.codemath.databinding.ActivityMainBinding;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Slider slider;
    private EditText baseNumberEditText;
    private TextView tipPercentTextView;
    private TextView tipAmountTextView;
    private TextView totalAmountTextView;
    private Spinner partySizeSpinner;
    private ActivityMainBinding binding;
    private double tipAmount = 0;
    private int baseNumber = 0;
    private double totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);
        getViewsFromBinding();
        setSpinnerData();
        getTipAmount();
        baseNumberEditText.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getTipAmount();
                getTotalAmount();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                setTipPercent(value);
                getTipAmount();
                getTotalAmount();
            }
        });
        partySizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                partyCalculator();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private void getViewsFromBinding(){
        baseNumberEditText = binding.baseNumberEditText;
        slider = binding.slider;
        tipPercentTextView = binding.tipPercentTextView;
        tipAmountTextView = binding.tipAmountTextView;
        totalAmountTextView = binding.totalAmountTextView;
        partySizeSpinner = binding.partySizeSpinner;
    }

    private void getTipAmount(){
        if(!baseNumberEditText.getText().toString().equals("")) {
            baseNumber = Integer.parseInt(baseNumberEditText.getText().toString());
            int tipPercent = Integer.parseInt(tipPercentTextView.getText().toString()
                    .substring(0, tipPercentTextView.getText().toString().length() - 1));
            tipAmount = baseNumber * tipPercent / 100.0;
            tipAmountTextView.setText(String.valueOf(tipAmount));
        } else {
            tipAmountTextView.setText("");
            totalAmountTextView.setText("");
        }
    }

    private void getTotalAmount(){
        totalAmount = baseNumber + tipAmount;
        totalAmountTextView.setText(String.valueOf(totalAmount));
    }

    private void partyCalculator(){
        if(totalAmount > 0 && tipAmount > 0 ) {
            totalAmount /= Integer.parseInt(partySizeSpinner.getSelectedItem().toString());
            tipAmount /= Integer.parseInt(partySizeSpinner.getSelectedItem().toString());
            totalAmountTextView.setText(String.valueOf(totalAmount));
            tipAmountTextView.setText(String.valueOf(tipAmount));
        }
    }

    @SuppressLint("SetTextI18n")
    private void setTipPercent(float value){
        tipPercentTextView.setText(String.valueOf((int) value) + "%");
    }

    private void setSpinnerData(){
        List<String> numbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            numbers.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, numbers);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partySizeSpinner.setAdapter(adapter);
    }
}