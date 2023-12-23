package com.example.healthai.Views;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthai.Controllers.HomeController;

public class HomeFragment extends Fragment {
    private HomeController controller;

    public HomeFragment() {controller = new HomeController(this);}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return controller.onCreateView(inflater, container, savedInstanceState);
    }

}
