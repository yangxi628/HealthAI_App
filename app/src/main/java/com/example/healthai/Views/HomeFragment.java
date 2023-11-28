package com.example.healthai.Views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;

import com.example.healthai.Controllers.ReportAdapter;
import com.example.healthai.Controllers.RiskAssesmentAdapter;
import com.example.healthai.Models.AssessmentData;
import com.example.healthai.Models.DiabetesAssessmentData;
import com.example.healthai.Models.HeartAssessmentData;
import com.example.healthai.Models.LungAssessmentData;
import com.example.healthai.Models.UserState;
import com.example.healthai.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
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

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getImages();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        profileButton = view.findViewById(R.id.profileButton);
        usernameTextView = view.findViewById(R.id.CurrentUserNameText);
        emailTextView = view.findViewById(R.id.currentUserEmail);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = view.findViewById(R.id.medical_history_listview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        db = FirebaseFirestore.getInstance();
        assessmentDataList = new ArrayList<>();

        reportAdapter = new ReportAdapter(getActivity(), assessmentDataList);

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
                switchToActivity(ProfileActivity.class);
            }
        });
        initRecyclerView(view);


        return view;
    }

    private void EventChangeListener() {
        db.collection("reports")
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


    private void switchToActivity(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RiskAssesmentAdapter adapter = new RiskAssesmentAdapter(getActivity(), mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
    }
}
