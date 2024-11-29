package com.example.ab_4;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppCompatEditText editText;
    private SwitchCompat urgentSwitch;

    private ArrayList<ToDoItem> toDoList;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize UI components
        editText = findViewById(R.id.editText);
        urgentSwitch = findViewById(R.id.urgentSwitch);
        AppCompatButton addButton = findViewById(R.id.addButton);
        ListView listView = findViewById(R.id.listView);

        // Initialize list and adapter
        toDoList = new ArrayList<>();
        adapter = new CustomAdapter(this, toDoList);

        listView.setAdapter(adapter);

        // Add button click listener
        addButton.setOnClickListener(v -> {
            String text = Objects.requireNonNull(editText.getText()).toString().trim();
            boolean isUrgent = urgentSwitch.isChecked();

            if (!text.isEmpty()) {
                // Add new item to the list
                toDoList.add(new ToDoItem(text, isUrgent));
                adapter.notifyDataSetChanged();
                editText.setText(""); // Clear input field
            }
        });

        // Set long-click listener for deleting items
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            showDeleteDialog(position);

            return true;
        });
    }

    // Method to display an AlertDialog
    private void showDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.delete_title));
        builder.setMessage(getString(R.string.delete_message) + position + "?");

        builder.setPositiveButton(getString(R.string.yes), (dialog, which) -> {
            toDoList.remove(position); // Remove the item
            adapter.notifyDataSetChanged(); // Refresh the list
        });

        builder.setNegativeButton(getString(R.string.no), null);
        builder.show();
    }
}