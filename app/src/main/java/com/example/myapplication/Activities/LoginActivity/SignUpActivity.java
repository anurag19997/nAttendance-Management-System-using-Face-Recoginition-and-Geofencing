package com.example.myapplication.Activities.LoginActivity;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Models.Registration;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.SignUpClient;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "SignUpActivity";
    EditText et_employee_id, et_name, et_contact_number, et_email_address, et_password, et_dob;
    Button btn_sign_up;

    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        et_contact_number = findViewById(R.id.et_contact_number);
        et_email_address = findViewById(R.id.et_email_address);
        et_employee_id = findViewById(R.id.et_employee_id);
        et_name = findViewById(R.id.et_name);
        et_password = findViewById(R.id.et_password);
        btn_sign_up = findViewById(R.id.btn_sign_up);
        et_dob = findViewById(R.id.et_dob_address);
        getSupportActionBar().hide();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SignUpActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_contact_number.getText().length() == 10 &&
                        et_email_address.getText().length() != 0 && et_employee_id.getText().length() != 0 &&
                        et_name.getText().length() != 0 && et_password.getText().length() != 0) {
                    //sendEmail();
                    Long tsLong = System.currentTimeMillis() / 1000;
                    String ts = tsLong.toString();

                    Registration registration = new Registration(
                            et_employee_id.getText().toString(),
                            et_name.getText().toString(),
                            et_contact_number.getText().toString(),
                            ts,
                            et_email_address.getText().toString(),
                            et_password.getText().toString(),
                            et_dob.getText().toString());

                    //new code
                    registration.setEmployeeType("LM");


                    SharedPreferences sharedPreferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("EmployeeCode", et_employee_id.getText().toString());
                    editor.putString("Name", et_name.getText().toString());
                    editor.putString("ContactNumber", et_contact_number.getText().toString());
                    editor.putString("EmailAddress", et_email_address.getText().toString());
                    editor.putString("Password", et_password.getText().toString());
                    editor.putString("EmployeeType", "LM");
                    editor.apply();



//                    //abhishek response new code
                    Gson gson = new Gson();
                    String json = gson.toJson(registration);
                    Log.d(TAG, json);

//                    startActivity(new Intent(getApplicationContext(), Loginactivityy.class));

                    try {
                        makeNetworkCall(registration);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.d(TAG, "onClick: ");
                    String id = "my_channel_01";
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(SignUpActivity.this, id)
                            .setSmallIcon(R.drawable.grofers_icon)
                            .setContentTitle("SignUp Details")
                            .setContentText("Your username and password to sign in to GSP app is " + et_employee_id.getText().toString() + " , " + et_password.getText().toString() + " respectively.")
                            .setPriority(NotificationCompat.PRIORITY_HIGH);
                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(0, builder.build());
                } else {
                    Toast.makeText(SignUpActivity.this, "Please fill the given fields correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_dob.setText(sdf.format(myCalendar.getTime()));
    }

    protected void sendEmail() {
        Log.i("Send email", "");
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, et_email_address.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "SRE Registration");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Your username and password to sign in to GSP app is " + et_employee_id.getText().toString() + " , " + et_password.getText().toString() + " respectively.");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SignUpActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void makeNetworkCall(final Registration registration) throws IOException {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://grofers.isoping.com:92/api/MobileAttendenceSignIn/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        SignUpClient signUpClient = retrofit.create(SignUpClient.class);
        Call<ResponseBody> call = signUpClient.createAccount(registration);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response!=null) {
//                    Log.d("MOCK!!!", response.body().byteStream().toString());
                    Log.d("MOCK!!!", response.toString());
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
                    Log.d(TAG, "onResponse: " + json);
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        int inserted = jsonObject.getInt("InsertId");
                        int apiKey = jsonObject.getInt("APIKEY");
                        SharedPreferences sharedPreferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("APIKEY", apiKey);
                        editor.apply();
                        Log.d(TAG, "onResponse: " + sharedPreferences.getInt("APIKEY", 0));
                        if (inserted > 0) {
                            Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignUpActivity.this, OneTimeLoginActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Problem Occurred", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "No Response From Server", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());

                Toast.makeText(SignUpActivity.this, "Problem making Network call", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

