package com.example.returnto0;

import com.example.returnto0.room.Room;
import com.example.returnto0.task.Task;

public interface OnItemClickListener {
    void onItemClick(Room room);

    void onItemClick(Task task);
}
