package com.example.sen6_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sen6_android.model.Lesson;
import com.example.sen6_android.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HistoryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    List<Lesson> lessons = new ArrayList<>();
    private final static String[] days = {"Lun","Mar","Mer","Gio","Ven"};
    private final static int scheduleNow = 9;
    private ListView history_list;
    SharedPreferences pref;
    TextView username;
    MenuItem history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username);
        pref = getSharedPreferences("user_details",MODE_PRIVATE);
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

        history_list = (ListView)findViewById(R.id.list_history);
        fillListHistory(history_list);
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
        getMenuInflater().inflate(R.menu.history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
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
                if (JSONString==null){
                    Toast.makeText(this,"Connessione non riuscita",Toast.LENGTH_LONG).show();
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
                    Intent main = new Intent(this, MainActivity.class);
                    startActivity(main);
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

    public void fillListHistory(ListView history){
        String response = "";
        Gson gson = new Gson();
        try {
            response = new FetchData(null).execute("list-user-lesson").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (response == null){
            Toast.makeText(this,"Connessione non riuscita",Toast.LENGTH_LONG).show();
        } else {
            ResponseMessage<List<Lesson>> rm = gson.fromJson(response, new TypeToken<ResponseMessage<List<Lesson>>>() {
            }.getType());
            lessons = rm.getData();
            CustomAdapter adapter = new CustomAdapter(this,
                    R.layout.list_item_history,
                    lessons);
            history.setAdapter(adapter);
        }
    }

    public void cancelLesson(View view) {
        String response = "";
        Gson gson = new Gson();
        try {
            response = new SendAction(null).execute("lesson-cancel",""+view.getId()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (response == null){
            Toast.makeText(this,"Connessione non riuscita",Toast.LENGTH_LONG).show();
        } else {
            ResponseMessage<Integer> rm = gson.fromJson(response, new TypeToken<ResponseMessage<Integer>>() {
            }.getType());
            Toast.makeText(this, rm.getMessage(), Toast.LENGTH_LONG).show();
            fillListHistory(history_list);
        }
    }

    private class CustomAdapter extends ArrayAdapter<Lesson> {

        public CustomAdapter(Context context, int resource) {
            super(context, resource);
        }

        public CustomAdapter(Context context, int resource, List<Lesson> items) {
            super(context, resource, items);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.list_item_history, null);
            }

            Lesson l = getItem(position);

            if (l != null) {
                TextView tt1 = (TextView) v.findViewById(R.id.item_history_subject);
                TextView tt2 = (TextView) v.findViewById(R.id.item_history_teacher);
                TextView tt3 = (TextView) v.findViewById(R.id.item_history_schedule);
                TextView tt4 = (TextView) v.findViewById(R.id.item_status);

                if (tt1 != null) {
                    tt1.setText(l.getCourse().getSubject().getName());
                }

                if (tt2 != null) {
                    String s1 = l.getCourse().getTeacher().getSurname() + " " + l.getCourse().getTeacher().getName();
                    tt2.setText(s1);
                }

                if (tt3 != null) {
                    String s2 = days[l.getSchedule()/4] + "\n" + ((l.getSchedule()%4)+15)+":00";
                    tt3.setText(s2);
                }

                if (tt4 !=null){
                    if (l.isActive() && l.getSchedule()>scheduleNow) {
                        tt4.setText("Annulla");
                        tt4.setBackgroundColor(getResources().getColor(R.color.colorSuccess));
                        tt4.setClickable(true);
                        tt4.setId(l.getId());
                    } else if (l.isActive() && l.getSchedule()<scheduleNow) {
                        tt4.setText("Effettuata");
                        tt4.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
                        tt4.setClickable(false);
                    } else if (!l.isActive()){
                        tt4.setText("Annullata");
                        tt4.setBackgroundColor(getResources().getColor(R.color.colorDanger));
                        tt4.setClickable(false);
                    }
                }

            }
            return v;
        }
    }
}
