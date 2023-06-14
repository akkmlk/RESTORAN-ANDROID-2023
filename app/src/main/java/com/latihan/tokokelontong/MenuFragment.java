package com.latihan.tokokelontong;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {

    RecyclerView recyclerView;
    AdapterMenu adapterMenu;
    Context context;
    List<MenuModel> listMenu;
    SearchView svSearch;
    LocalStorage localStorage;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
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
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rvMenu);
        listMenu = new ArrayList<>();
        adapterMenu = new AdapterMenu(getActivity(), listMenu);

//        localStorage = new LocalStorage(getActivity());

        getMenuData();

        svSearch = view.findViewById(R.id.svSearch);

        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                FilterList(newText);
                return true;
            }
        });

    }

    private void FilterList(String text) {
        List<MenuModel> filteredList = new ArrayList<>();
        for (MenuModel menuModel : listMenu) {
            if (menuModel.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(menuModel);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(getActivity(), "Oops! Menu Tidak Ada", Toast.LENGTH_SHORT).show();
        } else {
            adapterMenu.setFilteredList(filteredList);
        }
    }


    private void getMenuData() {
        String url = "http://103.67.187.184/api/products";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listMenu = new ArrayList<>();
                try {
                    String name;
                    Integer price;
                    Double rate;
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray("data");
//                    JSONArray jsonArray = new JSONArray(response);
//                    JSONArray dataArray = jsonArray.getJSONArray(Integer.parseInt("data"));

                    listMenu.clear();
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject data = dataArray.getJSONObject(i);
                        localStorage = new LocalStorage(getActivity());
                        localStorage.getData();
                        name = data.getString("name");
                        price = data.getInt("price");
                        rate = data.getDouble("rating");
                        listMenu.add(new MenuModel(name, price, rate));
                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    adapterMenu = new AdapterMenu(getActivity(), listMenu);
                    recyclerView.setAdapter(adapterMenu);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Gagal Memuat Data, Periksa Koneksi lalu Coba Lagi", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                localStorage = new LocalStorage(getActivity());
                localStorage.getData();

                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + localStorage.getData());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}