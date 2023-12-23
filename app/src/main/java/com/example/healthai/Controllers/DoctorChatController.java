package com.example.healthai.Controllers;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthai.Controllers.Adapters.MessageAdapter;
import com.example.healthai.Controllers.Helpers.SwitchViewHelper;
import com.example.healthai.Models.Message;
import com.example.healthai.Models.UserState;
import com.example.healthai.R;
import com.example.healthai.Views.DoctorChatActivity;
import com.example.healthai.Views.NavigationBar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorChatController {
    ImageView backButton;
    RecyclerView recyclerView;
    EditText messageEditText;
    ImageButton sendButton;
    List<Message> messageList;
    MessageAdapter messageAdapter;
    FirebaseFirestore db;
    UserState userState;

    private final DoctorChatActivity view;

    public DoctorChatController(DoctorChatActivity view) {
        this.view = view;
        initialize();
    }

    private void initialize() {
        view.setContentView(R.layout.activity_doctor_chat);

        db = FirebaseFirestore.getInstance();
        userState = UserState.getInstance();

        backButton = view.findViewById(R.id.backButton);

        getUserMessagesFromFirebase();
        getDoctorMessagesFromFirebase();

        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.backButton) {
                    SwitchViewHelper.switchToActivity(view,NavigationBar.class);
                }
            }
        };

        backButton.setOnClickListener(buttonClickListener);
        messageList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recycler_view);
        messageEditText = view.findViewById(R.id.message_edit_text);
        sendButton = view.findViewById(R.id.send_btn);

        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(view);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        sendButton.setOnClickListener((v) -> {
            String question = messageEditText.getText().toString().trim();
            addToChat(question, Message.SENT_BY_ME, new Timestamp(new Date()));
            messageEditText.setText("");
            addMessageToFirebase(question);
        });
    }


    private void getDoctorMessagesFromFirebase() {

        db.collection("comments")
                .whereEqualTo("creator", userState.getDoctor())
                .whereEqualTo("reciever", userState.getUserID())
                .orderBy("created")


                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            Log.e("Firestore Error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            String comment = dc.getDocument().getString("comment");
                            Timestamp created = dc.getDocument().getTimestamp("created");
                            addToChat(comment, Message.SENT_BY_BOT,created);
                        }
                    }
                });
    }

    private void getUserMessagesFromFirebase() {

        db.collection("comments")
                .whereEqualTo("creator", userState.getUserID())
                .whereEqualTo("reciever", userState.getDoctor())

                .orderBy("created")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            Log.e("Firestore Error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            String comment = dc.getDocument().getString("comment");
                            Timestamp created = dc.getDocument().getTimestamp("created");
                            addToChat(comment, Message.SENT_BY_ME, created);
                        }
                    }
                });
    }

    private void addMessageToFirebase(String comment) {
        UserState userState = UserState.getInstance();

        Map<String, Object> commentData = new HashMap<>();
        commentData.put("comment", comment);
        commentData.put("created", FieldValue.serverTimestamp());
        commentData.put("creator", userState.getUserID());
        commentData.put("reciever", userState.getDoctor());

        db.collection("comments")
                .add(commentData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Firestore", "Comment added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore Error", "Error adding comment", e);
                    }
                });
    }


    private void addToChat(String message, String sentBy, Timestamp created) {
        view.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Message newMessage = new Message(message, sentBy, created);
                messageList.add(newMessage);

                // Sort the messageList based on timestamp
                Collections.sort(messageList, new Comparator<Message>() {
                    @Override
                    public int compare(Message m1, Message m2) {
                        if (m1.getCreated() == null || m2.getCreated() == null)
                            return 0;
                        return m1.getCreated().compareTo(m2.getCreated());
                    }
                });

                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

}
