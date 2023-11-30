package br.com.ifsp.hospital;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateActivity extends Activity {

    private EditText nameEditText;
    private EditText ageEditText;
    private RadioGroup genderRadioGroup;
    private EditText diagnosticEditText;
    private EditText medicinesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        nameEditText = findViewById(R.id.name_edit_text);
        ageEditText = findViewById(R.id.age_edit_text);
        genderRadioGroup = findViewById(R.id.gender_radio_group);
        diagnosticEditText = findViewById(R.id.diagnostic_edit_text);
        medicinesEditText = findViewById(R.id.medicines_edit_text);

        Button btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(v -> finish());

        Button btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(v -> {
            if (isDataValid()) {
                saveDataAndFinish();
            } else {
                showToast("Insira todos os campos.");
            }
        });
    }

    private void saveDataAndFinish() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("hospital");

        String name = nameEditText.getText().toString();
        int age = Integer.parseInt(ageEditText.getText().toString());
        int genderRadioId = genderRadioGroup.getCheckedRadioButtonId();
        String gender = (genderRadioId == R.id.male_radio_button) ? "Male" : "Female";
        String diagnostic = diagnosticEditText.getText().toString();
        String medicines = medicinesEditText.getText().toString();

        String key = databaseRef.push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("name", name);
        childUpdates.put("age", age);
        childUpdates.put("gender", gender);
        childUpdates.put("diagnostic", diagnostic);
        childUpdates.put("medicines", medicines);
        databaseRef.child(key).setValue(childUpdates);

        setResult(RESULT_OK);
        finish();
    }

    private boolean isDataValid() {
        String name = nameEditText.getText().toString();
        String ageText = ageEditText.getText().toString();
        int genderRadioId = genderRadioGroup.getCheckedRadioButtonId();
        String diagnostic = diagnosticEditText.getText().toString();
        String medicines = medicinesEditText.getText().toString();

        return !name.isEmpty() && !ageText.isEmpty() &&
                genderRadioId != -1 && !diagnostic.isEmpty() && !medicines.isEmpty();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}