package com.example.healthai;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessagesViewAdapter extends RecyclerView.Adapter{

    private ArrayList<Message> messagesArrayList;
    private Context context;

    public MessagesViewAdapter(ArrayList<Message> messagesArrayList, Context context){
        this.messagesArrayList = messagesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }
}
