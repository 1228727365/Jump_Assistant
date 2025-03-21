package com.crawling.studio;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;

/**
 * 正常的系统前台进程，会在系统通知栏显示一个Notification通知图标
 *
 * @author clock
 * @since 2016-04-12
 */
public class WhiteService extends Service {
	
	private final static String TAG = WhiteService.class.getSimpleName();
	
	private final static int FOREGROUND_ID = 1000;
	
	@Override
	public void onCreate() {
		Log.i(TAG, "白色服务->on 创造");
		super.onCreate();
	}
	
	@SuppressLint("ForegroundServiceType")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "白色服务->on启动命令");
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setSmallIcon(R.mipmap.ic_launcher);
		builder.setContentTitle("Foreground");
		builder.setContentText("I am a foreground service");
		builder.setContentInfo("Content Info");
		builder.setWhen(System.currentTimeMillis());
		Intent activityIntent = new Intent(this, homeActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pendingIntent);
		Notification notification = builder.build();
		startForeground(FOREGROUND_ID, notification);
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("尚未实施");
	}
	
	@Override
	public void onDestroy() {
		Log.i(TAG, "白色服务->销毁");
		super.onDestroy();
	}
}
