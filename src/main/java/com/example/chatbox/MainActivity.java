package com.example.chatbox;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbox.Message;
import com.example.chatbox.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    private EditText messageInput;
    private ImageButton sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.chat_recycler_view);
        messageInput = findViewById(R.id.message_input);
        sendButton = findViewById(R.id.send_button);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        // Add a welcome message
        addBotMessage("Hello! I'm your HealthCare Assistant. How can I help you today?");
    }

    private void sendMessage() {
        String messageText = messageInput.getText().toString().trim();
        if (!messageText.isEmpty()) {
            // Add user message to the list
            messageList.add(new Message(messageText, true));
            messageAdapter.notifyItemInserted(messageList.size() - 1);
            recyclerView.scrollToPosition(messageList.size() - 1);

            // Clear input
            messageInput.setText("");

            // Get bot response
            generateBotResponse(messageText);
        }
    }

    private void generateBotResponse(String userMessage) {
        String botResponse = getBotResponse(userMessage.toLowerCase());
        addBotMessage(botResponse);
    }

    private void addBotMessage(String message) {
        messageList.add(new Message(message, false));
        messageAdapter.notifyItemInserted(messageList.size() - 1);
        recyclerView.scrollToPosition(messageList.size() - 1);
    }

    private String getBotResponse(String query) {
        if (query.contains("hello") || query.contains("hi")) {
            return "Hello there! How can I assist you with your health concerns?";
        } else if (query.contains("fever")) {
            return "For a fever, it's important to rest, drink plenty of fluids, and you can take over-the-counter medication like paracetamol. If the fever persists for more than 3 days or is very high, please consult a doctor.";
        } else if (query.contains("headache")) {
            return "For a mild headache, you can try resting in a quiet, dark room, applying a cold compress to your forehead, and staying hydrated. If it's severe or frequent, seeing a doctor is recommended.";
        } else if (query.contains("cough") || query.contains("sore throat")) {
            return "For a cough or sore throat, try gargling with warm salt water, drinking warm liquids like tea with honey, and using throat lozenges. If your cough is severe or you have trouble breathing, seek medical attention.";
        } else if (query.contains("thank you") || query.contains("thanks")) {
            return "You're welcome! Is there anything else I can help you with?";
        } else if (query.contains("bye")) {
            return "Goodbye! Stay healthy.";
        } else {
            return "I'm sorry, I have limited knowledge. For specific medical advice, please consult a qualified healthcare professional.";
        }
    }
}
