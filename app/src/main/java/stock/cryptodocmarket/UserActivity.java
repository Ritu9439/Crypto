package stock.cryptodocmarket;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.w3c.dom.Text;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import stock.cryptodocmarket.ForeignCompany.GraphActivity;
import stock.cryptodocmarket.SessionData.SessionManagement;

import static stock.cryptodocmarket.R.id.v;

public class UserActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    Button sign_out;
    LinearLayout loggedin,loggedout;
    SignInButton signin;
    SessionManagement sessionManagement;
    String name = "";
    TextView useremail;
    ImageView profileimage;
    Uri photoUrl = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        signin = (SignInButton) findViewById(R.id.sign_in_button);
        sign_out= (Button) findViewById(R.id.logout);
        useremail= (TextView) findViewById(R.id.useremail);
        profileimage= (ImageView) findViewById(R.id.profileimg);
        loggedin= (LinearLayout) findViewById(R.id.loggedin);
        loggedout= (LinearLayout) findViewById(R.id.loggedout);
        sessionManagement=new SessionManagement(UserActivity.this);
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                int i = v.getId();

                signOut();
            }
        });
        findViewById(R.id.sign_in_button).setOnClickListener(UserActivity.this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(UserActivity.this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            Toast.makeText(this, "loggedin", Toast.LENGTH_SHORT).show();
            loggedin.setVisibility(View.VISIBLE);
            loggedout.setVisibility(View.GONE);
            // User is logged in
            if (sessionManagement.isLoggedIn()){
                HashMap<String,String> hashMap=sessionManagement.getUserDetails();
                useremail.setText(hashMap.get(SessionManagement.KEY_EMAIL));
                Glide.with(UserActivity.this).load(hashMap.get(SessionManagement.KEY_PHOTOURI)).into(profileimage);
            }
        }
    }
    public void signIn(){

        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(intent, RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN_IN){

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()){

                GoogleSignInAccount account = result.getSignInAccount();

                firebaseAuthWithGoogle(account);
            }

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        Toast.makeText(UserActivity.this,""+credential.getProvider(),Toast.LENGTH_LONG).show();

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            // Name, email address, and profile photo Url
                            name = user.getEmail();
                            //  String email = user.getEmail();
                            photoUrl = user.getPhotoUrl();
useremail.setText(name);
                            Glide.with(UserActivity.this).load(photoUrl).into(profileimage);
                            // The user's ID, unique to the Firebase project. Do NOT use this value to
                            // authenticate with your backend server, if you have one. Use
                            // FirebaseUser.getToken() instead.
                            String uid = user.getUid();
                        }

                        if (task.isSuccessful()){
                            loggedin.setVisibility(View.VISIBLE);
                            loggedout.setVisibility(View.GONE);
                            Log.d(TAG, "name:onComplete:" + name+photoUrl);
                            RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://cryptodoc.in").build();
                            MyInterface myinterface = restAdapter.create(MyInterface.class);
                            myinterface.addUser(name, String.valueOf(photoUrl), new Callback<Response>() {
                                @Override
                                public void success(Response response, Response response2) {
                                    sessionManagement.createLoginSession(name,String.valueOf(photoUrl));

                                    finish();
                                }

                                @Override
                                public void failure(RetrofitError error) {

                                }
                            });

                            /*
                            tvname.setText("Welcome "+name);
*/

                        }else {
                            Toast.makeText(UserActivity.this,"Something went wrong"+name,Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void signOut() {
        // Firebase sign out
        mAuth.signOut();


        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {

                        sessionManagement.logoutUser();
                        Toast.makeText(UserActivity.this, "Logout", Toast.LENGTH_SHORT).show();

                    }
                });
        loggedin.setVisibility(View.GONE);
/*
        tvname.setText(null);
*/
        loggedout.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == android.R.id.home){
            // Do stuff
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();

        if (i==R.id.sign_in_button){

            signIn();

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
