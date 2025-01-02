package com.main.mp1.openmiba;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import androidx.core.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import com.main.mp1.R;

public class MIBALoginActivity extends Activity{
    public static final int INPUTLENGTH = 9;
    /* access modifiers changed from: private */
    public static String LOG_TAG = "LoginActivity";
    private Button btnBack;
    int[][] colors_off = {new int[]{1006632960, 1006632960, 1006632960, 1006632960}, new int[]{1006632960, 1006632960, 1006632960, 1006632960}};
    public int current_round = 1;
    boolean firsttime = false;
    /* access modifiers changed from: private */
    public Handler handler = new MIBALoginHandler(new WeakReference(this), (MIBALoginHandler) null);
    /* access modifiers changed from: private */
    public int height;
    private ImageIndexTable imgidxtable = new ImageIndexTable();
    /* access modifiers changed from: private */
    public ArrayList<Boolean> input = new ArrayList<>();
    /* access modifiers changed from: private */
    public LinearLayout[][] linlGrid;
    /* access modifiers changed from: private */
    public boolean longpress = false;
    public int min_rounds = 2;
    private String password;
    public int rounds_to_do = 2;
    /* access modifiers changed from: private */
    public TableLayout tableLayout;
    /* access modifiers changed from: private */
    public TouchListener touchlistener;
    /* access modifiers changed from: private */
    public TextView tvRound;
    /* access modifiers changed from: private */
    public int width;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(1);
        setContentView(R.layout.activity_miba_login);
        setupActionBar();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        this.rounds_to_do = Integer.parseInt(sharedPref.getString("miba_length", "1"));
        this.min_rounds = this.rounds_to_do;
        this.password = sharedPref.getString("miba_pw", "");
        setViews();
    }

    private void setupActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.miba_login, menu);
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

    private void setViews() {
        this.tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        this.btnBack = (Button) findViewById(R.id.miba_btnBack);
        this.btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MIBALoginActivity.this.back();
            }
        });
        this.tvRound = (TextView) findViewById(R.id.tvRound);
        this.tvRound.setText(String.valueOf(getString(R.string.label_round)) + " 1");
        this.linlGrid = (LinearLayout[][]) Array.newInstance(LinearLayout.class, new int[]{2, 4});
        this.linlGrid[0][0] = (LinearLayout) findViewById(R.id.square1);
        this.linlGrid[1][0] = (LinearLayout) findViewById(R.id.square2);
        this.linlGrid[0][1] = (LinearLayout) findViewById(R.id.square3);
        this.linlGrid[1][1] = (LinearLayout) findViewById(R.id.square4);
        this.linlGrid[0][2] = (LinearLayout) findViewById(R.id.square5);
        this.linlGrid[1][2] = (LinearLayout) findViewById(R.id.square6);
        this.linlGrid[0][3] = (LinearLayout) findViewById(R.id.square7);
        this.linlGrid[1][3] = (LinearLayout) findViewById(R.id.square8);
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 2; x++) {
                this.linlGrid[x][y].setBackgroundColor(this.colors_off[x][y]);
            }
        }
        this.tableLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (MIBALoginActivity.this.getWindowManager().getDefaultDisplay().getRotation() == 0) {
                    MIBALoginActivity.this.width = MIBALoginActivity.this.tableLayout.getWidth();
                    MIBALoginActivity.this.height = MIBALoginActivity.this.tableLayout.getHeight();
                } else {
                    MIBALoginActivity.this.height = MIBALoginActivity.this.tableLayout.getWidth();
                    MIBALoginActivity.this.width = MIBALoginActivity.this.tableLayout.getHeight();
                }
                MIBALoginActivity.this.tableLayout.setBackgroundDrawable(new BitmapDrawable(MIBALoginActivity.this.getResources(), Bitmap.createScaledBitmap(BitmapFactory.decodeResource(MIBALoginActivity.this.getResources(), R.drawable.ccp000), MIBALoginActivity.this.width, MIBALoginActivity.this.height, true)));
                MIBALoginActivity.this.touchlistener = new TouchListener(MIBALoginActivity.this.linlGrid, MIBALoginActivity.this.width, MIBALoginActivity.this.height, MIBALoginActivity.this.handler);
                MIBALoginActivity.this.tableLayout.setOnTouchListener(MIBALoginActivity.this.touchlistener);
                MIBALoginActivity.this.tableLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    /* access modifiers changed from: private */
    public void back() {
        boolean isshift = this.touchlistener.getLastState();
        boolean vorletztzerisshift = this.touchlistener.reset();
        if (!isshift) {
            if (this.current_round > 1) {
                this.current_round--;
            }
            if (vorletztzerisshift) {
                this.tvRound.setText(String.valueOf(getString(R.string.label_round)) + " " + this.current_round + " -Shift-");
            } else {
                this.tvRound.setText(String.valueOf(getString(R.string.label_round)) + " " + this.current_round);
            }
        } else {
            this.tvRound.setText(String.valueOf(getString(R.string.label_round)) + " " + this.current_round);
        }
        if (this.input.size() >= 9) {
            for (int i = 0; i < 9; i++) {
                this.input.remove(this.input.size() - 1);
            }
        }
        this.imgidxtable.removeLastIndex();
        changeBackground(getImageIndex());
    }

    public int getImageIndex() {
        if (this.input.size() < 9) {
            return -1;
        }
        int res = 0;
        for (int i = 0; i < 9; i++) {
            res = (res << 1) + boolToInt(this.input.get((this.input.size() - 9) + i).booleanValue());
        }
        int res2 = this.imgidxtable.getIndex(res);
        Log.i(LOG_TAG, "this entry lead to imageindex " + res2);
        return res2;
    }

    public void changeBackground(int imageindex) {
        int img;
        if (imageindex == -1) {
            img = R.drawable.ccp000;
        } else {
            img = R.drawable.ccp001 + imageindex;
        }
        this.tableLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), img), this.width, this.height, true)));
    }

    public int boolToInt(boolean b) {
        if (b) {
            return 1;
        }
        return 0;
    }

    public void checkIfFinished() {
        DialogFragment dialog;
        if (this.current_round == this.min_rounds + 1) {
            this.tableLayout.setOnClickListener((View.OnClickListener) null);
            if (buildPasswordString().equals(this.password)) {
                dialog = new ResultOKDialog();
            } else {
                dialog = new WrongResultDialog();
            }
            dialog.show(getFragmentManager(), "result");
        }
    }

    public String buildPasswordString() {
        String res = "";
        for (int i = 0; i < this.input.size(); i++) {
            if (this.input.get(i).booleanValue()) {
                res = String.valueOf(res) + "1";
            } else {
                res = String.valueOf(res) + "0";
            }
        }
        return res;
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

    private static class MIBALoginHandler extends Handler {
        MIBALoginActivity activity;

        private MIBALoginHandler(WeakReference<MIBALoginActivity> instance) {
            this.activity = (MIBALoginActivity) instance.get();
        }

        /* synthetic */ MIBALoginHandler(WeakReference weakReference, MIBALoginHandler mIBALoginHandler) {
            this(weakReference);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    this.activity.longpress = false;
                    boolean[][] active = (boolean[][]) msg.obj;
                    for (int y1 = 0; y1 < 4; y1++) {
                        for (int x1 = 0; x1 < 2; x1++) {
                            if (active[x1][y1]) {
                                Log.i(MIBALoginActivity.LOG_TAG, "Rec x" + x1 + " y" + y1 + " is activated");
                            }
                            this.activity.input.add(Boolean.valueOf(active[x1][y1]));
                        }
                    }
                    this.activity.input.add(false);
                    this.activity.current_round++;
                    this.activity.tvRound.setText(String.valueOf(this.activity.getString(R.string.label_round)) + " " + this.activity.current_round);
                    this.activity.changeBackground(this.activity.getImageIndex());
                    this.activity.checkIfFinished();
                    return;
                case 2:
                    ((Vibrator) this.activity.getSystemService("vibrator")).vibrate(300);
                    this.activity.tvRound.setText(String.valueOf(this.activity.getString(R.string.label_round)) + " " + this.activity.current_round + " -Shift-");
                    this.activity.longpress = true;
                    boolean[][] shifted = (boolean[][]) msg.obj;
                    for (int y12 = 0; y12 < 4; y12++) {
                        for (int x12 = 0; x12 < 2; x12++) {
                            if (shifted[x12][y12]) {
                                Log.i(MIBALoginActivity.LOG_TAG, "Rec x" + x12 + " y" + y12 + " was shifted");
                            }
                            this.activity.input.add(Boolean.valueOf(shifted[x12][y12]));
                        }
                    }
                    this.activity.input.add(true);
                    this.activity.changeBackground(this.activity.getImageIndex());
                    this.activity.checkIfFinished();
                    return;
                default:
                    return;
            }
        }
    }

}
