package com.example.conversor;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Conversor2 extends AppCompatActivity {

    private EditText editTextValue;
    private Spinner spinnerFromUnit, spinnerToUnit;
    private TextView resultTextView;
    private Button btnConvert, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversor2);

        editTextValue = findViewById(R.id.editTextValue);
        spinnerFromUnit = findViewById(R.id.spinnerFromUnit);
        spinnerToUnit = findViewById(R.id.spinnerToUnit);
        resultTextView = findViewById(R.id.resultTextView);
        btnConvert = findViewById(R.id.btnConvert);
        btnBack = findViewById(R.id.btnBack);

        // Cargar unidades según la medida seleccionada
        String selectedMeasure = getIntent().getStringExtra("selectedMeasure");
        loadUnitsForMeasure(selectedMeasure);

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performConversion();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadUnitsForMeasure(String measure) {
        ArrayAdapter<CharSequence> adapter;

        switch (measure) {
            case "Peso":
                adapter = ArrayAdapter.createFromResource(this, R.array.peso_units, android.R.layout.simple_spinner_item);
                break;
            case "Volumen":
                adapter = ArrayAdapter.createFromResource(this, R.array.volumen_units, android.R.layout.simple_spinner_item);
                break;
            case "Distancia":
                adapter = ArrayAdapter.createFromResource(this, R.array.distancia_units, android.R.layout.simple_spinner_item);
                break;
            case "Temperatura":
                adapter = ArrayAdapter.createFromResource(this, R.array.temperatura_units, android.R.layout.simple_spinner_item);
                break;
            case "Capacidad Disco Duro":
                adapter = ArrayAdapter.createFromResource(this, R.array.disco_duro_units, android.R.layout.simple_spinner_item);
                break;
            default:
                throw new IllegalArgumentException("Medida desconocida: " + measure);
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromUnit.setAdapter(adapter);
        spinnerToUnit.setAdapter(adapter);
    }

    private void performConversion() {
        String value = editTextValue.getText().toString();
        String fromUnit = spinnerFromUnit.getSelectedItem().toString();
        String toUnit = spinnerToUnit.getSelectedItem().toString();

        if (value.isEmpty()) {
            resultTextView.setText("Por favor, ingresa un valor.");
            return;
        }

        try {
            BigDecimal inputValue = new BigDecimal(value);
            BigDecimal result = convertUnits(inputValue, fromUnit, toUnit);

            DecimalFormat decimalFormat = new DecimalFormat("#.###");
            resultTextView.setText(decimalFormat.format(result));

        } catch (Exception e) {
            resultTextView.setText("Error en la conversión.");
        }
    }

    private BigDecimal convertUnits(BigDecimal value, String fromUnit, String toUnit) {
        // Lógica de conversión (completa para cada unidad)
        BigDecimal conversionFactor = BigDecimal.ONE;
        // Conversión de peso
        if (fromUnit.equals("Kilogramo") && toUnit.equals("Gramo")) {
            conversionFactor = new BigDecimal("1000");
        } else if (fromUnit.equals("Gramo") && toUnit.equals("Kilogramo")) {
            conversionFactor = new BigDecimal("0.001");
        } else if (fromUnit.equals("Tonelada") && toUnit.equals("Kilogramo")) {
            conversionFactor = new BigDecimal("1000");
        } else if (fromUnit.equals("Miligramo") && toUnit.equals("Gramo")) {
            conversionFactor = new BigDecimal("0.001");
        } else if (fromUnit.equals("Microgramo") && toUnit.equals("Miligramo")) {
            conversionFactor = new BigDecimal("0.001");
        } else if (fromUnit.equals("Microgramo") && toUnit.equals("Gramo")) {
            conversionFactor = new BigDecimal("0.000001");
        } else if (fromUnit.equals("Gramo") && toUnit.equals("Miligramo")) {
            conversionFactor = new BigDecimal("1000");
        }

        // Conversión de volumen
        else if (fromUnit.equals("Litro") && toUnit.equals("Mililitro")) {
            conversionFactor = new BigDecimal("1000");
        } else if (fromUnit.equals("Mililitro") && toUnit.equals("Litro")) {
            conversionFactor = new BigDecimal("0.001");
        } else if (fromUnit.equals("Metro cúbico") && toUnit.equals("Litro")) {
            conversionFactor = new BigDecimal("1000");
        } else if (fromUnit.equals("Litro") && toUnit.equals("Metro cúbico")) {
            conversionFactor = new BigDecimal("0.001");
        }

        // Conversión de distancia
        else if (fromUnit.equals("Milímetro") && toUnit.equals("Centímetro")) {
            conversionFactor = new BigDecimal("0.1");
        } else if (fromUnit.equals("Centímetro") && toUnit.equals("Milímetro")) {
            conversionFactor = new BigDecimal("10");
        } else if (fromUnit.equals("Metro") && toUnit.equals("Centímetro")) {
            conversionFactor = new BigDecimal("100");
        } else if (fromUnit.equals("Kilómetro") && toUnit.equals("Metro")) {
            conversionFactor = new BigDecimal("1000");
        } else if (fromUnit.equals("Centímetro") && toUnit.equals("Metro")) {
            conversionFactor = new BigDecimal("0.01");
        }

        // Conversión de temperatura
        else if (fromUnit.equals("Celsius") && toUnit.equals("Kelvin")) {
            return value.add(new BigDecimal("273.15")); // Celsius a Kelvin
        } else if (fromUnit.equals("Kelvin") && toUnit.equals("Celsius")) {
            return value.subtract(new BigDecimal("273.15")); // Kelvin a Celsius
        } else if (fromUnit.equals("Celsius") && toUnit.equals("Fahrenheit")) {
            return value.multiply(new BigDecimal("9")).divide(new BigDecimal("5")).add(new BigDecimal("32")); // Celsius a Fahrenheit
        } else if (fromUnit.equals("Fahrenheit") && toUnit.equals("Celsius")) {
            return value.subtract(new BigDecimal("32")).multiply(new BigDecimal("5")).divide(new BigDecimal("9")); // Fahrenheit a Celsius
        } else if (fromUnit.equals("Fahrenheit") && toUnit.equals("Kelvin")) {
            return value.subtract(new BigDecimal("32"))
                    .multiply(new BigDecimal("5"))
                    .divide(new BigDecimal("9"))
                    .add(new BigDecimal("273.15")); // Fahrenheit a Kelvin
        }

        // Conversión de capacidad de disco duro
        else if (fromUnit.equals("Bit") && toUnit.equals("Byte")) {
            conversionFactor = new BigDecimal("0.125"); // 1 Byte = 8 Bits
        } else if (fromUnit.equals("Byte") && toUnit.equals("Bit")) {
            conversionFactor = new BigDecimal("8");
        } else if (fromUnit.equals("Kilobyte") && toUnit.equals("Byte")) {
            conversionFactor = new BigDecimal("1024");
        } else if (fromUnit.equals("Megabyte") && toUnit.equals("Kilobyte")) {
            conversionFactor = new BigDecimal("1024");
        } else if (fromUnit.equals("Gigabyte") && toUnit.equals("Megabyte")) {
            conversionFactor = new BigDecimal("1024");
        } else if (fromUnit.equals("Terabyte") && toUnit.equals("Gigabyte")) {
            conversionFactor = new BigDecimal("1024");
        } else if (fromUnit.equals("Gigabyte") && toUnit.equals("Kilobyte")) {
            conversionFactor = new BigDecimal("1048576"); // 1 GB = 1024 * 1024 KB
        }

       return value.multiply(conversionFactor);
    }
}