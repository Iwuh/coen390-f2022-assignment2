package com.mfaigan.assignment2;

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

        public void setText(Profile profile, int position, boolean useProfileName) {
            String text;
            if (useProfileName) {
                text = String.format(Locale.ENGLISH, "%d. %s, %s", position, profile.getSurname(), profile.getName());
            }
            else {
                text = String.format(Locale.ENGLISH, "%d. %d", position, profile.getUid());
            }
            textViewRecyclerViewProfile.setText(text);
        }

        // TODO implement click handler
    }

    private List<Profile> profiles;
    private boolean useProfileNames;

    public MainActivityRecyclerViewAdapter(List<Profile> profiles, boolean useProfileNames) {
        this.profiles = profiles;
        this.useProfileNames = useProfileNames;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

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
        holder.setText(profiles.get(position), position, useProfileNames);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }
}
