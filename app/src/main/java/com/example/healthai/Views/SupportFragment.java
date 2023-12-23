package com.example.healthai.Views;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.healthai.Controllers.SupportController;

public class SupportFragment extends Fragment {
    private SupportController controller;
    public SupportFragment() {controller = new SupportController(this);}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return controller.onCreateView(inflater, container, savedInstanceState);

    }

}