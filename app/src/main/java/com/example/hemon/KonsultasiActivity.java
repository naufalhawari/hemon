package com.example.hemon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.smartreply.FirebaseTextMessage;
import com.google.firebase.ml.naturallanguage.smartreply.FirebaseSmartReply;
import com.google.firebase.ml.naturallanguage.smartreply.SmartReplySuggestion;
import com.google.firebase.ml.naturallanguage.smartreply.SmartReplySuggestionResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class KonsultasiActivity extends AppCompatActivity {

    private List<ChatMessage> messageList;
    private ChatAdapter chatAdapter;
    private List<FirebaseTextMessage> conversation;
    private FirebaseFirestore db;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konsultasi);

        final EditText messageEditText = findViewById(R.id.messageEditText);
        Button sendButton = findViewById(R.id.sendButton);
        ListView messageListView = findViewById(R.id.messageListView);
        preferenceManager = new PreferenceManager(getApplicationContext());
        db = FirebaseFirestore.getInstance();

        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        });

        messageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, messageList);
        conversation = new ArrayList<>();
        messageListView.setAdapter(chatAdapter);

        db.collection("konsultasi")
//                .whereEqualTo("emailPengguna", preferenceManager.getString("email"))
                .orderBy("tanggalKirim", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult().getDocuments().size() > 0){
                            for (int i = 0; i < task.getResult().getDocuments().size(); i++) {

                                DocumentSnapshot data = task.getResult().getDocuments().get(i);

                                if (data.getString("emailPengguna").equals(preferenceManager.getString("email"))) {
                                    messageList.add(new ChatMessage(data.getString("pesan"), "user"));

                                    messageList.add(new ChatMessage(data.getString("balasan"), "bot"));


                                    chatAdapter.notifyDataSetChanged();
                                }

                            }


                        } else {
                            Toast.makeText(getApplicationContext(), "Anda belum melakukan konsultasi.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageText = messageEditText.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    generateSmartReplySuggestions(messageText);
                    messageEditText.setText("");
                }
            }
        });

    }


    private void generateSmartReplySuggestions(String chatMessage) {
        conversation.add(FirebaseTextMessage.createForLocalUser(
                chatMessage, System.currentTimeMillis()));
        FirebaseSmartReply smartReply = FirebaseNaturalLanguage.getInstance().getSmartReply();
        smartReply.suggestReplies(conversation)
                .addOnSuccessListener((OnSuccessListener<SmartReplySuggestionResult>) result -> {
                    if (result.getStatus() == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
                        Toast.makeText(getApplicationContext(), "Gunakan bahasa inggris", Toast.LENGTH_SHORT).show();
                    } else if (result.getStatus() == SmartReplySuggestionResult.STATUS_SUCCESS) {
                        ChatMessage userMessage = new ChatMessage(chatMessage, "user");
                        messageList.add(userMessage);
                        messageList.add(new ChatMessage(result.getSuggestions().get(0).getText().trim(), "bot"));
                        HashMap<String, Object> data = new HashMap<>();
                        data.put("emailPengguna", preferenceManager.getString("email"));
                        data.put("pesan", chatMessage);
                        data.put("balasan", result.getSuggestions().get(0).getText().trim());
                        data.put("tanggalKirim", FieldValue.serverTimestamp());

                        db.collection("konsultasi")
                                .add(data);
                    }
                });


    }
}
