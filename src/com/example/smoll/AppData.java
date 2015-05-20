package com.example.smoll;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

/**
 * Created by Issac on 7/18/13.
 */
public class AppData extends Application
{
	private static Context sContext;
	public final static boolean DEBUG = true;
	private static AppData instance = null;
	public final static String CHARSET = "UTF-8";
	public static final String ACTION_MODE_CHANGE = "mode_change";
	public boolean isHighMode = true;

	@Override
	public void onCreate()
	{
		super.onCreate();
		sContext = getApplicationContext();
		instance = this;
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
			}
		}).start();

	}

	public static Context getContext()
	{
		return sContext;
	}

	public static AppData getInstance()
	{
		return instance;
	}

	public static boolean getNetWorkded()
	{
		boolean isNetWork = true;
		if (getCurrentNetType() == null)
		{
			isNetWork = false;
		}
		else
		{
			isNetWork = true;
		}
		return isNetWork;
	}

	public static String getCurrentNetType()
	{

		ConnectivityManager connectMgr = (ConnectivityManager) sContext.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo info = connectMgr.getActiveNetworkInfo();
		if (info == null)
		{
			return null;
		}
		if (info.getType() == ConnectivityManager.TYPE_WIFI)
		{
			return "WIFI";
		}
		else
		{
			int subType = info.getSubtype();
			if (subType == TelephonyManager.NETWORK_TYPE_CDMA //ctnet
			        || subType == TelephonyManager.NETWORK_TYPE_GPRS //Un
			        || subType == TelephonyManager.NETWORK_TYPE_EDGE || subType == TelephonyManager.NETWORK_TYPE_IDEN//mobile
			)
			{
				return "2G";
			}
			else if (subType == TelephonyManager.NETWORK_TYPE_UMTS
			        || subType == TelephonyManager.NETWORK_TYPE_HSUPA //ctnet
			        || subType == TelephonyManager.NETWORK_TYPE_EVDO_0 || subType == TelephonyManager.NETWORK_TYPE_EVDO_A
			        || subType == TelephonyManager.NETWORK_TYPE_HSPA || subType == TelephonyManager.NETWORK_TYPE_1xRTT
			        || subType == TelephonyManager.NETWORK_TYPE_HSDPA || subType == TelephonyManager.NETWORK_TYPE_EVDO_B)
			{
				return "3G";
			}
			else if (subType == TelephonyManager.NETWORK_TYPE_LTE || subType == TelephonyManager.NETWORK_TYPE_EHRPD
			        || subType == TelephonyManager.NETWORK_TYPE_HSPAP)
			{
				return "4G";
			}
		}
		return null;
	}

	public static int getScreenWidth()
	{
		DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
		return displayMetrics.widthPixels;
	}

	public static int getScreenHeight()
	{
		DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
		return displayMetrics.heightPixels;
	}
}
