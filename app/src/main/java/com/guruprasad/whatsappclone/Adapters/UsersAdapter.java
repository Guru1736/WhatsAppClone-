package com.guruprasad.whatsappclone.Adapters;



import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.guruprasad.whatsappclone.Models.users;
import com.guruprasad.whatsappclone.R;
import com.guruprasad.whatsappclone.chatdetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    ArrayList<users> list;

    public UsersAdapter(ArrayList<users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_user,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        users users = list.get(position);
        Picasso.get().load(users.getProfile_pic()).placeholder(R.drawable.user).into(holder.imageView);
        holder.username.setText(users.getFullname());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , chatdetail.class);
                intent.putExtra("userid",users.getUserID());
                intent.putExtra("profilepic",users.getProfile_pic());
                intent.putExtra("username",users.getFullname());
                context.startActivity(intent);



            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView ;
        TextView username , lastmessage ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.profile_image);
            username = itemView.findViewById(R.id.username);
            lastmessage = itemView.findViewById(R.id.lastMessage);
        }
    }
}
