package com.example.twitter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SendTweet extends AppCompatActivity implements View.OnClickListener{
     private EditText edtSendTweet;
     private Button btnSendTweet,btnViewTweets;
     private ListView viewTweetListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tweet);

        setTitle("Send Tweet");

        edtSendTweet=findViewById(R.id.edtSendTweet);
        btnSendTweet=findViewById(R.id.btnSendTweet);
        btnViewTweets=findViewById(R.id.btnViewTweets);
      viewTweetListView=findViewById(R.id.viewTweetListView);

        btnSendTweet.setOnClickListener(this);
        btnViewTweets.setOnClickListener(this);

        edtSendTweet.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
                    onClick(btnSendTweet);
                }
                return false;
            }
        });
    }

    public void rootTapped(View view) {
        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
         inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }

    @Override
    public void onClick(View btnView) {
        switch (btnView.getId()){
            case R.id.btnSendTweet:

                if(edtSendTweet.getText().toString().equals("")){
                    FancyToast.makeText(SendTweet.this, "Please write tweet", Toast.LENGTH_SHORT,
                            FancyToast.WARNING,false).show();
                }else {
                    ParseObject parseObject = new ParseObject("MyTweets");
                    parseObject.put("tweet", edtSendTweet.getText().toString());
                    parseObject.put("user", ParseUser.getCurrentUser().getUsername());
                    final ProgressDialog progressDialog = new ProgressDialog(SendTweet.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SendTweet.this,
                                        ParseUser.getCurrentUser().getUsername() +
                                                "'s tweet: (" + edtSendTweet.getText().toString() + ") is saved!!",
                                        Toast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                            } else {
                                FancyToast.makeText(SendTweet.this, e.getMessage(),
                                        Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }

                break;
            case R.id.btnViewTweets:
               
                break;
        }
    }
}