package com.example.pokeloot_android.vistas;

import androidx.annotation.NonNull;
import androidx.annotation.NonUiContext;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pokeloot_android.R;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;

public class MenuActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        toggle.syncState();
        drawer.addDrawerListener(toggle);

        fragmentManager = getSupportFragmentManager();
        navigationView.setNavigationItemSelectedListener(this::onOptionsItemSelected);
        carregarPrimeiroFragmento();
    }

    private boolean carregarPrimeiroFragmento(){
        Menu menu = navigationView.getMenu();
        MenuItem item = menu.getItem(0);
        item.setChecked(true);
        return onOptionsItemSelected(item);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navCartas:
                fragment = new CartasFragment();
                toolbar.setTitle("Minhas Cartas");
                drawer.closeDrawer(GravityCompat.START);
                break;
        }
        if (fragment!=null)
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();

        return true;
    }
}