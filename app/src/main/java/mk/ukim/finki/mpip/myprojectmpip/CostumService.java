package mk.ukim.finki.mpip.myprojectmpip;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static mk.ukim.finki.mpip.myprojectmpip.App.CHANNEL_ID;

public class CostumService extends Service {

    private Notification notification;

    @Override
    public void onCreate() {
        super.onCreate();
        Intent clickintent = new Intent(this,SucessAct.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,clickintent,0);

        notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("Copy catch service")
                .setSmallIcon(R.drawable.service_icon)
                .setContentIntent(pendingIntent)
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //izgleda ke treba da se naprae nov thread ili asynctask za fakjaneto copy intents u pozadina
        //i na destroy da se uniste threado ili asynco za da nema leaks


        startForeground(1,notification);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
