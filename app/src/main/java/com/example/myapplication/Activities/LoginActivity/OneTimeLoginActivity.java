package com.example.myapplication.Activities.LoginActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Activities.MainActivity;
import com.example.myapplication.Models.User;
import com.example.myapplication.Models.UserLogin;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.LoginClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OneTimeLoginActivity extends AppCompatActivity { //implements LoaderCallbacks<Cursor> {

//    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText NameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    String name, password;
    private boolean isAccepted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_time_login2);
        NameView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        autofillform();
        final SharedPreferences sharedPreferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        isAccepted = sharedPreferences.getBoolean("isAccepted", false);
        if (!isAccepted) {
            View view = getLayoutInflater().inflate(R.layout.activity_privacy_policy, null);
            final WebView web = view.findViewById(R.id.webView);
            Handler mainHandler = new Handler(getApplicationContext().getMainLooper());
            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {
                    web.loadUrl("file:///android_asset/privacy_policy.html");
                } // This is your code
            };
            mainHandler.post(myRunnable);
            final AlertDialog alertDialog = new AlertDialog.Builder(OneTimeLoginActivity.this)
                    .setView(view)
                    .setTitle("Privacy Policy")
                    .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("isAccepted", true);
                            editor.apply();
                        }
                    })
                    .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            OneTimeLoginActivity.this.finish();
                            finishAffinity();
                            System.exit(0);
                        }
                    })
                    .setCancelable(false)
                    .create();


            alertDialog.show();
        }


        autofillform();


        Button signInButton = (Button) findViewById(R.id.email_sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
//        mProgressView = findViewById(R.id.login_progress);
    }

    private void autofillform() {
        SharedPreferences settings = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        name = settings.getString("EmailAddress", "null");
        password = settings.getString("Password", "null");
        if (name.equals("null") || password.equals("null")) {
            return;
        } else {
            NameView.setText(name);
            mPasswordView.setText(password);
        }

    }

    private void attemptLogin() {
        password = mPasswordView.getText().toString();
        name = NameView.getText().toString();
        SharedPreferences shared = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        int ApiKey = (shared.getInt("APIKEY", 1));
        SharedPreferences settings = getSharedPreferences("UserNo", MODE_PRIVATE);

        // Writing data to SharedPreferences
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("name", name);
        editor.putString("password", password);
        editor.putInt("APIKEY", ApiKey);
        editor.commit();

        UserLogin userLogin = new UserLogin(name, password);
        makeNetworkCall(userLogin);
//        startActivity(new Intent(getApplicationContext(), Loginactivityy.class));
    }

    private void makeNetworkCall(final UserLogin userLogin) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://grofers.isoping.com:92/api/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        LoginClient loginClient = retrofit.create(LoginClient.class);
        Call<ResponseBody> call = loginClient.signIn(userLogin);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("MOCK!!!", response.body().byteStream().toString());
                InputStream result = response.body().byteStream();
                InputStreamReader isr = new InputStreamReader(result);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String buffer = null;
                try {
                    buffer = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (buffer != null) {
                    sb.append(buffer);
                    try {
                        buffer = br.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                String json = sb.toString();
                Log.d("OneTimeLoginActivity", "onResponse: " + json);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int valid = jsonObject.getInt("Valid");
                    Log.i("Valid", String.valueOf(valid));
                    Toast.makeText(OneTimeLoginActivity.this, String.valueOf(valid), Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPreferences = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
                    sharedPreferences.edit().putInt("Valid", valid).apply();

                    if (valid > 0) {
                        Toast.makeText(OneTimeLoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(OneTimeLoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else if (valid == 0) {
                        Log.d("Not Valid!!!", userLogin.getUsername() + ", " + userLogin.getPassword());
//                        Toast.makeText(OneTimeLoginActivity.this, "Problem Occurred", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(OneTimeLoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("OneTimeLoginActivity", "onFailure: " + t.toString());
//                Toast.makeText(OneTimeLoginActivity.this, "Problem making Network call", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(OneTimeLoginActivity.this, MainActivity.class);
//                startActivity(intent);

            }
        });
    }
//        if (password.equals("Isourse9876"))
//            startActivity(new Intent(getApplicationContext(), Loginactivityy.class));
//    }


    public void createNewUser(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
    }
}


