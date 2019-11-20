package com.appsinventiv.chatapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appsinventiv.chatapp.Model.MessageModel;
import com.appsinventiv.chatapp.Model.UserModel;
import com.appsinventiv.chatapp.R;
import com.appsinventiv.chatapp.Utils.CommonUtils;
import com.appsinventiv.chatapp.Utils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {
    Context context;

    List<MessageModel> itemList;

    public int RIGHT_CHAT = 1;
    public int LEFT_CHAT = 0;

    public MessagesAdapter(Context context,List<MessageModel> itemList) {
        this.context = context;
        this.itemList = itemList;

    }

    public void setItemList(List<MessageModel> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public int getItemViewType(int position) {
        MessageModel model = itemList.get(position);
        if (model.getMessageById() != null) {
            if (model.getMessageById()== SharedPrefs.getUserModel().getId()) {
                return RIGHT_CHAT;
            } else {
                return LEFT_CHAT;
            }
        }
        return -1;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        if (viewType == RIGHT_CHAT) {
            View view = LayoutInflater.from(context).inflate(R.layout.right_chat_layout, parent, false);
            viewHolder = new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.left_chat_layout, parent, false);
            viewHolder = new ViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final MessageModel userModel = itemList.get(i);
        viewHolder.messageText.setText(userModel.getMessageText());
        viewHolder.time.setText(CommonUtils.getFormattedDate(userModel.getTime()));


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageText, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            time = itemView.findViewById(R.id.time);
        }
    }


}
