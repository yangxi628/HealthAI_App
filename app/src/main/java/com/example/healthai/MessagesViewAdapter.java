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

    private ArrayList<ChatMessage> messagesArrayList;
    private Context context;

    public MessagesViewAdapter(ArrayList<ChatMessage> messagesArrayList, Context context){
        this.messagesArrayList = messagesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_message, parent, false);
            return new UserMessagesViewHolder(view);
        }

        else if (viewType == 1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_message, parent, false);
            return new BotMessagesViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = messagesArrayList.get(position);

        if (Objects.equals(message.getSender(), "user")){
            ((UserMessagesViewHolder) holder).userView.setText(message.getMessage());
        }

        else if (Objects.equals(message.getSender(), "bot")){
            ((BotMessagesViewHolder) holder).botView.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        // below line of code is to set position.
        switch (messagesArrayList.get(position).getSender()) {
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
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
