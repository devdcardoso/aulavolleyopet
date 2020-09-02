package com.example.appvolleyopet202002;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView textResultado;
    EditText editNumero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResultado = findViewById(R.id.textResult);
        editNumero = findViewById(R.id.editNumeroPokemon);
    }

    public void solicitarDado(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "https://www.google.com";
        int nPokemon = Integer.parseInt(editNumero.getText().toString());
        String url = "https://pokeapi.co/api/v2/pokemon/"+nPokemon;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            /*String dados = "";
                            dados += response.getString("name");
                            dados += "\nAtaques Possiveis do Pokemon:";
                            JSONArray ataques = response.getJSONArray("moves");
                            for(int i = 0; i < ataques.length();i++){
                                JSONObject ataque_info = ataques.getJSONObject(i);
                                JSONObject ataque = ataque_info.getJSONObject("move");
                                dados += "\n" + ataque.getString("name");
                            }
                            textResultado.setText(dados);*/
                            Pokemon pokemon = new Pokemon();
                            pokemon.setNome(response.getString("name"));
                            JSONArray ataques = response.getJSONArray("moves");
                            for(int i = 0; i < ataques.length();i++){
                                JSONObject ataque_info = ataques.getJSONObject(i);
                                JSONObject ataque = ataque_info.getJSONObject("move");
                                pokemon.addAtaque(ataque.getString("name"));
                            }
                            JSONArray tipos = response.getJSONArray("types");
                            for(int i = 0; i < tipos.length(); i++){
                                JSONObject tipo_info = tipos.getJSONObject(i);
                                JSONObject tipo = tipo_info.getJSONObject("type");
                                pokemon.addTipo(tipo.getString("name"));
                            }
                            textResultado.setText(pokemon.toString());
                        }
                        catch (JSONException error){
                            textResultado.setText(error.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textResultado.setText(error.getMessage());
                    }
                }
        );

        /*StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textResultado.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textResultado.setText(error.getMessage());
                    }
                }
        );*/

        queue.add(request);
    }

    public void solicitarDadoGoverno(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.transparencia.gov.br/api-de-dados/bolsa-familia-por-municipio?mesAno=202001&codigoIbge=4106902&pagina=1";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String dados = response.toString();
                        textResultado.setText(dados);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textResultado.setText(error.getMessage());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("chave-api-dados", "sua chave de API");

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy( 5000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }
}