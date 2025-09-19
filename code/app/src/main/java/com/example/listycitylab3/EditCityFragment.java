package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    public interface EditCityDialogListener {
        void onCityEdited(int position, City editedCity);
    }

    private EditCityDialogListener listener;

    private static final String ARG_CITY = "arg_city";
    private static final String ARG_POSITION = "arg_position";

    public static EditCityFragment newInstance(City city, int position) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);
        args.putInt(ARG_POSITION, position);
        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext())
                .inflate(R.layout.fragment_edit_city, null);

        EditText editCity = view.findViewById(R.id.edit_text_city_text);
        EditText editProv = view.findViewById(R.id.edit_text_province_text);

        Bundle args = requireArguments();
        City city = (City) args.getSerializable(ARG_CITY);
        int position = args.getInt(ARG_POSITION, -1);

        if (city != null) {
            editCity.setText(city.getName());
            editProv.setText(city.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        return builder.setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (d, w) -> {
                    String newName = editCity.getText().toString().trim();
                    String newProv = editProv.getText().toString().trim();

                    city.setName(newName);
                    city.setProvince(newProv);

                    if (listener != null && position >= 0) {
                        listener.onCityEdited(position, city);
                    }
                })
                .create();
    }
}
