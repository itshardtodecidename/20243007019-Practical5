package com.example.practicalfive;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;

import android.view.inputmethod.EditorInfo;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    // Intent extra key, shared with Municipality activity
    public static final String EXTRA_MUNICIPALITY = "com.example.practicalfive.MUNICIPALITY";

    private EditText editTextMunicipality;
    private final TextProcessor textProcessor = new TextProcessor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v
                    .setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the input field from the layout
        editTextMunicipality
                = findViewById(R.id.editTextMunicipality);

        // Listen for the keyboard's Go/Done (Enter) key
        editTextMunicipality
                .setOnEditorActionListener((v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_GO) {
                        handleInput();
                        return true;   // we handled the event
                    }
                    return false;
                });
    }

    /** Reads, validates and forwards the municipality name. */
    private void handleInput() {
        String rawInput = editTextMunicipality.getText().toString();
        String validatedName = textProcessor.processMunicipality(rawInput);

        if (validatedName == null) {
            // Invalid input: show an error and stay on this screen
            if (rawInput.trim().isEmpty()) {
                editTextMunicipality
                        .setError(getString(R.string.error_empty));
            } else {
                editTextMunicipality
                        .setError(getString(R.string.error_invalid));
            }
            return;
        }

        // Valid: open the second activity with an explicit intent
        Intent intent = new Intent(MainActivity.this, Municipality.class);
        intent
                .putExtra(EXTRA_MUNICIPALITY, validatedName);
        startActivity(intent);
    }
}