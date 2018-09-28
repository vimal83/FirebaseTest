package com.example.vimal.firebasetest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView txtView,txtEmail,txtBirthday,txtFriendCount;
    LoginButton fblog;
    CallbackManager callbackManager;
    ImageView imgAvatar;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        Initializeontrols();
        LoginWithFB();

    }

    private void Initializeontrols() {
        callbackManager = CallbackManager.Factory.create();
        txtView = (TextView) findViewById(R.id.txtView);
        fblog = (LoginButton) findViewById(R.id.login_button);
        fblog.setReadPermissions(Arrays.asList("public_profile","email","user_birthday","user_friends"));

        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtBirthday = (TextView) findViewById(R.id.txtBirthday);
        txtFriendCount = (TextView) findViewById(R.id.txtFriendCount);
    }

    private void LoginWithFB() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mDialog=new ProgressDialog(MainActivity.this);
                mDialog.setMessage("Retrieving data...");
                mDialog.show();

                txtView.setText("Login successfully\n"+ loginResult.getAccessToken());

                           }

            @Override
            public void onCancel() {
                txtView.setText("Login cancel\n");
            }

            @Override
            public void onError(FacebookException error) {
                txtView.setText("Login error\n"+ error.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
