package com.example.pbl2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.pbl2.databinding.ActivityEligibilityBinding;

import java.util.ArrayList;
import java.util.List;

public class EligibilityActivity extends AppCompatActivity {

    private static final String GMI_ADMISSION_URL = "https://gmi.vialing.com/oa/login";

    private ActivityEligibilityBinding binding;

    private final String[] programmeTypes = {"Diploma", "GAPP", "GUFP"};

    private final String[] diplomaProgrammes = {
            "Diploma of Mechatronics Engineering Technology",
            "Diploma in Engineering Technology (Instrumentation and Control)",
            "Diploma of Electronics Engineering Technology (Computer)",
            "Diploma in Autotronics Engineering Technology",
            "Diploma in Engineering Technology (Sustainable Energy and Power Distribution)",
            "Diploma in Precision Tooling Engineering Technology",
            "Diploma in Engineering Technology (Industrial Design)",
            "Diploma in Industrial Quality Engineering Technology",
            "Diploma in Innovative Product Design Engineering Technology",
            "Diploma of Mechanical Engineering Technology (CNC Precision)",
            "Diploma in Engineering Technology (Machine Tools Maintenance)",
            "Diploma of Mechanical Engineering Technology (Manufacturing)",
            "Diploma in Cyber Security Technology",
            "Diploma in Software Engineering",
            "Diploma in Creative Multimedia"
    };

    private final String[] gappProgrammes = {
            "German A Levels Preparatory Programme (GAPP)"
    };

    private final String[] gufpProgrammes = {
            "GMI-UTP Foundation Programme (GUFP)"
    };

    private final String[] spmGrades = {"A+", "A", "A-", "B+", "B", "C+", "C", "D", "E", "G"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_eligibility);

        binding.btnBack.setOnClickListener(v -> finish());

        setupSpinners();

        binding.btnCheckEligibility.setOnClickListener(v -> evaluateEligibility());

        binding.btnApplyNow.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(GMI_ADMISSION_URL));
            startActivity(intent);
        });
    }

    private void setupSpinners() {
        // Programme Type
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, programmeTypes);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spProgrammeType.setAdapter(typeAdapter);

        // Grade
        ArrayAdapter<String> gradeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spmGrades);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spBahasaMelayu.setAdapter(gradeAdapter);
        binding.spEnglish.setAdapter(gradeAdapter);
        binding.spMathematics.setAdapter(gradeAdapter);
        binding.spAddMaths.setAdapter(gradeAdapter);
        binding.spPhysics.setAdapter(gradeAdapter);
        binding.spChemistry.setAdapter(gradeAdapter);

        // Choose course
        binding.spProgrammeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = programmeTypes[position];
                String[] currentProgrammeList;

                if (selectedType.equals("GAPP")) {
                    currentProgrammeList = gappProgrammes;
                } else if (selectedType.equals("GUFP")) {
                    currentProgrammeList = gufpProgrammes;
                } else {
                    currentProgrammeList = diplomaProgrammes;
                }

                ArrayAdapter<String> programmeAdapter = new ArrayAdapter<>(
                        EligibilityActivity.this,
                        android.R.layout.simple_spinner_item,
                        currentProgrammeList
                );
                programmeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spProgramme.setAdapter(programmeAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    // Grading system
    private int gradeToPoint(String grade) {
        switch (grade) {
            case "A+":
                return 9;
            case "A":
                return 8;
            case "A-":
                return 7;
            case "B+":
                return 6;
            case "B":
                return 5;
            case "C+":
                return 4;
            case "C":
                return 3;
            case "D":
                return 2;
            case "E":
                return 1;
            default:
                return 0; // G = 0
        }
    }

    private void evaluateEligibility() {
        if (binding.spProgramme.getSelectedItem() == null) {
            return;
        }

        String selectedProgramme = binding.spProgramme.getSelectedItem().toString();

        String bm = binding.spBahasaMelayu.getSelectedItem().toString();
        String eng = binding.spEnglish.getSelectedItem().toString();
        String math = binding.spMathematics.getSelectedItem().toString();
        String addMath = binding.spAddMaths.getSelectedItem().toString();
        String phy = binding.spPhysics.getSelectedItem().toString();
        String chem = binding.spChemistry.getSelectedItem().toString();

        binding.tvEligibilityResult.setVisibility(View.VISIBLE);
        List<String> missingReqs = new ArrayList<>();

        if (selectedProgramme.contains("GAPP")) {
            // GAPP requirements: English, Math, Add Math, Physics, Chemistry min C
            if (gradeToPoint(eng) < gradeToPoint("C")) missingReqs.add("English: Minimum C required");
            if (gradeToPoint(math) < gradeToPoint("C")) missingReqs.add("Mathematics: Minimum C required");
            if (gradeToPoint(addMath) < gradeToPoint("C")) missingReqs.add("Additional Mathematics: Minimum C required");
            if (gradeToPoint(phy) < gradeToPoint("C")) missingReqs.add("Physics: Minimum C required");
            if (gradeToPoint(chem) < gradeToPoint("C")) missingReqs.add("Chemistry: Minimum C required");

        } else if (selectedProgramme.contains("GUFP")) {
            // GUFP requirements: BM, English, Math, Add Math, Physics, Chemistry min C
            if (gradeToPoint(bm) < gradeToPoint("C")) missingReqs.add("Bahasa Melayu: Minimum C required");
            if (gradeToPoint(eng) < gradeToPoint("C")) missingReqs.add("English: Minimum C required");
            if (gradeToPoint(math) < gradeToPoint("C")) missingReqs.add("Mathematics: Minimum C required");
            if (gradeToPoint(addMath) < gradeToPoint("C")) missingReqs.add("Additional Mathematics: Minimum C required");
            if (gradeToPoint(phy) < gradeToPoint("C")) missingReqs.add("Physics: Minimum C required");
            if (gradeToPoint(chem) < gradeToPoint("C")) missingReqs.add("Chemistry: Minimum C required");

        } else if (selectedProgramme.contains("Software Engineering") ||
                   selectedProgramme.contains("Cyber Security") ||
                   selectedProgramme.contains("Creative Multimedia")) {
            // CID: Math & English min C
            if (gradeToPoint(math) < gradeToPoint("C")) missingReqs.add("Mathematics: Minimum C required");
            if (gradeToPoint(eng) < gradeToPoint("C")) missingReqs.add("English: Minimum C required");

        } else {
            // Engineering: Math & Physics min C
            if (gradeToPoint(math) < gradeToPoint("C")) missingReqs.add("Mathematics: Minimum C required");
            if (gradeToPoint(phy) < gradeToPoint("C")) missingReqs.add("Physics: Minimum C required");
        }

        // Display result
        if (missingReqs.isEmpty()) {
            binding.tvEligibilityResult.setText("Selected Programme: " + selectedProgramme + "\n\nYou meet the basic academic requirements for " + selectedProgramme + ".");
            binding.tvEligibilityResult.setTextColor(0xFF388E3C);
            binding.btnApplyNow.setVisibility(View.VISIBLE);
        } else {
            StringBuilder sb = new StringBuilder("Selected Programme: " + selectedProgramme + "\n\nYou are not eligible for " + selectedProgramme + ".\n\nRequirements not met:\n");
            for (String req : missingReqs) {
                sb.append("• ").append(req).append("\n");
            }
            binding.tvEligibilityResult.setText(sb.toString().trim());
            binding.tvEligibilityResult.setTextColor(0xFFD32F2F);
            binding.btnApplyNow.setVisibility(View.GONE);
        }
    }
}