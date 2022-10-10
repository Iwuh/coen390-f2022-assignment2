package com.mfaigan.assignment2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mfaigan.assignment2.database.entity.Profile;

import java.util.List;
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

        // Set the activity toolbar for menu use.
        setSupportActionBar(findViewById(R.id.toolbarMain));

        textViewTotalProfiles = findViewById(R.id.textViewTotalProfiles);

        databaseHelper = new DatabaseHelper(getApplicationContext());

        // Initialize the recyclerview to list all profiles in surname display.
        RecyclerView recyclerViewProfileList = findViewById(R.id.recyclerViewProfileList);
        useProfileNames = true;
        recyclerViewAdapter = new MainActivityRecyclerViewAdapter(databaseHelper.getAllProfilesOrderedByName(), useProfileNames);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        recyclerViewProfileList.setAdapter(recyclerViewAdapter);
        recyclerViewProfileList.setLayoutManager(manager);
        // Add a line divider between recyclerview elements.
        recyclerViewProfileList.addItemDecoration(new DividerItemDecoration(recyclerViewProfileList.getContext(), manager.getOrientation()));

        findViewById(R.id.floatingActionButtonAddProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddProfileDialogFragment fragment = new AddProfileDialogFragment();
                fragment.show(getSupportFragmentManager(), "AddProfile");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Default to surname display when entering the activity.
        useProfileNames = true;
        refreshProfileDisplay();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuItemToggleDisplay) {
            // Toggle the name display type and refresh the views.
            useProfileNames = !useProfileNames;
            refreshProfileDisplay();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshProfileDisplay() {
        String mode;
        List<Profile> profiles;
        if (useProfileNames) {
            mode = "Surname";
            profiles = databaseHelper.getAllProfilesOrderedByName();
        } else {
            mode = "ID";
            profiles = databaseHelper.getAllProfilesOrderedByUid();
        }
        recyclerViewAdapter.setProfiles(profiles);
        recyclerViewAdapter.setUseProfileNames(useProfileNames);
        // We need to fully refresh the adapter because the elements change position (name vs id order).
        recyclerViewAdapter.notifyDataSetChanged();
        textViewTotalProfiles.setText(String.format(Locale.ENGLISH, "%d Profiles, by %s", recyclerViewAdapter.getItemCount(), mode));
    }
}