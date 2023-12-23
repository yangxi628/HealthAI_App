package com.example.healthai.Controllers;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthai.Controllers.Adapters.MessageAdapter;
import com.example.healthai.Controllers.Helpers.SwitchViewHelper;
import com.example.healthai.Models.Message;
import com.example.healthai.Models.UserState;
import com.example.healthai.R;
import com.example.healthai.Views.AIChatFragment;
import com.example.healthai.Views.ProfileActivity;
import com.google.firebase.Timestamp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AIChatController {
    private final AIChatFragment fragment;
    ImageView profileButton;
    TextView usernameTextView;
    TextView emailTextView;
    RecyclerView recyclerView;
    TextView welcomeTextView;
    EditText messageEditText;
    ImageButton sendButton;
    List<Message> messageList;
    MessageAdapter messageAdapter;
    public AIChatController(AIChatFragment fragment) {
        this.fragment = fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_i_chat, container, false);
        initializeView(view);
        return view;
    }

    private void initializeView(View view) {
        profileButton = view.findViewById(R.id.profileButton);

        usernameTextView = view.findViewById(R.id.CurrentUserNameText);
        emailTextView = view.findViewById(R.id.currentUserEmail);

        UserState userState = UserState.getInstance();
        String currentUserNameText = "Hello, " + userState.getFirstName();
        String currentUserEmail = userState.getEmail();

        usernameTextView.setText(currentUserNameText);
        emailTextView.setText(currentUserEmail);
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.profileButton) {
                    SwitchViewHelper.switchToActivity(view.getContext(), ProfileActivity.class);
                }
            }
        };
        profileButton.setOnClickListener(buttonClickListener);

        messageList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recycler_view);
        welcomeTextView = view.findViewById(R.id.welcome_text);
        messageEditText = view.findViewById(R.id.message_edit_text);
        sendButton = view.findViewById(R.id.send_btn);

        //setup recycle view
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);


        sendButton.setOnClickListener((v) -> {
            sendQuestion();
        });

    }


    void callAPI(String question) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        String roleplayPrompt = "Speak like you are a medical help bot giving assistance to a patient. (Provide appropriate disclaimers that you are not a medical professional about advice)";

        String combinedPrompt = roleplayPrompt + " " + question;

        // Create JSON request body
        try {
            JSONObject mainJsonBody = new JSONObject();
            mainJsonBody.put("model", "text-davinci-003");
            mainJsonBody.put("prompt", combinedPrompt);
            mainJsonBody.put("max_tokens", 4000);
            mainJsonBody.put("temperature", 0);

            // Create RequestBody using MediaType
            RequestBody body = RequestBody.create(mainJsonBody.toString(), JSON);

            // Build the request
            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/completions")
                    .header("Authorization","Bearer REPLACE_WITH_API_KEY_HERE ") //NEED TO ADD THE API KEY HERE, CANT BE SHARED OVER GITHUB
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    addResponse("Failed to load response due to " + e.getMessage());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    try {
                        if (response.isSuccessful()) {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            JSONArray jsonArray = jsonObject.getJSONArray("choices");
                            String result = jsonArray.getJSONObject(0).getString("text");
                            addResponse(result.trim());
                        } else {
                            addResponse("Failed to load response due to " + response.body().string());
                        }
                    } catch (JSONException | IOException e) {
                        throw new RuntimeException(e);
                    } finally {
                        response.close();
                    }
                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    private void addToChat(String message, String sentBy) {
        fragment.requireActivity().runOnUiThread(() -> {
            messageList.add(new Message(message, sentBy, new Timestamp(new Date())));
            messageAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
        });
    }

    private void addResponse(String response) {
        addToChat(response, Message.SENT_BY_BOT);
    }

    private void sendQuestion() {
        String question = messageEditText.getText().toString().trim();
        addToChat(question, Message.SENT_BY_ME);
        messageEditText.setText("");
        callAPI(question);
        welcomeTextView.setVisibility(View.GONE);
    }
}
