package com.unifaj.projeto_final.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.Volley;
import com.unifaj.projeto_final.R;
import com.unifaj.projeto_final.interfaces.VolleyCallback;
import com.unifaj.projeto_final.models.dto.DefaultResponse;
import com.unifaj.projeto_final.models.entities.Products;
import com.unifaj.projeto_final.services.ProductService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    private ProductService _productService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        _productService = new ProductService(Volley.newRequestQueue(this));

        Button finishButton = findViewById(R.id.close_button);
        finishButton.setOnClickListener(v -> {
            try {
                SharedPreferences preferences = getSharedPreferences("userPref", Context.MODE_PRIVATE);
                ;
                SharedPreferences.Editor preferenceEditor = preferences.edit();

                preferenceEditor.putString("user", "");
                preferenceEditor.commit();

                finish();
            } catch (Exception e) {
                Toast.makeText(this, "Erro ao salvar os dados. Tente novamente", Toast.LENGTH_LONG).show();
            }
        });

        getProducts();
    }

    public void getProducts() {
        _productService.getProducts(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    DefaultResponse<List<Products>> productList = new DefaultResponse<>();
                    JSONObject obj = (JSONObject) new
                            JSONTokener(response).nextValue();
                    productList.setMessage(obj.getString("message"));
                    productList.setStatus(obj.getString("status"));
                    JSONArray products = obj.getJSONArray("data");
                    List<Products> prodList = new ArrayList<>();
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject ob = new JSONObject(products.get(i).toString());
                        Products newProd = new Products();
                        newProd.setName(ob.getString("name"));
                        newProd.setQuantity(ob.getInt("quantity"));
                        newProd.setPrice(ob.getDouble("price"));
                        prodList.add(newProd);
                    }
                    productList.setData(prodList);

                    List<String> names = new ArrayList<>();
                    productList.getData().forEach(x -> {
                        names.add(x.toString());
                    });

                    ListView simpleList = (ListView) findViewById(R.id.product_listview);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ProductsActivity.this, R.layout.activity_product_array_adapter, R.id.adapter_textview, names);
                    simpleList.setAdapter(arrayAdapter);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

}