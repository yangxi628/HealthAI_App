package com.example.healthai.Views;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.healthai.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class NavigationActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);
        frameLayout = findViewById(R.id.frame_layout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        replaceFragment(new HomeFragment());

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.home) {
                    replaceFragment(new HomeFragment());
                } else if (itemId == R.id.aichat) {
                    replaceFragment(new AIChatFragment());
                } else if (itemId == R.id.support) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String userid = user.getUid(); // Get the UID of the currently signed-in user
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference userDocRef = db.collection("users").document(userid); // Reference to the user's document
                    userDocRef.get().addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String role = documentSnapshot.getString("role");
                            if (Objects.equals(role, "patient")) {
                                replaceFragment(new SupportFragment());

                            }
                            else if (!Objects.equals(role, "patient")) {
                                Toast.makeText(NavigationActivity.this, "Doctor cannot use this", Toast.LENGTH_SHORT).show();
                                Toast.makeText(NavigationActivity.this, role, Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(NavigationActivity.this, "Profile doesn't exist", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(e -> {
                        Toast.makeText(NavigationActivity.this, "access failed", Toast.LENGTH_SHORT).show();
                    });


                }

                return true;
            }
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        Log.d("FragmentTransaction", "Fragment replaced");
    }

}
