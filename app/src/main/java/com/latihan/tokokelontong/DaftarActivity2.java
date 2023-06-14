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

public class DaftarActivity2 extends AppCompatActivity {

    EditText etName, etUsername, etAlamat, etPassword, etConfirmPass;
    Button btnDaftar, btnHaveAkun;
    ImageView btnEye, btnEyeConfirm;
    String name, username, alamat, password, confirmPass;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar2);

        etName  = findViewById(R.id.etName);
        etUsername  = findViewById(R.id.etUsername);
        etAlamat  = findViewById(R.id.etAlamat);
        etPassword  = findViewById(R.id.etPassword);
        etConfirmPass  = findViewById(R.id.etConfirmPass);
        btnDaftar  = findViewById(R.id.btnDaftar);
        btnHaveAkun  = findViewById(R.id.btnHaveAkun);
        btnEye  = findViewById(R.id.btnEye);
        btnEyeConfirm  = findViewById(R.id.btnEyeConfirm);

        btnEye.setImageResource(R.drawable.ic_eye_hide);
        btnEyeConfirm.setImageResource(R.drawable.ic_eye_hide);

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

        btnEyeConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etConfirmPass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    etConfirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btnEyeConfirm.setImageResource(R.drawable.ic_eye_hide);
                } else {
                    etConfirmPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    btnEyeConfirm.setImageResource(R.drawable.ic_eye);
                }
            }
        });

        btnHaveAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DaftarActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaftarCheck();
            }
        });

    }

    private void DaftarCheck() {
        name =  etName.getText().toString().trim();
        username =  etUsername.getText().toString().trim();
        alamat =  etAlamat.getText().toString().trim();
        password =  etPassword.getText().toString().trim();
        confirmPass =  etConfirmPass.getText().toString().trim();

        if (name.isEmpty() || username.isEmpty() || alamat.isEmpty() || password.isEmpty()) {
            FailedMsg("Seluruh Field Sangat Dibutuhlkan, Tolong Dilengkapi");
        } else if (!password.equals(confirmPass)) {
            FailedMsg("Password Dan Konfirmasi  Password Tidak Cocok");
        } else {
            DaftarSend();
        }
    }

    private void DaftarSend() {
        String url = "http://103.67.187.184/api/register";

        JSONObject param = new JSONObject();
        try {
            param.put("name", name);
            param.put("username", username);
            param.put("address", alamat);
            param.put("password", password);
            param.put("password_confirmation", confirmPass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    if (status.equals("201")) {
                        etName.setText("");
                        etUsername.setText("");
                        etAlamat.setText("");
                        etPassword.setText("");
                        etConfirmPass.setText("");
                        SuccesMsg("Daftar Succes, Silahkan Login");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                FailedMsg("Username Sudah Tersedia \n \n" + error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(DaftarActivity2.this);
        requestQueue.add(jsonRequest);
    }

    private void SuccesMsg(String s) {
        AlertDialog.Builder alertBuild = new AlertDialog.Builder(DaftarActivity2.this);
        alertBuild.setTitle("Daftar Succes")
                .setMessage(s)
                .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        AlertDialog alert = alertBuild.create();
        alert.show();
    }

    private void FailedMsg(String s) {
        AlertDialog.Builder alertBuild = new AlertDialog.Builder(DaftarActivity2.this);
        alertBuild.setTitle("Daftar Error")
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
}