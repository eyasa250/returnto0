package com.example.returnto0.ui.Room;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.returnto0.OnItemClickListener;
import com.example.returnto0.room.*;

import com.example.returnto0.R;
import com.example.returnto0.RetrofitClient;
import com.example.returnto0.room.RoomService;
import com.example.returnto0.task.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Roomfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Roomfragment extends Fragment implements OnItemClickListener, AddRoomDialogFragment.OnRoomAddedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private RoomAdapter adapter;
    private RecyclerView recyclerView;
    private List<Room> roomList = new ArrayList<>(); // Initialize roomList properly

    public static Roomfragment newInstance(String param1, String param2) {
        Roomfragment fragment = new Roomfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_room_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = view.findViewById(R.id.recyclerView); // Initialize recyclerView here
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new RoomAdapter(new ArrayList<>()); // Initialize with empty list
        adapter.setOnItemClickListener(this);

        recyclerView.setAdapter(adapter);
        Log.d("TodoListFragment", "RecyclerView: Adapter is set.");

        RoomService roomService = RetrofitClient.getClient().create(RoomService.class);
        Call<List<Room>> call = roomService.getAllRooms();

        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (!response.isSuccessful()) {
                    Log.e("TodoListFragment", "API Response Error: Code " + response.code());

                    Log.e("TodoListFragment", "Response Code: " + response.code());
                    Toast.makeText(requireContext(), "Failed to fetch room data (code: " + response.code() + ")", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Room> rooms = response.body();
                if (rooms    != null) {
                    adapter.setRoomList(rooms);
                    Log.d("TodoListFragment", "API Response: Rooms updated, count = " + rooms.size());
                } else {
                    Toast.makeText(requireContext(), "No rooms available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Log.e("TodoListFragment", "API call failed", t);
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        FloatingActionButton addRoomButton = view.findViewById(R.id.addroomActionButton);
        addRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddRoomDialog();
            }
        });
    }

    private void showAddRoomDialog() {
        AddRoomDialogFragment dialogFragment = new AddRoomDialogFragment();
        dialogFragment.setOnRoomAddedListener(this); // Pass Roomfragment as the listener
        dialogFragment.show(getParentFragmentManager(), "AddRoomDialogFragment");
    }
    public void onRoomAdded(String roomName) {
        // Assuming you have a Room object ready to be sent to the server
        Room room = new Room(roomName); // Create a Room object with the given name

        // Get the Retrofit service instance
        RoomService roomService = RetrofitClient.getClient().create(RoomService.class);
        Log.d("RoomFragment", "Room list size: " + roomList.size());
        // Make the API call to create the room
        Call<Room> call = roomService.createRoom(room);
        call.enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {
                    // Room created successfully, handle the response if needed
                    Room createdRoom = response.body();
                    if (createdRoom != null) {
                        // Add the created room to your list and update the RecyclerView
                        roomList.add(createdRoom);

                        // Notify the adapter of the data change
                        adapter.notifyItemInserted(roomList.size() - 1);

                        // Scroll to the newly added item
                        recyclerView.scrollToPosition(roomList.size() - 1);                    }
                } else {
                    // Handle error response
                    Toast.makeText(requireContext(), "Failed to create room", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
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