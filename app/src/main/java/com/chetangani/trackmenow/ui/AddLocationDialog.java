package com.chetangani.trackmenow.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chetangani.trackmenow.R;

/**
 * Created by Chetan G on 6/26/2016.
 */
public class AddLocationDialog extends AppCompatDialogFragment {
    LayoutInflater inflater;
    View view;
    LocationAddedHandler handler;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        inflater = getActivity().getLayoutInflater();

        view = inflater.inflate(R.layout.dialog_add_location, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText edittextlocation = (EditText) view.findViewById(R.id.addlocation_etTxt);
                handler.locationadded(edittextlocation.getText().toString());
                Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        handler = (LocationAddedHandler) context;
    }

    public interface LocationAddedHandler {
        public void locationadded(String location);
    }
}
