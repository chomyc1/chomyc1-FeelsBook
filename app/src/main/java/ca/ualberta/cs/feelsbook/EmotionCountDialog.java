package ca.ualberta.cs.feelsbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class EmotionCountDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        Bundle argumentsBundle = getArguments();
        String countMessage = argumentsBundle.getString("countMessageKey");

        dialogBuilder.setTitle(R.string.emotion_count);
        dialogBuilder.setMessage(countMessage);
        dialogBuilder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        AlertDialog displayedDialog = dialogBuilder.create();
        return displayedDialog;
    }
}
