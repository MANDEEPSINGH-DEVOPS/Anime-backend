package com.example.animeg6.ui.perfil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.animeg6.R;
import com.example.animeg6.databinding.FragmentPerfilBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PerfilFragment extends Fragment {

    private EditText etName, etEmail,etPassword, etPhone;
    private Button btnEdit, btnSave;
    private PerfilViewModel perfilViewModel;
    private RequestQueue requestQueue;
    private static final String BASE_URL = "http://10.0.2.2:8091/";
    private FragmentPerfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);

        perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        requestQueue = Volley.newRequestQueue(requireContext());

        etName = root.findViewById(R.id.etName);
        etEmail = root.findViewById(R.id.etEmail);
        etPassword = root.findViewById(R.id.etPassword);
        etPhone = root.findViewById(R.id.etPhone);
        btnEdit = root.findViewById(R.id.btnEdit);
        btnSave = root.findViewById(R.id.btnSave);


        int userId = obtenerUserID();
        if (userId == -1) {
            Toast.makeText(getContext(), "Usuario no autenticado", Toast.LENGTH_SHORT).show();
            return root;
        }
        obtenerDatosUsuario(userId);
        btnSave.setOnClickListener(v -> actualizarDatosUsuario(userId));
        btnEdit.setOnClickListener(v -> habilitarEdicion(true));

        perfilViewModel.getName().observe(getViewLifecycleOwner(), etName::setText);
        perfilViewModel.getEmail().observe(getViewLifecycleOwner(), etEmail::setText);
        perfilViewModel.getPassword().observe(getViewLifecycleOwner(), etPassword::setText);
        perfilViewModel.getPhone().observe(getViewLifecycleOwner(), etPhone::setText);

        return root;
    }

    private void obtenerDatosUsuario(int userId){
        String url = BASE_URL + "user/" + userId;
        Log.d("PerfilFragment", "Obteniendo datos de: " + url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try{
                        String name = response.getString("name");
                        String email = response.getString("email");
                        String password = response.getString("password");
                        String phone = response.getString("phone");
                        Log.d("PerfilFragment", "Datos recibidos: " + name + ", " + email + ", " + phone);

                        perfilViewModel.setUserData(name, email, password,phone);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Error al parsear datos", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("PerfilFragment", "Error en la petición", error);
                    Toast.makeText(getContext(), "Error al obtener datos", Toast.LENGTH_SHORT).show();
                });

        requestQueue.add(request);
    }


    private void habilitarEdicion(boolean habilitar){
        etName.setEnabled(habilitar);
        etPhone.setEnabled(habilitar);
        etPassword.setEnabled(habilitar);
        btnSave.setVisibility(habilitar ? View.VISIBLE : View.GONE);
        btnEdit.setVisibility(habilitar ? View.GONE : View.VISIBLE);
    }

    private void actualizarDatosUsuario(int userId) {
        String url = BASE_URL + "user/" + userId;

        JSONObject params = new JSONObject();
        try {
            params.put("name", etName.getText().toString());
            params.put("email", etEmail.getText().toString());
            params.put("password", etPassword.getText().toString());
            params.put("phone", etPhone.getText().toString());
            Log.d("PerfilFragment", "JSON enviado: " + params.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, params,
                response -> {
                    Toast.makeText(getContext(), "Datos actualizados", Toast.LENGTH_SHORT).show();
                    habilitarEdicion(false);
                    Log.d("MS: HOLA", "MS: params: "  + params.toString());
                },
                error -> {
                    Log.e("PerfilFragment", "Error en la petición: " + error.toString());
                    Toast.makeText(getContext(), "Error al actualizar", Toast.LENGTH_SHORT).show();
                }) {
        };
        requestQueue.add(request);
    }

    private int obtenerUserID(){
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("AnimeG6Prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("userId", -1);
    }


}