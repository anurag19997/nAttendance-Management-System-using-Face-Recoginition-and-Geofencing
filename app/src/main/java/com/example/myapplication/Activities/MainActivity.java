package com.example.myapplication.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Activities.AddingActivities.AddLeadsActivity;
import com.example.myapplication.Activities.AddingActivities.AddTasksActivity;
import com.example.myapplication.Activities.LoginActivity.OneTimeLoginActivity;
import com.example.myapplication.Activities.LoginActivity.SignUpActivity;
import com.example.myapplication.Models.APIKEY;
import com.example.myapplication.Models.LocationModel;
import com.example.myapplication.Models.User;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.LocationClient;
import com.example.myapplication.Retrofit.PunchInClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final int MY_PERMISSION_REQUEST_READ_FINE_LOCATION = 100;//implements MyLocationListner {
    float[] results;
    Float startLat, startLong;

    private Location location;
    boolean isStart;
    Chronometer chronometer;
    private long timeWhenStopped = 0;
    Toolbar toolbar;
    BottomNavigationView navigationView2;
    DrawerLayout drawer;
    TextView dayInTxtView, dayOutTxtView;
    SwitchCompat day_in_day_out_switch;
    NestedScrollView scrollView;
    SharedPreferences settings;

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    // integer for permissions results request
    private GoogleApiClient googleApiClient;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private LocationRequest locationRequest;
    private static final long UPDATE_INTERVAL = 5000, FASTEST_INTERVAL = 5000; // = 5 seconds
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String TAG = "PERM!!!";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    boolean isChecked;


    LocationManager locationManager;
    LocationListener locationListener;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted = false;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String no, name;
    String dayIn, dayOut;
    Boolean userCheckedIn;

    double currentLat;
    double currentLng;

    int yes;
    int withinCircumference;

    List<LocationModel> locationModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_main2);
        askPermissions();
        isChecked = false;
        yes = 0;
        withinCircumference = 0;
        no = "99123456";
        View view = findViewById(R.id.content_scrolling);
        permissionsToRequest = new ArrayList<>();
        no = getIntent().getStringExtra("phone");
        initViews();
        setSupportActionBar(toolbar);
        //  getSupportActionBar().setDisplayShowTitleEnabled(false);
        settings = getSharedPreferences("UserNo", MODE_PRIVATE);
        name = settings.getString("name", "name");
        no = settings.getString("phone", "901");
        userCheckedIn = settings.getBoolean("userCheckedIn", false);
        day_in_day_out_switch.setChecked(userCheckedIn);
        dayInTxtView = (TextView) findViewById(R.id.last_day_in_text_view);
        dayOutTxtView = (TextView) findViewById(R.id.last_day_out_text_view);
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        displayDayInOutTime();


        locationModelList = new ArrayList<LocationModel>();


        SharedPreferences shared = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        int ApiKey = (shared.getInt("APIKEY", 1));
        APIKEY apikey = new APIKEY(ApiKey);
        apikey.setKey(1);
        makeNetworkCallForLocation(apikey);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        getLocationPermission();
        getDeviceLocation();

        navigationView2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_add_task:

                    case R.id.navigation_add_leads:
                        Intent i = new Intent(getApplicationContext(), AddLeadsActivity.class).putExtra("phone", no);
                        startActivity(i);
                        return true;
                    case R.id.navigation_notifications:

                }
                return false;
            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);


        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometerChanged) {
                chronometer = chronometerChanged;

            }
        });
        results = new float[10];
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        startLat = settings.getFloat("startLat", 0);
        startLong = settings.getFloat("startLong", 0);
        permissionsToRequest = permissionsToRequest(permissions);

        day_in_day_out_switch.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
