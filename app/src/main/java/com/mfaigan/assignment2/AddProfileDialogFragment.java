package com.mfaigan.assignment2;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddProfileDialogFragment extends DialogFragment {

    EditText editTextAddProfileSurname;
    EditText editTextAddProfileName;
    EditText editTextAddProfileId;
    EditText editTextAddProfileGpa;

    Button buttonAddProfileCancel;
    Button buttonAddProfileSave;

    DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Create the fragment's view from our custom layout file.
        View view = inflater.inflate(R.layout.dialogfragment_add_profile, container);

        editTextAddProfileSurname = view.findViewById(R.id.editTextAddProfileSurname);
        editTextAddProfileName = view.findViewById(R.id.editTextAddProfileName);
        editTextAddProfileId = view.findViewById(R.id.editTextAddProfileId);
        editTextAddProfileGpa = view.findViewById(R.id.editTextAddProfileGpa);

        buttonAddProfileCancel = view.findViewById(R.id.buttonAddProfileCancel);
        buttonAddProfileSave = view.findViewById(R.id.buttonAddProfileSave);

        databaseHelper = new DatabaseHelper(getContext());

        buttonAddProfileCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        buttonAddProfileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Only add a new profile if all input checks pass.
                if (validateInputs()) {
                    databaseHelper.addNewProfile(
                            Integer.parseInt(editTextAddProfileId.getText().toString()),
                            editTextAddProfileSurname.getText().toString(),
                            editTextAddProfileName.getText().toString(),
                            Double.parseDouble(editTextAddProfileGpa.getText().toString()));

                    ((MainActivity) requireActivity()).refreshProfileDisplay();

                    dismiss();
                }
            }
        });

        return view;
    }

    /**
     * Validates the inputs to all four fields and displays a Toast error message in the case of invalid input.
     *
     * @return True if all checks passed, otherwise false.
     */
    private boolean validateInputs() {
        Editable surname = editTextAddProfileSurname.getText();
        Editable name = editTextAddProfileName.getText();
        Editable id = editTextAddProfileId.getText();
        Editable gpa = editTextAddProfileGpa.getText();

        // All fields must be non-empty.
        if (surname.length() == 0 || name.length() == 0 || id.length() == 0 || gpa.length() == 0) {
            Toast.makeText(getContext(), R.string.error_field_empty, Toast.LENGTH_LONG).show();
            return false;
        }

        // The ID field is guaranteed to be an integer thanks to the digits restriction.
        long idL = Long.parseLong(id.toString());
        if (idL < 10000000 || idL > 99999999) {
            // The ID must be between 10000000 and 99999999.
            Toast.makeText(getContext(), R.string.error_id_out_of_range, Toast.LENGTH_LONG).show();
            return false;
        } else if (databaseHelper.getProfile(idL) != null) {
            // The ID must also be not in use.
            Toast.makeText(getContext(), R.string.error_id_already_in_use, Toast.LENGTH_LONG).show();
            return false;
        }

        // Testing showed that the input restriction always provides a valid input to parseDouble.
        // Thus, we don't need to worry about NumberFormatException.
        double gpaD = Double.parseDouble(gpa.toString());
        // The GPA must be between 0 and 4.3 inclusive.
        if (gpaD < 0 || gpaD > 4.3) {
            Toast.makeText(getContext(), R.string.error_gpa_out_of_range, Toast.LENGTH_LONG).show();
            return false;
        }

        // If all checks pass, return true.
        return true;
    }
}
