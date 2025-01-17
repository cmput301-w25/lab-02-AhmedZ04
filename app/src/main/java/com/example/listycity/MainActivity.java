package com.example.listycity;

import android.app.Dialog;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityList = findViewById(R.id.city_list);

        String[]cities = {"Edmonton", "Moscow"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);
        Button addButton = findViewById(R.id.add_button);
        Button deleteButton = findViewById(R.id.delete_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this);

                // Inflate the custom layout
                View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_popup, null);
                dialog.setContentView(dialogView);

                // Find the EditText and Button in the popup
                EditText inputCity = dialogView.findViewById(R.id.edit_text_dialog);
                Button submitButton = dialogView.findViewById(R.id.submit_button);

                // Handle the submit button click
                submitButton.setOnClickListener(V -> {
                    String cityName = inputCity.getText().toString().trim();
                    if (!cityName.isEmpty()) {
                        // Add the new city to the list and notify the adapter
                        dataList.add(cityName);
                        cityAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "City added: " + cityName, Toast.LENGTH_SHORT).show();
                        dialog.dismiss(); // Close the popup
                    } else {
                        Toast.makeText(MainActivity.this, "Please enter a valid city name", Toast.LENGTH_SHORT).show();
                    }
                });

                // Show the dialog
                dialog.show();
            }



        });

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            // Get the selected city name
            String selectedCity = dataList.get(position);

            // Show a Toast (optional)
            Toast.makeText(MainActivity.this, "Selected: " + selectedCity, Toast.LENGTH_SHORT).show();

            // Set the delete button's onClickListener to delete the selected city
            deleteButton.setOnClickListener(v -> {
                // Remove the selected item from the list
                dataList.remove(position);

                // Notify the adapter of the data change
                cityAdapter.notifyDataSetChanged();

                // Show confirmation
                Toast.makeText(MainActivity.this, "Deleted: " + selectedCity, Toast.LENGTH_SHORT).show();
            });
        });




    }
}

