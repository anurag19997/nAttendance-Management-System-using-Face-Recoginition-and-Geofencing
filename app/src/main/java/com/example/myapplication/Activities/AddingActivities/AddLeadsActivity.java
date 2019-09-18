package com.example.myapplication.Activities.AddingActivities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.Activities.MainActivity;
import com.example.myapplication.Models.Lead;
import com.example.myapplication.MyRecyclerViewholder;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

//import com.activeandroid.ActiveAndroid;
//import com.activeandroid.Configuration;

public class AddLeadsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, ZXingScannerView.ResultHandler {
    AlertDialog alertDialog;
    public static final String ns = "";


    //declaring variables
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage storage;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Lead> options;
    FirebaseRecyclerAdapter<Lead, MyRecyclerViewholder> adapter;

    EditText CompanyNameEditText;
    EditText LeadOwnerEditText;
    EditText FirstNameEditText;
    EditText LastNameEditText;
    EditText EmailEditText;
    EditText PhonenoEditText;
    EditText LeadSourseEditText;
    EditText AdressEditText;
    EditText DesignationEditText;


    EditText SocialNetworlEditText;
    EditText PanCardEditText, GSTINEditText, AdharCardEditText;

    TextView pickDateTxtView2, pickTimeTxtView2;
    ImageView pickDateImgView2, pickTimeImgview2;
    ImageView canceledCheckPhotosImageView, shopPhotosImageView;

    ProgressDialog mprogress;

    Spinner NextActivitySpinner;
    String nextActivity;

    Button saveButton;
    Button cancelButton;
    Button clickShopPhotosButton, clickCanceledCheckPhotosButton;

    String no, name;

    private StorageReference mstorage;

    private Uri filePath;

    private Bitmap mImageBitmap;

    private static int CAMERA_REQUEST_CODE = 1;

    private String mCurrentPhotoPath;
    private File image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_leads);
        no = "99123456";
        no = getIntent().getStringExtra("phone");
        SharedPreferences settings = getSharedPreferences("UserNo", MODE_PRIVATE);
        name = settings.getString("name", "name");

        mstorage = FirebaseStorage.getInstance().getReference();

        // Writing data to SharedPreferences
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("phone", no);
        editor.commit();


        //finding views
        CompanyNameEditText = (EditText) findViewById(R.id.company_name_edit_text);
        LeadOwnerEditText = (EditText) findViewById(R.id.lead_owner_edit_text);
        FirstNameEditText = (EditText) findViewById(R.id.first_name_edit_text);
        LastNameEditText = (EditText) findViewById(R.id.last_name_edit_text);
        DesignationEditText = (EditText) findViewById(R.id.designation_edit_text);
        EmailEditText = (EditText) findViewById(R.id.email_edit_text);
        PhonenoEditText = (EditText) findViewById(R.id.phone_no_edit_text);
        LeadSourseEditText = (EditText) findViewById(R.id.lead_source_edit_text);
        NextActivitySpinner = (Spinner) findViewById(R.id.next_activity_spinner);
        SocialNetworlEditText = (EditText) findViewById(R.id.social_network_edit_text);
        AdressEditText = (EditText) findViewById(R.id.adress_edit_text);
        pickDateTxtView2 = (TextView) findViewById(R.id.pick_date_textview2);
        pickTimeTxtView2 = (TextView) findViewById(R.id.pick_time_textview2);
        pickDateImgView2 = (ImageView) findViewById(R.id.date_picker_image2);
        pickTimeImgview2 = (ImageView) findViewById(R.id.time_picker_image2);
        canceledCheckPhotosImageView = (ImageView) findViewById(R.id.canceled_check_photos_imageview);
        shopPhotosImageView = (ImageView) findViewById(R.id.shop_photos_imageview);
        clickShopPhotosButton = (Button) findViewById(R.id.click_shop_photos_button);
        clickCanceledCheckPhotosButton = (Button) findViewById(R.id.cancel_check_photos_button);
        PanCardEditText = (EditText) findViewById(R.id.pan_number_edit_text);
        GSTINEditText = (EditText) findViewById(R.id.gstin_number_edit_text);
        AdharCardEditText = (EditText) findViewById(R.id.adhaar_number_edit_text);
        mprogress = new ProgressDialog(this);

        AdharCardEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                View view = getLayoutInflater().inflate(R.layout.scanner, null);
