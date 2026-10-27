package com.example.practicalfive;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Municipality extends AppCompatActivity {

    private TextView textViewName;
    private ImageView imageViewMunicipality;
    private TextView textViewInfo;
    private Button buttonVisitSite;

    private final TextProcessor textProcessor = new TextProcessor();
    private String municipalityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_municipality);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get references to the UI components
        textViewName = findViewById(R.id.textViewName);
        imageViewMunicipality = findViewById(R.id.imageViewMunicipality);
        textViewInfo = findViewById(R.id.textViewInfo);
        buttonVisitSite = findViewById(R.id.buttonVisitSite);

        // Retrieve the name passed from MainActivity and re-validate it
        String received = getIntent().getStringExtra(MainActivity.EXTRA_MUNICIPALITY);
        municipalityName = textProcessor.processMunicipality(received);

        if (municipalityName == null) {
            // Missing or invalid data: fail safely and go back
            Toast.makeText(this, R.string.error_invalid, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        customiseScreen();
    }

    /** Sets the name, image, summary and colours for the selected municipality. */
    private void customiseScreen() {
        int colourResId;   // colour resource for this municipality
        int imageResId;    // drawable resource for this municipality
        int infoIndex;     // index of the summary inside municipalities_array

        switch (municipalityName) {
            case "Beijing":
                colourResId = R.color.municipality_beijing;
                imageResId = R.drawable.beijing;
                infoIndex = 0;
                break;
            case "Shanghai":
                colourResId = R.color.municipality_shanghai;
                imageResId = R.drawable.shanghai;
                infoIndex = 1;
                break;
            case "Chongqing":
                colourResId = R.color.municipality_chongqing;
                imageResId = R.drawable.chongqing;
                infoIndex = 2;
                break;
            case "Tianjin":
                colourResId = R.color.municipality_tianjin;
                imageResId = R.drawable.tianjin;
                infoIndex = 3;
                break;
            default:
                finish();
                return;
        }

        // Resolve the colour resource to an actual colour value
        int colour = ContextCompat.getColor(this, colourResId);

        // Name bar: coloured background with light text
        textViewName.setText(municipalityName);
        textViewName.setBackgroundColor(colour);

        // Municipality image
        imageViewMunicipality.setImageResource(imageResId);

        // Summary text from the string array, coloured to match
        String[] summaries = getResources().getStringArray(R.array.municipalities_array);
        textViewInfo.setText(summaries[infoIndex]);
        textViewInfo.setTextColor(colour);

        // Visit site button tinted with the same colour
        buttonVisitSite.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(colour));
    }
}