package com.latihan.tokokelontong;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin, btnDaftarGo;
    ImageView btnEye;
    String username, password;
    LocalStorage localStorage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localStorage = new LocalStorage(MainActivity.this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnDaftarGo = findViewById(R.id.btnDaftarGo);
        btnEye = findViewById(R.id.btnEye);

        btnEye.setImageResource(R.drawable.ic_eye_hide);

        btnEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btnEye.setImageResource(R.drawable.ic_eye_hide);
                } else {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    btnEye.setImageResource(R.drawable.ic_eye);
                }
            }
        });

        btnDaftarGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DaftarActivity2.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginCheck();
            }
        });

    }

    private void LoginCheck() {
        username = etUsername.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            FailedMsg("Username Dan Password Tidak Boleh Kosong");
        } else {
            LoginSend();
        }
    }

    private void FailedMsg(String s) {
        AlertDialog.Builder alertBuild = new AlertDialog.Builder(MainActivity.this);
        alertBuild.setTitle("Login Error")
                .setMessage(s)
                .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = alertBuild.create();
        alert.show();
    }

    private void LoginSend() {
        String url = "http://103.67.187.184/api/login";

        JSONObject param = new JSONObject();
        try {
            param.put("username", username);
            param.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    String token = response.getString("data");
                    if (status.equals("200")) {
                        localStorage.setData(token);
                        etUsername.setText("");
                        etPassword.setText("");
                        Intent intent = new Intent(MainActivity.this, ContainerActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                FailedMsg("Username Atau Password Invalid");
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonRequest);
    }
}