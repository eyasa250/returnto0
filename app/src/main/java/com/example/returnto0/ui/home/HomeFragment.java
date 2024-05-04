package com.example.returnto0.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.returnto0.R;

import com.example.returnto0.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Find the button by its ID
        Button viewTaskListButton = root.findViewById(R.id.tasklist);
        FloatingActionButton  addeventActionButton = root.findViewById(R.id.addeventActionButton);

        // Set OnClickListener for the button
        viewTaskListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the button click action here
                // For example, navigate to the task list fragment
                navigateToTaskListFragment();
            }


        });
        addeventActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToNewEventFormFragment();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void navigateToTaskListFragment() {
        // Replace the current fragment with the task list fragment
        Navigation.findNavController(requireView()).navigate(R.id.navigation_task);
    }
    private void navigateToNewEventFormFragment() {
        // Replace the current fragment with the task list fragment
        Navigation.findNavController(requireView()).navigate(R.id.navigation_addEvent);
    }
}
