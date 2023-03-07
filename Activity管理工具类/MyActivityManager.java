package com.adayo.window;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;

public class MyActivityManager {

	private static final String TAG = "MyActivityManager";
	private static MyActivityManager mSelf = null;

	/**
	 * 获取MyActivityManager实例
	 * 
	 * @return MyActivityManager
	 */
	public static MyActivityManager getInstance() {
		if (mSelf == null) {
			mSelf = new MyActivityManager();
		}
		return mSelf;
	}

	/**
	 * 打开窗口
	 * 
	 * @param context
	 *            上下文
	 * @param action
	 *            动作
	 */
	public void openActivityByAction(Context context, String action) {
		try {
			Log.v(TAG, "openActivityByAction action=" + action);
			Intent intent = new Intent();
			intent.setAction(action);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);

		} catch (Exception e) {
			Log.e(TAG, "openActivityByAction e=" + e);
		}
	}

	/**
	 * 打开一个普通窗口
	 * 
	 * @param context
	 *            上下文
	 * @param pkgName
	 *            包名
	 */
	public void openActivity(Context context, String pkgName) {
		try {
			Log.v(TAG, "openActivity pkgName=" + pkgName);
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(pkgName);
			if (intent != null) {
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}

		} catch (Exception e) {
			Log.e(TAG, "openActivity e=" + e);
		}
	}

	/**
	 * 打开一个普通窗口
	 * 
	 * @param context
	 *            上下文
	 * @param cls
	 *            Activity.Class
	 */
	public void openActivity(Context context, Class<?> cls) {
		try {
			Log.v(TAG, "openActivity cls=" + cls);
			Intent intent = new Intent(context, cls);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);

		} catch (Exception e) {
			Log.e(TAG, "openActivity e=" + e);
		}
	}

	/**
	 * 打开一个普通窗口（无动画）
	 * 
	 * @param context
	 *            上下文
	 * @param pkgName
	 *            包名
	 */
	public void openActivityNoAnim(Context context, String pkgName) {
		try {
			Log.v(TAG, "openActivityNoAnim pkgName=" + pkgName);
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(pkgName);
			if (intent != null) {
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				context.startActivity(intent);
			}

		} catch (Exception e) {
			Log.e(TAG, "openActivityNoAnim e=" + e);
		}
	}

	/**
	 * 打开一个普通窗口（无动画）
	 * 
	 * @param context
	 *            上下文
	 * @param cls
	 *            Activity.Class
	 */
	public void openActivityNoAnim(Context context, Class<?> cls) {
		try {
			Log.v(TAG, "openActivityNoAnim cls=" + cls);
			Intent intent = new Intent(context, cls);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			context.startActivity(intent);

		} catch (Exception e) {
			Log.e(TAG, "openActivityNoAnim e=" + e);
		}
	}

	/**
	 * 打开蓝牙设置
	 * 
	 * @param context
	 */
	public void openBTSettings(Context context) {
		Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * 打开日期设置
	 * 
	 * @param context
	 */
	public void openDateSettings(Context context) {
		Intent intent = new Intent(Settings.ACTION_DATE_SETTINGS);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * 判断指定窗口是否已置顶
	 * 
	 * @param context
	 *            上下文
	 * @param cls
	 *            Activity.Class
	 * @return boolean
	 */
	public boolean isActivityTop(Context context, Class<?> cls) {
		final ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
		if (cn.getClassName().equals(cls.getName()))
			return true;

		return false;
	}

	/**
	 * 判断指定窗口是否已置顶
	 * 
	 * @param context
	 *            上下文
	 * @param pkgName
	 *            包名
	 * @return boolean
	 */
	public boolean isActivityTop(Context context, String pkgName) {
		// 获取当前置顶窗口对应的应用
		try {
			final ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
			if (cn.getPackageName().equals(pkgName))
				return true;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	/**
	 * 获取置顶窗口的包名
	 * 
	 * @param context
	 * @return String 包名
	 */
	public static String getTopActivityPkgName(Context context) {
		final ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
		return cn.getPackageName();
	}

	/**
	 * 获取置顶窗口的类名
	 * 
	 * @param context
	 * @return String 类名
	 */
	public static String getTopActivityClassName(Context context) {
		final ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
		return cn.getClassName();
	}

	/**
	 * 设置窗口全屏（必须在主线程中调用，否则会报错）
	 * 
	 * @param activity
	 * @param bFull
	 */
	public static void fullScreen(Activity activity, boolean bFull) {
		try {
			if (activity == null)
				return;

			WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
			if (bFull) {
				lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
				activity.getWindow().setAttributes(lp);
				activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
			} else {
				lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
				activity.getWindow().setAttributes(lp);
				activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
			}
		} catch (Exception e) {
			Log.v(TAG, "fullScreen e=" + e.getMessage());
		}
	}

	/**
	 * 判断窗口是否全屏（必须在主线程中调用，否则会报错）
	 * 
	 * @param activity
	 * @return boolean
	 */
	public static boolean isFullScreen(Activity activity) {
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
		if ((lp.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != 0)
			return true;
		return false;
	}

	/**
	 * 窗口全屏切换（必须在主线程中调用，否则会报错）
	 * 
	 * @param activity
	 */
	public static void fullScreenSwitch(Activity activity) {
		if (isFullScreen(activity))
			fullScreen(activity, false);
		else
			fullScreen(activity, true);
	}

	/**
	 * 设置窗口是否显示系统墙纸
	 * 
	 * @param activity
	 */
	public static void setWallpaperVisibility(Activity activity, boolean visible) {
		int wpflags = visible ? WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER : 0;
		int curflags = activity.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER;
		if (wpflags != curflags) {
			activity.getWindow().setFlags(wpflags, WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER);
		}
	}

	/**
	 * 隐藏系统墙纸
	 * 
	 * @param activity
	 */
	public static void hideWallpaper(Activity activity) {
		setWallpaperVisibility(activity, false);
	}

	/**
	 * 显示系统墙纸
	 * 
	 * @param activity
	 */
	public static void showWallpaper(Activity activity) {
		setWallpaperVisibility(activity, true);
	}
}