//                showDialogBoxAlert();
                startLocationUpdates();

                SharedPreferences shared = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
                int ApiKey = (shared.getInt("APIKEY", 1));
                APIKEY apikey = new APIKEY(ApiKey);
                apikey.setKey(1);
                locationModelList = makeNetworkCallForLocation(apikey);



                if (day_in_day_out_switch.isChecked()) {


                    Location userlocation = new Location("My_Current_Location");
                    userlocation.setLatitude(currentLat);
                    userlocation.setLongitude(currentLng);

                    //new code
                    for (LocationModel locationModel : locationModelList) {
                        Location startPoint = new Location("Received Location");
                        startPoint.setLatitude(locationModel.getLatitude());
                        startPoint.setLongitude(locationModel.getLongitude());

                        double distance = startPoint.distanceTo(userlocation);
                        float [] dist = new float[1];
//                        double distance2 =
                        Location.distanceBetween(locationModel.getLatitude(), locationModel.getLongitude(),
                                userlocation.getLatitude(), userlocation.getLongitude(), dist);
                        Log.d("Distance1", String.valueOf(distance));
                        Log.d("Distance2", String.valueOf(dist[0] * 0.000621371192f));
                        distance = dist[0] * 0.000621371192f;
                        Toast.makeText(getApplicationContext(), String.valueOf(distance), Toast.LENGTH_LONG).show();

                        // the subject is within range

                        if (distance < 6000) {

                            withinCircumference = 1;

                        // subject is out of range
                        } else {
                            withinCircumference = 0;
//                            day_in_day_out_switch.setChecked(false);
                        }
                    }

                    if (withinCircumference == 1) {
//                        makeNetworkCallForPunch(apikey);
                        Toast.makeText(getApplicationContext(), "You are in circumference", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "You are out of circumference", Toast.LENGTH_LONG).show();
                    }


                    postDayInTime();
                    SharedPreferences settings = getSharedPreferences("UserNo", MODE_PRIVATE);
                    // Writing data to SharedPreferences
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("userCheckedIn", true);
                    editor.commit();
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    timeWhenStopped = 0;
                    Snackbar.make(view, "Your Day has started", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                    chronometer.start();

                } else {
//                    makeNetworkCallForPunch(apikey);
                    postDayOutTime();
                    SharedPreferences settings = getSharedPreferences("UserNo", MODE_PRIVATE);

                    // Writing data to SharedPreferences
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("userCheckedIn", false);
                    editor.commit();
                    Snackbar.make(view, "Your Day has ended", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
                    chronometer.stop();
                }
            }


//            }
        });


