package com.unifaj.projeto_final.services;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.unifaj.projeto_final.env.APIEnv;
import com.unifaj.projeto_final.interfaces.VolleyCallback;
import com.unifaj.projeto_final.models.entities.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class UserService {
    public APIEnv env;
    private RequestQueue _queue;

    public UserService(RequestQueue queue) {
        this._queue = queue;
        env = APIEnv.getInstance();
    }

    public void register(User register, VolleyCallback callback) throws JSONException {
        String url = env.getURI() + "users/register";

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("name", register.getName());
        jsonBody.put("email", register.getEmail());
        jsonBody.put("password", register.getPassword());

        final String json = jsonBody.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.getMessage());
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return json.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding. Bytes of %s using %s", json, "utf-8");
                    return null;
                }
            }

        };
        _queue.add(stringRequest);
    }
}
