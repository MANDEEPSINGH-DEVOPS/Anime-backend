package com.example.animeg6;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {


    private EditText textCorreo;
    private EditText textPassword;
    private Button buttonLogin;
    private TextView textSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //Hooks
        textCorreo = findViewById(R.id.textInputLoginCorreo);
        textPassword = findViewById(R.id.textInputLoginPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textSignUp = findViewById(R.id.textSignUp);


        buttonLogin.setOnClickListener(v -> {
            String correo = textCorreo.getText().toString().trim();
            String password = textPassword.getText().toString().trim();


            if(correo.isEmpty() || password.isEmpty()){
                textCorreo.setError(correo.isEmpty() ? "Correo requerido" :  null);
                textPassword.setError(password.isEmpty() ? "Contraseña requerida" : null);
            }else{
                realizarLogin(correo, password);
            }
        });

        textSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Registra Usuario Nuevo", Toast.LENGTH_SHORT).show();

            finish();
        });
    }


    private void realizarLogin(String correo, String password) {
        String url = "http://10.0.2.2:8091/login";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", correo);
            jsonBody.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest loginRequest = new JsonObjectRequest(
                Request.Method.POST, url, jsonBody,
                response -> {
                    try {
                        if(response.has("id") && response.has("email")){
                            int userId = response.getInt("id");
                            String email = response.getString("email");
                            guardarUserIdEnSharedPreferences(userId);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    if (error.networkResponse != null) {
                        int statusCode = error.networkResponse.statusCode;
                        String body = new String(error.networkResponse.data);
                        Log.e("VolleyError", "Status: " + statusCode + " | Body: " + body);
                        Log.d("LoginRequest", "Request Body: " + jsonBody.toString());
                        if (statusCode == 401 || statusCode == 404) {
                            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                            textPassword.setError("Credenciales incorrectas");
                        } else {
                            Toast.makeText(this, "Error: " + statusCode, Toast.LENGTH_SHORT).show();
                            textPassword.setError("error " + statusCode);
                        }
                    } else {
                        Log.e("VolleyError", "Error sin respuesta de red", error);
                        textPassword.setError("Error de red");
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        queue.add(loginRequest);
    }

    private void guardarUserIdEnSharedPreferences(int userId){
        SharedPreferences sharedPreferences = getSharedPreferences("AnimeG6Prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("userId", userId);
        editor.apply();
    }


}

