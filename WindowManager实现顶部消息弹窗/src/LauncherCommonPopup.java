package com.adayo.launcher.tools;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adayo.launcher.R;
import com.adayo.skinbase.SkinUtil;

@SuppressLint("NewApi")
public class LauncherCommonPopup {
	// DEFINE
	private final static String TAG = "LauncherCommonPopup";
	private final static int MSG_HIDE_MESSAGE = 1001;
	private static int mLayoutProps = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

	// OBJECT
	private static Context mContext;
	private static WindowManager mWindowManager;

	// VIEW
	private static View mLayoutMessage;
	private static TextView mTvContent;

	// VARIABLE
	private static boolean isMessageShown = false;
	private static boolean hasInit = false;

	private static Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_HIDE_MESSAGE:
				hideMessage();
				break;
			default:
				break;
			}
		}
	};

	public static void showToast(final Context context, String message) {
		mContext = context;

		if (!hasInit) {
			initData();
			initView();
			hasInit = true;
		}

		showMessage(message);
	}

	public static void onPause() {
		if (isMessageShown) {
			mWindowManager.removeView(mLayoutMessage);
			isMessageShown = false;
		}
		mHandler.removeCallbacksAndMessages(null);
	}

	private static void initData() {
		Log.i(TAG, "initData");
		mWindowManager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
	}

	private static void initView() {
		Log.i(TAG, "initView");
		mLayoutMessage = LayoutInflater.from(mContext).inflate(
				R.layout.t1_layout_launcher_popup, null);
		mLayoutMessage.setSystemUiVisibility(mLayoutProps);
		mTvContent = (TextView) mLayoutMessage
				.findViewById(R.id.id_popup_tv_message);
	}

	private static void refreshMessage(String message) {
		Log.i(TAG, "refreshMessage: message = " + message);
		mTvContent.setText(message);
	}

	private static WindowManager.LayoutParams getTopLayoutParams() {
		LayoutParams lp = new LayoutParams(736, 174, 2021, 0
				| WindowManager.LayoutParams.FLAG_TOUCHABLE_WHEN_WAKING
				// | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				// 添加下面属性，可以让输入法弹出时覆盖这个区域
				| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
				PixelFormat.TRANSPARENT);
		lp.gravity = Gravity.TOP;
		lp.windowAnimations = R.style.anim_view;
		return lp;
	}

	private static void showMessage(String message) {
		Log.i(TAG, "showMessage");
		refreshMessage(message);

		if (!isMessageShown) {
			mWindowManager.addView(mLayoutMessage, getTopLayoutParams());
			isMessageShown = true;
		}

		mHandler.removeMessages(MSG_HIDE_MESSAGE);
		mHandler.sendEmptyMessageDelayed(MSG_HIDE_MESSAGE, 3000);
	}

	private static void hideMessage() {
		Log.i(TAG, "hideMessage");
		if (isMessageShown) {
			mWindowManager.removeView(mLayoutMessage);
			isMessageShown = false;
		}
	}

}
