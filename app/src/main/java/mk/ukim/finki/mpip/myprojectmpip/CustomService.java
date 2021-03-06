package mk.ukim.finki.mpip.myprojectmpip;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.*;


import static mk.ukim.finki.mpip.myprojectmpip.App.CHANNEL_ID;

public class CustomService extends Service {

    private Notification notification;
    private ClipboardManager clipboard;

    @Override
    public void onCreate() {
        super.onCreate();
        Intent clickintent = new Intent(this, SuccessAct.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, clickintent, 0);

        clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Copy catch service")
                .setSmallIcon(R.drawable.service_icon)
                .setContentIntent(pendingIntent)
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //izgleda ke treba da se naprae nov thread ili asynctask za fakjaneto copy intents u pozadina
        //i na destroy da se uniste threado ili asynco za da nema leaks


        Log.i("SERVICE", "SERVICE SERVICE SERVICE");
        clipboard.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                //When copy happens clipdata needs to be put on firebase database
                Log.i("COPY", "COPY HAPPENED " + clipboard.getPrimaryClip().getItemAt(0).getText().toString());

                FirebaseDatabase.getInstance().getReference("Users")
                        .child(SignUpSignInFireBase.getInstance().getCurrentUser().getUid())
                        .child("Clipboard").setValue(clipboard.getPrimaryClip().getItemAt(0).getText().toString());

            }
        });

        FirebaseDatabase.getInstance().getReference("Users")
                .child(SignUpSignInFireBase.getInstance().getCurrentUser().getUid())
                .child("Clipboard")     //ako nema staveno clipboard u bazata?
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.getValue() != null) {
                            String clip = snapshot.getValue().toString();
                            Log.i("CHANGE", "Database clipboard changed to " + clip);

                            clipboard.setPrimaryClip(ClipData.newPlainText("New clip from Firebase", clip));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        startForeground(1, notification);
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
