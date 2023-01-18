package com.example.pokeloot_android.modelos;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pokeloot_android.listeners.BaralhoListener;
import com.example.pokeloot_android.listeners.CartaListener;
import com.example.pokeloot_android.listeners.LoginListener;
import com.example.pokeloot_android.utils.CartasJsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingletonCartas {
    private static SingletonCartas instance = null;
    private static RequestQueue volleyQueue = null;
    private ArrayList<Carta> cartas;
    private ArrayList<Baralho> baralhos;
    private String auth_key;
    private static final String urlAPILogin = "http://10.0.2.2/PokeLoot-PLSI/backend/web/api/user/login";
    private static final String urlAPIRegister = "http://10.0.2.2/PokeLoot-PLSI/backend/web/api/user/register";
    private LoginListener loginListener;
    private static final String urlAPICartasCompradas = "http://10.0.2.2/PokeLoot-PLSI/backend/web/api/carta/compradas";
    private CartaListener cartaListener;
    private static final String urlAPIBaralhosDoUser = "http://10.0.2.2/PokeLoot-PLSI/backend/web/api/baralho/lista";
    private BaralhoListener baralhoListener;

    public static synchronized SingletonCartas getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonCartas(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    public SingletonCartas(Context context){
        cartas = new ArrayList<>();
        baralhos = new ArrayList<>();
    }

    //region Listeners
    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void setCartaListener(CartaListener cartaListener) {
        this.cartaListener = cartaListener;
    }

    public void setBaralhoListener(BaralhoListener baralhoListener) {
        this.baralhoListener = baralhoListener;
    }

    //endregion

    //region Acesso API

    //region LOGIN
    public void loginAPI(final String username, final String password, final Context context) {
        if (!CartasJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, String> params = new HashMap<String, String>();
            params.put("username", username);
            params.put("password", password);
            JSONObject jsonObject = new JSONObject(params);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlAPILogin, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    auth_key = CartasJsonParser.parserJsonLogin(response);
                    if (response != null) {
                        loginListener.onValidateLogin(auth_key, username, context);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
            };
            volleyQueue.add(request);
        }
    }
    //endregion

    //region Criar Conta
    public void registerAPI(final String username, final String email, final String password, final Context context) {
        if (!CartasJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligação à internet", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, String> params = new HashMap<String, String>();
            params.put("username", username);
            params.put("email", email);
            params.put("password", password);
            JSONObject jsonObject = new JSONObject(params);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlAPIRegister, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    auth_key = CartasJsonParser.parserJsonLogin(response);

                    if (response != null){
                        loginListener.onValidateLogin(auth_key, username, context);
                        SharedPreferences userInfoPreferences = context.getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
                        userInfoPreferences.edit().putString("AUTH_KEY", auth_key).apply();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
            };
            volleyQueue.add(request);
        }
    }
    //endregion

    //region Cartas do Utilizador
    public void getCartasCompradasAPI(final Context context) {
        if (!CartasJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlAPICartasCompradas, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    cartas = CartasJsonParser.parserJsonCartas(response);
                    if (cartas != null){
                        if (cartaListener != null) {
                            cartaListener.onRefreshGridCarta(cartas);
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    SharedPreferences preferences = context.getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
                    String authkey = preferences.getString("AUTH_KEY", null);
                    if (authkey != "Error, username or password may be wrong." && authkey != null && authkey != "null") {
                        headers.put("auth", authkey);
                        return headers;
                    } else {
                        return null;
                    }
                }
            };
            volleyQueue.add(request);
        }
    }
    //endregion


    //Region Baralhos do Utilizador
    public void getBaralhosDoUserAPI(final Context context) {
        if (!CartasJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlAPIBaralhosDoUser, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    baralhos = CartasJsonParser.parserJsonBaralhos(response);
                    if (baralhos != null) {
                        baralhoListener.onRefreshListaBaralho(baralhos);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    SharedPreferences preferences = context.getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
                    String authkey = preferences.getString("AUTH_KEY", null);
                    if (authkey != "Error, username or password may be wrong." && authkey != null && authkey != "null") {
                        headers.put("auth", authkey);
                        return headers;
                    } else {
                        return null;
                    }
                }
            };
            volleyQueue.add(request);
        }
    }

    //endregion


}
