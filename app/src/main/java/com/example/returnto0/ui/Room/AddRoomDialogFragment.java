package com.example.returnto0.ui.Room;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.returnto0.R;

public class AddRoomDialogFragment extends DialogFragment {

    private EditText roomNameEditText;
    private OnRoomAddedListener listener;

    public interface OnRoomAddedListener {
        void onRoomAdded(String roomName);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_room, null);
        roomNameEditText = view.findViewById(R.id.editTextRoomName);

        builder.setView(view)
                .setTitle("Add Room")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String roomName = roomNameEditText.getText().toString().trim();
                        listener.onRoomAdded(roomName);
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

    public void setOnRoomAddedListener(OnRoomAddedListener listener) {
        this.listener = listener;
    }
}
