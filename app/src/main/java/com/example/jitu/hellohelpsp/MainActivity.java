package com.example.jitu.hellohelpsp;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SearchView.OnQueryTextListener {
    AutoCompleteTextView area_name,service_name;
    ArrayList<String> students,students1;
    private JSONArray result;
    //**************************************************
    public static String [] prgmNameList={"Computer","Electric","Carpenter","pg","Microsoft .Net","Android","PHP","Jquery","JavaScript","JavaScript","JavaScript","JavaScript","JavaScript","JavaScript","JavaScript","JavaScript"};
    public static int [] prgmImages={R.drawable.css,R.drawable.html,R.drawable.js,R.drawable.wp,R.drawable.css,R.drawable.html,R.drawable.js,R.drawable.wp,R.drawable.css,R.drawable.css,R.drawable.css,R.drawable.css,R.drawable.css,R.drawable.css,R.drawable.css,R.drawable.css};
    GridView gv;
    //****************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Button button1=(Button)findViewById(R.id.Button01);

        //**************************
        gv=(GridView) findViewById(R.id.gridView1);
        gv.setAdapter(new CustomAdapter(this, prgmNameList,prgmImages));
        //*******************************
        area_name = (AutoCompleteTextView)findViewById(autoCompleteTextView);
        service_name = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        students = new ArrayList<String>();
        students1 = new ArrayList<String>();

        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DataRetriveRecycle.class);
                String area_name1 = area_name.getText().toString();
                String service_name1 = service_name.getText().toString();
                i.putExtra("area_name2", area_name1);
                i.putExtra("service_name2", service_name1);
                // Set the request code to any code you like, you can identify the
                // callback via this code
                startActivity(i);
            }
        });


        //************************************************************
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              /*  Toast.makeText(MainActivity.this, prgmNameList[position] + position, Toast.LENGTH_SHORT).show();*/
                try{
                    Intent i = new Intent(getApplicationContext(), DataRetriveRecycle.class);
                    String area_name1 = area_name.getText().toString();
                    String service_name1 = prgmNameList[position];
                    i.putExtra("area_name2", area_name1);
                    i.putExtra("service_name2", service_name1);
                    // Set the request code to any code you like, you can identify the
                    // callback via this code
                    startActivity(i);}
                catch(Exception e){
                    Toast.makeText(MainActivity.this, "enter the area name", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //*****************************************************************

        getCityName();
        getServiceName();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.gobusiness) {
            Intent i=new Intent(getApplicationContext(),GoBusiness.class);
            startActivity(i);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    @Override
    public boolean onQueryTextSubmit(String query) {
       /* Toast.makeText(getApplicationContext(),"Values are:\n First value: "+
                "\n Second Value: ",Toast.LENGTH_LONG).show();*/
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
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
                            Toast.makeText(MainActivity.this, "fgdgdf"+result, Toast.LENGTH_LONG).show();
                            //Calling method getStudents to get the students from the JSON Array
                            getCityName1(j);
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

    private void getCityName1(JSONArray j) {
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
        area_name.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_item, students));
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
                            Toast.makeText(MainActivity.this, "fgdgdf"+result, Toast.LENGTH_LONG).show();
                            //Calling method getStudents to get the students from the JSON Array
                            getServiceName1(j);
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

    private void getServiceName1(JSONArray j) {
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
        service_name.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_item, students1));
    }
}
