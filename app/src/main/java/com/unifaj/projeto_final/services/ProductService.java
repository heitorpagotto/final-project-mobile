package com.unifaj.projeto_final.services;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unifaj.projeto_final.env.APIEnv;
import com.unifaj.projeto_final.interfaces.VolleyCallback;

public class ProductService {
    public APIEnv env;
    private RequestQueue _queue;

    public ProductService(RequestQueue queue) {
        _queue = queue;
        env = APIEnv.getInstance();
    }

    public void getProducts(VolleyCallback callback) {
        String url = env.getURI() + "products/getAll";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
                });
        _queue.add(stringRequest);
    }
}
