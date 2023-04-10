package org.tensorflow.lite.examples.objectdetection;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatDialogFragment;


public class CameraNameDialog extends AppCompatDialogFragment {
    private EditText editCameraName;
    private CameraNameDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.camera_name_layout, null);

        builder.setView(view)
                .setTitle("Edit Camera Name")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String cameraname = editCameraName.getText().toString();
                        listener.applyTexts(cameraname);
                    }
                });

        editCameraName = view.findViewById(R.id.edit_cameraname);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (CameraNameDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement CameraNameDialogListener");
        }
    }

    public interface CameraNameDialogListener {
        void applyTexts(String username);
    }
}