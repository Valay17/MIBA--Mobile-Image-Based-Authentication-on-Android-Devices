package com.main.mp1.openmiba;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import androidx.core.app.NavUtils;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.MotionEventCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import com.main.mp1.R;

public class MIBACreatePasswordActivity extends Activity {
    public static String LOG_TAG = "MIBALoginActivity";
    private Button btnBack;
    private Button btnDone;
    int[][] colors_off = {new int[]{1006632960, 1006632960, 1006632960, 1006632960}, new int[]{1006632960, 1006632960, 1006632960, 1006632960}};
    public int current_round = 1;
    /* access modifiers changed from: private */
    public Handler handler = new MIBACreatePasswordHandler(new WeakReference(this), (MIBACreatePasswordHandler) null);
    /* access modifiers changed from: private */
    public int height;
    private ImageIndexTable imgidxtable = new ImageIndexTable();
    /* access modifiers changed from: private */
    public ArrayList<Boolean> input = new ArrayList<>();
    /* access modifiers changed from: private */
    public LinearLayout[][] linlGrid;
    /* access modifiers changed from: private */
    public boolean longpress = false;
    public int min_rounds = 1;
    public int rounds_to_do = 2;
    private ArrayList<Integer> seenPictureIds = new ArrayList<>();
    /* access modifiers changed from: private */
    public TableLayout tblLayout;
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
        setContentView(R.layout.activity_miba_create_password);
        this.rounds_to_do = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this).getString("miba_length", "1"));
        this.min_rounds = this.rounds_to_do;
        Bundle bundle = new Bundle();
        bundle.putInt("length", this.rounds_to_do);
        DialogFragment intro = new IntroDialogFragment();
        intro.setArguments(bundle);
        intro.show(getFragmentManager(), "intro");
        setupActionBar();
        setViews();
        this.seenPictureIds.add(Integer.valueOf(R.drawable.ccp000));
    }

    public static class IntroDialogFragment extends DialogFragment {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.title_dialog_miba_createpw).setMessage(getString(R.string.msg_dialog_miba_createpw, new Object[]{Integer.valueOf(getArguments().getInt("length"))})).setNeutralButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            return builder.create();
        }
    }

    public static class RememberPasswordDialogFragment extends DialogFragment {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            MIBACreatePasswordActivity activity = (MIBACreatePasswordActivity) getActivity();
            View layout = ((LayoutInflater) getActivity().getSystemService("layout_inflater")).inflate(R.layout.miba_rememberpw_dialog, (Gallery) getActivity().findViewById(R.id.miba_rememberpw_gallery));
            activity.unbindDrawables(activity.findViewById(R.id.tableLayout));
            System.gc();
            ((Gallery) layout.findViewById(R.id.miba_rememberpw_gallery)).setAdapter(new ImageAdapter(activity.getBaseContext(), activity.getSeenPictureIDs(), activity.getInput()));
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(layout).setTitle(getResources().getString(R.string.title_dialog_miba_rememberpw)).setPositiveButton(getResources().getString(R.string.btn_done), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(RememberPasswordDialogFragment.this.getActivity()).edit();
                    edit.putString("miba_pw", ((MIBACreatePasswordActivity) RememberPasswordDialogFragment.this.getActivity()).buildPasswordString());
                    edit.commit();
                    dialog.dismiss();
                    RememberPasswordDialogFragment.this.getActivity().finish();
                }
            });
            return builder.create();
        }

        private class ImageAdapter extends BaseAdapter {
            private int galleryItemBackground;
            private Context mContext;
            private ArrayList<Boolean> password;
            private ArrayList<Integer> pictureIDs;

            public ImageAdapter(Context c, ArrayList<Integer> seenPictureIds, ArrayList<Boolean> eingabe) {
                this.mContext = c;
                this.pictureIDs = seenPictureIds;
                this.password = eingabe;
//MIBAActivity changes added here
                TypedArray attr = this.mContext.obtainStyledAttributes(R.styleable.MIBAGallery);
                this.galleryItemBackground = attr.getResourceId(0, 0);
                attr.recycle();
            }

            public int getCount() {
                return this.pictureIDs.size() - 1;
            }

            public Object getItem(int position) {
                return Integer.valueOf(position);
            }

            public long getItemId(int position) {
                return (long) position;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView i = new ImageView(this.mContext);
                i.setScaleType(ImageView.ScaleType.FIT_XY);
                i.setBackgroundResource(this.galleryItemBackground);
                Bitmap mutableBitmap = BitmapFactory.decodeResource(RememberPasswordDialogFragment.this.getResources(), this.pictureIDs.get(position).intValue()).copy(Bitmap.Config.ARGB_8888, true);
                Canvas c = new Canvas(mutableBitmap);
                Paint p = new Paint();
                p.setColor(SupportMenu.CATEGORY_MASK);
                p.setStyle(Paint.Style.FILL);
                p.setAlpha(75);
                p.setAntiAlias(true);
                Paint p2 = new Paint();
                p2.setColor(-1);
                p2.setStyle(Paint.Style.STROKE);
                p2.setStrokeWidth(5.0f);
                p2.setAntiAlias(true);
                float imageWidth = (float) (mutableBitmap.getWidth() / 2);
                float imageHeight = (float) (mutableBitmap.getHeight() / 4);
                new ArrayList();
                List<Boolean> tmp = this.password.subList(position * 9, (position * 9) + 8);
                for (int j = 0; j < tmp.size(); j++) {
                    switch (j) {
                        case 0:
                            if (tmp.get(j).booleanValue()) {
                                c.drawRect(5.0f, 5.0f, imageWidth - 5.0f, imageHeight - 5.0f, p);
                            }
                            c.drawRect(5.0f, 5.0f, imageWidth - 5.0f, imageHeight - 5.0f, p2);
                            break;
                        case 1:
                            if (tmp.get(j).booleanValue()) {
                                c.drawRect(5.0f + imageWidth, 5.0f, (2.0f * imageWidth) - 5.0f, imageHeight - 5.0f, p);
                            }
                            c.drawRect(imageWidth + 5.0f, 5.0f, (2.0f * imageWidth) - 5.0f, imageHeight - 5.0f, p2);
                            break;
                        case 2:
                            if (tmp.get(j).booleanValue()) {
                                c.drawRect(5.0f, 5.0f + imageHeight, imageWidth - 5.0f, (2.0f * imageHeight) - 5.0f, p);
                            }
                            c.drawRect(5.0f, imageHeight + 5.0f, imageWidth - 5.0f, (2.0f * imageHeight) - 5.0f, p2);
                            break;
                        case 3:
                            if (tmp.get(j).booleanValue()) {
                                c.drawRect(5.0f + imageWidth, 5.0f + imageHeight, (2.0f * imageWidth) - 5.0f, (2.0f * imageHeight) - 5.0f, p);
                            }
                            c.drawRect(imageWidth + 5.0f, imageHeight + 5.0f, (2.0f * imageWidth) - 5.0f, (2.0f * imageHeight) - 5.0f, p2);
                            break;
                        case 4:
                            if (tmp.get(j).booleanValue()) {
                                c.drawRect(5.0f, (2.0f * imageHeight) + 5.0f, imageWidth - 5.0f, (3.0f * imageHeight) - 5.0f, p);
                            }
                            c.drawRect(5.0f, (2.0f * imageHeight) + 5.0f, imageWidth - 5.0f, (3.0f * imageHeight) - 5.0f, p2);
                            break;
                        case 5:
                            if (tmp.get(j).booleanValue()) {
                                c.drawRect(5.0f + imageWidth, (2.0f * imageHeight) + 5.0f, (2.0f * imageWidth) - 5.0f, (3.0f * imageHeight) - 5.0f, p);
                            }
                            c.drawRect(imageWidth + 5.0f, (2.0f * imageHeight) + 5.0f, (2.0f * imageWidth) - 5.0f, (3.0f * imageHeight) - 5.0f, p2);
                            break;
                        case 6:
                            if (tmp.get(j).booleanValue()) {
                                c.drawRect(5.0f, (3.0f * imageHeight) + 5.0f, imageWidth - 5.0f, (4.0f * imageHeight) - 5.0f, p);
                            }
                            c.drawRect(5.0f, (3.0f * imageHeight) + 5.0f, imageWidth - 5.0f, (4.0f * imageHeight) - 5.0f, p2);
                            break;
                        case MotionEventCompat.ACTION_HOVER_MOVE:
                            if (tmp.get(j).booleanValue()) {
                                c.drawRect(5.0f + imageWidth, (3.0f * imageHeight) + 5.0f, (2.0f * imageWidth) - 5.0f, (4.0f * imageHeight) - 5.0f, p);
                            }
                            c.drawRect(imageWidth + 5.0f, (3.0f * imageHeight) + 5.0f, (2.0f * imageWidth) - 5.0f, (4.0f * imageHeight) - 5.0f, p2);
                            break;
                    }
                }
                i.setImageBitmap(mutableBitmap);
                return i;
            }
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(1);
    }

    @TargetApi(11)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= 11) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.miba_create_password, menu);
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

    public int boolToInt(boolean b) {
        if (b) {
            return 1;
        }
        return 0;
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

    private void setViews() {
        this.tblLayout = (TableLayout) findViewById(R.id.tableLayout);
        this.btnBack = (Button) findViewById(R.id.miba_btnBack);
        this.btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MIBACreatePasswordActivity.this.back();
            }
        });
        this.btnDone = (Button) findViewById(R.id.miba_btnCreateMasterKey);
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
        this.tblLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (MIBACreatePasswordActivity.this.getWindowManager().getDefaultDisplay().getRotation() == 0) {
                    MIBACreatePasswordActivity.this.width = MIBACreatePasswordActivity.this.tblLayout.getWidth();
                    MIBACreatePasswordActivity.this.height = MIBACreatePasswordActivity.this.tblLayout.getHeight();
                } else {
                    MIBACreatePasswordActivity.this.height = MIBACreatePasswordActivity.this.tblLayout.getWidth();
                    MIBACreatePasswordActivity.this.width = MIBACreatePasswordActivity.this.tblLayout.getHeight();
                }
                MIBACreatePasswordActivity.this.tblLayout.setBackgroundDrawable(new BitmapDrawable(MIBACreatePasswordActivity.this.getResources(), Bitmap.createScaledBitmap(BitmapFactory.decodeResource(MIBACreatePasswordActivity.this.getResources(), R.drawable.ccp000), MIBACreatePasswordActivity.this.width, MIBACreatePasswordActivity.this.height, true)));
                MIBACreatePasswordActivity.this.touchlistener = new TouchListener(MIBACreatePasswordActivity.this.linlGrid, MIBACreatePasswordActivity.this.width, MIBACreatePasswordActivity.this.height, MIBACreatePasswordActivity.this.handler);
                MIBACreatePasswordActivity.this.tblLayout.setOnTouchListener(MIBACreatePasswordActivity.this.touchlistener);
                MIBACreatePasswordActivity.this.tblLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        this.btnDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new RememberPasswordDialogFragment().show(MIBACreatePasswordActivity.this.getFragmentManager(), "remember");
            }
        });
        this.btnDone.setVisibility(0);
    }

    /* access modifiers changed from: private */
    public void back() {
        setFinishable(false);
        boolean isshift = this.touchlistener.getLastState();
        boolean lastbutoneisshift = this.touchlistener.reset();
        if (!isshift) {
            if (this.current_round > 1) {
                this.current_round--;
            }
            if (lastbutoneisshift) {
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
        this.seenPictureIds.remove(this.seenPictureIds.size() - 1);
        this.seenPictureIds.remove(this.seenPictureIds.size() - 1);
        changeBackground(getImageIndex());
    }

    public void changeBackground(int imageindex) {
        int img;
        if (imageindex == -1) {
            img = R.drawable.ccp000;
        } else {
            img = R.drawable.ccp001 + imageindex;
        }
        this.seenPictureIds.add(Integer.valueOf(img));
        this.tblLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), img), this.width, this.height, true)));
    }

    public ArrayList<Gesture> getPasswordPictures() {
        ArrayList<Gesture> res = new ArrayList<>();
        int length = this.input.size() / 9;
        for (int i = 0; i < length; i++) {
            boolean[] list = new boolean[9];
            for (int j = 0; j < list.length; j++) {
                list[j] = this.input.get((i * 9) + j).booleanValue();
            }
            res.add(new Gesture(this.seenPictureIds.get(i).intValue(), list));
        }
        return res;
    }

    /* access modifiers changed from: private */
    public void checkIfFinished() {
        if (this.current_round == this.rounds_to_do + 1) {
            setFinishable(true);
        }
    }

    private void setFinishable(boolean finish) {
        this.btnDone.setEnabled(finish);
        this.btnDone.setClickable(finish);
        if (finish) {
            this.tblLayout.setOnTouchListener((View.OnTouchListener) null);
        } else {
            this.tblLayout.setOnTouchListener(this.touchlistener);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        unbindDrawables(findViewById(R.id.tableLayout));
        System.gc();
    }

    /* access modifiers changed from: protected */
    public void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback((Drawable.Callback) null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }

    /* access modifiers changed from: protected */
    public ArrayList<Integer> getSeenPictureIDs() {
        return this.seenPictureIds;
    }

    /* access modifiers changed from: protected */
    public ArrayList<Boolean> getInput() {
        return this.input;
    }

    private static class MIBACreatePasswordHandler extends Handler {
        MIBACreatePasswordActivity activity;

        private MIBACreatePasswordHandler(WeakReference<MIBACreatePasswordActivity> instance) {
            this.activity = (MIBACreatePasswordActivity) instance.get();
        }

        /* synthetic */ MIBACreatePasswordHandler(WeakReference weakReference, MIBACreatePasswordHandler mIBACreatePasswordHandler) {
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
                                Log.i(MIBACreatePasswordActivity.LOG_TAG, "Rec x" + x1 + " y" + y1 + " is activated");
                            }
                            this.activity.input.add(Boolean.valueOf(active[x1][y1]));
                        }
                    }
                    this.activity.input.add(false);
                    this.activity.current_round++;
                    this.activity.tvRound.setText(String.valueOf(this.activity.getString(R.string.label_round)) + " " + this.activity.current_round);
                    this.activity.changeBackground(this.activity.getImageIndex());
                    break;
                case 2:
                    ((Vibrator) this.activity.getSystemService("vibrator")).vibrate(300);
                    this.activity.tvRound.setText(String.valueOf(this.activity.getString(R.string.label_round)) + " " + this.activity.current_round + " -Shift-");
                    this.activity.longpress = true;
                    boolean[][] shifted = (boolean[][]) msg.obj;
                    for (int y12 = 0; y12 < 4; y12++) {
                        for (int x12 = 0; x12 < 2; x12++) {
                            if (shifted[x12][y12]) {
                                Log.i(MIBACreatePasswordActivity.LOG_TAG, "Rec x" + x12 + " y" + y12 + " was shifted");
                            }
                            this.activity.input.add(Boolean.valueOf(shifted[x12][y12]));
                        }
                    }
                    this.activity.input.add(true);
                    this.activity.changeBackground(this.activity.getImageIndex());
                    break;
            }
            this.activity.checkIfFinished();
        }
    }

}