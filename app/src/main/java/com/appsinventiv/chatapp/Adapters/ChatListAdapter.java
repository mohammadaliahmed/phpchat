package com.appsinventiv.chatapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appsinventiv.chatapp.Activities.ChattingScreen;
import com.appsinventiv.chatapp.Model.MessageModel;
import com.appsinventiv.chatapp.Model.UserMessages;
import com.appsinventiv.chatapp.Model.UserModel;
import com.appsinventiv.chatapp.R;
import com.appsinventiv.chatapp.Utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {
    Context context;

    List<UserMessages> itemList;

    public ChatListAdapter(Context context, List<UserMessages> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public void setItemList(List<UserMessages> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item_layout, viewGroup, false);
        ChatListAdapter.ViewHolder viewHolder = new ChatListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final UserMessages userModel = itemList.get(i);
        viewHolder.name.setText(userModel.getUserName());
        viewHolder.time.setText(CommonUtils.getFormattedDate(userModel.getTime()));
        viewHolder.message.setText(userModel.getMessageText());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ChattingScreen.class);
                i.putExtra("roomId", userModel.getRoomId());
                i.putExtra("name", userModel.getUserName());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, message, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
        }
    }


}
