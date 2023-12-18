package com.example.hemon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class ChatAdapter extends BaseAdapter {

    private Context context;
    private List<ChatMessage> messageList;

    public ChatAdapter(Context context, List<ChatMessage> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            viewHolder = new ViewHolder();

            // Determine the layout based on the sender
            ChatMessage message = messageList.get(position);
            if ("user".equals(message.getSender())) {
                convertView = inflater.inflate(R.layout.item_chat_message, parent, false);
                viewHolder.messageTextView = convertView.findViewById(R.id.messageTextView);
            } else {
                convertView = inflater.inflate(R.layout.item_chat_message_reply, parent, false);
                viewHolder.messageTextView = convertView.findViewById(R.id.messageTextView);
            }

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ChatMessage message = messageList.get(position);
        viewHolder.messageTextView.setText(message.getMessage());

        return convertView;
    }

    private static class ViewHolder {
        TextView messageTextView;

    }
}

