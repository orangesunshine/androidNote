package com.orange.pullloadmorelib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

public class PullLoadmoreListView extends ListView {
    //final
    private final int PULL_NOMAL = 1;
    private final int PULL_READY = 2;
    private final int PULL_REFRESHING = 3;
    private final float RADIO = 2.0f;
    private final int ROTATE_ANIM_DURATION = 200;
    private final int SCROLL_DURATION = 500;
    private final int PULL_OFFSET = 100;

    //vars
    private Context mContext;
    private int mDefaultHeaderHeight;
    private int mState;
    private boolean isPullUp;
    private View mFooter;
    private FrameLayout mHeader,mContainerFl;
    private RelativeLayout rlHeaderContent;
    private ImageView ivProgressBar,ivFooterProgressBar, ivArrow;
    private TextView mHeaderTv;
    private Animation mRotateUp,mRotateDown;
    private AnimationDrawable mProgressDrawable,mFooterProgressDrawable;
    private Scroller mScroller;
    private RefreshLoadmoreListener mRefreshLoadmoreListener;

    public void setRefreshListener(RefreshLoadmoreListener refreshListener)
    {
        mRefreshLoadmoreListener = refreshListener;
    }

    public PullLoadmoreListView(Context context) {
        super(context);
    }

    public PullLoadmoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PullLoadmoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        mContext = context;
        isPullUp = false;
        mState = PULL_NOMAL;
        mScroller = new Scroller(context, new DecelerateInterpolator());
        //header
        mHeader = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.pull_header, null);
        mContainerFl = mHeader.findViewById(R.id.fl_header_container);
        rlHeaderContent = mHeader.findViewById(R.id.rl_header_content);
        ivProgressBar = mHeader.findViewById(R.id.iv_progressbar);
        ivArrow = mHeader.findViewById(R.id.iv_arrow);
        mHeaderTv = mHeader.findViewById(R.id.tv_hint);
        mContainerFl.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        addHeaderView(mHeader);
        setHeaderDividersEnabled(false);
        mHeader.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {
                mDefaultHeaderHeight = rlHeaderContent.getHeight();
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        //footer
        mFooter = LayoutInflater.from(context).inflate(R.layout.pull_footer,null);
        ivFooterProgressBar = mFooter.findViewById(R.id.iv_footer_progressbar);
        mFooterProgressDrawable = (AnimationDrawable) ivFooterProgressBar.getDrawable();
        addFooterView(mFooter);
        mFooter.setVisibility(GONE);

        mProgressDrawable = (AnimationDrawable) ivProgressBar.getDrawable();
        mRotateUp = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUp.setDuration(ROTATE_ANIM_DURATION);
        mRotateUp.setFillAfter(true);
        mRotateDown = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDown.setDuration(ROTATE_ANIM_DURATION);
        mRotateDown.setFillAfter(true);
    }

    private float mPreY;
    private float mDownX, mDownY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getRawX();
                mDownY = mPreY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float rawY = ev.getRawY();
                float deltY = rawY - mPreY;
                mPreY = rawY;
                if(0==getFirstVisiblePosition()&&(deltY>0||mContainerFl.getHeight()>0))
                updateHeader(deltY);
                break;
            case MotionEvent.ACTION_UP:
                mPreY =ev.getRawY();
                onActionUp(mState);
                if (canLoadmore()) {
                    isPullUp = true;
                    mFooter.setVisibility(VISIBLE);
                    mFooterProgressDrawable.start();
                    if (null != mRefreshLoadmoreListener) {
                        mRefreshLoadmoreListener.onLoadmore();
                    }
                }
                break;
        }

        return super.onTouchEvent(ev);
    }

    private void onActionUp(int state) {
        int height = mContainerFl.getHeight();
        if(height<=0)return;
        int destHeight = 0;
        if (height > mDefaultHeaderHeight && PULL_READY == state) {
            destHeight = mDefaultHeaderHeight;
            setState(PULL_REFRESHING);
        }
        mScroller.startScroll(0,height,0,destHeight-height,SCROLL_DURATION);
        invalidate();
    }

    private void updateHeader(float deltY) {
        int contentHeight = mContainerFl.getHeight();
        int height = (int) (contentHeight + deltY);
        if (height < 0) {
            height = 0;
        }
        if(height>mDefaultHeaderHeight && mState!=PULL_READY)
        {
            setState(PULL_READY);

        }else if(height<=mDefaultHeaderHeight && mState != PULL_NOMAL){
            setState(PULL_NOMAL);
        }
        ViewGroup.LayoutParams layoutParams = mContainerFl.getLayoutParams();
        layoutParams.height = height;
        mContainerFl.setLayoutParams(layoutParams);
        setSelection(0); // scroll to top each time
    }

    private void setState(int state)
    {
        ivArrow.clearAnimation();
        if (PULL_REFRESHING == state) {
            ivProgressBar.setVisibility(VISIBLE);
            ivArrow.setVisibility(GONE);
            mHeaderTv.setText("正在刷新……");
            mProgressDrawable.start();
            if (null != mRefreshLoadmoreListener) {
                mRefreshLoadmoreListener.onRefresh();
            }
        }else{
            mProgressDrawable.stop();
            ivProgressBar.setVisibility(GONE);
            ivArrow.setVisibility(VISIBLE);
            if (PULL_NOMAL == state) {
                if (PULL_READY == mState) {
                    ivArrow.startAnimation(mRotateDown);
                }
                mHeaderTv.setText("下拉刷新");
            } else {
                if (PULL_NOMAL == mState) {
                    ivArrow.startAnimation(mRotateUp);
                }
                mHeaderTv.setText("松开刷新数据");
            }
        }
        mState=state;
    }

    private boolean canLoadmore()
    {
        return isBottom() && !isPullUp && isPullUp();
    }

    private boolean isBottom()
    {
        if (getCount() > 0) {
            Log.e("HY","getBottom: "+getChildAt(getChildCount()-1).getBottom() + ", getHeight: "+getHeight());
            return getLastVisiblePosition() == getAdapter().getCount() - 1;
        }
        return false;
    }

    private boolean isPullUp()
    {
        return (mDownY-mPreY)>PULL_OFFSET;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            ViewGroup.LayoutParams layoutParams = mContainerFl.getLayoutParams();
            layoutParams.height = mScroller.getCurrY();
            mContainerFl.setLayoutParams(layoutParams);
            postInvalidate();
        }
        super.computeScroll();
    }

    public void refreshComplete()
    {
        if (PULL_REFRESHING == mState) {
            onActionUp(mState);
        }
    }

    public void loadmoreComplete()
    {
        if (isPullUp) {
            isPullUp = false;
            mFooterProgressDrawable.stop();
            mFooter.setVisibility(GONE);
        }
    }

    public interface RefreshLoadmoreListener
    {
        void onRefresh();
        void onLoadmore();
    }
}

