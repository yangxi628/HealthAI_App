package com.example.healthai.Views;

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

public class RunHeartRiskAssessmentActivity extends AppCompatActivity {
    private FrameLayout container;
    private View formView;
    private View resultView;
    private Button runButton;
    private EditText AgeEditText;
    private EditText GenderEditText;
    private EditText ChestPainTypeEditText;
    private EditText BPEditText;
    private EditText CholesterolEditText;
    private EditText FBSOver120EditText;
    private EditText EKGResultsEditText;
    private EditText MaxHREditText;
    private EditText ExerciseAnginaEditText;
    private EditText STdepressionEditText;
    private EditText SlopeOfSTEditText;
    private EditText NumberOfVesselsFluroEditText;
    private EditText ThalliumEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_heart_risk_assessment);
        container = findViewById(R.id.container);

        formView = getLayoutInflater().inflate(R.layout.activtity_heart_form, container, false);
        resultView = getLayoutInflater().inflate(R.layout.activity_run_risk_result, container, false);
        showFormView();

        ImageView backButton = findViewById(R.id.BackButton);
        runButton = findViewById(R.id.RunButton);

        AgeEditText = findViewById(R.id.AgeEditText);
        GenderEditText = findViewById(R.id.GenderEditText);
        ChestPainTypeEditText = findViewById(R.id.ChestPainTypeEditText);

        BPEditText = findViewById(R.id.BPEditText);
        CholesterolEditText = findViewById(R.id.CholesterolEditText);
        FBSOver120EditText = findViewById(R.id.FBSOver120EditText);
        EKGResultsEditText = findViewById(R.id.EKGResultsEditText);
        MaxHREditText = findViewById(R.id.MaxHREditText);
        ExerciseAnginaEditText = findViewById(R.id.ExerciseAnginaEditText);
        STdepressionEditText = findViewById(R.id.STdepressionEditText);
        SlopeOfSTEditText = findViewById(R.id.SlopeOfSTEditText);
        NumberOfVesselsFluroEditText = findViewById(R.id.NumberOfVesselsFluroEditText);
        ThalliumEditText = findViewById(R.id.ThalliumEditText);

        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.BackButton) {
                    switchToActivity(NavigationActivity.class);
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
        String gender = GenderEditText.getText().toString();
        String ChestPainType = ChestPainTypeEditText.getText().toString();
        String BP = BPEditText.getText().toString();
        String Cholesterol = CholesterolEditText.getText().toString();
        String FBSOver120 = FBSOver120EditText.getText().toString();
        String EKGResults = EKGResultsEditText.getText().toString();
        String MaxHR = MaxHREditText.getText().toString();
        String ExerciseAngina = ExerciseAnginaEditText.getText().toString();
        String STdepression = STdepressionEditText.getText().toString();
        String SlopeOfST = SlopeOfSTEditText.getText().toString();
        String NumberOfVesselsFluro = NumberOfVesselsFluroEditText.getText().toString();
        String Thallium = ThalliumEditText.getText().toString();


        new RunHeartRiskAssessmentActivity.SendReportRequestTaskHeart(RunHeartRiskAssessmentActivity.this).execute(age,gender,ChestPainType,BP,Cholesterol,FBSOver120,EKGResults,MaxHR,ExerciseAngina,STdepression,SlopeOfST,NumberOfVesselsFluro,Thallium);
    }

    private void switchToActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    private class SendReportRequestTaskHeart extends AsyncTask<String, Void, String> {
        private Context mContext;

        public SendReportRequestTaskHeart(Context context) {
            mContext = context;
        }
        String age;
        String gender;
        String ChestPainType;
        String BP;
        String Cholesterol;
        String FBSOver120;
        String EKGResults;
        String MaxHR;
        String ExerciseAngina;
        String STdepression;
        String SlopeOfST;
        String NumberOfVesselsFluro;
        String Thallium;

        @Override
        protected String doInBackground(String... params) {
            String apiUrl = "https://healthaibackendtester.onrender.com/predict-heart-disease";

            age = params[0];
            gender = params[1];
            ChestPainType = params[2];
            BP = params[3];
            Cholesterol = params[4];
            FBSOver120 = params[5];
            EKGResults = params[6];
            MaxHR = params[7];
            ExerciseAngina = params[8];
            STdepression = params[9];
            SlopeOfST = params[10];
            NumberOfVesselsFluro = params[11];
            Thallium = params[12];

            try {
                URL url = new URL(apiUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");

                // Create JSON object with the data
                JSONObject jsonRequest = new JSONObject();
                jsonRequest.put("age",age);
                jsonRequest.put("sex", gender);
                jsonRequest.put("ChestPainType", ChestPainType);
                jsonRequest.put("BP", BP);
                jsonRequest.put("Cholesterol", Cholesterol);
                jsonRequest.put("FBSOver120", FBSOver120);
                jsonRequest.put("EKGResults", EKGResults);
                jsonRequest.put("MaxHR", MaxHR);
                jsonRequest.put("ExerciseAngina", ExerciseAngina);
                jsonRequest.put("STdepression", STdepression);
                jsonRequest.put("SlopeOfST", SlopeOfST);
                jsonRequest.put("NumberOfVesselsFluro",NumberOfVesselsFluro);
                jsonRequest.put("Thallium", Thallium);


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

                    saveToFirebase(predictionResult, age, gender, ChestPainType, BP,
                            Cholesterol, FBSOver120, EKGResults, MaxHR, ExerciseAngina,STdepression,SlopeOfST,NumberOfVesselsFluro,Thallium,mContext);

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
                                String ChestPainType, String BP, String Cholesterol, String FBSOver120,
                                String EKGResults, String MaxHR, String ExerciseAngina,String STdepression,String SlopeOfST, String NumberOfVesselsFluro, String Thallium, Context context) {

        UserState userState = UserState.getInstance();
        if (userState != null) {
            String userID = userState.getUserID();
            Log.d("UserID", "User ID: " + userID);

            // Rest of your code...
        } else {
            Log.e("UserID", "UserState is null");
            // Handle the case where userState is null
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> diabetesData = new HashMap<>();
        diabetesData.put("age", age);
        diabetesData.put("sex", gender);
        diabetesData.put("BP", BP);
        diabetesData.put("ChestPainType", ChestPainType);
        diabetesData.put("Cholesterol", Cholesterol);
        diabetesData.put("EKGResults", EKGResults);
        diabetesData.put("ExerciseAngina", ExerciseAngina);
        diabetesData.put("FBSOver120", FBSOver120);
        diabetesData.put("MaxHR",  MaxHR);
        diabetesData.put("NumberOfVesselsFluro",NumberOfVesselsFluro);
        diabetesData.put("STdepression", STdepression);
        diabetesData.put("SlopeOfST",SlopeOfST);
        diabetesData.put("Thallium",Thallium);
        diabetesData.put("date",new Date().toString());
        diabetesData.put("patient",userState.getUserID());
        diabetesData.put("result",predictionResult);
        diabetesData.put("type","heart");

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