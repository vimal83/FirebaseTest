package com.example.vimal.firebasetest;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private static final int RC_SIGN_IN = 0;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build(),
            new AuthUI.IdpConfig.FacebookBuilder().build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            Log.d("Success","Auth already signed in");
            Log.d("Success email",mAuth.getCurrentUser().getEmail());

            AuthUI.getInstance()
                    .signOut(this).addOnCompleteListener(new OnCompleteListener<Void>(){
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d("Signed out","Sign out");
                    finish();
                }
            });
        }
        else
        {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        }

       /* Button btnLogout = (Button) findViewById(R.id.btnSignOut);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance().signOut(this);
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.d("Success","Auth log successfully");
                Log.d("Success email",mAuth.getCurrentUser().getEmail());

                String facebookUserId = "";
                // find the Facebook profile and get the user's id
                for(UserInfo profile : user.getProviderData()) {
                    // check if the provider id matches "facebook.com"
                    if(FacebookAuthProvider.PROVIDER_ID.equals(profile.getProviderId())) {
                        facebookUserId = profile.getUid();
                        // construct the URL to the profile picture, with a custom height
// alternatively, use '?type=small|medium|large' instead of ?height=
                        String photoUrl = "https://graph.facebook.com/" + facebookUserId + "/picture?height=500&width=500";

                       ImageView profilePicture = (ImageView) findViewById(R.id.imgProfile);
// (optional) use Picasso to download and show to image
                        Picasso.with(this).load(photoUrl).into(profilePicture);
                    }
                }


                // ...
            } else {
                Log.d("failed","Auth log failed");
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSignOut){
            AuthUI.getInstance()
                    .signOut(this).addOnCompleteListener(new OnCompleteListener<Void>(){
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("Signed out","Sign out");
                            finish();
                        }
                     });
        }
    }

    public void signOut(View view) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("Signed out","Sign out");
                        finish();
                        Toast.makeText(Login.this, "signed out succesfully ... ", Toast.LENGTH_SHORT).show();
                    }
                });
    }

  /*  private void updateUi() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            btnSignIn.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.GONE);
            txtEmail.setVisibility(View.GONE);
            txtUser.setVisibility(View.GONE);
            imgProfile.setImageBitmap(null);
        } else {
            btnSignIn.setVisibility(View.GONE);
            btnSignOut.setVisibility(View.VISIBLE);
            txtEmail.setVisibility(View.VISIBLE);
            txtUser.setVisibility(View.VISIBLE);

            txtUser.setText(user.getDisplayName());
            txtEmail.setText(user.getEmail());
            Picasso.with(this).load(user.getPhotoUrl()).into(imgProfile);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == Activity.RESULT_OK) {
                Log.d(this.getClass().getName(), "This user signed in with " + response.getProviderType());
                updateUi();
            } else {
                updateUi();
            }
        }
    }

    public void deleteAccount(View view) {
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    public void signIn(View view) {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        //.setIsSmartLockEnabled(false,true)
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers)
                        //.setTheme(R.style.AppTheme)
                        .setTosUrl("https://superapp.example.com/terms-of-service.html")
                        .setPrivacyPolicyUrl("https://superapp.example.com/privacy-policy.html")
                        .setLogo(R.drawable.logo)
                        .build(),
                RC_SIGN_IN);
    }*/
}