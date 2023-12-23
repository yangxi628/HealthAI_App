package com.example.healthai.Views.Assessment;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthai.Models.UserState;
import com.example.healthai.R;
import com.example.healthai.Views.NavigationBar;
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

public class RunDiabetesRiskAssessmentActivity extends AppCompatActivity {
    private FrameLayout container;
    private View formView;
    private View resultView;

    private EditText AgeEditText;
    private EditText PregnanciesEditText;
    private EditText GlucoseEditText;

    private EditText BloodPressureEditText;
    private EditText SkinThicknessEditText;
    private EditText InsulinEditText;

    private EditText BMIEditText;
    private EditText DiabetesPedigreeFunctionEditText;
    private Button runButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_diabetes_risk_assessment);
        container = findViewById(R.id.container);

        formView = getLayoutInflater().inflate(R.layout.activity_diabetes_form, container, false);
        resultView = getLayoutInflater().inflate(R.layout.activity_run_risk_result, container, false);
        showFormView();

        ImageView backButton = findViewById(R.id.BackButton);
        runButton = findViewById(R.id.RunButton);


        AgeEditText = findViewById(R.id.AgeEditText);
        PregnanciesEditText = findViewById(R.id.PregnanciesEditText);
        GlucoseEditText = findViewById(R.id.GlucoseEditText);
        BloodPressureEditText = findViewById(R.id.BloodPressureEditText);
        SkinThicknessEditText = findViewById(R.id.SkinThicknessEditText);
        InsulinEditText = findViewById(R.id.InsulinEditText);
        BMIEditText = findViewById(R.id.BMIEditText);
        DiabetesPedigreeFunctionEditText = findViewById(R.id.DiabetesPedigreeFunctionEditText);


        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.BackButton) {
                    switchToActivity(NavigationBar.class);
                } else if (v.getId() == R.id.RunButton) {
                    RunButton();
                }
            }
        };

        backButton.setOnClickListener(buttonClickListener);
        runButton.setOnClickListener(buttonClickListener);
    }

    private void showFormView() {
        container.removeAllViews();
        container.addView(formView);
    }

    private void showResultView() {
        container.removeAllViews();
        container.addView(resultView);
    }


    private void RunButton() {
        String age = AgeEditText.getText().toString();
        String Pregnancies = PregnanciesEditText.getText().toString();
        String Glucose = GlucoseEditText.getText().toString();
        String BloodPressure = BloodPressureEditText.getText().toString();
        String SkinThickness = SkinThicknessEditText.getText().toString();
        String Insulin = InsulinEditText.getText().toString();
        String BMI = BMIEditText.getText().toString();
        String DiabetesPedigreeFunction = DiabetesPedigreeFunctionEditText.getText().toString();

        new SendReportRequestTaskDiabetes(RunDiabetesRiskAssessmentActivity.this).execute(Pregnancies, Glucose, BloodPressure,
                SkinThickness, Insulin, BMI, DiabetesPedigreeFunction,age);
    }

    private void switchToActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    private class SendReportRequestTaskDiabetes extends AsyncTask<String, Void, String> {
        private Context mContext;

        public SendReportRequestTaskDiabetes(Context context) {
            mContext = context;
        }
        String Pregnancies;
        String Glucose;
        String BloodPressure;
        String SkinThickness;
        String Insulin;
        String BMI;
        String DiabetesPedigreeFunction;
        String Age;
        @Override
        protected String doInBackground(String... params) {
            String apiUrl = "https://healthaibackendtester.onrender.com/predict-diabetes";

            Pregnancies = params[0];
            Glucose = params[1];
            BloodPressure = params[2];
            SkinThickness = params[3];
            Insulin = params[4];
            BMI = params[5];
            DiabetesPedigreeFunction = params[6];
            Age = params[7];


            try {
                URL url = new URL(apiUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");

                // Create JSON object with the data
                JSONObject jsonRequest = new JSONObject();
                jsonRequest.put("Pregnancies",Pregnancies);
                jsonRequest.put("Glucose", Glucose);
                jsonRequest.put("BloodPressure", BloodPressure);
                jsonRequest.put("SkinThickness", SkinThickness);
                jsonRequest.put("Insulin", Insulin);
                jsonRequest.put("BMI",BMI);
                jsonRequest.put("DiabetesPedigreeFunction", DiabetesPedigreeFunction);
                jsonRequest.put("Age",Age);

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
            if (result != null) {
                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    String predictionResult = jsonResponse.getString("result");

                    Log.d("Success", "Prediction Result is: " + predictionResult);
                    showResultView();

                    TextView predictionResultTextView = resultView.findViewById(R.id.predictionResultTextView);
                    predictionResultTextView.setText(predictionResult);

                    saveToFirebase(predictionResult, Age, Pregnancies, Glucose, BloodPressure,
                            SkinThickness, Insulin, BMI, DiabetesPedigreeFunction, mContext);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("HTTP Request Error", "Error occurred during HTTP request");
            }
        }

        private void saveToFirebase(String predictionResult, String age, String Pregnancies,
                                    String Glucose, String BloodPressure, String SkinThickness, String Insulin,
                                    String BMI, String DiabetesPedigreeFunction, Context context) {

            UserState userState = UserState.getInstance();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Map<String, Object> diabetesData = new HashMap<>();
            diabetesData.put("Age", age);
            diabetesData.put("BMI", BMI);
            diabetesData.put("BloodPressure", BloodPressure);
            diabetesData.put("DiabetesPedigreeFunction", DiabetesPedigreeFunction);
            diabetesData.put("Glucose", Glucose);
            diabetesData.put("Insulin", Insulin);
            diabetesData.put("Pregnancies", Pregnancies);
            diabetesData.put("SkinThickness", SkinThickness);
            diabetesData.put("date",  new Date().toString());
            diabetesData.put("patient",userState.getUserID());
            diabetesData.put("result", predictionResult);
            diabetesData.put("type", "diabetes");

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
}