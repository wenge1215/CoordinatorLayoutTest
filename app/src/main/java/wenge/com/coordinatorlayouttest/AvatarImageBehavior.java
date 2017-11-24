package wenge.com.coordinatorlayouttest;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("unused")
public class AvatarImageBehavior extends CoordinatorLayout.Behavior<CircleImageView> {

    private final static float MIN_AVATAR_PERCENTAGE_SIZE = 0.3f;
    private final static int EXTRA_FINAL_AVATAR_PADDING = 80;

    private final static String TAG = "behavior";
    private Context mContext;

    private float mCustomFinalYPosition;
    private float mCustomStartXPosition;
    private float mCustomStartToolbarPosition;
    private float mCustomStartHeight;
    private float mCustomFinalHeight;

    private float mAvatarMaxSize;
    private float mFinalLeftAvatarPadding;
    private float mStartPosition;
    private int mStartXPosition;
    private float mStartToolbarPosition;
    private int mStartYPosition;
    private int mFinalYPosition;
    private int mStartHeight;
    private int mFinalXPosition;
    private float mChangeBehaviorPoint;

    public AvatarImageBehavior(Context context, AttributeSet attrs) {
        mContext = context;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AvatarImageBehavior);
            mCustomFinalYPosition = a.getDimension(R.styleable.AvatarImageBehavior_finalYPosition, 0);
            mCustomStartXPosition = a.getDimension(R.styleable.AvatarImageBehavior_startXPosition, 0);
            mCustomStartToolbarPosition = a.getDimension(R.styleable.AvatarImageBehavior_startToolbarPosition, 0);
            mCustomStartHeight = a.getDimension(R.styleable.AvatarImageBehavior_startHeight, 0);
            mCustomFinalHeight = a.getDimension(R.styleable.AvatarImageBehavior_finalHeight, 0);

            a.recycle();
        }

        init();

        mFinalLeftAvatarPadding = context.getResources().getDimension(
                R.dimen.spacing_normal);
    }

    private void init() {
        bindDimensions();
    }

    private void bindDimensions() {
        mAvatarMaxSize = mContext.getResources().getDimension(R.dimen.image_width);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, CircleImageView child, View dependency) {
        return dependency instanceof ImageView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, CircleImageView child, View dependency) {
        maybeInitProperties(child, dependency);

        final int maxScrollDistance = (int) (mStartToolbarPosition);
        Log.e("maxScrollDistance", maxScrollDistance + "");

        float expandedPercentageFactor = dependency.getY() / maxScrollDistance;  //0-816
        Log.e("expandedPeFactor", expandedPercentageFactor + "");

        /**
         * expandedPercentageFactor  变化范围从0-1
         */
//        if (expandedPercentageFactor < mChangeBehaviorPoint) {
//            float heightFactor = (mChangeBehaviorPoint - expandedPercentageFactor) / mChangeBehaviorPoint;
//
//            float distanceXToSubtract = ((mStartXPosition - mFinalXPosition)
//                    * heightFactor) + (child.getHeight() / 2);
//            float distanceYToSubtract = ((mStartYPosition - mFinalYPosition)
//                    * (1f - expandedPercentageFactor)) + (child.getHeight() / 2);
//
//            child.setX(mStartXPosition - distanceXToSubtract);
//            child.setY(mStartYPosition - distanceYToSubtract);
//
//            float heightToSubtract = ((mStartHeight - mCustomFinalHeight) * heightFactor);
//
//            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
//            lp.width = (int) (mStartHeight - heightToSubtract);
//            lp.height = (int) (mStartHeight - heightToSubtract);
//            child.setLayoutParams(lp);
//        } else {
//            float distanceYToSubtract = ((mStartYPosition - mFinalYPosition)
//                    * (1f - expandedPercentageFactor)) + (mStartHeight / 2);


        child.setX(mStartXPosition - child.getWidth() / 2);

        if (dependency.getTop() - child.getWidth() / 2 > 0) {
            child.setY((dependency.getTop() / 2));
        } else {
            child.setY(38);
        }

        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
//        lp.width = (int) (mStartHeight);
//        lp.height = (int) (mStartHeight);


        float heightFactor = (mChangeBehaviorPoint - expandedPercentageFactor) / mChangeBehaviorPoint;
        float heightToSubtract = ((mStartHeight - mCustomFinalHeight) * heightFactor);
        Log.w("heightFactor", heightFactor + "---" + heightToSubtract);

        if (expandedPercentageFactor < 0.45) {
            lp.width = (int) ((mStartHeight * 0.45));
            lp.height = (int) ((mStartHeight * 0.45));
        } else {
            lp.width = (int) ((mStartHeight * expandedPercentageFactor));
            lp.height = (int) ((mStartHeight * expandedPercentageFactor));
        }


//        lp.width = (int) ( (mStartHeight*expandedPercentageFactor));
//        lp.height = (int) ( (mStartHeight*expandedPercentageFactor));
        child.setLayoutParams(lp);
//        }
        return true;
    }

    private void maybeInitProperties(CircleImageView child, View dependency) {
        if (mStartYPosition == 0) {
            mStartYPosition = (int) (dependency.getY());
            Log.e("1", mStartYPosition + "");
        }

        /**
         * 屏幕宽度的1/2
         */
        if (mFinalYPosition == 0) {
            mFinalYPosition = (dependency.getHeight() / 2);
            Log.e("2", mFinalYPosition + "");
        }

        if (mStartHeight == 0) {
            mStartHeight = child.getHeight();
            Log.e("3", mStartHeight + "");
        }

        if (mStartXPosition == 0) {
            mStartXPosition = (int) (child.getX() + (child.getWidth() / 2));
            Log.e("4", mStartXPosition + "");
        }

        if (mFinalXPosition == 0) {
//            mFinalXPosition = mContext.getResources().getDimensionPixelOffset(R.dimen.abc_action_bar_content_inset_material) + ((int) mCustomFinalHeight / 2);
//            Log.e("5", mFinalXPosition + "");
            mFinalXPosition = mStartXPosition;
        }

        if (mStartToolbarPosition == 0) {
            mStartToolbarPosition = dependency.getY();
            Log.e("6", mStartToolbarPosition + "");
        }

        /**
         * 开始改变图片的标准
         */
        if (mChangeBehaviorPoint == 0) {
            mChangeBehaviorPoint = (child.getHeight() - mCustomFinalHeight) / (2f * (mStartYPosition - mFinalYPosition));
            Log.e("7", mChangeBehaviorPoint + "");
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
