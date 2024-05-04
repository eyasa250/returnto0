package com.example.returnto0.ui.task;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.returnto0.R;

public class AddTaskDialogFragment extends DialogFragment {

    private EditText taskNameEditText;
    private OnTaskAddedListener listener;

    public interface OnTaskAddedListener {
        void onTaskAdded(String taskName);


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_room, null);
        taskNameEditText = view.findViewById(R.id.editTextRoomName);

        builder.setView(view)
                .setTitle("Add task")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String taskName = taskNameEditText.getText().toString().trim();
                        listener.onTaskAdded(taskName);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });

        return builder.create();
    }

    public void setOnRoomAddedListener(OnTaskAddedListener listener) {
        this.listener = listener;
    }
}
