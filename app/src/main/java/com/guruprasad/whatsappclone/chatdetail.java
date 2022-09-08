package com.guruprasad.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.guruprasad.whatsappclone.Adapters.ChatAdapter;
import com.guruprasad.whatsappclone.Models.MessageModel;
import com.guruprasad.whatsappclone.databinding.ActivityChatdetailBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class chatdetail extends AppCompatActivity {

    FirebaseAuth auth ;
    FirebaseDatabase firebaseDatabase ;
    ImageView back ;




    ActivityChatdetailBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityChatdetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        back = findViewById(R.id.back);

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        final String senderid  = auth.getUid();
        String receiverid = getIntent().getStringExtra("userid");
        String username = getIntent().getStringExtra("username");
        String profilepic = getIntent().getStringExtra("profilepic");

        binding.usernameChat.setText(username);
        Picasso.get().load(profilepic).placeholder(R.drawable.user).into(binding.profileImage);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Home.class));
                finish();
            }
        });
        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        final ChatAdapter chatAdapter = new ChatAdapter(messageModels,this);

        binding.chatscreen.setAdapter(chatAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.chatscreen.setLayoutManager(linearLayoutManager);

        final  String senderroom = senderid+receiverid;
        final  String receiverroom = receiverid+senderid;






        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mess = binding.message.getText().toString();

                final MessageModel model = new MessageModel(senderid,mess);
                model.setTimestamp(new Date().getTime());
                binding.message.setText("");

                firebaseDatabase.getReference().child("chats").child(senderroom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                         firebaseDatabase.getReference().child("chats").child(receiverroom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void unused) {


                             }
                         });
                    }
                });


            }
        });

    }
}