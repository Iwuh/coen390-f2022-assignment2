package com.mfaigan.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private MainActivityRecyclerViewAdapter recyclerViewAdapter;
    private DatabaseHelper databaseHelper;

    private boolean useProfileNames;

    private TextView textViewTotalProfiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTotalProfiles = findViewById(R.id.textViewTotalProfiles);

        databaseHelper = new DatabaseHelper(getApplicationContext());

        RecyclerView recyclerViewProfileList = findViewById(R.id.recyclerViewProfileList);
        useProfileNames = true;
        recyclerViewAdapter = new MainActivityRecyclerViewAdapter(databaseHelper.getAllProfilesOrdered(), useProfileNames);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        recyclerViewProfileList.setAdapter(recyclerViewAdapter);
        recyclerViewProfileList.setLayoutManager(manager);
        recyclerViewProfileList.addItemDecoration(new DividerItemDecoration(recyclerViewProfileList.getContext(), manager.getOrientation()));
        updateTotalProfileDisplay();
    }

    private void updateTotalProfileDisplay() {
        String mode;
        if (useProfileNames) {
            mode = "Surname";
        }
        else {
            mode = "ID";
        }
        textViewTotalProfiles.setText(String.format(Locale.ENGLISH, "%d Profiles, by %s", recyclerViewAdapter.getItemCount(), mode));
    }
}