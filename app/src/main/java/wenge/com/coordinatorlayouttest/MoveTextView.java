package wenge.com.coordinatorlayouttest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by WENGE on 2017/11/24.
 * 备注：
 */


public class MoveTextView extends TextView {
    public MoveTextView(Context context) {
        super(context, null);
    }

    public MoveTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    private int lastX;
    private int lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                CoordinatorLayout.MarginLayoutParams layoutParams = (CoordinatorLayout.MarginLayoutParams) getLayoutParams();
                int left = layoutParams.leftMargin + x - lastX;
                int top = layoutParams.topMargin + y - lastY;

                layoutParams.leftMargin = left;
                layoutParams.topMargin = top;
                setLayoutParams(layoutParams);
                requestLayout();
                break;

            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        lastX = x;
        lastY = y;
        return true;
    }

}

