package com.example.healthai.Controllers;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.healthai.R;
import com.example.healthai.Views.AIChatFragment;
import com.example.healthai.Views.HomeFragment;
import com.example.healthai.Views.NavigationBar;
import com.example.healthai.Views.SupportFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class NavigationBarController {

    private final NavigationBar view;

    public NavigationBarController(NavigationBar view) {
        this.view = view;
        initialize();
    }

    private void initialize() {
        replaceFragment(new HomeFragment());

        view.getBottomNavigationView().setOnItemSelectedListener(item -> {
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
                            Toast.makeText(view, "Doctor cannot use this", Toast.LENGTH_SHORT).show();
                            Toast.makeText(view, role, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(view, "Profile doesn't exist", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(view, "access failed", Toast.LENGTH_SHORT).show();
                });

            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = view.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(view.getFrameLayout().getId(), fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
