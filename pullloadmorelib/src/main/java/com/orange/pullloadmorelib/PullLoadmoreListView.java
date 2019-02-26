package com.orange.pullloadmorelib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class PullLoadmoreListView extends ListView {
    private Context mContext;
    private View mHeader;
    private TextView mHeaderTv;
    public PullLoadmoreListView(Context context) {
        super(context);
    }

    public PullLoadmoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullLoadmoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context)
    {
        mContext = context;
        mHeader = LayoutInflater.from(context).inflate(R.layout.pull_header,null);
        mHeaderTv = mHeader.findViewById(R.id.tv_hint);

        addHeaderView(mHeader);
    }
}
