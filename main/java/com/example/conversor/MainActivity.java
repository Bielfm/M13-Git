package com.example.conversor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerMeasure;
    private Button btnGoToConversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerMeasure = findViewById(R.id.spinnerMeasure);
        btnGoToConversion = findViewById(R.id.btnGoToConversion);

        btnGoToConversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedMeasure = spinnerMeasure.getSelectedItem().toString();
                Intent intent = new Intent(MainActivity.this, Conversor2.class);
                intent.putExtra("selectedMeasure", selectedMeasure);
                startActivity(intent);
            }
        });
    }
}

