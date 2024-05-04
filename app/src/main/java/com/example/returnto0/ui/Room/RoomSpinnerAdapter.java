package com.example.returnto0.ui.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.returnto0.R;
import com.example.returnto0.room.Room;

import java.util.List;

public class RoomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private List<Room> roomList;

    public RoomSpinnerAdapter(List<Room> roomList) {
        this.roomList = roomList;
    }

    @Override
    public int getCount() {
        Log.d("RoomAdapter", "getItemCount: Total items in the dataset = " + roomList.size());

        return roomList.size();

    }
    public void updateList(List<Room> newList) {
        roomList = newList;
        notifyDataSetChanged(); // Notify the adapter about data change
    }
    @Override
    public Object getItem(int position) {
        return roomList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item_room, parent, false);
        }

        TextView roomNameTextView = convertView.findViewById(R.id.room_name_text_view);
        Room room = roomList.get(position);
        roomNameTextView.setText(room.getName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
