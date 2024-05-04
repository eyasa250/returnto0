package com.example.returnto0.ui.event;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.returnto0.R;
import com.example.returnto0.RetrofitClient;
import com.example.returnto0.room.Room;
import com.example.returnto0.room.RoomService;
import com.example.returnto0.task.Task;
import com.example.returnto0.task.TaskService;
import com.example.returnto0.ui.Room.RoomAdapter;
import com.example.returnto0.ui.Room.RoomSpinnerAdapter;
import com.example.returnto0.ui.task.TaskSpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class addeventFragment extends Fragment {

    private RoomSpinnerAdapter roomSpinneradapter;
    private TaskSpinnerAdapter taskSpinneradapter;
    private List<Room> roomList;
    private List<Task> taskList;
   Spinner roomSpinner;
    Spinner taskSpinner;
    public addeventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addevent, container, false);
        roomSpinneradapter = new RoomSpinnerAdapter(new ArrayList<>()); // Initialize with empty list
        taskSpinneradapter = new TaskSpinnerAdapter(new ArrayList<>()); // Initialize with empty list

        roomSpinner = view.findViewById(R.id.spinnerroom);
        taskSpinner = view.findViewById(R.id.spinnertask);

        RoomService roomservice = RetrofitClient.getClient().create(RoomService.class); // Assuming you have a MyService interface for API calls
        TaskService taskservice = RetrofitClient.getClient().create(TaskService.class); // Assuming you have a MyService interface for API calls

        Call<List<Room>> roomListCall = roomservice.getAllRooms();
        roomListCall.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    roomList = response.body();
                    roomSpinneradapter.updateList(roomList);
                    roomSpinner.setAdapter(roomSpinneradapter);
                } else {
                    // Handle API error
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                // Handle network error
            }
        });

        // Fetch task list (replace with your actual API call)
        Call<List<Task>> taskListCall = taskservice.getAllTasks(); // Assuming you have a method to get task list
        taskListCall.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful()) {
                    taskList = response.body();
                    taskSpinneradapter.updateList(taskList); // Update the task spinner adapter with fetched data
                    taskSpinner.setAdapter(taskSpinneradapter);
                } else {
                    // Handle API error
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                // Handle network error
            }
        });

        return view;
    }
}