package com.genesis.apps.comm.hybrid.core;

import android.content.Context;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * 웹뷰 여러 WebView 를 사용하는 App에서 "<a>" 링크 클릭시 백그라운드 색상이 변경안되는 이슈 해결
 * 
 */
public class MyWebView extends WebView {

	public interface OnScrollChangedListener {
		void onScrollChanged(int l, int t, int oldl, int oldt);
	}

	private OnScrollChangedListener mOnScrollChangedListener;

	public MyWebView(Context context) {
		super(context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			onScrollChanged(getScrollX(), getScrollY(), getScrollX(),getScrollY());
		case MotionEvent.ACTION_UP:
			if (!hasFocus())
				requestFocus();
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		// if (mTitleBar != null) {
		// mTitleBar.onScrollChanged();
		// }
		if (mOnScrollChangedListener != null) {
			mOnScrollChangedListener.onScrollChanged(l, t, oldl, oldt);
		}
	}

	public void setOnScrollChangedListener(OnScrollChangedListener listener) {
		mOnScrollChangedListener = listener;
	}

}
