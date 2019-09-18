package com.example.myapplication.Activities.AddingActivities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.myapplication.Activities.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.RetrofitClientInstance;
import com.example.myapplication.Retrofit.TaskService;
import com.example.myapplication.Models.Tasks;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AddTasksActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner prioritySpinner; //projectSpinner, tasksSpinner, assignedToSpinner,
    Switch privacySwitch;
    EditText tagsEditText, descriptionEditText, hoursToCompleteEditText;
    ImageView pickStartDateImageView, pickDueDateImageView;
    Button saveTasksButton, cancelButton;
    TextView projectNameTextView, TasksTextView, pickStartDateTextview, assignedToTextView, pickDueDateTextview, priorityTextview;
    private String TAG = "MOCK!!!";
    Tasks task1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);

        initViews();
//        initSpinners();
        initForm();
//        showDataOnForm();

        pickDueDateImageView.setOnClickListener(new View.OnClickListener() {
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

                        pickDueDateTextview.setText(sdf.format(myCalendar.getTime()));
                    }

                };

                pickDueDateImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(AddTasksActivity.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
            }
        });


        pickStartDateImageView.setOnClickListener(new View.OnClickListener() {
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

                        pickStartDateTextview.setText(sdf.format(myCalendar.getTime()));
                    }

                };

                pickStartDateImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(AddTasksActivity.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
            }
        });

        saveTasksButton.setOnClickListener(new View.OnClickListener() {
            private static final String TAG = "MARK 45";

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    private void showDataOnForm(Tasks task1) {
        projectNameTextView.setText(this.task1.getProjectName());
        TasksTextView.setText(this.task1.getTaskName());
        tagsEditText.setText(this.task1.getTags());
        assignedToTextView.setText(this.task1.getAssignedTo());
        pickStartDateTextview.setText(this.task1.getStartDate());
        pickDueDateTextview.setText(this.task1.getDueDate());
        hoursToCompleteEditText.setText(this.task1.getEstTimeToCompleteHr());
        descriptionEditText.setText(this.task1.getDescription());
        priorityTextview.setText(this.task1.getPriority());

    }

    private void initForm() {

        String params = "{\"user\":\"editoruser\"}";

        TaskService service = RetrofitClientInstance.getRetrofitInstance().create(TaskService.class);
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params);
        Call<ResponseBody> call = service.getAllTasks(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                ResponseBody resp1 = response.body();
                Log.d("MOCK3!!!", resp1.byteStream().toString());
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
                String text = sb.toString();
                Log.d("MOCK", "onResponse: " + text);
                try {
                    JSONObject jsonObject = new JSONObject(text);
//                    Log.d(TAG, "onResponse: " + jsonObject);
//                    Log.d(TAG, "onResponse: " + jsonObject.getString("d"));
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("d"));
//                    Log.d(TAG, "onResponse: " + jsonArray);
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
//                    Log.d(TAG, "onResponse: " + jsonObject1);
                    Gson gson = new Gson();
                    task1 = gson.fromJson(jsonObject1.toString(), Tasks.class);
                    showDataOnForm(task1);
//                    text = "[{\"AssignToBikerId\":1,\"BikerId\":null,\"AssignToBikerName\":\"\",\"PrsId\":1,\"PrsNo\":\"PRS/1920/0000001\",\"PrsDate\":\"29/06/2019 00:00:00\",\"MerchantId\":45,\"MerchantName\":\"abhishek\",\"MerchantAddress\":\"Ambarhai\",\"TrackingNo\":\"ABC12347\",\"ManifestId\":0,\"PinCode\":\"0\",\"MerchantCity\":\"\",\"bitmap\":null,\"PrsQty\":3,\"PickedQty\":0,\"isValid\":false,\"isPicked\":false,\"validShipments\":null,\"invalidShipments\":null,\"SkuId\":54125,\"SkuCode\":\"A00000123\",\"Remark\":null,\"SkuDescription\":\"54125-WHITE\"}]";
//                    String text1 = text.replaceAll("\\\\", text);
//                    JSONArray jsonArray1 = new JSONArray(text1);
//                    Log.d("REPLACEALL!!!", text1);
//                            Log.d(TAG, t.AddedById);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private void getResponse(String mMessage, Response response) {
        DocumentBuilder newDocumentBuilder =
                null;
        try {
            newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document parse = null;
        try {
            parse = newDocumentBuilder.parse(new
                    ByteArrayInputStream(response.toString().getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        Log.d("MOCK!!!", response.toString());

//        String dataString = parse.getElementsByTagName
//                ("Your TAG NAME").item(0).getTextContent();
    }

    private void initSpinners() {
//        projectSpinner.setOnItemSelectedListener(this);
//        // Spinner Drop down elements
//        List<String> projectCategories = new ArrayList<String>();
//        projectCategories.add("TWMS");
//        projectCategories.add("HRMS");
//        ArrayAdapter<String> projectCategoriesDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, projectCategories);
//        // Drop down layout style - list view with radio button
//        projectCategoriesDataAdapter.setDropDownViewResource(R.layout.spinner_item_textview);
//        // attaching data adapter to spinner
//        projectSpinner.setAdapter(projectCategoriesDataAdapter);
//        projectCategoriesDataAdapter.setDropDownViewResource(R.layout.spinner_item_textview);
//
//
//        tasksSpinner.setOnItemSelectedListener(this);
//        // Spinner Drop down elements
//        List<String> tasksCategories = new ArrayList<String>();
//        tasksCategories.add("Backened");
//        tasksCategories.add("FrontEnd");
//        tasksCategories.add("Testing");
//        ArrayAdapter<String> tasksCategoriesDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tasksCategories);
//        // Drop down layout style - list view with radio button
//        tasksCategoriesDataAdapter.setDropDownViewResource(R.layout.spinner_item_textview);
//        // attaching data adapter to spinner
//        tasksSpinner.setAdapter(tasksCategoriesDataAdapter);
//        tasksCategoriesDataAdapter.setDropDownViewResource(R.layout.spinner_item_textview);


//        prioritySpinner.setOnItemSelectedListener(this);
//        // Spinner Drop down elements
//        List<String> priorityCategories = new ArrayList<String>();
//        priorityCategories.add("Low");
//        priorityCategories.add("Medium");
//        priorityCategories.add("High");
//        ArrayAdapter<String> priorityCategoriesDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, priorityCategories);
//        // Drop down layout style - list view with radio button
//        priorityCategoriesDataAdapter.setDropDownViewResource(R.layout.spinner_item_textview);
//        // attaching data adapter to spinner
//        prioritySpinner.setAdapter(priorityCategoriesDataAdapter);
//        priorityCategoriesDataAdapter.setDropDownViewResource(R.layout.spinner_item_textview);


    }

    private void initViews() {
        pickDueDateTextview = findViewById(R.id.pick_due_date_textview);
        pickStartDateTextview = findViewById(R.id.pick_start_date_textview);
        priorityTextview = (TextView) findViewById(R.id.priority_textview);
        privacySwitch = (Switch) findViewById(R.id.tasks_privacy_switch);
        projectNameTextView = (TextView) findViewById(R.id.project_name_textview);
        tagsEditText = (EditText) findViewById(R.id.tags_edit_text);
        TasksTextView = (TextView) findViewById(R.id.tasks_textview);
        assignedToTextView = (TextView) findViewById(R.id.assigned_to_textview);
        descriptionEditText = (EditText) findViewById(R.id.description_edit_text);
        pickStartDateImageView = (ImageView) findViewById(R.id.pick_start_date_imageview);
        pickDueDateImageView = (ImageView) findViewById(R.id.pick_due_date_imageview);
        hoursToCompleteEditText = (EditText) findViewById(R.id.choose_hourtocomplete_edittext);
        saveTasksButton = (Button) findViewById(R.id.save_tasks_button);
        cancelButton = (Button) findViewById(R.id.cancel_tasks_button);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
