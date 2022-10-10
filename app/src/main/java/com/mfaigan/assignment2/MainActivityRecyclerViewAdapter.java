package com.mfaigan.assignment2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mfaigan.assignment2.database.entity.Profile;

import java.util.List;
import java.util.Locale;

public class MainActivityRecyclerViewAdapter extends RecyclerView.Adapter<MainActivityRecyclerViewAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewRecyclerViewProfile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRecyclerViewProfile = itemView.findViewById(R.id.textViewRecyclerViewProfile);
        }

        public void setText(Profile profile, int lineNumber, boolean useProfileName) {
            String text;
            if (useProfileName) {
                text = String.format(Locale.ENGLISH, "%d. %s, %s", lineNumber, profile.getSurname(), profile.getName());
            } else {
                text = String.format(Locale.ENGLISH, "%d. %d", lineNumber, profile.getUid());
            }
            textViewRecyclerViewProfile.setText(text);
        }
    }

    private List<Profile> profiles;
    private boolean useProfileNames;

    public MainActivityRecyclerViewAdapter(List<Profile> profiles, boolean useProfileNames) {
        this.profiles = profiles;
        this.useProfileNames = useProfileNames;
    }

    /**
     * Set the list of profiles to be displayed in the RecyclerView.
     *
     * @param profiles The list of profiles to be displayed. Should be ordered such that the entries at the top of the list come first.
     */
    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    /**
     * Set whether or not profiles should be displayed by name or ID. Does not affect the order in which they are displayed.
     *
     * @param useProfileNames If true, display profile names. Otherwise, display profile IDs.
     */
    public void setUseProfileNames(boolean useProfileNames) {
        this.useProfileNames = useProfileNames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // The internal list is zero-indexed, but we want the display to be one-indexed.
        holder.setText(profiles.get(position), position + 1, useProfileNames);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getLayoutPosition();

                Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                intent.putExtra(view.getContext().getString(R.string.intent_extra_key_profile_id), profiles.get(pos).getUid());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }
}
