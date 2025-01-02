package com.main.mp1.openpassgo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.core.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.main.mp1.R;

public class PassGoLoginActivity extends Activity implements PassGo{
    private int length;
    private String password;
    private PatternView patternView;
    private Button save;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_go_login);
        setupActionBar();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        this.length = Integer.parseInt(sharedPref.getString("passgo_length", "6"));
        this.password = sharedPref.getString("passgo_pw", "");
        this.patternView = (PatternView) findViewById(R.id.passgo_patternview);
        this.patternView.setActivity(this);
        this.patternView.setLength(this.length);
        this.save = (Button) findViewById(R.id.passgo_save);
        setReady(false);
    }

    private void setupActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pass_go_login, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void clearAll(View view) {
        this.patternView.clear();
    }

    public void submit(View view) {
        DialogFragment dialog;
        if (this.password.equals(this.patternView.getInput().toString())) {
            dialog = new ResultOKDialog();
        } else {
            dialog = new WrongResultDialog();
        }
        dialog.show(getFragmentManager(), "result");
    }

    public static class ResultOKDialog extends DialogFragment {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.msg_pw_correct).setNeutralButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    ResultOKDialog.this.getActivity().finish();
                }
            });
            return builder.create();
        }
    }

    public static class WrongResultDialog extends DialogFragment {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.msg_pw_wrong).setNeutralButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    WrongResultDialog.this.getActivity().finish();
                }
            });
            return builder.create();
        }
    }

    public void setReady(boolean ready) {
        this.save.setClickable(ready);
        this.save.setEnabled(ready);
    }

}
