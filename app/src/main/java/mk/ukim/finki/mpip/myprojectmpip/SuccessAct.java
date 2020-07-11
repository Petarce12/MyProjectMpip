package mk.ukim.finki.mpip.myprojectmpip;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class SuccessAct extends AppCompatActivity {

    private SignUpSignInFireBase mFireBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucess);
        mFireBase = SignUpSignInFireBase.getInstance();


        customStartService();

    }

    private void customStartService() {
        Intent serviceIntent = new Intent(this,CustomService.class);
        startService(serviceIntent);
    }

    private void customStopService()
    {
        Intent serviceIntent = new Intent(this,CustomService.class);
        stopService(serviceIntent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.costum_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.logoutItemID:
                signOutUser();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void signOutUser() {


        mFireBase.getAuth().signOut();
        mFireBase.setCurrentUser(mFireBase.getAuth().getCurrentUser());
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        customStopService();
        finish();
    }


}
