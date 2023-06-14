package com.latihan.tokokelontong;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    RecyclerView recyclerView;
    List<ProfileModel> profile;
    AdapterProfile adapterProfile;
    LocalStorage localStorage;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        localStorage = new LocalStorage(getActivity());
        profile = new ArrayList<>();

        recyclerView = view.findViewById(R.id.rvProfile);

        getProfileData();

    }

    private void getProfileData() {
        String url = "http://103.67.187.184/api/user";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                profile = new ArrayList<>();
                try {
                    String name, username, address;
                    JSONObject jsonObject = new JSONObject(response);
                    profile.clear();
                    for (int i = 0; i < jsonObject.length(); i++) {
                        localStorage = new LocalStorage(getActivity());
                        localStorage.getData();
                        name = jsonObject.getString("name");
                        username = jsonObject.getString("username");
                        address = jsonObject.getString("address");
                        profile.add(new ProfileModel(name, username, address));
                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    adapterProfile = new AdapterProfile(getActivity(), profile);
                    recyclerView.setAdapter(adapterProfile);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                FailedMsg("Gagal Memuat Data, Periksa Koneksi Internet");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                localStorage = new LocalStorage(getActivity());
                localStorage.getData();

                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer" + localStorage.getData());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void FailedMsg(String s) {
        AlertDialog.Builder alertBuild = new AlertDialog.Builder(getActivity());
        alertBuild.setTitle("Error")
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