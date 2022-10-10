package com.mfaigan.assignment2;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mfaigan.assignment2.database.entity.Access;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ProfileActivityRecyclerViewAdapter extends RecyclerView.Adapter<ProfileActivityRecyclerViewAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewRecyclerViewAccess;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRecyclerViewAccess = itemView.findViewById(R.id.textViewRecyclerViewAccess);
        }

        public void setData(Access access) {
            // Each entry in the list should have the timestamp and access type.
            CharSequence timestamp = DateFormat.format("yyyy-MM-dd @ hh:mm:ss", access.getTimestamp());
            textViewRecyclerViewAccess.setText(String.format(Locale.ENGLISH, "%s %s", timestamp, access.getAccessType()));
        }
    }

    private List<Access> accessList;

    public ProfileActivityRecyclerViewAdapter() {
        // We call setAccessList every time ProfileActivity is started, so default to an empty list rather than demanding one in the constructor.
        accessList = Collections.emptyList();
    }

    /**
     * Set the access history to be displayed in the RecyclerView.
     *
     * @param accessList The access history to be displayed. Should be ordered with the newest timestamps first.
     */
    public void setAccessList(List<Access> accessList) {
        this.accessList = accessList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_access, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(accessList.get(position));
    }

    @Override
    public int getItemCount() {
        return accessList.size();
    }
}
