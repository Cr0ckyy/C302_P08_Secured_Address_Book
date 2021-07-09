package com.myapplicationdev.android.c302_p08_secured_address_book;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CreateContactActivity extends AppCompatActivity {

    EditText etFirstName, etLastName, etMobile;
    Button btnCreate;
    String loginId, apikey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etMobile = findViewById(R.id.etMobile);
        btnCreate = findViewById(R.id.btnCreate);

        // Return the intent that started this activity.
        Intent loginIntent = getIntent();

        loginId = loginIntent.getStringExtra("loginId");
        apikey = loginIntent.getStringExtra("apikey");

        btnCreate.setOnClickListener(v -> {
            String fName = etFirstName.getText().toString().trim();
            String lName = etLastName.getText().toString().trim();
            String mobile = etMobile.getText().toString().trim();

            assert fName.length() != 0;
            assert lName.length() != 0;
            assert mobile.length() != 0;

            // TODO: it can be used in your Android applications to
            //  asynchronous GET, POST, PUT, and DELETE HTTP requests.
            AsyncHttpClient client = new AsyncHttpClient();

            // TODO: a set of string request parameters/files to send
            //  with requests made by an AsyncHttpClient instance.
            RequestParams params = new RequestParams();

            // Adds a key/value string pair to the request.
            params.put("loginId", loginId);
            params.put("apikey", apikey);
            params.put("FirstName", fName);
            params.put("LastName", lName);
            params.put("Mobile", mobile);

            // Perform a HTTP POST request with parameters
            client.post("http://10.0.2.2/C302_P08_SecuredCloudAddressBook/createContact.php", params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    try {
                        String message = response.getString("message");
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        finish();

                    } catch (JSONException e) {
                        Toast.makeText(CreateContactActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        });
    }
}