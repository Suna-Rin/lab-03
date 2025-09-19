package com.example.listycitylab3;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    public interface AddCityDialogListener {
        void addCity(City city);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_add_city, null);
        EditText cityEt = view.findViewById(R.id.add_text_city_text);
        EditText provEt = view.findViewById(R.id.add_text_province_text);

        return new AlertDialog.Builder(requireContext())
                .setView(view)
                .setTitle(R.string.title_add_city)
                .setNegativeButton(R.string.action_cancel, null)
                .setPositiveButton(R.string.action_ok, (d, w) -> {
                    String name = cityEt.getText().toString().trim();
                    String prov = provEt.getText().toString().trim();
                    if (!name.isEmpty() && !prov.isEmpty() && getActivity() instanceof AddCityDialogListener) {
                        ((AddCityDialogListener) getActivity()).addCity(new City(name, prov));
                    }
                })
                .create();
    }
}
