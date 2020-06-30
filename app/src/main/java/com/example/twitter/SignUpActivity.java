package com.example.twitter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtSignUpEmail,edtSignUpUsername,edtSignUpPassword;
    private Button btnSignUP,btnLogIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        setTitle("Sign Up");

        edtSignUpEmail=findViewById(R.id.edtSignUpEmail);
        edtSignUpUsername=findViewById(R.id.edtSignUpUsername);
        edtSignUpPassword=findViewById(R.id.edtSignUpPassword);
        btnSignUP=findViewById(R.id.btnSignUP);
        btnLogIn=findViewById(R.id.btnLogIn);

        btnSignUP.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);
        edtSignUpPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
                    onClick(btnSignUP);
                }
                return false;
            }
        });
        if(ParseUser.getCurrentUser()!=null){
           transitionToSocialMediaActivity();
        }

    }

    private void transitionToSocialMediaActivity() {
        Intent intent=new Intent(SignUpActivity.this, UserActivity.class);
        startActivity(intent);
        finish();
    }

    public void layoutIsTapped(View view) {
        try{
            InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View btnView) {

     switch (btnView.getId()){

            case R.id.btnSignUP:
                if(edtSignUpEmail.getText().toString().equals("")||
                edtSignUpUsername.getText().toString().equals("")||
                edtSignUpPassword.getText().toString().equals("")){
                    FancyToast.makeText(SignUpActivity.this,
                            " Fill up all details",
                            Toast.LENGTH_SHORT,FancyToast.INFO,false).show();
                }else {
                    ParseUser parseUser = new ParseUser();
                    parseUser.setEmail(edtSignUpEmail.getText().toString());
                    parseUser.setUsername(edtSignUpUsername.getText().toString());
                    parseUser.setPassword(edtSignUpPassword.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("SigningUp " + edtSignUpUsername.getText().toString());
                    progressDialog.show();

                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SignUpActivity.this,
                                        edtSignUpUsername.getText().toString() + " is Signed up",
                                        Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                transitionToSocialMediaActivity();
                            } else {
                                FancyToast.makeText(SignUpActivity.this,
                                        "There is an error: " + e.getMessage(),
                                        Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
                break;
         case R.id.btnLogIn:
             Intent intent=new Intent(SignUpActivity.this,LogInActivity.class);
             startActivity(intent);
        }
    }
}