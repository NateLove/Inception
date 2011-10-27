package com.redevs.apps.inception;



import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;
import android.widget.RemoteViews;

public class InceptionActivity extends AppWidgetProvider {
	public  InceptionActivity Widget = null;
	public  Context context;
	public  AppWidgetManager appWidgetManager;
	public  int appWidgetIds[];	
	
	@Override
    public void onUpdate( Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds )    {		
		if (null == context) context = this.context.getApplicationContext();
	    if (null == appWidgetManager) appWidgetManager = this.appWidgetManager;
	    if (null == appWidgetIds) appWidgetIds = this.appWidgetIds;

	    this.Widget = this;
	    this.context = context;
	    this.appWidgetManager = appWidgetManager;
	    this.appWidgetIds = appWidgetIds;
	    
		//Log.i("PXR", "onUpdate");
		
		final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];  
            
            updateAppWidget(context,appWidgetManager, appWidgetId);            
        }
        
    }

	
	static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId) {
        
                
        Intent intent = new Intent(context, UpdateService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        remoteViews.setOnClickPendingIntent(R.id.imageView1, pendingIntent);
        
 
 
        // Tell the widget manager
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }
	
	
	
	public static class UpdateService extends Service {
        @Override
        public void onStart(Intent intent, int startId) {
        	//InceptionActivity.Widget.onUpdate(context, appWidgetManager, appWidgetIds);
        	MediaPlayer media = MediaPlayer.create(this.getApplicationContext(), R.raw.cricket1);
        	media.setLooping(false);
        	media.start();

        	media.setOnCompletionListener(new OnCompletionListener() {
        	    @Override
        	    public void onCompletion(MediaPlayer media) {
        	        media.release();
        	    }
        	});
        }

		@Override
		public IBinder onBind(Intent arg0) {
			return null;
		}
    }
	
}