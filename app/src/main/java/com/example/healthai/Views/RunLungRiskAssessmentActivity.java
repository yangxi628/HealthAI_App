package com.example.healthai.Views;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthai.Models.UserState;
import com.example.healthai.R;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RunLungRiskAssessmentActivity extends AppCompatActivity {

    private FrameLayout container;
    private View formView;
    private View resultView;

    private EditText ageEditText;
    private EditText genderEditText;
    private Switch patientSmokesSwitch;
    private Switch patientHasYellowFingersSwitch;
    private Switch patientHasAnxietySwitch;
    private Switch patientExperiencesPeerPressureSwitch;
    private Switch patientHasChronicDiseaseSwitch;
    private Switch patientExperiencesFatigueSwitch;
    private Switch patientHasAllergiesSwitch;
    private Switch patientExperiencesWheezingSwitch;
    private Switch patientConsumesAlcoholSwitch;
    private Switch patientHasCoughingSwitch;
    private Switch patientExperiencesShortnessOfBreathSwitch;
    private Switch patientHasSwallowingDifficultySwitch;
    private Switch patientExperiencesChestPainSwitch;
    private Button runButton;
    private String patientSmokes;
    private String patientHasYellowFingers;
    private String patientHasAnxiety;
    private String patientExperiencesPeerPressure;
    private String patientHasChronicDisease;
    private String patientExperiencesFatigue;
    private String patientHasAllergies;
    private String patientExperiencesWheezing;
    private String patientConsumesAlcohol;
    private String patientHasCoughing;
    private String patientExperiencesShortnessOfBreath;
    private String patientHasSwallowingDifficulty;
    private String patientExperiencesChestPain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_lung_risk_assessment);
        container = findViewById(R.id.container);

        formView = getLayoutInflater().inflate(R.layout.activity_lung_form, container, false);
        resultView = getLayoutInflater().inflate(R.layout.activity_run_risk_result, container, false);
        showFormView();

        ImageView backButton = findViewById(R.id.BackButton);
        ageEditText = findViewById(R.id.AgeEditText);
        genderEditText = findViewById(R.id.GenderEditText);

        patientSmokesSwitch = findViewById(R.id.PatientSmokesSwitch);
        patientHasYellowFingersSwitch = findViewById(R.id.PatienthasYellowFingersSwitch);
        patientHasAnxietySwitch = findViewById(R.id.PatienthasAnxietySwitch);
        patientExperiencesPeerPressureSwitch = findViewById(R.id.PatientExperiencesPeerPressureSwitch);
        patientHasChronicDiseaseSwitch = findViewById(R.id.PatienthasChronicDiseaseSwitch);
        patientExperiencesFatigueSwitch = findViewById(R.id.PatientExperiencesFatigueSwitch);
        patientHasAllergiesSwitch = findViewById(R.id.PatientHasAllergiesSwitch);
        patientExperiencesWheezingSwitch = findViewById(R.id.PatientExperiencesWheezingSwitch);
        patientConsumesAlcoholSwitch = findViewById(R.id.PatientConsumesAlcoholSwitch);
        patientHasCoughingSwitch = findViewById(R.id.PatienthasCoughingSwitch);
        patientExperiencesShortnessOfBreathSwitch = findViewById(R.id.PatientExperiencesShortnessofBreathSwitch);
        patientHasSwallowingDifficultySwitch = findViewById(R.id.PatientHasSwallowingDifficultySwitch);
        patientExperiencesChestPainSwitch = findViewById(R.id.PatientExperiencesChestPainSwitch);

        runButton = findViewById(R.id.RunButton);

        setSwitchListener(patientSmokesSwitch, value -> patientSmokes = value);
        setSwitchListener(patientHasYellowFingersSwitch, value -> patientHasYellowFingers = value);
        setSwitchListener(patientHasAnxietySwitch, value -> patientHasAnxiety = value);
        setSwitchListener(patientExperiencesPeerPressureSwitch, value -> patientExperiencesPeerPressure = value);
        setSwitchListener(patientHasChronicDiseaseSwitch, value -> patientHasChronicDisease = value);
        setSwitchListener(patientExperiencesFatigueSwitch, value -> patientExperiencesFatigue = value);
        setSwitchListener(patientHasAllergiesSwitch, value -> patientHasAllergies = value);
        setSwitchListener(patientExperiencesWheezingSwitch, value -> patientExperiencesWheezing = value);
        setSwitchListener(patientConsumesAlcoholSwitch, value -> patientConsumesAlcohol = value);
        setSwitchListener(patientHasCoughingSwitch, value -> patientHasCoughing = value);
        setSwitchListener(patientExperiencesShortnessOfBreathSwitch, value -> patientExperiencesShortnessOfBreath = value);
        setSwitchListener(patientHasSwallowingDifficultySwitch, value -> patientHasSwallowingDifficulty = value);
        setSwitchListener(patientExperiencesChestPainSwitch, value -> patientExperiencesChestPain = value);

        View.OnClickListener buttonClickListener = v -> {
            if (v.getId() == R.id.BackButton) {
                switchToActivity(NavigationActivity.class);
            }
        };

        runButton.setOnClickListener(v -> RunButton());

        backButton.setOnClickListener(buttonClickListener);
    }

    private void showFormView() {
        container.removeAllViews();
        container.addView(formView);
    }

    private void showResultView() {
        container.removeAllViews();
        container.addView(resultView);
    }

    private void setSwitchListener(Switch switchView, SwitchListener listener) {
        switchView.setOnCheckedChangeListener((buttonView, isChecked) -> listener.onValueChanged(isChecked ? "1" : "0"));
    }

    private interface SwitchListener {
        void onValueChanged(String value);
    }

    private void RunButton() {
        String age = ageEditText.getText().toString();
        String gender = genderEditText.getText().toString();

        new RunLungRiskAssessmentActivity.SendLungReportRequestTask(RunLungRiskAssessmentActivity.this).execute(age, gender, patientSmokes, patientHasYellowFingers,
                patientHasAnxiety, patientExperiencesPeerPressure, patientHasChronicDisease,
                patientExperiencesFatigue, patientHasAllergies, patientExperiencesWheezing,
                patientConsumesAlcohol, patientHasCoughing, patientExperiencesShortnessOfBreath,
                patientHasSwallowingDifficulty, patientExperiencesChestPain);
    }

    private void switchToActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    private class SendLungReportRequestTask extends AsyncTask<String, Void, String> {
        private Context mContext;

        public SendLungReportRequestTask(Context context) {
            mContext = context;
        }
        String age;
        String gender;
        String patientSmokes;
        String patientHasYellowFingers;
        String patientHasAnxiety;
        String patientExperiencesPeerPressure;
        String patientHasChronicDisease;
        String patientExperiencesFatigue;
        String patientHasAllergies;
        String patientExperiencesWheezing;
        String patientConsumesAlcohol;
        String patientHasCoughing;
        String patientExperiencesShortnessOfBreath;
        String patientHasSwallowingDifficulty;
        String patientExperiencesChestPain;
        @Override
        protected String doInBackground(String... params) {
             age = params[0];
             gender = params[1];
             patientSmokes =params[2];
             patientHasYellowFingers = params[3];
             patientHasAnxiety = params[4];
             patientExperiencesPeerPressure = params[5];
             patientHasChronicDisease = params[6];
             patientExperiencesFatigue = params[7];
             patientHasAllergies = params[8];
             patientExperiencesWheezing = params[9];
             patientConsumesAlcohol = params[10];
             patientHasCoughing = params[11];
             patientExperiencesShortnessOfBreath = params[12];
             patientHasSwallowingDifficulty = params[13];
             patientExperiencesChestPain = params[14];

            String apiUrl = "https://healthaibackendtester.onrender.com/predict-lung-disease";

            try {
                URL url = new URL(apiUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");

                // Create JSON object with the data
                JSONObject jsonRequest = new JSONObject();
                jsonRequest.put("GENDER", gender);
                jsonRequest.put("AGE", Integer.parseInt(age));
                jsonRequest.put("SMOKING", Integer.parseInt(patientSmokes));
                jsonRequest.put("YELLOW_FINGERS", Integer.parseInt(patientHasYellowFingers));
                jsonRequest.put("ANXIETY", Integer.parseInt(patientHasAnxiety));
                jsonRequest.put("PEER_PRESSURE", Integer.parseInt(patientExperiencesPeerPressure));
                jsonRequest.put("CHRONIC_DISEASE", Integer.parseInt(patientHasChronicDisease));
                jsonRequest.put("FATIGUE", Integer.parseInt(patientExperiencesFatigue));
                jsonRequest.put("ALLERGY", Integer.parseInt(patientHasAllergies));
                jsonRequest.put("WHEEZING", Integer.parseInt(patientExperiencesWheezing));
                jsonRequest.put("ALCOHOL_CONSUMING", Integer.parseInt(patientConsumesAlcohol));
                jsonRequest.put("COUGHING", Integer.parseInt(patientHasCoughing));
                jsonRequest.put("SHORTNESS_OF_BREATH", Integer.parseInt(patientExperiencesShortnessOfBreath));
                jsonRequest.put("SWALLOWING_DIFFICULTY", Integer.parseInt(patientHasSwallowingDifficulty));
                jsonRequest.put("CHEST_PAIN", Integer.parseInt(patientExperiencesChestPain));


                // Log the JSON data being sent
                Log.d("SendReportRequestTask", "JSON Request: " + jsonRequest.toString());

                // Write the JSON object to the output stream
                urlConnection.getOutputStream().write(jsonRequest.toString().getBytes("UTF-8"));

                // Get the response from the server
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    return response.toString();
                } else {
                    // Handle error response
                    Log.e("HTTP Error", "HTTP error code: " + responseCode);
                    return null;
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            // Handle the result as needed
            if (result != null) {
                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    String predictionResult = jsonResponse.getString("result");

                    Log.d("Success", "Prediction Result is: " + predictionResult);
                    showResultView();

                    TextView predictionResultTextView = resultView.findViewById(R.id.predictionResultTextView);
                    predictionResultTextView.setText(predictionResult);

                    saveToFirebase(predictionResult,age,gender,patientSmokes,patientHasYellowFingers,patientHasAnxiety,patientExperiencesPeerPressure,  patientHasChronicDisease,patientExperiencesFatigue, patientHasAllergies, patientExperiencesWheezing,
                            patientConsumesAlcohol,patientHasCoughing, patientExperiencesShortnessOfBreath, patientHasSwallowingDifficulty, patientExperiencesChestPain,mContext);


                        // Update UI or perform other actions with the prediction result
                } catch (JSONException e) {
                    e.printStackTrace();
                }

        } else {
                // Handle the case where the result is null (error occurred)
                Log.e("HTTP Request Error", "Error occurred during HTTP request");
            }
        }
    }
    private void saveToFirebase(String predictionResult, String age, String gender,
                                String patientSmokes, String patientHasYellowFingers, String patientHasAnxiety, String patientExperiencesPeerPressure,
                                String patientHasChronicDisease, String patientExperiencesFatigue, String patientHasAllergies,String patientExperiencesWheezing,
                                String patientConsumesAlcohol, String patientHasCoughing, String patientExperiencesShortnessOfBreath,String patientHasSwallowingDifficulty, String patientExperiencesChestPain,Context context) {

        UserState userState = UserState.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> diabetesData = new HashMap<>();
        diabetesData.put("AGE", age);
        diabetesData.put("ALCOHOL_CONSUMING", patientConsumesAlcohol);
        diabetesData.put("ALLERGY", patientHasAllergies);
        diabetesData.put("ANXIETY", patientHasAnxiety);
        diabetesData.put("CHEST_PAIN", patientExperiencesChestPain);
        diabetesData.put("CHRONIC_DISEASE", patientHasChronicDisease);
        diabetesData.put("COUGHING", patientHasCoughing);
        diabetesData.put("FATIGUE", patientExperiencesFatigue);
        diabetesData.put("GENDER",  gender);
        diabetesData.put("PEER_PRESSURE",patientExperiencesPeerPressure);
        diabetesData.put("SHORTNESS_OF_BREATH", patientExperiencesShortnessOfBreath);
        diabetesData.put("SMOKING",patientSmokes);
        diabetesData.put("SWALLOWING_DIFFICULTY",patientHasSwallowingDifficulty);
        diabetesData.put("WHEEZING",patientExperiencesWheezing);
        diabetesData.put("YELLOW_FINGERS",patientHasYellowFingers);
        diabetesData.put("date",new Date().toString());
        diabetesData.put("patient",userState.getUserID());
        diabetesData.put("result",predictionResult);
        diabetesData.put("type","lung");

        db.collection("reports")
                .add(diabetesData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(context, "Report saved successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Error saving Report: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

}
