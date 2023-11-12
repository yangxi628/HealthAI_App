package com.example.healthai;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONException;

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
        Button homeButton = findViewById(R.id.homeButton);
        Button aiChatButton = findViewById(R.id.aiChatButton);
        Button contactButton = findViewById(R.id.contactButton);
        Button profileButton = findViewById(R.id.profileButton);

        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.homeButton) {
                    switchToActivity(HomeActivity.class);
                } else if (v.getId() == R.id.aiChatButton) {
                    switchToActivity(AiChatActivity.class);
                } else if (v.getId() == R.id.contactButton) {
                    switchToActivity(ContactActivity.class);
                } else if (v.getId() == R.id.profileButton) {
                    switchToActivity(ProfileActivity.class);
                }
            }
        };

        // Assign the common click listener to all buttons
        homeButton.setOnClickListener(buttonClickListener);
        aiChatButton.setOnClickListener(buttonClickListener);
        contactButton.setOnClickListener(buttonClickListener);
        profileButton.setOnClickListener(buttonClickListener);

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




        //String url = "https://api.openai.com/v1/chat/completions/" + userMessage;




    }




    private void switchToActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

}
// luke