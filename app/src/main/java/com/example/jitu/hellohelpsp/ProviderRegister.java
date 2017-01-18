package com.example.jitu.hellohelpsp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import static com.example.jitu.hellohelpsp.R.id.autoCompleteTextView;
import static com.example.jitu.hellohelpsp.R.id.autoCompleteTextView_Provider_City;
import static com.example.jitu.hellohelpsp.R.id.autoCompleteTextView_Provider_Service;

public class ProviderRegister extends AppCompatActivity implements View.OnClickListener {
    private Button buttonChoose;
    private Button buttonUpload;
    private ImageView imageView;
    // private EditText editTextName;
    // private EditText Provider_City;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    private String UPLOAD_URL = "http://ipvworld.com/hellohelps/XProvderRegister.php";
    private String KEY_IMAGE = "image";
    private String KEY_SERVICE = "name";
    private String KEY_Provider_City = "Provider_City";
    private String KEY_NAME = "namee";
    private String KEY_PASSWORD = "password";
    private String KEY_GMAIL = "gmail";
    private String KEY_MOBILENO = "mobno";
    private String KEY_ADDRESS = "address";
    private String KEY_USERINFO = "userinfo";


    private EditText TextName, TextPassword, TextGmail, TextMobileno, TextAddress, TextUserInfo;

    AutoCompleteTextView area_name, service_name;
    ArrayList<String> students, students1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_register);
        buttonChoose = (Button) findViewById(R.id.button_Choose);
        buttonUpload = (Button) findViewById(R.id.button_Upload);
        // editTextName = (EditText) findViewById(R.id.editText_Name);
        // Provider_City = (EditText) findViewById(R.id.editText_Provider_City);
        imageView = (ImageView) findViewById(R.id.imageView);
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        area_name = (AutoCompleteTextView) findViewById(autoCompleteTextView_Provider_City);
        service_name = (AutoCompleteTextView) findViewById(autoCompleteTextView_Provider_Service);
        TextName = (EditText) findViewById(R.id.editText_Name);
        TextPassword = (EditText) findViewById(R.id.editText_Password);
        TextGmail = (EditText) findViewById(R.id.editText_Gmail);
        TextMobileno = (EditText) findViewById(R.id.editText_Mobileno);
        TextAddress = (EditText) findViewById(R.id.editText_Address);
        TextUserInfo = (EditText) findViewById(R.id.editText_UserInfo);
        students = new ArrayList<String>();
        students1 = new ArrayList<String>();
        getCityName();
        getServiceName();
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage() {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(ProviderRegister.this, s, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(ProviderRegister.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Getting Image Name
                String service = service_name.getText().toString().trim();
                String Provider_City1 = area_name.getText().toString().trim();
                String TextName1 = TextName.getText().toString().trim();
                String TextPassword1 = TextPassword.getText().toString().trim();
                String TextGmail1 = TextGmail.getText().toString().trim();
                String TextMobileno1 = TextMobileno.getText().toString().trim();
                String TextAddress1 = TextAddress.getText().toString().trim();
                String TextUserInfo1 = TextUserInfo.getText().toString().trim();
                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put(KEY_SERVICE, service);
                params.put(KEY_Provider_City, Provider_City1);
                params.put(KEY_NAME, TextName1);
                params.put(KEY_PASSWORD, TextPassword1);
                params.put(KEY_GMAIL, TextGmail1);
                params.put(KEY_MOBILENO, TextMobileno1);
                params.put(KEY_ADDRESS, TextAddress1);
                params.put(KEY_USERINFO, TextUserInfo1);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (v == buttonChoose) {
            showFileChooser();
        }

        if (v == buttonUpload) {
            uploadImage();
        }
    }

    private void getCityName() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Config.DATA_URL5,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray j;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONArray(response);
                            //Storing the Array of JSON String to our JSON Array
                            // result = j.getJSONArray(Config.JSON_ARRAY);
                            Toast.makeText(ProviderRegister.this, "fgdgdf", Toast.LENGTH_LONG).show();
                            //Calling method getStudents to get the students from the JSON Array
                            getCityName12(j);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    public void getCityName12(JSONArray j) {
        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                students.add(json.getString(Config.TAG_USERNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        area_name.setThreshold(1);
        area_name.setAdapter(new ArrayAdapter<String>(ProviderRegister.this, android.R.layout.select_dialog_item, students));
    }

    private void getServiceName() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Config.DATA_URL6,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray j;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONArray(response);
                            //Storing the Array of JSON String to our JSON Array
                            // result = j.getJSONArray(Config.JSON_ARRAY);
                            Toast.makeText(ProviderRegister.this, "fgdgdf", Toast.LENGTH_LONG).show();
                            //Calling method getStudents to get the students from the JSON Array
                            getServiceName12(j);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getServiceName12(JSONArray j) {
        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                students1.add(json.getString(Config.TAG_Service));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        service_name.setThreshold(1);
        service_name.setAdapter(new ArrayAdapter<String>(ProviderRegister.this, android.R.layout.select_dialog_item, students1));
    }


}
