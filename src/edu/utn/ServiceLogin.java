package edu.utn;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import edu.utn.frre.R;

public class ServiceLogin extends  Service {
	private static final String TAG = "ServiceLogin";
	private NotificationManager notificationMgr;
private Intent miIntent;

	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onBind");
		return null;
	}
	
	
	@Override
	public void onCreate() {
	super.onCreate();
	

	notificationMgr =(NotificationManager)getSystemService(
	NOTIFICATION_SERVICE);
	displayNotificationMessage("starting Background Service");
	Thread thr = new Thread(null, new ServiceWorker(), "BackgroundService");
	thr.start();
	}
	
	class ServiceWorker implements Runnable
	{
	public void run() {
		
/*		String keywords=null;
    	HttpEntity entity = null;
    	HttpClient httpClient = new DefaultHttpClient();
    	HttpContext localContext = new BasicHttpContext();
    	
    	
    	HttpPost httpH = new HttpPost(miIntent.getExtras);
    	
    	String datosquevan = ServiceLogin.user + "," + ServiceLogin.pass;

    	try {
    		httpH.setEntity(new StringEntity(datosquevan));
    	} catch (UnsupportedEncodingException e1) {
    		// TODO Auto-generated catch block
    		Log.i("algo origino lo sgte","ClientProtocolException" , e1);
    		//Toast.makeText(this, e1.getCause().toString(), Toast.LENGTH_LONG).show();
    	}
    	
    	try {
    		HttpResponse response = httpClient.execute(httpH);
    		entity = response.getEntity();
    		keywords = EntityUtils.toString(entity);
    	} catch (ClientProtocolException e) {
    		// TODO Auto-generated catch block
    		Log.i("algo origino lo sgte","ClientProtocolException" , e);
    		//Toast.makeText(this, e.getCause().toString(), Toast.LENGTH_LONG).show();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		//e.printStackTrace();biblioVirtual.no-ip.com
    		Log.i("AAAAAAalgo origino lo sgte","ClientProtocolException" , e);
    		//Toast.makeText(this, e.getCause().toString(), Toast.LENGTH_LONG).show();
    		//Toast.makeText(this, "Socket timeout or Server No Respondig", Toast.LENGTH_LONG).show();
    	}*/
    
		
		//ServiceLogin.setRdo(keywords);
		
	// do background processing here...
	// stop the service when done...
	 ServiceLogin.this.stopSelf();
	}
	}


	private void displayNotificationMessage(String message)
	{
	Notification notification = new Notification(R.drawable.icon,
	message,System.currentTimeMillis());
	PendingIntent contentIntent =
	PendingIntent.getActivity(this, 0,new Intent(this, ServiceLogin.class), 0);
	notification.setLatestEventInfo(this, "Background Service",message,
	contentIntent);
	notificationMgr.notify(R.string.app_notification_id, notification);
	}

	@Override
	public void onDestroy()
	{
	displayNotificationMessage("stopping Background Service");
	super.onDestroy();
	}
	@Override
	public void onStart(Intent intent, int startId) {
	super.onStart(intent, startId);
	this.miIntent=intent;
	
	}


}
