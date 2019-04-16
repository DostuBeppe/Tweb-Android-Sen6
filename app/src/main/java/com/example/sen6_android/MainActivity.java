package com.example.sen6_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sen6_android.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences pref;
    TextView username;
    MenuItem history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username);
        pref = getSharedPreferences("user_details",MODE_PRIVATE);
        navigationView.setNavigationItemSelectedListener(this);
        history = navigationView.getMenu().getItem(1);

        if (pref.contains("username") && pref.contains("password")){
            MenuItem logout = navigationView.getMenu().getItem(2);
            String response="";
            try {
                response = new SendAction(null).execute("user-login",pref.getString("email",null),pref.getString("password",null)).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (response == null){
                Toast.makeText(this,"Connessione non riuscita, login automatico non effettuato",Toast.LENGTH_LONG).show();
                history.setVisible(false);
                logout.setTitle("Login");
            } else {
                logout.setTitle("Logout");
                history.setVisible(true);
                username.setText(pref.getString("username", null));
            }
        } else {
            MenuItem login = navigationView.getMenu().getItem(2);
            history.setVisible(false);
            login.setTitle("Login");
        }

        new FetchData(null).execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.button_courses) {
            Intent i = new Intent(this, CoursesActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.button_courses) {
            Intent i = new Intent(this, CoursesActivity.class);
            startActivity(i);
        } else if (id == R.id.button_login) {
            Intent i = new Intent(this, LoginActivity.class);
            if (item.getTitle().equals("Login")) {
                startActivity(i);
            } else {
                String JSONString="";
                try {
                    JSONString = new SendAction(null).execute("user-logout").get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (JSONString == null){
                    Toast.makeText(this,"Connessione non riuscita",Toast.LENGTH_LONG).show();
                    return false;
                } else {
                    Gson gson = new Gson();
                    ResponseMessage<User> rm = gson.fromJson(JSONString, new TypeToken<ResponseMessage<User>>() {
                    }.getType());
                    Toast.makeText(getApplicationContext(), rm.getMessage(), Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear();
                    editor.apply();
                    username.setText("Utente Anonimo");
                    item.setTitle("Login");
                    history.setVisible(false);
                }
            }
        } else if (id == R.id.button_history){
            Intent i = new Intent(this,HistoryActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
