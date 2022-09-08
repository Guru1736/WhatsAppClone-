package com.guruprasad.whatsappclone.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.guruprasad.whatsappclone.Models.MessageModel;
import com.guruprasad.whatsappclone.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter{

    ArrayList<MessageModel> messageModels ;
    Context context;
    int SENDER_VIEW_TYPE = 1 ;
    int RECEIVER_VIEW_TYPE = 2 ;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == SENDER_VIEW_TYPE)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciver,parent,false);
            return new ReceiverViewHolder(view);
        }

    }

    @Override
    public int getItemViewType(int position) {

        if (messageModels.get(position).getUid().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else
        {
            return RECEIVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel= messageModels.get(position);

        if (holder.getClass() == SenderViewHolder.class)
        {
            ((SenderViewHolder)holder).sendermsg.setText(messageModel.getMessage());
        }
        else
        {
            ((ReceiverViewHolder)holder).receivermsg.setText(messageModel.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }





    public  class ReceiverViewHolder extends RecyclerView.ViewHolder {

        TextView receivermsg , receivertime;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            receivermsg = itemView.findViewById(R.id.receivertext);
            receivertime = itemView .findViewById(R.id.receivertime);
        }
    }

    public class SenderViewHolder extends  RecyclerView.ViewHolder {
        TextView sendermsg , sendertime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            sendermsg = itemView.findViewById(R.id.sendertext);
            sendertime = itemView.findViewById(R.id.sendertime);

        }
    }
}
