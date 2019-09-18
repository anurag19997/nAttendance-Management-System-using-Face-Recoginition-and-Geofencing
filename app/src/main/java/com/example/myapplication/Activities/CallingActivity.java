package com.example.myapplication.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.Activities.AddingActivities.AddLeadsActivity;
import com.example.myapplication.Listners.ItemClickListner;
import com.example.myapplication.Listners.RecyclerTouchListener;
import com.example.myapplication.Models.Lead;
import com.example.myapplication.MyRecyclerViewholder;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//import com.activeandroid.ActiveAndroid;

public class CallingActivity extends AppCompatActivity implements ItemClickListner, AdapterView.OnItemSelectedListener {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Lead> options;
    FirebaseRecyclerAdapter<Lead, MyRecyclerViewholder> adapter;
    Lead selectedlead;
    String selectedKey;
    RecyclerView recyclerView;
    ItemClickListner itemClickListner;
    Spinner NextActivitySpinner;
    Spinner spancoStatusSpinner;
    ImageView dateTimePicker;
    ImageView timePicker;
    TextView dateTimeText;
    TextView pickTimeText;
    EditText addressEditText, placeEditText, remarksEditText;
    RadioButton notIntrestedCheckBox, askedForPICheckBox, callAgainCheckBox;
    List<String> nextActivityCategories;
    List<String> spancoStatusCategories;
    ArrayAdapter<String> nextActivityDataAdapter;
    ArrayAdapter<String> spancoStatusDataAdapter;
    Button updateBtn, cancelBtn;
    String selectedActivity;
    Boolean showAll, showPendingCalls;
    Boolean datepassed;

    String no, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showAll = false;
        showAll = getIntent().getBooleanExtra("showAll", false);
        showPendingCalls = getIntent().getBooleanExtra("showPendingCalls", false);
        SharedPreferences settings = getSharedPreferences("UserNo", MODE_PRIVATE);
        no = settings.getString("phone", "9013356899");
        name = settings.getString("name", "name");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Leads firebase" + no + name);
        setContentView(R.layout.activity_calling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (showAll) {
            getSupportActionBar().setTitle("All Leads");
        } else {
            getSupportActionBar().setTitle("Leads for Today");
            Toast.makeText(getApplicationContext(), showPendingCalls.toString(), Toast.LENGTH_LONG).show();
        }
        recyclerView = (RecyclerView) findViewById(R.id.calling_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        display_leads();


    }

    private void display_leads() {

        options = new FirebaseRecyclerOptions.Builder<Lead>().
                setQuery(databaseReference, Lead.class).
                build();
        adapter = new FirebaseRecyclerAdapter<Lead, MyRecyclerViewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyRecyclerViewholder holder, final int position, @NonNull final Lead model) {
                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
                String formattedDate = df.format(c);
                datepassed = false;
                if (model.getDate() != null) {
                    try {
                        Date storedDate = df.parse(model.getDate());
                        Date currentDate = new SimpleDateFormat("MM/dd/yy").parse(formattedDate);
                        if (storedDate.before(currentDate)) {
                            datepassed = true;
                            Log.d("FIRE!!! datepassed=true", model.getDate());
                        } else datepassed = false;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (showAll) {
                    holder.callerNameTextView.setText(model.getFirstName());
                    holder.callingReminderTextview.setText(model.getCompanyName());
                    holder.userDetailsTextview.setText(model.getDesignation());
                    holder.updateLeadsImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectedlead = model;
                            selectedKey = getSnapshots().getSnapshot(position).getKey();
                            showForm();
                        }
                    });
                    holder.callerNameTextView.getRootView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String phonenumber = model.getPhoneNo();

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phonenumber));
                            startActivity(intent);