//                ZXingScannerView zXingScannerView = view.findViewById(R.id.scanner);
//                zXingScannerView.setResultHandler(AddLeadsActivity.this);
//                zXingScannerView.startCamera();
//                alertDialog = new AlertDialog.Builder(AddLeadsActivity.this)
//                        .setView(view).create();
//                alertDialog.show();


                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0); //Barcode Scanner to scan for us
            }
        });


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        saveButton = (Button) findViewById(R.id.save_leads_button);
        getSupportActionBar().setTitle("Add Leads");
        getSupportActionBar().setIcon(R.drawable.baseline_contacts);


        // Spinner click listener
        NextActivitySpinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> nextActivityCategories = new ArrayList<String>();
        nextActivityCategories.add("Calling");
        nextActivityCategories.add("Meeting");
        ArrayAdapter<String> nextActivityDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nextActivityCategories);
        // Drop down layout style - list view with radio button
        nextActivityDataAdapter.setDropDownViewResource(R.layout.spinner_item_textview);
        // attaching data adapter to spinner
        NextActivitySpinner.setAdapter(nextActivityDataAdapter);
        nextActivityDataAdapter.setDropDownViewResource(R.layout.spinner_item_textview);


        //initializing database
        //FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        String user = "Leads firebase" + no + name;
        databaseReference = firebaseDatabase.getReference(user);
        pickDateImgView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalendar = Calendar.getInstance();
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "MM/dd/yy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                        pickDateTxtView2.setText(sdf.format(myCalendar.getTime()));
                    }

                };

                pickDateImgView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(AddLeadsActivity.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
            }
        });

        pickTimeImgview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddLeadsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        pickTimeTxtView2.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        clickCanceledCheckPhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Uri fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, fileUri), CAMERA_REQUEST_CODE);
                chooseImage(1);

            }
        });

        clickShopPhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(2);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences("UserNo", MODE_PRIVATE);

                // Writing data to SharedPreferences
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("phone", no);
                editor.commit();
                post_lead();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }

    private void chooseImage(int i) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("CAMERA!!!", "IOException");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                CAMERA_REQUEST_CODE = i;
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void post_lead() {
        String companyname = CompanyNameEditText.getText().toString();
        String leadowner = LeadOwnerEditText.getText().toString();
        String firstname = FirstNameEditText.getText().toString();
        String lastname = LastNameEditText.getText().toString();
        String designation = DesignationEditText.getText().toString();
        String email = EmailEditText.getText().toString();
        String phone = PhonenoEditText.getText().toString();
        String leadSourse = LeadSourseEditText.getText().toString();
        String socialnetwork = SocialNetworlEditText.getText().toString();
        String address = AdressEditText.getText().toString();
        String spancoStatus = "S";
        String date = pickDateTxtView2.getText().toString();
        String time = pickTimeTxtView2.getText().toString();
        String pan = PanCardEditText.getText().toString();
        String gstin = GSTINEditText.getText().toString();
        String adhaar = AdharCardEditText.getText().toString();
        Lead lead = new Lead(companyname, leadowner, firstname, lastname, designation, email, phone, leadSourse, nextActivity, socialnetwork, address, spancoStatus, pan, gstin, adhaar);
        lead.setDate(date);
        lead.setTime(time);
        databaseReference.push().setValue(lead);
        //adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        nextActivity = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + nextActivity, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
                uploadImage();
                if (CAMERA_REQUEST_CODE == 1) {
                    canceledCheckPhotosImageView.setImageBitmap(mImageBitmap);
                } else {
                    shopPhotosImageView.setImageBitmap(mImageBitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == 0) {
            if(data!=null) {
                String adharInfo = data.getStringExtra("SCAN_RESULT");
                JSONObject jsonObj = null;
                try {
                    jsonObj = XML.toJSONObject(adharInfo);
                    Log.d("Mark89", "onActivityResult: " + jsonObj.toString());
                    Log.d("Mark89", "onActivityResult: " + jsonObj.getJSONObject("PrintLetterBarcodeData"));
                    AdharCardEditText.setText(String.valueOf(jsonObj.getJSONObject("PrintLetterBarcodeData").getString("uid")));
                } catch (JSONException e) {
                    Log.e("JSON exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void handleResult(Result result) {
        String myResult = result.getText();

        JSONObject jsonObj = null;
        try {
            jsonObj = XML.toJSONObject(myResult);
            Log.d("Mark89", "onActivityResult: " + jsonObj.toString());
            Log.d("Mark89", "onActivityResult: " + jsonObj.getJSONObject("PrintLetterBarcodeData"));
            AdharCardEditText.setText(String.valueOf(jsonObj.getJSONObject("PrintLetterBarcodeData").getString("uid")));
            alertDialog.dismiss();
        } catch (JSONException e) {
            Log.e("JSON exception", e.getMessage());
            e.printStackTrace();
        }

    }

    private void uploadImage() {
        Uri uri = Uri.fromFile(image);

        if (uri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            StorageReference ref = mstorage.child("images/" + UUID.randomUUID().toString());
            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(AddLeadsActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddLeadsActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });


        }

//    private static Uri getOutputMediaFileUri(int type) {
//        return Uri.fromFile(getOutputMediaFile(type));
//    }
//
//    private static File getOutputMediaFile(int type) {
//        // To be safe, you should check that the SDCard is mounted
//        // using Environment.getExternalStorageState() before doing this.
//
//        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES), "ISOPRONTO");
//        // This location works best if you want the created images to be shared
//        // between applications and persist after your app has been uninstalled.
//
//        // Create the storage directory if it does not exist
//        if (!mediaStorageDir.exists()) {
//            if (!mediaStorageDir.mkdirs()) {
//                Log.d("MyCameraApp", "failed to create directory");
//                return null;
//            }
//        }
//        // Create a media file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        Log.d("CAMERA!!!", "getOutputMediaFile: " + timeStamp);
//        File mediaFile;
//        if (type == MEDIA_TYPE_IMAGE) {
//            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
//                    "IMG_" + timeStamp + ".jpg");
//        } else if (type == MEDIA_TYPE_VIDEO) {
//            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
//                    "VID_" + timeStamp + ".mp4");
//        } else {
//            return null;
//        }
//        return mediaFile;
//    }

    }
}