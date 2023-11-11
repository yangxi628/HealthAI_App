package com.example.healthai;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

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
        Message messages = messagesArrayList.get(position);

        if (Objects.equals(messages.getSender(), "user")){
            ((UserMessagesViewHolder) holder).userView.setText(messages.getMessage());
        }

        else if (Objects.equals(messages.getSender(), "bot")){
            ((BotMessagesViewHolder) holder).botView.setText(messages.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }

    public static class UserMessagesViewHolder extends RecyclerView.ViewHolder{

        TextView userView;

        public UserMessagesViewHolder(@NonNull View itemView){
        super(itemView);
        userView = itemView.findViewById(R.id.user_message_view);
        }
    }

    public static class BotMessagesViewHolder extends RecyclerView.ViewHolder{

        TextView botView;

        public BotMessagesViewHolder(@NonNull View itemView){
            super(itemView);
            botView = itemView.findViewById(R.id.bot_message_view);
        }}
}
