package com.mfaigan.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mfaigan.assignment2.database.AccessType;
import com.mfaigan.assignment2.database.entity.Access;
import com.mfaigan.assignment2.database.entity.Profile;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    TextView textViewDisplaySurname;
    TextView textViewDisplayName;
    TextView textViewDisplayId;
    TextView textViewDisplayGpa;
    TextView textViewDisplayCreationDate;

    ProfileActivityRecyclerViewAdapter recyclerViewAdapter;

    DatabaseHelper databaseHelper;
    Profile currentProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Enable the toolbar and up-navigation.
        setSupportActionBar(findViewById(R.id.toolbarProfile));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewDisplaySurname = findViewById(R.id.textViewDisplaySurname);
        textViewDisplayName = findViewById(R.id.textViewDisplayName);
        textViewDisplayId = findViewById(R.id.textViewDisplayId);
        textViewDisplayGpa = findViewById(R.id.textViewDisplayGpa);
        textViewDisplayCreationDate = findViewById(R.id.textViewDisplayCreationDate);

        databaseHelper = new DatabaseHelper(getApplicationContext());

        // Setup the recyclerview to be initially empty; we fill it out in onStart.
        RecyclerView recyclerView = findViewById(R.id.recyclerViewAccessList);
        recyclerViewAdapter = new ProfileActivityRecyclerViewAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(this);

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), manager.getOrientation()));

        findViewById(R.id.buttonDeleteProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentProfile != null) {
                    databaseHelper.deleteProfile(currentProfile);
                    returnToMain();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Get the ID of the profile that triggered this activity switch.
        Intent parentIntent = getIntent();
        long profileId = parentIntent.getLongExtra(getString(R.string.intent_extra_key_profile_id), 0);
        if (profileId == 0) {
            // If we weren't passed a profile ID from the main activity, immediately return.
            returnToMain();
        }

        // Fill out the profile information textViews.
        currentProfile = databaseHelper.getProfile(profileId);
        textViewDisplaySurname.setText(currentProfile.getSurname());
        textViewDisplayName.setText(currentProfile.getName());
        textViewDisplayId.setText(Long.toString(currentProfile.getUid()));
        textViewDisplayGpa.setText(Double.toString(currentProfile.getGpa()));
        textViewDisplayCreationDate.setText(DateFormat.format("yyyy-MM-dd @ hh:mm:ss", currentProfile.getCreationDate()));

        // Before updating the recyclerview, add a new "Opened" access to the database.
        databaseHelper.addNewAccess(profileId, AccessType.Opened);

        // Update the recyclerView with a history of all accesses.
        List<Access> accesses = databaseHelper.getAllAccessesToProfile(profileId);
        recyclerViewAdapter.setAccessList(accesses);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Before leaving the activity, add a new "Closed" access to the database, if we had a profile ID given.
        if (currentProfile != null) {
            databaseHelper.addNewAccess(currentProfile.getUid(), AccessType.Closed);
        }
    }

    private void returnToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}