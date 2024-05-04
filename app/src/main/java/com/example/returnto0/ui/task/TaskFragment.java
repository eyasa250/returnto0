package com.example.returnto0.ui.task;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.returnto0.OnItemClickListener;
import com.example.returnto0.R;
import com.example.returnto0.RetrofitClient;
import com.example.returnto0.room.Room;
import com.example.returnto0.room.RoomService;
import com.example.returnto0.task.Task;
import com.example.returnto0.task.TaskService;
import com.example.returnto0.ui.Room.AddRoomDialogFragment;
import com.example.returnto0.ui.Room.RoomAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskFragment extends Fragment implements  AddTaskDialogFragment.OnTaskAddedListener ,OnItemClickListener
    {
        private TaskAdapter adapter;

    private RecyclerView taskRecyclerView;
    private TaskAdapter taskAdapter;
        private List<Task> taskList = new ArrayList<>(); // Initialize roomList properly

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {

        super.onViewCreated(view, savedInstanceState);
        taskAdapter = new TaskAdapter(new ArrayList<>()); // Empty list initially
        taskAdapter.setOnItemClickListener(this);

        TaskService TaskService = RetrofitClient.getClient().create(TaskService.class);
        Call<List<Task>> call = TaskService.getAllTasks();
        Log.d("TodoListFragment", "API URL: " + call.request().url());

        taskRecyclerView = view.findViewById(R.id.recyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskRecyclerView.setAdapter(taskAdapter);
        TaskService.getAllTasks().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful()) {
                    List<Task> tasks = response.body();
                    Log.d("TaskFragment", "Tasks received: " + tasks.size());
                    taskAdapter.setTaskList(tasks); // Update adapter with tasks (existing code)
                } else {
                    // Handle API error
                    Log.e("TaskFragment", "Error fetching tasks: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                // Handle network error or other failures
                Log.e("TaskFragment", "Error fetching tasks", t);

                // You can display an error message to the user here
                // For example:
                Toast.makeText(getContext(), "Failed to load tasks!", Toast.LENGTH_SHORT).show();
            }
        });
        FloatingActionButton addTaskButton = view.findViewById(R.id.addtaskmActionButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTaskDialog();
            }
        });

    }  private void showAddTaskDialog() {
        AddTaskDialogFragment dialogFragment = new AddTaskDialogFragment();
        dialogFragment.setOnRoomAddedListener(this); // Pass Roomfragment as the listener
        dialogFragment.show(getParentFragmentManager(), "AddRoomDialogFragment");
    }



        @Override
        public void onTaskAdded(String taskName) {
            // Assuming you have a Room object ready to be sent to the server
            Task task = new Task(taskName); // Create a Room object with the given name

            // Get the Retrofit service instance
            TaskService TaskService = RetrofitClient.getClient().create(TaskService.class);
            Log.d("TaskFragment", "task list size: " + taskList.size());
            // Make the API call to create the room
            Call<Task> call = TaskService.createTask(task);
            call.enqueue(new Callback<Task>() {
                @Override
                public void onResponse(Call<Task> call, Response<Task> response) {
                    if (response.isSuccessful()) {
                        // Room created successfully, handle the response if needed
                        Task createdTask = response.body();
                        if (createdTask != null) {
                            // Add the created room to your list and update the RecyclerView
                            taskList.add(createdTask);

                            // Notify the adapter of the data change
                            adapter.notifyItemInserted(taskList.size() - 1);

                            // Scroll to the newly added item
                            taskRecyclerView.scrollToPosition(taskList.size() - 1);                    }
                    } else {
                        // Handle error response
                        Toast.makeText(requireContext(), "Failed to create room", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Task> call, Throwable t) {
                    // Handle network failures or other errors
                    Log.e("RoomFragment", "API call failed", t);
                    Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                }

            });
        }

        @Override
        public void onItemClick(Room room) {

        }

        @Override
        public void onItemClick(Task task) {

        }
    }