//                    }
        // we build google api client
        googleApiClient = new GoogleApiClient.Builder(MainActivity.this).
                addApi(LocationServices.API).
                addConnectionCallbacks(MainActivity.this).
                addOnConnectionFailedListener(MainActivity.this).build();


    }

    private void makeNetworkCallForPunch(APIKEY apikey) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://grofers.isoping.com:92/api/MobileAttendenceSignIn/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        final PunchInClient punchInClient = retrofit.create(PunchInClient.class);
        APIKEY apikey1 = new APIKEY(1);
        Call<ResponseBody> call = punchInClient.getLocationList(apikey1);
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
                Log.d("MainActivity!!!", "onResponse: " + json);
                Log.d(TAG, "onResponse: " + json);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int inserted = jsonObject.getInt("Result");
                    if (inserted > 0) {
                        Log.d("Punch In !!!", String.valueOf(inserted));
                    } else {
                        Toast.makeText(MainActivity.this, "Attendance not Marked!!!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("OneTimeLoginActivity", "onFailure: ");
                Toast.makeText(MainActivity.this, "Problem making Network call", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private List<LocationModel> makeNetworkCallForLocation(APIKEY apikey) {


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://grofers.isoping.com:92/api/MobileAttendenceSignIn/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        final LocationClient locationClient = retrofit.create(LocationClient.class);
        APIKEY apikey1 = new APIKEY(1);
//        Gson gson = new Gson();
//        String apikeyJson = gson.toJson(apikey);
        Call<ResponseBody> call = locationClient.getLocationList(apikey1);
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
                    JSONArray jsonArray = new JSONArray(json);
                    JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                    Gson gson = new Gson();
                    LocationModel locationModel = (LocationModel) gson.fromJson(jsonObject.toString(), LocationModel.class);
                    Log.d("OneTimeLoginActivity", "onResponse: " + locationModel.getLatitude());
                    locationModelList.add(locationModel);
                    jsonObject = (JSONObject) jsonArray.get(1);
                    locationModel = (LocationModel) gson.fromJson(jsonObject.toString(), LocationModel.class);
                    locationModelList.add(locationModel);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("OneTimeLoginActivity", "onFailure: ");
                Toast.makeText(MainActivity.this, "Problem making Network call", Toast.LENGTH_SHORT).show();
            }
        });
        Location startPoint = new Location("Dwarka_sector_12");
        startPoint.setLatitude(28.592140);
        startPoint.setLongitude(77.046051);

        Location endPoint = new Location("My_Current_Location");
        endPoint.setLatitude(currentLat);
        endPoint.setLongitude(currentLng);
        List<Location> locationList = new ArrayList<Location>();
        locationList.add(startPoint);
        locationList.add(endPoint);
        return locationModelList;
    }


    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        //new code added
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSION_REQUEST_READ_FINE_LOCATION);

                // MY_PERMISSION_REQUEST_READ_FINE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                getDeviceLocation();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{(Manifest.permission.ACCESS_FINE_LOCATION)},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    private void displayDayInOutTime() {
        try {
            databaseReference = firebaseDatabase.getReference("Leads firebase" + no + name).child("User");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                //    databaseReference = firebaseDatabase.getReference("Leads firebase" + no).child("User");
                    User user = dataSnapshot.getValue(User.class);
                    if ((user != null) && (user.getLastDayIn() != null)) {
                        dayInTxtView.setText("Last Day In: " + user.getLastDayIn());
                    }
                    if ((user != null) && (user.getLastDayOut() != null)) {
                        dayOutTxtView.setText("Last Day Out: " + user.getLastDayOut());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
//            databaseReference = firebaseDatabase.getReference("Leads firebase" + no);


            //old code
//            User user = new User(no, "12:35", "6:20");
//            databaseReference.push().setValue(user);
        }

    }

    private void postDayInTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        final String currentDateandTime = sdf.format(new Date());
        try {
            databaseReference = firebaseDatabase.getReference("Leads firebase" + no + name).child("User");
            databaseReference.child("lastDayIn").setValue(currentDateandTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void postDayOutTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        final String currentDateandTime = sdf.format(new Date());
        try {
            databaseReference = firebaseDatabase.getReference("Leads firebase" + no + name).child("User");
            databaseReference.child("lastDayOut").setValue(currentDateandTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @SuppressLint("MissingPermission")
//    private void findCurrentLocation() {
//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                10);
//        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
//    }


    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView2 = (BottomNavigationView) findViewById(R.id.navigation);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        day_in_day_out_switch = (SwitchCompat) findViewById(R.id.day_in_day_out_switch);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(getApplicationContext(), location.getLongitude() + " " + location.getLatitude(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

//        requestForPermission();

    }

//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//
//        mLocationPermissionsGranted = false;
//        switch (requestCode) {
//            case LOCATION_PERMISSION_REQUEST_CODE: {
//                if (grantResults.length > 0) {
//                    for (int i = 0; i < grantResults.length; i++) {
//                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
//                            mLocationPermissionsGranted = false;
//                            Log.d(TAG, "onRequestPermissionsResult: permission failed22" + permissions[i]);
//                            return;
//                        }
//                    }
//                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
//                    mLocationPermissionsGranted = true;
//                    //initialize our map
//                    getDeviceLocation();
//                }
//            }
//            default:
//                break;
//            case 1: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Log.d("PERM!!!", String.valueOf(grantResults[0]));
//
//                } else {
//
//                    Toast.makeText(getApplicationContext(), "Permission not granted", Toast.LENGTH_LONG).show();
//                    requestPermissions(new String[]{
//                            Manifest.permission.ACCESS_COARSE_LOCATION
//                    }, 1);
//                }
//                return;
//            }
//        }
//    }


    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            Toast.makeText(getApplicationContext(), String.valueOf(currentLocation.getLatitude()), Toast.LENGTH_LONG).show();

                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MainActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.navigation_add_task:

            case R.id.navigation_add_leads:
                if (!day_in_day_out_switch.isChecked()) {
                    toast();
                } else {
                    Intent i = new Intent(getApplicationContext(), CallingActivity.class).putExtra("phone", no);
                    startActivity(i);
                    return true;
                }
            case R.id.navigation_add_event:
                Toast.makeText(MainActivity.this, "Under Development", Toast.LENGTH_SHORT);
                return true;
//                mTextMessage.setText(R.string.title_notifications);
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void GetCalls(View view) {
        if (!day_in_day_out_switch.isChecked()) {
            toast();
        } else {
            Intent i = new Intent(getApplicationContext(), CallingActivity.class).putExtra("phone", no);
            startActivity(i);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        //noinspection SimplifiableIfStatement
        switch (menuItem.getItemId()) {
            case R.id.navigation_add_task:
                if (!day_in_day_out_switch.isChecked()) {
                    toast();
                } else {
                    Intent i = new Intent(getApplicationContext(), AddTasksActivity.class);
                    startActivity(i);
                    return true;
                }
            case R.id.navigation_add_leads:
                if (!day_in_day_out_switch.isChecked()) {
                    toast();
                } else {
                    Intent i = new Intent(getApplicationContext(), AddLeadsActivity.class);
                    startActivity(i);
                    return true;
                }
            case R.id.navigation_add_event:
                Toast.makeText(MainActivity.this, "Under Development", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.navigation_add_issues:
                Toast.makeText(MainActivity.this, "Under Development", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.navigation_logout:

//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        public void onComplete(@NonNull Task<Void> task) {
//                            // user is now signed out
//                            startActivity(new Intent(MyActivity.this, SignInActivity.class));
//                            finish();
//                        }
//                    });;
//                return true;
//                mTextMessage.setText(R.string.title_notifications);
//                return true;
        }
        return false;

    }

    public void GetMeetings(View view) {
        if (!day_in_day_out_switch.isChecked()) {
            toast();
        } else {
            startActivity(new Intent(getApplicationContext(), MeetingsActivity.class).putExtra("phone", no));
        }

    }

    public void displayAllLeads(View view) {
        if (!day_in_day_out_switch.isChecked()) {
            toast();
        } else {
            Intent i = new Intent(MainActivity.this, CallingActivity.class);
            i.putExtra("showPendingCalls", false);
            i.putExtra("showAll", true);
            startActivity(i);
        }
    }

    public void GetPendingCalls(View view) {
        if (!day_in_day_out_switch.isChecked()) {
            toast();
        } else {
            Intent i = new Intent(getApplicationContext(), CallingActivity.class).putExtra("phone", no);
            i.putExtra("showPendingCalls", true);
            startActivity(i);
        }
    }

//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (googleApiClient != null) {
//            googleApiClient.connect();
//        }
//
////        Log.d("PERM!!!", "isServicesOK: checking google services version");
////
////        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
////
////        if (available == ConnectionResult.SUCCESS) {
////            //everything is fine and the user can make map requests
////            Log.d("PERM!!!", "isServicesOK: Google Play Services is working");
////        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
////            //an error occured but we can resolve it
////            Log.d("PERM!!!", "isServicesOK: an error occured but we can fix it");
////            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
////            dialog.show();
////        } else {
////            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
////        }
//    }

    public void AddTask(MenuItem item) {
        if (!day_in_day_out_switch.isChecked()) {
            toast();
        } else {
            startActivity(new Intent(getApplicationContext(), AddTasksActivity.class));
        }
    }

    public void GoToTask(View view) {
        if (!day_in_day_out_switch.isChecked()) {
            toast();
        } else {
            startActivity(new Intent(getApplicationContext(), AddTasksActivity.class));
        }
    }

    public void toast() {
        Toast.makeText(MainActivity.this, "Please mark your attendance", Toast.LENGTH_LONG).show();
    }

    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wantedPermissions) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!checkPlayServices()) {

        }
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        // stop location updates
//        if (googleApiClient != null && googleApiClient.isConnected()) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, new com.google.android.gms.location.LocationListener() {
//                @Override
//                public void onLocationChanged(Location location) {
//                }
//            });
//            googleApiClient.disconnect();
//        }
//    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
            } else {
                finish();
            }

            return false;
        }

        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // Permissions ok, we get last location
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (location != null) {

        }
    }

    private void startLocationUpdates() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        Log.d(TAG, "startLocationUpdates: ");
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You need to enable permissions to display location !", Toast.LENGTH_SHORT).show();
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, new com.google.android.gms.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
//                startLat = (float) location.getLatitude();
//                startLong = (float) location.getLongitude();
//                Log.d(TAG, "onLocationChanged: "+location.getLatitude());
//                Geocoder geocoder;
//                List<Address> addresses = null;
//                geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
//                databaseReference = firebaseDatabase.getReference("Leads firebase" + no + name).child("User");
//                databaseReference.child("latitude").setValue(startLat);
//                databaseReference.child("longitude").setValue(startLong);

//                try {
//                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                String city = addresses.get(0).getLocality();
//                String state = addresses.get(0).getAdminArea();
//                String country = addresses.get(0).getCountryName();
//                String postalCode = addresses.get(0).getPostalCode();
//                String knownName = addresses.get(0).getFeatureName();
//                Log.d(TAG, "onLocationChanged: "+address+","+city+","+state+","+country+","+postalCode+","+knownName);
            }
        });

        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Location mGetedLocation = task.getResult();
                            double currentLat = mGetedLocation.getLatitude();
                            double currentLng = mGetedLocation.getLongitude();
