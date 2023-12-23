package com.example.healthai.Controllers;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthai.Controllers.Adapters.ReportAdapter;
import com.example.healthai.Controllers.Adapters.RiskAssesmentAdapter;
import com.example.healthai.Controllers.Helpers.SwitchViewHelper;
import com.example.healthai.Models.Assessment.AssessmentData;
import com.example.healthai.Models.Assessment.DiabetesAssessmentData;
import com.example.healthai.Models.Assessment.HeartAssessmentData;
import com.example.healthai.Models.Assessment.LungAssessmentData;
import com.example.healthai.Models.UserState;
import com.example.healthai.R;
import com.example.healthai.Views.HomeFragment;
import com.example.healthai.Views.ProfileActivity;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class HomeController {
    private final HomeFragment fragment;

    ImageView profileButton;
    TextView usernameTextView;
    TextView emailTextView;
    ReportAdapter reportAdapter;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    ArrayList<AssessmentData> assessmentDataList;
    ProgressDialog progressDialog;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    public HomeController(HomeFragment fragment) {
        this.fragment = fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getImages();
        initializeView(view);
        return view;
    }

    private void initializeView(View view) {
        profileButton = view.findViewById(R.id.profileButton);
        usernameTextView = view.findViewById(R.id.CurrentUserNameText);
        emailTextView = view.findViewById(R.id.currentUserEmail);

        progressDialog = new ProgressDialog(fragment.getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = view.findViewById(R.id.medical_history_listview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(fragment.getActivity()));
        db = FirebaseFirestore.getInstance();
        assessmentDataList = new ArrayList<>();

        reportAdapter = new ReportAdapter(fragment.getActivity(), assessmentDataList);

        recyclerView.setAdapter(reportAdapter);

        EventChangeListener();

        UserState userState = UserState.getInstance();
        String currentUserNameText = "Hello, " + userState.getFirstName();
        String currentUserEmail = userState.getEmail();

        usernameTextView.setText(currentUserNameText);
        emailTextView.setText(currentUserEmail);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchViewHelper.switchToActivity(view.getContext(),ProfileActivity.class);
            }
        });
        initRecyclerView(view);
    }


    private void EventChangeListener() {
        UserState userState = UserState.getInstance();

        db.collection("reports")
                .whereEqualTo("patient", userState.getUserID())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore Error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                AssessmentData assessmentData = getAssessmentData(dc.getDocument());
                                if (assessmentData != null) {
                                    assessmentDataList.add(assessmentData);
                                }
                            }
                        }

                        reportAdapter.notifyDataSetChanged();

                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });
    }


    private AssessmentData getAssessmentData(DocumentSnapshot document) {
        String type = document.getString("type");

        if (type != null) {
            switch (type) {
                case "lung":
                    return document.toObject(LungAssessmentData.class);
                case "diabetes":
                    return document.toObject(DiabetesAssessmentData.class);
                case "heart":
                    return document.toObject(HeartAssessmentData.class);
                default:
                    return null;
            }
        }

        return null;
    }
    private void getImages(){
        mImageUrls.add(String.valueOf(R.drawable.heart));
        mNames.add("Heart Disease");
        mImageUrls.add(String.valueOf(R.drawable.lung));
        mNames.add("Lung Cancer");
        mImageUrls.add(String.valueOf(R.drawable.colon));
        mNames.add("Diabetes");
    }

    private void initRecyclerView(View view){
        LinearLayoutManager layoutManager = new LinearLayoutManager(fragment.getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);

        UserState userState = UserState.getInstance();
        Toast.makeText(fragment.getActivity(), userState.getRole(), Toast.LENGTH_SHORT).show();

        if (Objects.equals(userState.getRole(), "patient")) {
            RiskAssesmentAdapter adapter = new RiskAssesmentAdapter(fragment.getActivity(), mNames, mImageUrls);
            recyclerView.setAdapter(adapter);
        }

    }
}
