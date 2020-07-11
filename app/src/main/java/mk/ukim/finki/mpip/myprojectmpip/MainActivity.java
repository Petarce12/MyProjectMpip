package mk.ukim.finki.mpip.myprojectmpip;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String FIREBASE_CODE = "firebaseCode";

    public static Activity sActivity;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");


    private SignUpSignInFireBase firebaseSignUpSignIn;

    private EditText mEditTextEmail,mEditTextPassword;
    private Button mButtonSignUp,mButtonSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiseComponents();
    }

    private void initialiseComponents() {

        sActivity = this;

        mEditTextEmail = findViewById(R.id.editTextEmailID);
        mEditTextPassword = findViewById(R.id.editTextPasswordID);

        firebaseSignUpSignIn = SignUpSignInFireBase.getInstance();

        mButtonSignIn = findViewById(R.id.btnSignInID);
        mButtonSignUp = findViewById(R.id.btnSignUpID);

        mButtonSignUp.setOnClickListener(this);
        mButtonSignIn.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        updateUI(firebaseSignUpSignIn.getCurrentUser());
    }


    private void updateUI(FirebaseUser currentUser) {


        if (currentUser != null)
        {
            Intent intent = new Intent(this, SuccessAct.class);
            startActivity(intent);
            finish();
        }
        else
        {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnSignUpID:
                signUpUser();
                break;
            case R.id.btnSignInID:
                signInUser();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void signUpUser()
    {
        if (validateEmailAndPassword())
        {
            firebaseSignUpSignIn.signUpUser(mEditTextEmail.getText().toString(),mEditTextPassword.getText().toString(),this);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void signInUser()
    {
        firebaseSignUpSignIn.signInUser(mEditTextEmail.getText().toString(),mEditTextPassword.getText().toString(),this);
    }

    private boolean validateEmailAndPassword()
    {
        boolean flag1,flag2;
        flag1 = validateEmail();
        flag2 = validatePassword();
        return flag1&&flag2;
    }

    private boolean validateEmail() {
        String s = mEditTextEmail.getText().toString();
        if (Patterns.EMAIL_ADDRESS.matcher(s).matches())
        {
            mEditTextEmail.setError(null);
            return true;
        }
        else{
            mEditTextEmail.setError("Invalid e-mail");
            return false;
        }
    }

    private boolean validatePassword() {

        String p1 = mEditTextPassword.getText().toString();

        if (PASSWORD_PATTERN.matcher(p1).matches()) {
            mEditTextPassword.setError(null);
            return true;
        } else {
            mEditTextPassword.setError("Weak Password !");
            return false;
        }
    }


}
