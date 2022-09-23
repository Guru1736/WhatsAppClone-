package com.guruprasad.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.guruprasad.whatsappclone.Adapters.ChatAdapter;
import com.guruprasad.whatsappclone.Models.MessageModel;
import com.guruprasad.whatsappclone.databinding.ActivityGroupchatBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Groupchat extends AppCompatActivity {
    ActivityGroupchatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupchatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Home.class));
                finish();
            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        String senderID = FirebaseAuth.getInstance().getUid();

        ArrayList<MessageModel> messageModels = new ArrayList<>();

        ChatAdapter adapter = new ChatAdapter(messageModels, this);
        binding.chatscreen.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatscreen.setLayoutManager(layoutManager);



        database.getReference().child("group_chat").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    MessageModel model = dataSnapshot.getValue(MessageModel.class);
                    messageModels.add(model);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupmessage = binding.message.getText().toString();

                if (TextUtils.isEmpty(groupmessage))
                {
                    binding.message.setError("Enter the message please !! ");
                    return;
                }

                final MessageModel model = new MessageModel(senderID,groupmessage);
                model.setTimestamp(new Date().getTime());
                binding.message.setText("");


                database.getReference().child("group_chat").push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });

            }
        });
    }
}