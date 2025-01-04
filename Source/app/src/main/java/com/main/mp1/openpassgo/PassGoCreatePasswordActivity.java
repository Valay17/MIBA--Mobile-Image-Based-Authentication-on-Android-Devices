package com.main.mp1.openpassgo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.core.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.main.mp1.R;

public class PassGoCreatePasswordActivity extends Activity implements PassGo{
    /* access modifiers changed from: private */
    public PatternView patternView;
    private Button save;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_go_create_password);
        int length = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this).getString("passgo_length", "6"));
        this.patternView = (PatternView) findViewById(R.id.passgo_patternview);
        this.patternView.setActivity(this);
        this.patternView.setLength(length);
        this.save = (Button) findViewById(R.id.passgo_save);
        setReady(false);
        Bundle bundle = new Bundle();
        bundle.putInt("length", length);
        DialogFragment intro = new IntroDialogFragment();
        intro.setArguments(bundle);
        intro.show(getFragmentManager(), "intro");
        setupActionBar();
    }

    @TargetApi(11)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= 11) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pass_go_create_password, menu);
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
        new DeletePatternDialogFragment().show(getFragmentManager(), "delete");
    }

    public void clearAll() {
        this.patternView.clear();
    }

    public void submit(View view) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this).edit();
        edit.putString("passgo_pw", this.patternView.getInput().toString());
        edit.commit();
        new RememberPasswordDialogFragment().show(getFragmentManager(), "remember");
    }

    public static class IntroDialogFragment extends DialogFragment {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.title_dialog_pass_go_createpw).setMessage(getString(R.string.msg_dialog_pass_go_createpw, new Object[]{Integer.valueOf(getArguments().getInt("length"))})).setNeutralButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            return builder.create();
        }
    }

    public static class DeletePatternDialogFragment extends DialogFragment {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.msg_dialog_pass_go_deletepattern).setCancelable(false).setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    ((PassGoCreatePasswordActivity) DeletePatternDialogFragment.this.getActivity()).clearAll();
                }
            }).setNegativeButton(R.string.btn_no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            return builder.create();
        }
    }

    public static class RememberPasswordDialogFragment extends DialogFragment {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            View layout = ((LayoutInflater) getActivity().getSystemService("layout_inflater")).inflate(R.layout.passgo_rememberpw_dialog, (ViewGroup) getActivity().findViewById(R.id.passgo_rememberpw_layout));
            PatternView patternView = ((PassGoCreatePasswordActivity) getActivity()).patternView;
            DialogPatternView patternViewDialog = (DialogPatternView) layout.findViewById(R.id.passgo_rememberpw_patternView);
            patternViewDialog.setFixedPath(patternView.getFixedPath());
            patternViewDialog.setDotPath(patternView.getDotPath());
            patternViewDialog.setOriginalDimensions(patternView.getWidth(), patternView.getHeight());
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(layout).setTitle(R.string.title_dialog_pass_go_rememberpw).setPositiveButton(R.string.btn_done, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    RememberPasswordDialogFragment.this.getActivity().finish();
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
