package ca.ualberta.cs.feelsbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ChangeTimeDialog extends DialogFragment {

    public interface ChangeTimeDialogListener {
        public void deleteDialogConfirmed(DialogFragment dialog);
    }

    ChangeTimeDialogListener dialogListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dialogListener = (ChangeTimeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("DeleteHistoryDialogListener must be implemented.");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(R.string.delete_history);
        dialogBuilder.setMessage(R.string.delete_message);

        dialogBuilder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            //@Override
            public void onClick(DialogInterface dialog, int id) {
                dialogListener.deleteDialogConfirmed(ChangeTimeDialog.this);
            }
        });
        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            //@Override
            public void onClick(DialogInterface dialog, int id) {
                // We don't need to do anything in this case (other than closing the dialog)
            }
        });

        AlertDialog displayedDialog = dialogBuilder.create();
        return displayedDialog;
    }
}