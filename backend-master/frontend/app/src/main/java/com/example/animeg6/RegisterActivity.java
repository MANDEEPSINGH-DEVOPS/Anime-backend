package com.example.animeg6;


import android.content.Intent;
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


public class RegisterActivity extends AppCompatActivity {


    private EditText textInputSignUpNombre;
    private EditText textInputSignUpCorreo;
    private EditText textInputSignUpPassword;
    private EditText textInputSignUpTelefono;
    private Button buttonSignUp;
    private TextView textLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textInputSignUpNombre = findViewById(R.id.textInputSignUpNombre);
        textInputSignUpCorreo = findViewById(R.id.textInputSignUpCorreo);
        textInputSignUpPassword = findViewById(R.id.textInputSignUpPassword);
        textInputSignUpTelefono = findViewById(R.id.textInputSignUpTelefono);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textLogin = findViewById(R.id.textLogin);


        buttonSignUp.setOnClickListener(v -> {
            String name = textInputSignUpNombre.getText().toString().trim();
            String email = textInputSignUpCorreo.getText().toString().trim();
            String password = textInputSignUpPassword.getText().toString().trim();
            String phone = textInputSignUpTelefono.getText().toString().trim();


            if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Campos requerido" ,Toast.LENGTH_SHORT).show();
            }else{
                createCuenta(name,email,password,phone);
            }
        });
        textLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });


    }


    private void createCuenta(String name, String email, String password, String phone) {
        String url = "http://10.0.2.2:8091/register";


        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name", name);
            jsonBody.put("email", email);
            jsonBody.put("password", password);
            jsonBody.put("phone", phone);
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(this);


        JsonObjectRequest registerRequest = new JsonObjectRequest(
                Request.Method.POST, url, jsonBody,
                response -> {
                    try {
                        if(response.has("email") && response.has("name") && response.has("password")){
                            String newEmail = response.getString("email");
                            Toast.makeText(this, "Registro exitoso para: " + newEmail, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "Error al registrar, intenta nuevamente", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(this, "Error en el servidor o datos inválidos", Toast.LENGTH_SHORT).show();
                    Log.e("VolleyError", error.toString());
                }
        ){
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        queue.add(registerRequest);


    }
}

