package com.unifaj.projeto_final;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.Volley;
import com.unifaj.projeto_final.activities.ProductsActivity;
import com.unifaj.projeto_final.activities.RegisterActivity;
import com.unifaj.projeto_final.env.APIEnv;
import com.unifaj.projeto_final.interfaces.VolleyCallback;
import com.unifaj.projeto_final.models.dto.DefaultResponse;
import com.unifaj.projeto_final.models.dto.LoginRequest;
import com.unifaj.projeto_final.models.entities.User;
import com.unifaj.projeto_final.services.AuthService;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MainActivity extends AppCompatActivity {
    private APIEnv _env;
    private AuthService _authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _authService = new AuthService(Volley.newRequestQueue(this));
        _env = APIEnv.getInstance();

        if (isAlreadyLogged()) {
            _navigate();
        }

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {
            try {
                login();
            } catch (JSONException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        EditText ip = findViewById(R.id.ip_input);
        ip.setText(_env.getURI());

        ip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                _env.setURI(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
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
                        DefaultResponse<User> loginResponse = new DefaultResponse<>();
                        JSONObject obj = null;
                        try {
                            obj = (JSONObject) new
                                    JSONTokener(response).nextValue();
                            loginResponse.setMessage(obj.getString("message"));
                            loginResponse.setStatus(obj.getString("status"));
                            JSONObject user = obj.getJSONObject("data");
                            saveData(user.toString());
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        startActivity(new Intent(MainActivity.this, ProductsActivity.class));
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(MainActivity.this, "Usuário ou senha inválidos.", Toast.LENGTH_LONG).show();
                    }
                }

        );

    }

    public boolean isAlreadyLogged() {
        SharedPreferences preferences = getSharedPreferences("userPref", Context.MODE_PRIVATE);
        String user = preferences.getString("user", "");

        return user != "";
    }

    public String getUsername() {
        EditText userNameInput = findViewById(R.id.user_input);
        return userNameInput.getText().toString();
    }

    public String getPassword() {
        EditText passwordInput = findViewById(R.id.password_input);
        return passwordInput.getText().toString();
    }

    public void saveData(String userJson) {
        try {
            SharedPreferences preferences = getSharedPreferences("userPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor preferenceEditor = preferences.edit();

            preferenceEditor.putString("user", userJson);
            preferenceEditor.commit();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Erro ao salvar os dados. Tente novamente", Toast.LENGTH_LONG).show();
        }
    }

    private void _navigate() {
        startActivity(new Intent(MainActivity.this, ProductsActivity.class));
    }
}