                            selectedKey = getSnapshots().getSnapshot(position).getKey();
                        }
                    });
                } else if ((formattedDate.equals(model.getDate()) && model.getNextActivity().equals("Calling")) && !showAll) {
                    Log.d("FIRE!!!", "today leads called " + datepassed + model.getDate());
                    holder.callerNameTextView.setText(model.getFirstName());
                    holder.callingReminderTextview.setText(model.getCompanyName());
                    holder.userDetailsTextview.setText(model.getDesignation());
                    holder.updateLeadsImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectedlead = model;
                            selectedKey = getSnapshots().getSnapshot(position).getKey();
                            showForm();
                        }
                    });
                    holder.callerNameTextView.getRootView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String phonenumber = model.getPhoneNo();

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phonenumber));
                            startActivity(intent);

                            selectedKey = getSnapshots().getSnapshot(position).getKey();
                        }
                    });
                } else if(showPendingCalls && datepassed) {
                    getSupportActionBar().setTitle("Pending Leads");
                    Log.d("FIRE!!!", "pending leads called " + showPendingCalls + model.getDate());
                    holder.callerNameTextView.setText(model.getFirstName());
                    holder.callingReminderTextview.setText(model.getCompanyName());
                    holder.userDetailsTextview.setText(model.getDesignation());
                    holder.updateLeadsImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectedlead = model;
                            selectedKey = getSnapshots().getSnapshot(position).getKey();
                            showForm();
                        }
                    });
                    holder.callerNameTextView.getRootView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String phonenumber = model.getPhoneNo();

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phonenumber));
                            startActivity(intent);

                            selectedKey = getSnapshots().getSnapshot(position).getKey();
                        }
                    });
                }
                else {
                    holder.callerNameTextView.getRootView().getLayoutParams().height = 0;
                }

            }

            @NonNull
            @Override
            public MyRecyclerViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.from(getBaseContext()).inflate(R.layout.list_row, viewGroup, false);
                return new MyRecyclerViewholder(itemView);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ItemClickListner() {
            @Override
            public void onCLick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Single Click on position :" + position,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Single Click on position :",
                        Toast.LENGTH_SHORT).show();
            }
        }));

    }

    private void updateSpancoStatus(Lead selectedlead, String selectedKey, String spancostatus) {
        Lead l = new Lead(selectedlead.getCompanyName(), selectedlead.getLeadOwner(), selectedlead.getFirstName(), selectedlead.getLastName(),
                selectedlead.getDesignation(), selectedlead.getEmail(), selectedlead.getPhoneNo(), selectedlead.getLeadSourse(), selectedActivity,
                selectedlead.getSocialNetwork(), selectedlead.getAdress(), spancostatus);
        l.setAdress(addressEditText.getText().toString());
        l.setRemarks(remarksEditText.getText().toString());
        l.setDate(dateTimeText.getText().toString());
        l.setTime(pickTimeText.getText().toString());
        databaseReference.child(selectedKey).
                setValue(l).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                    }
                }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void showForm() {
        AlertDialog.Builder builder;
        final AlertDialog alertDialog;
        Context mContext = CallingActivity.this;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View urlayoutfile = inflater.inflate(R.layout.custom_dialog, (ViewGroup) findViewById(R.id.custom_dialog_scroll_layout));
        initViews(urlayoutfile);

        //check the spanco status


        //picking date and time
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

                dateTimeText.setText(sdf.format(myCalendar.getTime()));
            }

        };

        dateTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CallingActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CallingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        pickTimeText.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        nextActivityCategories = new ArrayList<String>();
        nextActivityCategories.add("Calling");
        nextActivityCategories.add("Meeting");
        bindSpinner(NextActivitySpinner, nextActivityCategories, nextActivityDataAdapter);

        builder = new AlertDialog.Builder(CallingActivity.this);
        builder.setView(urlayoutfile);
        alertDialog = builder.create();
        alertDialog.show();
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String spancostatus = checkSpancoStatus();
                updateSpancoStatus(selectedlead, selectedKey, spancostatus);
                alertDialog.dismiss();
            }
        });

    }

    private String checkSpancoStatus() {

        if (notIntrestedCheckBox.isChecked()) {
            return "S";
        } else if (askedForPICheckBox.isChecked()) {
            return "A";
        } else return "P";


    }

    private void bindSpinner(Spinner spinner, List<String> categories, ArrayAdapter<String> adapter) {
        spinner.setOnItemSelectedListener(CallingActivity.this);
        adapter = new ArrayAdapter<String>(CallingActivity.this, android.R.layout.simple_spinner_dropdown_item, categories);
        adapter.setDropDownViewResource(R.layout.spinner_item_textview);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.spinner_item_textview);

    }

    private void initViews(View urlayoutfile) {
        NextActivitySpinner = (Spinner) urlayoutfile.findViewById(R.id.next_activity_spinner2);
        dateTimePicker = (ImageView) urlayoutfile.findViewById(R.id.date_picker_image);
        timePicker = (ImageView) urlayoutfile.findViewById(R.id.time_picker_image);
        dateTimeText = (TextView) urlayoutfile.findViewById(R.id.pick_date_textview);
        pickTimeText = (TextView) urlayoutfile.findViewById(R.id.pick_time_textview);
        addressEditText = (EditText) urlayoutfile.findViewById(R.id.adress_edit_text2);
        placeEditText = (EditText) urlayoutfile.findViewById(R.id.place_edit_text);
        remarksEditText = (EditText) urlayoutfile.findViewById(R.id.place_edit_text);
        notIntrestedCheckBox = (RadioButton) urlayoutfile.findViewById(R.id.not_intrested_checkbox);
        askedForPICheckBox = (RadioButton) urlayoutfile.findViewById(R.id.not_intrested_checkbox);
        callAgainCheckBox = (RadioButton) urlayoutfile.findViewById(R.id.call_again_Checkbox);
        updateBtn = (Button) urlayoutfile.findViewById(R.id.update_leads_button);
        cancelBtn = (Button) urlayoutfile.findViewById(R.id.cancel_leads_button2);

    }

    //
    @Override
    public void onCLick(View view, int position) {
        Toast.makeText(getApplicationContext(), position, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), nextActivityCategories.get(position) + "selected", Toast.LENGTH_SHORT).show();
        selectedActivity = nextActivityCategories.get(position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedActivity = "Calling";
    }

    public void goToAddLeads(View view) {
        Intent i = new Intent(getApplicationContext(), AddLeadsActivity.class);
        startActivity(i);
    }
}
