package com.example.sen6_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sen6_android.model.Course;
import com.example.sen6_android.model.Lesson;
import com.example.sen6_android.model.Scheduler;
import com.example.sen6_android.model.Subject;
import com.example.sen6_android.model.Teacher;
import com.example.sen6_android.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CoursesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<Scheduler> schedulers = new ArrayList<>();
    SharedPreferences pref;
    TextView username;
    MenuItem history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ListView lvCourses = (ListView) findViewById(R.id.listCourses);

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
        getCoursesList(lvCourses);
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
        getMenuInflater().inflate(R.menu.courses, menu);
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
                    if (JSONString == null){
                        Toast.makeText(this,"Connessione non riuscita",Toast.LENGTH_LONG).show();
                        return false;
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                ResponseMessage<User> rm = gson.fromJson(JSONString,new TypeToken<ResponseMessage<User>>(){}.getType());
                Toast.makeText(getApplicationContext(),rm.getMessage(),Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.apply();
                username.setText("Utente Anonimo");
                item.setTitle("Login");
                history.setVisible(false);
            }
        } else if (id == R.id.button_history){
            Intent i = new Intent(this,HistoryActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getCoursesList(ListView lvCourses) {
        String response = "";
        Gson gson = new Gson();
        try {
            response = new FetchData(null).execute("list-available-lessons").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (response == null){
            Toast.makeText(this,"Connessione non riuscita",Toast.LENGTH_LONG).show();
        } else {
            ResponseMessage<List<Scheduler>> rm = gson.fromJson(response, new TypeToken<ResponseMessage<List<Scheduler>>>() {
            }.getType());
            try {
                response = new FetchData(null).execute("list-user-active-lesson").get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ResponseMessage<List<Lesson>> rm2 = gson.fromJson(response, new TypeToken<ResponseMessage<List<Lesson>>>() {
            }.getType());
            schedulers = rm.getData();
            if (pref.contains("username")) {
                for (Scheduler s : schedulers) {
                    for (Lesson l : rm2.getData()) {
                        if (s.getSchedule().get(l.getSchedule())) {
                            s.setFalse(l.getSchedule());
                        }
                    }
                }
            }
            CustomAdapter adapter = new CustomAdapter(this,
                    R.layout.list_item_course,
                    this.schedulers);
            lvCourses.setAdapter(adapter);
        }
    }

    public void changeActivity(View view) {
        Intent i = new Intent(this, BookingActivity.class);
        Scheduler s = this.schedulers.get(view.getId());
        Gson gson = new Gson();
        i.putExtra("scheduler",gson.toJson(s));
        startActivity(i);
    }

    private class CustomAdapter extends ArrayAdapter<Scheduler> {

        public CustomAdapter(Context context, int resource) {
            super(context, resource);
        }

        public CustomAdapter(Context context, int resource, List<Scheduler> items) {
            super(context, resource, items);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.list_item_course, null);
            }

            Scheduler s = getItem(position);

            if (s != null) {
                TextView tt1 = (TextView) v.findViewById(R.id.item_subject);
                TextView tt2 = (TextView) v.findViewById(R.id.item_teacher);
                TextView tt3 = (TextView) v.findViewById(R.id.item_available_lessons);
                LinearLayout ll4 = (LinearLayout) v.findViewById(R.id.scheduler_index);

                if (tt1 != null) {
                    tt1.setText(s.getCourse().getSubject().getName());
                }

                if (tt2 != null) {
                    String s1 = s.getCourse().getTeacher().getSurname() + " " + s.getCourse().getTeacher().getName();
                    tt2.setText(s1);
                }

                if (tt3 != null) {
                    String s2 = "" + s.getAvailableLessons();
                    tt3.setText(s2);
                }

                if (ll4 !=null){
                    ll4.setId(position);
                }


            }
            return v;
        }
    }
}
