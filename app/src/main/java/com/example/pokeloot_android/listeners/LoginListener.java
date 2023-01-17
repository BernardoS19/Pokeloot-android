package com.example.pokeloot_android.listeners;

import android.content.Context;

public interface LoginListener {
    void onValidateLogin(String auth_key, String username, Context context);
}
