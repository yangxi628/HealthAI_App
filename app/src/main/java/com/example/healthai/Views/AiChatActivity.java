package com.example.healthai.Views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthai.Controllers.MessagesViewAdapter;
import com.example.healthai.Models.ChatMessage;
import com.example.healthai.R;

import java.util.ArrayList;

public class AiChatActivity extends AppCompatActivity {

    private RecyclerView chat;
    private Button send;
    private EditText userMessage;
    private final String USER = "user";
    private final String BOT = "bot";

    private ArrayList<ChatMessage> messageArrayList;
    private MessagesViewAdapter chatAdapter;

    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.aichat);

        chat = findViewById(R.id.chat_view);
        userMessage = findViewById(R.id.message);
        send = findViewById(R.id.send);

        messageArrayList = new ArrayList<>();

        chatAdapter = new MessagesViewAdapter(messageArrayList, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AiChatActivity.this, RecyclerView.VERTICAL, false);

        chat.setLayoutManager(linearLayoutManager);
        chat.setAdapter(chatAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userMessage.getText().toString().isEmpty()){
                    //Dont send
                    return;
                }

                sendMessage(userMessage.getText().toString());

                userMessage.setText("");
            }
        });

    }

    private void sendMessage(String userMessage){
        messageArrayList.add(new ChatMessage(userMessage, USER));
        chatAdapter.notifyDataSetChanged();
        // send to ai, get response
    }
}
// luke