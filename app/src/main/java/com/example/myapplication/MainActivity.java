package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class MainActivity extends AppCompatActivity {
    private EditText usernTxt, passwordTxt;
    private Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernTxt = findViewById(R.id.username);
        passwordTxt = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usernamestr = usernTxt.getText().toString().trim();
                String passwordstr = passwordTxt.getText().toString().trim();
                if (TextUtils.isEmpty(usernamestr)) {
                    usernTxt.setError("Please enter username");
                    usernTxt.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(passwordstr)) {
                    passwordTxt.setError("Please enter username");
                    passwordTxt.requestFocus();
                    return;
                }


                String URL = "https://ignis-english-guru.herokuapp.com/authapi/users/login/";
                JSONObject mainJson  = new JSONObject();
                JSONObject userJson  = new JSONObject();
                try {
                    userJson.put("username", usernamestr);
                    userJson.put("password", passwordstr);
                    mainJson.put("user", userJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, mainJson,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    VolleyLog.v("Response:%n %s", response.toString(4));
                                    Toast.makeText(MainActivity.this,"Unsuccessful",Toast.LENGTH_LONG).show();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.v("Response:%n %s", error);
                        Toast.makeText(MainActivity.this,"Unsuccessful",Toast.LENGTH_LONG).show();
                        System.out.println(error);
                    }
                })
                {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json");
                        headers.put("Authorization","secret_key django-insecure-b@$s0n&mw5v$k-)v(nt7u$%f6_s*c*qm8+7g^gbx9utpry$(x6");
                        return headers;
                    }
                };
                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}