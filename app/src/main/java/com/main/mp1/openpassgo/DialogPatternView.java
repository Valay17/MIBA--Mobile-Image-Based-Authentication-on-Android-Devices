package com.main.mp1.openpassgo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

public class DialogPatternView extends View{
    private final int COUNT_CELLS = 4;
    private float d;
    private Path dotPath = new Path();
    private Path fixedPath = new Path();
    private int offset;
    private int originalHeight = 0;
    private int originalWidth = 0;
    Paint paint = new Paint();
    Rect rect = new Rect();
    private boolean resized = false;
    private Path tmpDotPath = new Path();
    private Path tmpPath = new Path();

    public DialogPatternView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DialogPatternView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DialogPatternView(Context context) {
        super(context);
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
            if (i == 2 || i == 5) {
                canvas.drawRect((1.0f * this.d) + ((float) this.rect.left), (((float) i) * this.d) + ((float) (this.rect.top + this.offset)), (3.0f * this.d) + ((float) this.rect.left), (((float) (i + 1)) * this.d) + ((float) (this.rect.top + this.offset)), this.paint);
            } else {
                canvas.drawRect((float) this.rect.left, (((float) i) * this.d) + ((float) (this.rect.top + this.offset)), (1.0f * this.d) + ((float) this.rect.left), (((float) (i + 1)) * this.d) + ((float) (this.rect.top + this.offset)), this.paint);
                canvas.drawRect((3.0f * this.d) + ((float) this.rect.left), (((float) i) * this.d) + ((float) (this.rect.top + this.offset)), (4.0f * this.d) + ((float) this.rect.left), (((float) (i + 1)) * this.d) + ((float) (this.rect.top + this.offset)), this.paint);
            }
        }
        this.paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.paint.setStyle(Paint.Style.STROKE);
        for (int i2 = 0; i2 < 5; i2++) {
            canvas.drawLine((float) this.rect.left, (((float) i2) * this.d) + ((float) (this.rect.top + this.offset)), (float) this.rect.right, (((float) i2) * this.d) + ((float) (this.rect.top + this.offset)), this.paint);
            canvas.drawLine((((float) i2) * this.d) + ((float) this.rect.left), (float) (this.rect.top + this.offset), (((float) i2) * this.d) + ((float) this.rect.left), (float) (this.rect.bottom - this.offset), this.paint);
        }
        this.paint.setStyle(Paint.Style.FILL);
        canvas.drawRect((((float) this.rect.left) + (2.0f * this.d)) - ((float) 4), (((float) (this.rect.top + this.offset)) + (2.0f * this.d)) - ((float) 4), ((float) 4) + ((float) this.rect.left) + (2.0f * this.d), ((float) 4) + ((float) (this.rect.top + this.offset)) + (2.0f * this.d), this.paint);
        canvas.drawRect((((float) this.rect.left) + (6.0f * this.d)) - ((float) 4), (((float) (this.rect.top + this.offset)) + (2.0f * this.d)) - ((float) 4), ((float) 4) + ((float) this.rect.left) + (6.0f * this.d), ((float) 4) + ((float) (this.rect.top + this.offset)) + (2.0f * this.d), this.paint);
        canvas.drawRect((((float) this.rect.left) + (2.0f * this.d)) - ((float) 4), (((float) (this.rect.top + this.offset)) + (6.0f * this.d)) - ((float) 4), ((float) 4) + ((float) this.rect.left) + (2.0f * this.d), ((float) 4) + ((float) (this.rect.top + this.offset)) + (6.0f * this.d), this.paint);
        canvas.drawRect((((float) this.rect.left) + (6.0f * this.d)) - ((float) 4), (((float) (this.rect.top + this.offset)) + (6.0f * this.d)) - ((float) 4), ((float) 4) + ((float) this.rect.left) + (6.0f * this.d), ((float) 4) + ((float) (this.rect.top + this.offset)) + (6.0f * this.d), this.paint);
        canvas.drawRect((((float) this.rect.left) + (4.0f * this.d)) - ((float) 4), (((float) (this.rect.top + this.offset)) + (4.0f * this.d)) - ((float) 4), ((float) 4) + ((float) this.rect.left) + (4.0f * this.d), ((float) 4) + ((float) (this.rect.top + this.offset)) + (4.0f * this.d), this.paint);
        if (this.originalWidth > 0 && this.originalHeight > 0 && !this.resized) {
            float faktor = (4.0f * this.d) / ((float) this.originalWidth);
            Matrix matrix = new Matrix();
            matrix.setScale(faktor, faktor);
            matrix.postTranslate(0.0f, -1.0f * (((((float) this.originalHeight) * faktor) - ((float) this.rect.height())) / 2.0f));
            this.fixedPath.transform(matrix);
            this.dotPath.transform(matrix);
            this.resized = true;
        }
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

    public void setFixedPath(Path fixedPath2) {
        this.fixedPath = fixedPath2;
    }

    public void setDotPath(Path dotPath2) {
        this.dotPath = dotPath2;
    }

    public void setOriginalDimensions(int width, int height) {
        this.originalWidth = width;
        this.originalHeight = height;
    }

}
