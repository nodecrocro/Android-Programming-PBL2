package com.example.pbl2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pbl2.databinding.ActivityCoursesBinding;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CoursesActivity extends AppCompatActivity {

    private static final String JSON_URL = "https://raw.githubusercontent.com/nodecrocro/Android-Programming-PBL2/refs/heads/main/programmes.json";

    private ActivityCoursesBinding binding;
    private JSONArray diplomaArray;
    private JSONArray gappArray;
    private JSONArray gufpArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_courses);

        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnDiploma.setOnClickListener(v -> displayDiplomaProgrammes());
        binding.btnGapp.setOnClickListener(v -> displayGappProgrammes());
        binding.btnGufp.setOnClickListener(v -> displayGufpProgrammes());

        binding.btnRetry.setOnClickListener(v -> fetchProgrammesData());

        fetchProgrammesData();
    }

    private void fetchProgrammesData() {
        binding.tvStatus.setVisibility(View.VISIBLE);
        binding.tvStatus.setText("Loading programmes...");
        binding.btnRetry.setVisibility(View.GONE);
        binding.layoutProgrammesContainer.removeAllViews();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                JSON_URL,
                null,
                response -> {
                    try {
                        diplomaArray = response.getJSONArray("diploma");
                        gappArray = response.getJSONArray("gapp");
                        gufpArray = response.getJSONArray("gufp");

                        binding.tvStatus.setVisibility(View.GONE);
                        // Display diploma programme by default
                        displayDiplomaProgrammes();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        binding.tvStatus.setVisibility(View.VISIBLE);
                        binding.tvStatus.setText("Error parsing programme data..");
                        binding.btnRetry.setVisibility(View.VISIBLE);
                    }
                },
                error -> {
                    binding.tvStatus.setVisibility(View.VISIBLE);
                    binding.tvStatus.setText("Unable to load programme information. Please check your internet connection..");
                    binding.btnRetry.setVisibility(View.VISIBLE);
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void displayDiplomaProgrammes() {
        binding.layoutProgrammesContainer.removeAllViews();

        if (diplomaArray == null || diplomaArray.length() == 0) {
            return;
        }

        for (int i = 0; i < diplomaArray.length(); i++) {
            try {
                JSONObject programme = diplomaArray.getJSONObject(i);

                String name = programme.optString("name", "");
                String category = programme.optString("category", "");
                String studyMode = programme.optString("study_mode", "");
                String duration = programme.optString("duration", "");
                String fee = programme.optString("fee", "");
                String status = programme.optString("status", "");
                String website = programme.optString("website", "");

                // Create Card
                MaterialCardView card = createCardView();
                LinearLayout layout = createCardLayout();

                // Name
                TextView tvName = createTextView(name, 18, true, 0xFF1976D2);
                layout.addView(tvName);

                // Course
                TextView tvCategory = createTextView("Course: " + category, 14, false, 0xFF333333);
                layout.addView(tvCategory);

                // Study Mode & Duration
                TextView tvDetails = createTextView("Study Mode: " + studyMode + " | Duration: " + duration, 14, false, 0xFF555555);
                layout.addView(tvDetails);

                // Fee & status
                TextView tvFeeStatus = createTextView("Fee: " + fee + " | Status: " + status, 14, false, 0xFF388E3C);
                layout.addView(tvFeeStatus);

                // Website button
                if (!website.isEmpty()) {
                    Button btnWeb = createWebsiteButton("View Official Page", website);
                    layout.addView(btnWeb);
                }

                card.addView(layout);
                binding.layoutProgrammesContainer.addView(card);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayGappProgrammes() {
        binding.layoutProgrammesContainer.removeAllViews();

        if (gappArray == null || gappArray.length() == 0) {
            return;
        }

        for (int i = 0; i < gappArray.length(); i++) {
            try {
                JSONObject programme = gappArray.getJSONObject(i);

                String name = programme.optString("name", "");
                String category = programme.optString("category", "");
                String duration = programme.optString("duration", "");
                String fee = programme.optString("fee", "");
                String description = programme.optString("description", "");
                String website = programme.optString("website", "");

                MaterialCardView card = createCardView();
                LinearLayout layout = createCardLayout();

                TextView tvName = createTextView(name, 18, true, 0xFF388E3C);
                layout.addView(tvName);

                TextView tvCategory = createTextView("Course: " + category, 14, false, 0xFF333333);
                layout.addView(tvCategory);

                TextView tvDetails = createTextView("Duration: " + duration + " | Fee: " + fee, 14, false, 0xFF555555);
                layout.addView(tvDetails);

                TextView tvDesc = createTextView("Description: " + description, 14, false, 0xFF444444);
                layout.addView(tvDesc);

                if (!website.isEmpty()) {
                    Button btnWeb = createWebsiteButton("View Official GAPP Page", website);
                    layout.addView(btnWeb);
                }

                card.addView(layout);
                binding.layoutProgrammesContainer.addView(card);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayGufpProgrammes() {
        binding.layoutProgrammesContainer.removeAllViews();

        if (gufpArray == null || gufpArray.length() == 0) {
            return;
        }

        for (int i = 0; i < gufpArray.length(); i++) {
            try {
                JSONObject programme = gufpArray.getJSONObject(i);

                String name = programme.optString("name", "");
                String category = programme.optString("category", "");
                String progDetails = programme.optString("programme", "");
                String duration = programme.optString("duration", "");
                String description = programme.optString("description", "");
                String website = programme.optString("website", "");

                MaterialCardView card = createCardView();
                LinearLayout layout = createCardLayout();

                TextView tvName = createTextView(name, 18, true, 0xFF7B1FA2);
                layout.addView(tvName);

                TextView tvCategory = createTextView("Course: " + category, 14, false, 0xFF333333);
                layout.addView(tvCategory);

                TextView tvDetails = createTextView("Programme: " + progDetails + " | Duration: " + duration, 14, false, 0xFF555555);
                layout.addView(tvDetails);

                TextView tvDesc = createTextView("Description: " + description, 14, false, 0xFF444444);
                layout.addView(tvDesc);

                if (!website.isEmpty()) {
                    Button btnWeb = createWebsiteButton("View Official GUFP Page", website);
                    layout.addView(btnWeb);
                }

                card.addView(layout);
                binding.layoutProgrammesContainer.addView(card);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // Function for card design
    private MaterialCardView createCardView() {

        MaterialCardView card = new MaterialCardView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 24);
        card.setLayoutParams(params);
        card.setRadius(12f);
        card.setCardElevation(4f);
        card.setCardBackgroundColor(0xFFFFFFFF);
        return card;
    }

    private LinearLayout createCardLayout() {
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32, 32, 32, 32);
        return layout;
    }

    private TextView createTextView(String text, float textSizeSp, boolean bold, int textColor) {
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 8);
        textView.setLayoutParams(params);
        textView.setText(text);
        textView.setTextSize(textSizeSp);
        textView.setTextColor(textColor);
        if (bold) {
            textView.setTypeface(null, android.graphics.Typeface.BOLD);
        }
        return textView;
    }

    private Button createWebsiteButton(String buttonText, String url) {
        Button button = new Button(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 16, 0, 0);
        button.setLayoutParams(params);
        button.setText(buttonText);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
        return button;
    }
}