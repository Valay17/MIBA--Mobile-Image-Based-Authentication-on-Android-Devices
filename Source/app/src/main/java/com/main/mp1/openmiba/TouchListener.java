package com.main.mp1.openmiba;

import android.os.Handler;
import android.os.Message;
import androidx.core.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TouchListener implements View.OnTouchListener {
    public static String LOG_TAG = "TouchListener";
    public static final int MSG_ROUND_FINISHED = 1;
    public static final int MSG_SHIFT_ROUND_FINISHED = 2;
    public static final int MSG_START_ROUND = 3;
    /* access modifiers changed from: private */
    public static Handler handler;
    int[][] activatedbypointerid = {new int[]{-1, -1, -1, -1}, new int[]{-1, -1, -1, -1}};
    boolean[][] active_recs = {new boolean[4], new boolean[4]};
    /* access modifiers changed from: private */
    public boolean cancel_shift = false;
    private final int height;
    private List<Boolean> isshifted = new ArrayList();
    private final LinearLayout[][] linlGrid;
    /* access modifiers changed from: private */
    public boolean longpress = false;
    private boolean secclick = false;
    boolean[][] secdown_recs = {new boolean[4], new boolean[4]};
    private Thread shiftchecker;
    boolean[][] up_recs = {new boolean[4], new boolean[4]};
    private final int width;

    /* access modifiers changed from: package-private */
    public boolean isArrayEqual(boolean[][] a, boolean[][] b) {
        boolean equal = true;
        for (int y1 = 0; y1 < 4; y1++) {
            for (int x1 = 0; x1 < 2; x1++) {
                if (a[x1][y1] != b[x1][y1]) {
                    equal = false;
                }
            }
        }
        return equal;
    }

    /* access modifiers changed from: package-private */
    public void initArray(boolean[][] a, boolean init) {
        for (int y1 = 0; y1 < 4; y1++) {
            for (int x1 = 0; x1 < 2; x1++) {
                a[x1][y1] = init;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void initArray(int[][] a, int init) {
        for (int y1 = 0; y1 < 4; y1++) {
            for (int x1 = 0; x1 < 2; x1++) {
                a[x1][y1] = init;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean[][] copyArray(boolean[][] a) {
        boolean[][] res = (boolean[][]) Array.newInstance(Boolean.TYPE, new int[]{2, 4});
        for (int y1 = 0; y1 < 4; y1++) {
            for (int x1 = 0; x1 < 2; x1++) {
                res[x1][y1] = a[x1][y1];
            }
        }
        return res;
    }

    class ShiftCheckerThread extends Thread {
        private boolean[][] laststate = ((boolean[][]) Array.newInstance(Boolean.TYPE, new int[]{2, 4}));

        ShiftCheckerThread() {
        }

        public void run() {
            while (!TouchListener.this.cancel_shift) {
                Log.i(TouchListener.LOG_TAG, "i m thread checker " + getName());
                if (TouchListener.this.isArrayEqual(this.laststate, TouchListener.this.active_recs) && !TouchListener.this.cancel_shift) {
                    Log.i(TouchListener.LOG_TAG, "thread: arrays are equal " + getName());
                    Message msg = Message.obtain();
                    msg.what = 2;
                    msg.obj = TouchListener.this.copyArray(TouchListener.this.active_recs);
                    TouchListener.handler.sendMessage(msg);
                    TouchListener.this.longpress = true;
                    return;
                } else if (!TouchListener.this.cancel_shift) {
                    this.laststate = TouchListener.this.copyArray(TouchListener.this.active_recs);
                    try {
                        Thread.sleep(750);
                    } catch (InterruptedException e) {
                        Log.i(TouchListener.LOG_TAG, "i got interupted from sleep " + getName());
                    }
                } else {
                    return;
                }
            }
            Log.i(TouchListener.LOG_TAG, "thread stopped " + getName());
        }
    }

    public TouchListener(LinearLayout[][] linlGrid2, int width2, int heigth, Handler hndl) {
        this.linlGrid = linlGrid2;
        this.width = width2;
        this.height = heigth;
        handler = hndl;
    }

    private int[] getRectangle(float x, float y) {
        return new int[]{((int) x) / (this.width / 2), ((int) y) / (this.height / 4)};
    }

    /* access modifiers changed from: package-private */
    public void printSamples(MotionEvent ev) {
        int historySize = ev.getHistorySize();
        int pointerCount = ev.getPointerCount();
        for (int h = 0; h < historySize; h++) {
            System.out.printf("At time %d:", new Object[]{Long.valueOf(ev.getHistoricalEventTime(h))});
            for (int p = 0; p < pointerCount; p++) {
                System.out.printf("  pointer %d: (%f,%f)", new Object[]{Integer.valueOf(ev.getPointerId(p)), Float.valueOf(ev.getHistoricalX(p, h)), Float.valueOf(ev.getHistoricalY(p, h))});
            }
        }
        System.out.printf("At time %d:", new Object[]{Long.valueOf(ev.getEventTime())});
        for (int p2 = 0; p2 < pointerCount; p2++) {
            System.out.printf("  pointer %d: (%f,%f)", new Object[]{Integer.valueOf(ev.getPointerId(p2)), Float.valueOf(ev.getX(p2)), Float.valueOf(ev.getY(p2))});
        }
    }

    public boolean reset() {
        if (this.isshifted.size() <= 1) {
            this.secclick = false;
            this.isshifted.clear();
            return false;
        }
        boolean vorletzter = this.isshifted.get(this.isshifted.size() - 2).booleanValue();
        boolean letzter = this.isshifted.get(this.isshifted.size() - 1).booleanValue();
        if (vorletzter) {
            this.secclick = true;
        }
        if (letzter) {
            this.secclick = false;
        }
        this.isshifted.remove(this.isshifted.size() - 1);
        return vorletzter;
    }

    public boolean getLastState() {
        if (this.isshifted.size() == 0) {
            return false;
        }
        boolean shift = this.isshifted.get(this.isshifted.size() - 1).booleanValue();
        for (int i = 0; i < this.isshifted.size(); i++) {
            Log.d(LOG_TAG, new StringBuilder().append(this.isshifted.get(i)).toString());
        }
        if (shift) {
            return true;
        }
        return false;
    }

    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction() & MotionEventCompat.ACTION_MASK;
        int ptrIdx = event.getActionIndex();
        int ptrId = event.getPointerId(ptrIdx);
        if (action == 0) {
            setRectangleStateMoving(event.getX(0), event.getY(0), true, 0, false);
            this.longpress = false;
            this.cancel_shift = false;
            Log.i(LOG_TAG, "frist pointer down: shift enabled");
            Message msg = Message.obtain();
            msg.what = 3;
            handler.sendMessage(msg);
            if (!this.secclick && (this.shiftchecker == null || !this.shiftchecker.isAlive())) {
                this.shiftchecker = new ShiftCheckerThread();
                this.shiftchecker.start();
                int i = ptrId;
                return true;
            }
        } else if (action == 1) {
            setRectangleStateMoving(event.getX(0), event.getY(0), false, ptrId, false);
            if (!this.longpress || this.secclick) {
                Log.i(LOG_TAG, "last pointer up: shift canceled");
                this.cancel_shift = true;
                this.shiftchecker.interrupt();
                Message msg2 = Message.obtain();
                msg2.what = 1;
                msg2.obj = copyArray(this.active_recs);
                handler.sendMessage(msg2);
                this.secclick = false;
                this.isshifted.add(false);
            } else if (this.longpress) {
                this.secclick = true;
                this.isshifted.add(true);
            }
            initArray(this.active_recs, false);
            int i2 = ptrId;
            return true;
        } else if (action == 2) {
            int pointerCount = event.getPointerCount();
            int i3 = ptrId;
            for (int ptrIdx2 = 0; ptrIdx2 < pointerCount; ptrIdx2++) {
                setRectangleStateMoving(event.getX(ptrIdx2), event.getY(ptrIdx2), true, event.getPointerId(ptrIdx2), true);
            }
            return true;
        } else if (action == 5) {
            setRectangleStateMoving(event.getX(ptrIdx), event.getY(ptrIdx), true, ptrId, false);
            int i4 = ptrId;
            return true;
        } else if (action == 6) {
            setRectangleStateMoving(event.getX(ptrIdx), event.getY(ptrIdx), false, ptrId, false);
        }
        int i5 = ptrId;
        return true;
    }

    private void setRectangleStateMoving(float x, float y, boolean active, int ptrID, boolean moving) {
        Log.d("coords", x + " " + y);
        int[] coords = getRectangle(x, y);
        Log.d("coords", coords[0] + " " + coords[1]);
        if (this.activatedbypointerid[coords[0]][coords[1]] != ptrID || !moving) {
            if ((this.activatedbypointerid[coords[0]][coords[1]] != ptrID && moving) || !active) {
                for (int y1 = 0; y1 < 4; y1++) {
                    for (int x1 = 0; x1 < 2; x1++) {
                        if (this.activatedbypointerid[x1][y1] == ptrID) {
                            this.linlGrid[x1][y1].setVisibility(0);
                            if (moving) {
                                this.activatedbypointerid[x1][y1] = -1;
                                this.active_recs[x1][y1] = false;
                            }
                        }
                    }
                }
            }
            if (active) {
                this.active_recs[coords[0]][coords[1]] = true;
                this.activatedbypointerid[coords[0]][coords[1]] = ptrID;
                this.linlGrid[coords[0]][coords[1]].setVisibility(4);
            }
        }
    }

    public String describeEvent(MotionEvent event) {
        StringBuilder result = new StringBuilder(500);
        result.append("Action: ").append(event.getAction()).append("\n");
        int numPointers = event.getPointerCount();
        result.append("Number of pointer: ").append(numPointers).append("\n");
        for (int ptrIdx = 0; ptrIdx < numPointers; ptrIdx++) {
            int ptrId = event.getPointerId(ptrIdx);
            result.append("Pointer Index: ").append(ptrIdx);
            result.append(", Pointer Id: ").append(ptrId).append("\n");
            result.append(" Location: ").append("x").append(event.getX(ptrIdx));
            result.append("y").append(event.getY(ptrIdx)).append("\n");
        }
        result.append("Downtime: ").append(event.getDownTime()).append("ms\n");
        result.append("Event time: ").append(event.getEventTime()).append("ms");
        result.append(" Elapsed: ").append(event.getEventTime() - event.getDownTime());
        result.append("ms\n");
        Log.i(LOG_TAG, result.toString());
        return result.toString();
    }

}
