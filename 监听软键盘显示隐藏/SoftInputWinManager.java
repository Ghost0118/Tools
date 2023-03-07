package com.adayo.personalcenter.utils;

import java.util.ArrayList;
import java.util.List;

import com.adayo.personalcenter.views.IAccountBindingView;
import com.adayo.personalcenter.views.IAccountLoginView;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class SoftInputWinManager implements OnGlobalLayoutListener {
	// DEFINE
	private final String TAG = this.getClass().getSimpleName();

	// OBJECT
	private static SoftInputWinManager mSelf;
	private List<onSoftInputChangedListener> mListenerList;

	// VIEW
	private View mRootView;

	// VARIABLE

	// INTERFACE
	public interface onSoftInputChangedListener {
		void onSoftInputShown();

		void onSoftInputHidden();
	}

	public SoftInputWinManager() {
		initData();
	}

	private void initData() {
		mListenerList = new ArrayList<onSoftInputChangedListener>();
	}

	@Override
	public void onGlobalLayout() {
		// TODO Auto-generated method stub
		boolean mKeyboardUp = isKeyboardShown(mRootView);
		if (mKeyboardUp) {
			notifyOnSoftInputShown();
		} else {
			notifyOnSoftInputHidden();
		}
	}

	/******************** 对外接口 start ********************/

	public static SoftInputWinManager getInstance() {
		if (mSelf == null) {
			synchronized (SoftInputWinManager.class) {
				if (mSelf == null) {
					mSelf = new SoftInputWinManager();
				}
			}
		}
		return mSelf;
	}

	/**
	 * @param rootView
	 *            所监听Activity的contentView
	 * 
	 *            在所监听Activity OnCreate处初始化
	 */
	public void init(View rootView) {
		this.mRootView = rootView;
	}

	public void register() {
		if (mRootView != null) {
			mRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
		} else {
			Log.d(TAG, "register: mRootView == null");
		}
	}

	public void unregister() {
		if (mRootView != null) {
			mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
		} else {
			Log.d(TAG, "unregister: mRootView == null");
		}
	}

	public void addListener(onSoftInputChangedListener listener) {
		Log.d(TAG, "addListener: listener = " + listener);
		mListenerList.add(listener);
	}

	public void removeListener(onSoftInputChangedListener listener) {
		Log.d(TAG, "removeListener: listener = " + listener);
		if (mListenerList.contains(listener)) {
			mListenerList.remove(mListenerList.indexOf(listener));
		}
	}

	public void removeListeners() {
		Log.d(TAG, "removeListeners");
		mListenerList.clear();
	}

	/******************** 对外接口 end ********************/

	private void notifyOnSoftInputShown() {
		Log.i(TAG, "notifyOnSoftInputShown");
		for (onSoftInputChangedListener listener : mListenerList) {
			listener.onSoftInputShown();
		}
	}

	private void notifyOnSoftInputHidden() {
		Log.i(TAG, "notifyOnSoftInputHidden");
		for (onSoftInputChangedListener listener : mListenerList) {
			listener.onSoftInputHidden();
		}
	}

	private boolean isKeyboardShown(View rootView) {
		final int softKeyboardHeight = 360;
		Rect r = new Rect();
		rootView.getWindowVisibleDisplayFrame(r);
		DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
		int heightDiff = rootView.getBottom() - r.bottom;
		return heightDiff > softKeyboardHeight * dm.density;
	}

}