//                            databaseReference = firebaseDatabase.getReference("Leads firebase" + no + name).child("User");
//                            databaseReference.child("latitude").setValue(String.valueOf(currentLat));
//                            databaseReference.child("longitude").setValue(String.valueOf(currentLng));
                            Toast.makeText(getApplicationContext(), String.valueOf(currentLat), Toast.LENGTH_LONG).show();
                            Log.e("LOC!!!!", String.valueOf(currentLat));
                            //updateUI();
                        } else {
                            Log.e(TAG, "no location detected");
                            Log.w(TAG, "getLastLocation:exception" + task.getException());
                        }
                    }
                });
        mFusedLocationClient.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLat = location.getLatitude();
                    currentLng = location.getLongitude();
                    Log.e("LOC!!!!", String.valueOf(currentLat));
                    Toast.makeText(getApplicationContext(), String.valueOf(currentLat), Toast.LENGTH_LONG).show();
                } else {
                    Log.e("LOC!!!!", "Last Location is null");
//                    Toast.makeText(getApplicationContext(), "Last Location is null", Toast.LENGTH_LONG).show();
                }
            }
        });
//        LocationRequest locationRequest;
////        LocationCallback locationCallback;
//        locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//        locationRequest.setInterval(20 * 1000);
//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                if (locationResult == null) {
//                    return;
//                }
//                for (Location location : locationResult.getLocations()) {
//                    if (location != null) {
//                        double wayLatitude = location.getLatitude();
//                        double wayLongitude = location.getLongitude();
//                        Toast.makeText(getApplicationContext(), String.valueOf(wayLatitude), Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//        };


    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {

//            android.location.Location.distanceBetween(startLat,startLong,location.getLatitude(),location.getLongitude(),results);
//            tv_distance.setText(String.valueOf(distance + results[0]/1000));
            if (isChecked) {
                Log.d(TAG, "onLocationChanged: " + location.getLatitude() + "," + location.getLongitude());
                isChecked = false;
            }

        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perm : permissionsToRequest) {
                    if (!hasPermission(perm)) {
                        permissionsRejected.add(perm);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            new AlertDialog.Builder(MainActivity.this).
                                    setMessage("These permissions are mandatory to get your location. You need to allow them.").
                                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.
                                                        toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    }).setNegativeButton("Cancel", null).create().show();

                            return;
                        }
                    }
                } else {
                    if (googleApiClient != null) {
                        googleApiClient.connect();
                    }
                }

                break;
            case MY_PERMISSION_REQUEST_READ_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }

    public void askPermissions() {
        int perm = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
        if (perm == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA}
                    , 101);
        }
    }

//    public void showDialogBoxAlert(View view) {
//        showDialogBoxAlert();
//
//    }
//
//
//

}