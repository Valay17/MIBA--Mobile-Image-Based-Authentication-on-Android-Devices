package com.main.mp1.openpassgo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;

public class PatternView extends View implements View.OnTouchListener{
    private final int COUNT_CELLS = 4;
    private PassGo activity;
    private int currentlength = 0;
    private float d;
    private boolean dot = false;
    private Path dotPath = new Path();
    private Path fixedPath = new Path();
    private ArrayList<Coordinates> input = new ArrayList<>();
    private float lastXc;
    private float lastYc;
    private int length;
    private int offset;
    Paint paint = new Paint();
    private Rect rect = new Rect();
    private Path tmpDotPath = new Path();
    private Path tmpPath = new Path();
    private boolean validInput = false;

    public PatternView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnTouchListener(this);
    }

    public PatternView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    public PatternView(Context context) {
        super(context);
        setOnTouchListener(this);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.getClipBounds(this.rect);
        this.offset = this.rect.height() - this.rect.width();
        this.offset /= 2;
        this.paint.setColor(-1);
        this.paint.setStyle(Paint.Style.FILL);
        canvas.drawRect((float) this.rect.left, (float) (this.rect.top + this.offset), (float) this.rect.right, (float) (this.rect.bottom - this.offset), this.paint);
        this.d = (((float) this.rect.width()) * 1.0f) / 4.0f;
        this.paint.setColor(-3355444);
        this.paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < 4; i++) {
            if (i == 0 || i == 3) {
                canvas.drawRect((this.d * 1.0f) + ((float) this.rect.left), (((float) i) * this.d) + ((float) (this.rect.top + this.offset)), (this.d * 3.0f) + ((float) this.rect.left), (((float) (i + 1)) * this.d) + ((float) (this.rect.top + this.offset)), this.paint);
            } else {
                canvas.drawRect((float) this.rect.left, (((float) i) * this.d) + ((float) (this.rect.top + this.offset)), (this.d * 1.0f) + ((float) this.rect.left), (((float) (i + 1)) * this.d) + ((float) (this.rect.top + this.offset)), this.paint);
                canvas.drawRect((this.d * 3.0f) + ((float) this.rect.left), (((float) i) * this.d) + ((float) (this.rect.top + this.offset)), (this.d * 4.0f) + ((float) this.rect.left), (((float) (i + 1)) * this.d) + ((float) (this.rect.top + this.offset)), this.paint);
            }
        }
        this.paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.paint.setStyle(Paint.Style.STROKE);
        for (int i2 = 0; i2 < 5; i2++) {
            canvas.drawLine((float) this.rect.left, (((float) i2) * this.d) + ((float) (this.rect.top + this.offset)), (float) this.rect.right, (((float) i2) * this.d) + ((float) (this.rect.top + this.offset)), this.paint);
            canvas.drawLine((((float) i2) * this.d) + ((float) this.rect.left), (float) (this.rect.top + this.offset), (((float) i2) * this.d) + ((float) this.rect.left), (float) (this.rect.bottom - this.offset), this.paint);
        }
        this.paint.setStyle(Paint.Style.FILL);
        canvas.drawRect((((float) this.rect.left) + (this.d * 2.0f)) - ((float) 4), (((float) (this.rect.top + this.offset)) + (this.d * 2.0f)) - ((float) 4), ((float) 4) + ((float) this.rect.left) + (this.d * 2.0f), ((float) 4) + ((float) (this.rect.top + this.offset)) + (this.d * 2.0f), this.paint);
        canvas.drawRect((((float) this.rect.left) + (this.d * 6.0f)) - ((float) 4), (((float) (this.rect.top + this.offset)) + (this.d * 2.0f)) - ((float) 4), ((float) 4) + ((float) this.rect.left) + (this.d * 6.0f), ((float) 4) + ((float) (this.rect.top + this.offset)) + (this.d * 2.0f), this.paint);
        canvas.drawRect((((float) this.rect.left) + (this.d * 2.0f)) - ((float) 4), (((float) (this.rect.top + this.offset)) + (this.d * 6.0f)) - ((float) 4), ((float) 4) + ((float) this.rect.left) + (this.d * 2.0f), ((float) 4) + ((float) (this.rect.top + this.offset)) + (this.d * 6.0f), this.paint);
        canvas.drawRect((((float) this.rect.left) + (this.d * 6.0f)) - ((float) 4), (((float) (this.rect.top + this.offset)) + (this.d * 6.0f)) - ((float) 4), ((float) 4) + ((float) this.rect.left) + (this.d * 6.0f), ((float) 4) + ((float) (this.rect.top + this.offset)) + (this.d * 6.0f), this.paint);
        canvas.drawRect((((float) this.rect.left) + (this.d * 4.0f)) - ((float) 4), (((float) (this.rect.top + this.offset)) + (this.d * 4.0f)) - ((float) 4), ((float) 4) + ((float) this.rect.left) + (this.d * 4.0f), ((float) 4) + ((float) (this.rect.top + this.offset)) + (this.d * 4.0f), this.paint);
        this.paint.setColor(SupportMenu.CATEGORY_MASK);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeCap(Paint.Cap.ROUND);
        this.paint.setStrokeWidth(3.0f);
        canvas.drawPath(this.fixedPath, this.paint);
        canvas.drawPath(this.tmpPath, this.paint);
        this.paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(this.dotPath, this.paint);
        canvas.drawPath(this.tmpDotPath, this.paint);
    }

    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int gridX = Math.round((1.0f * x) / this.d);
        int gridY = Math.round(((1.0f * y) - ((float) this.offset)) / this.d);
        float gridXc = ((float) gridX) * this.d;
        float gridYc = ((float) this.offset) + (((float) gridY) * this.d);
        if (y < ((float) this.offset) - (3.0f / this.d) || y > ((float) this.offset) + (8.0f * this.d) + 1.0f) {
            this.tmpPath.reset();
            this.tmpDotPath.reset();
            return true;
        } else if (this.currentlength == this.length) {
            return true;
        } else {
            switch (event.getActionMasked()) {
                case 0:
                    if (Math.sqrt(Math.pow((double) (x - gridXc), 2.0d) + Math.pow((double) (y - gridYc), 2.0d)) <= ((double) (this.d / 3.0f))) {
                        this.tmpDotPath.addCircle(gridXc, gridYc, 6.0f, Path.Direction.CW);
                        this.dot = true;
                        this.lastXc = gridXc;
                        this.lastYc = gridYc;
                        this.validInput = true;
                        invalidate();
                        this.input.add(new Coordinates(gridX, gridY));
                        break;
                    }
                    break;
                case 1:
                    if (this.validInput) {
                        if (this.dot) {
                            this.dotPath.addCircle(gridXc, gridYc, 6.0f, Path.Direction.CW);
                            this.currentlength++;
                        } else if (Math.sqrt(Math.pow((double) (x - gridXc), 2.0d) + Math.pow((double) (y - gridYc), 2.0d)) <= ((double) (this.d / 3.0f))) {
                            this.fixedPath.moveTo(this.lastXc, this.lastYc);
                            this.fixedPath.lineTo(gridXc, gridYc);
                        }
                        this.input.add(new Coordinates(-1, -1));
                    }
                    this.tmpPath.reset();
                    this.tmpDotPath.reset();
                    this.validInput = false;
                    invalidate();
                    checkLength();
                    break;
                case 2:
                    this.tmpPath.reset();
                    if (this.validInput) {
                        if (Math.sqrt(Math.pow((double) (x - gridXc), 2.0d) + Math.pow((double) (y - gridYc), 2.0d)) <= ((double) (this.d / 3.0f))) {
                            this.fixedPath.moveTo(this.lastXc, this.lastYc);
                            this.fixedPath.lineTo(gridXc, gridYc);
                            this.lastXc = gridXc;
                            this.lastYc = gridYc;
                            this.tmpPath.reset();
                            this.tmpDotPath.reset();
                            if (!this.input.get(this.input.size() - 1).equals(new Coordinates(gridX, gridY))) {
                                this.input.add(new Coordinates(gridX, gridY));
                                this.dot = false;
                                this.currentlength++;
                            }
                        } else {
                            this.tmpPath.moveTo(this.lastXc, this.lastYc);
                            this.tmpPath.lineTo(x, y);
                        }
                    }
                    invalidate();
                    checkLength();
                    break;
            }
            return true;
        }
    }

    private void checkLength() {
        if (this.currentlength == this.length) {
            this.activity.setReady(true);
        } else {
            this.activity.setReady(false);
        }
    }

    public void clear() {
        this.fixedPath.reset();
        this.tmpPath.reset();
        this.dotPath.reset();
        this.tmpDotPath.reset();
        invalidate();
        this.input = new ArrayList<>();
        this.currentlength = 0;
        this.activity.setReady(false);
    }

    public ArrayList<Coordinates> getInput() {
        return this.input;
    }

    public void setActivity(PassGo app) {
        this.activity = app;
    }

    public Path getFixedPath() {
        return this.fixedPath;
    }

    public Path getDotPath() {
        return this.dotPath;
    }

    public void setLength(int length2) {
        this.length = length2;
    }

}