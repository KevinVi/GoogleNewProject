
package com.exemple.projetgooglenews.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.widget.Toast;

/**
 * The {@link YesNoPreference} is a preference to show a dialog with Yes and No
 * buttons.
 * <p>
 * This preference will store a boolean into the SharedPreferences.
 */
public class YesNoPreference  extends DialogPreference
{
    public YesNoPreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onClick()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Reset application?");
        dialog.setMessage("This action will delete all your data. Are you sure you want to continue?");
        dialog.setCancelable(true);

        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //reset database
                SharedPreferences.Editor editor = getContext().getSharedPreferences("pref",getContext().MODE_PRIVATE).edit();

                editor.putString("search", "");
                editor.commit();
                Toast.makeText(getContext(), "Application reset!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dlg, int which)
            {
                dlg.cancel();
            }
        });

        AlertDialog al = dialog.create();
        al.show();
    }
}