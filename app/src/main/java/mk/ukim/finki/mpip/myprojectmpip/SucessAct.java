package mk.ukim.finki.mpip.myprojectmpip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class SucessAct extends AppCompatActivity {

    private SignUpSignInFireBase mFireBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucess);
        mFireBase = SignUpSignInFireBase.getInstance();


        costumStartService();

    }

    private void costumStartService() {
        Intent serviceIntent = new Intent(this,CostumService.class);
        startService(serviceIntent);
    }

    private void costumStopService()
    {
        Intent serviceIntent = new Intent(this,CostumService.class);
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
        costumStopService();
        finish();
    }


}
