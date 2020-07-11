package mk.ukim.finki.mpip.myprojectmpip;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpSignInFireBase {

    private static final String TAG = "SignUpSignInFireBase";
    
    private static SignUpSignInFireBase object;

    private static FirebaseAuth mAuth;
    private static FirebaseUser currentUser;

    private static FirebaseDatabase database;

    public SignUpSignInFireBase()
    {
    }

    public static SignUpSignInFireBase getInstance()
    {
        if (object != null)
        {
            return object;
        }
        else{
            object = new SignUpSignInFireBase();
            object.setAuth(FirebaseAuth.getInstance());
            object.setCurrentUser(mAuth.getCurrentUser());
            return object;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void signUpUser(final String email, final String password, final Context context)
    {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(context.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                FirebaseDatabase.getInstance().getReference().child("Users")
                                        .child(task.getResult().getUser().getUid())
                                        .child("email").setValue(task.getResult().getUser().getEmail());

                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(context, "Sign up successfull", Toast.LENGTH_SHORT).show();
                                object.setCurrentUser(mAuth.getCurrentUser());
                                updateUI(context);
                            }
                            else {
                                // If sign in fails, display a message to the user.
                               Toast.makeText(context, "Sign up failed.",
                                       Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void signInUser(String email, String password, final Context context)
    {
        final boolean[] flag = {false};
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(context.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            object.setCurrentUser(mAuth.getCurrentUser());
                            Toast.makeText(context, "Sign in successfull.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(context);
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(context, "Sign in failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(Context context)
    {
        if (object.getCurrentUser()!= null) {
            Intent intent = new Intent(context, SuccessAct.class);
            context.startActivity(intent);
            MainActivity.sActivity.finish();
        }

    }

    public FirebaseAuth getAuth() {
        return object.mAuth;
    }

    public void setAuth(FirebaseAuth auth) {
        object.mAuth = auth;
    }

    public FirebaseUser getCurrentUser() {
        return object.currentUser;
    }

    public void setCurrentUser(FirebaseUser currentUser) {
        object.currentUser = currentUser;
    }
}
