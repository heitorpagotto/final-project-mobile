package com.unifaj.projeto_final.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.unifaj.projeto_final.MainActivity;
import com.unifaj.projeto_final.R;
import com.unifaj.projeto_final.interfaces.VolleyCallback;
import com.unifaj.projeto_final.models.entities.User;
import com.unifaj.projeto_final.services.UserService;

import org.json.JSONException;

public class RegisterActivity extends AppCompatActivity {

    private UserService _userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        _userService = new UserService(Volley.newRequestQueue(this));

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(v -> {
            try {
                registerNewUser();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void registerNewUser() throws JSONException {
        User newUser = new User();
        newUser.setEmail(_getEmailInput());
        newUser.setName(_getNameInput());
        newUser.setPassword(_getPasswordInput());

        _userService.register(newUser, new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(RegisterActivity.this, "Cadastro realizado! Faça o Login.",Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(RegisterActivity.this, error.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private String _getNameInput() {
        EditText nameET = findViewById(R.id.name_input);
        return nameET.getText().toString();
    }
    private String _getEmailInput() {
        EditText emailET = findViewById(R.id.register_email_input);
        return emailET.getText().toString();
    }
    private String _getPasswordInput() {
        EditText passwordET = findViewById(R.id.passwordregister_input);
        return passwordET.getText().toString();
    }
}