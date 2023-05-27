package com.unifaj.projeto_final;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.Volley;
import com.unifaj.projeto_final.activities.ProductsActivity;
import com.unifaj.projeto_final.activities.RegisterActivity;
import com.unifaj.projeto_final.interfaces.VolleyCallback;
import com.unifaj.projeto_final.models.dto.DefaultResponse;
import com.unifaj.projeto_final.models.dto.LoginRequest;
import com.unifaj.projeto_final.services.AuthService;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private AuthService _authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _authService = new AuthService(Volley.newRequestQueue(this));

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {
            try {
                login();
            } catch (JSONException e) {
                Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

        Button registerButton = findViewById(R.id.signup_button);
        registerButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RegisterActivity.class)));
    }

    private void login() throws JSONException {
        LoginRequest login = new LoginRequest(getUsername(), getPassword());
        _authService.logIn(login, new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        DefaultResponse<JSONObject> resp = new DefaultResponse<>();
                        //resp = objConversion.convertToObj(resp.getClass(), response);
                        startActivity(new Intent(MainActivity.this, ProductsActivity.class));
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
                    }
                }

        );

    }

    public String getUsername() {
        EditText userNameInput = findViewById(R.id.user_input);
        return userNameInput.getText().toString();
    }

    public String getPassword() {
        EditText passwordInput = findViewById(R.id.password_input);
        return passwordInput.getText().toString();
    }
}