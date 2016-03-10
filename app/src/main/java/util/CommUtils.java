package util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import java.math.BigDecimal;

public class CommUtils {

	private static Toast toast = null;

	public static void makeToast(Context context, String text) {
		if (context == null) {
			return;
		}
		if (toast == null) {
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		} else {
			toast.setText(text);
		}
		toast.show();
	}

	public static void makeToast(Context context, int toastTime, String text) {
		if (toast == null) {
			toast = Toast.makeText(context, text, toastTime);
		} else {
			toast.setText(text);
		}
		toast.show();
	}



	public static boolean isMobileNO(String mobiles) {
		String telRegex = "[1][34578]\\d{9}";
		if ("".equals(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	public static int dp2px(Context context, float dpValue) {
		if(null==context){
			return 0;
		}
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	private static int deviceWidth = 0;

	

	@SuppressWarnings("deprecation")
	public static int getDeviceWidth(Activity activity) {
		if (deviceWidth == 0) {
			deviceWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
		}
		return deviceWidth;
	}

	private static int deviceHeight = 0;

	@SuppressWarnings("deprecation")
	public static int getDeviceHeight(Activity activity) {
		if (deviceHeight == 0) {
			deviceHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
		}
		return deviceHeight;
	}

	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}


	@SuppressLint("DefaultLocale")
	public static String getPriceString(double price) {
		BigDecimal a1 = new BigDecimal(Double.toString(price));
		return String.valueOf(a1.doubleValue());
	}

	public static String getChannel(Context context) {
		try {
			ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);

			Bundle bundle = ai.metaData;
			String value = bundle.get("UMENG_CHANNEL").toString();
			return value;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Double mul(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		double m = b1.multiply(b2).doubleValue();
		return m;
	}
	
	
	public static int getAppVersionVersionCode(Context context) {

		int versionCode = 1;
		try {
			PackageManager pkgMng = context.getPackageManager();
			PackageInfo pkgInfo = pkgMng.getPackageInfo(context.getPackageName(), 0);
			versionCode = pkgInfo.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionCode;
	}
	
}
