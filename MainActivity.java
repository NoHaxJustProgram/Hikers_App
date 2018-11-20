package com.sictc.cspm.hikers_app;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.property.ObjectProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String BE_APP_ID = "B80CB09F-B6A5-BFC5-FF84-48066894A300";
    private final String BE_ANDROID_API_KEY = "37CDE6A6-11A1-521D-FF83-57D5CA077300";
    private final String SERVER_URL = "https://api.backendless.com";

    public CreateAppTable createAppTable;
    private Button login;
    private Button signup;
    private Button loginSignup;
    private EditText email;
    private EditText password;
    private EditText username;

    public BackendlessUser user = new BackendlessUser();

    public Map<String, Object> userTableMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Backendless.setUrl(SERVER_URL);
        Backendless.initApp(this, BE_APP_ID, BE_ANDROID_API_KEY);

        setContentView(R.layout.activity_login);
        loginScreen();
    }

    public void activityMain()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void loginScreen()
    {
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                if (Login())
                {
                    setContentView(R.layout.activity_main);
                    activityMain();
                    Log.i("login", "Login successful");
                }

            }
        });

        loginSignup = (Button) findViewById(R.id.loginSignup);
        loginSignup.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                setContentView(R.layout.activity_signup);
                Signup();
            }
        });
    }

    public boolean sign = true;
    public void Signup()
    {
        while (sign)
        {
            signup = (Button) findViewById(R.id.signup);
            signup.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    email = (EditText) findViewById(R.id.signupEmail);
                    username = (EditText) findViewById(R.id.username);
                    password = (EditText) findViewById(R.id.signupPass);

                    String strEmail = email.getText().toString();
                    final String strUser = username.getText().toString();
                    String strPass = password.getText().toString();


                    if (!(strEmail.isEmpty() && strUser.isEmpty() && strPass.isEmpty()))
                    {
                        user.setEmail(strEmail);
                        user.setPassword(strPass);
                        user.setProperty("username", strUser);

                        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                            @Override
                            public void handleResponse(BackendlessUser response){
                                Log.i("User ", response.getEmail() + " successfully registered");
                                user = response;
                                setContentView(R.layout.activity_main);
                                activityMain();
                                UserTableMain.user = user;
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Log.e("Registration error! ", fault.getMessage());
                            }
                        });
                        sign = false;
                    }
                }
            });
            break;
        }
    }

    public boolean retValue = false;

    public List<ObjectProperty> appTable = null;
    public HashMap userTable = new HashMap<>();
    public boolean Login() {
        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPass);

        final String strEmail = email.getText().toString();
        String strPass = password.getText().toString();


        if (!(strEmail.isEmpty() && strPass.isEmpty())) {
            Backendless.UserService.login(strEmail, strPass, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser response) {
                    user = response;
                    UserTableMain.user = user;
                    userTable.put("Name", "To Be Entered");
                    userTable.put("Height", "0'0");
                    userTable.put("Weight", "0");

                    retValue = true;

                    createAppTable = new CreateAppTable(UserTableMain.user);

                    createAppTable.createTable();
                    Handler mhandler = new Handler();

                    mhandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Log.i("Handler", "In the handler");
                            appTable = createAppTable.getAppTable();
                            if (appTable == null) {
                                Backendless.Data.of(user.getProperty("username").toString()).save(userTable, new AsyncCallback<Map>() {
                                    @Override
                                    public void handleResponse(Map response) {
                                        userTableMain = response;
                                        UserTableMain.userTableMain = response;
                                        Log.i("Saved", "saved table successfully");
                                    }

                                    @Override
                                    public void handleFault(BackendlessFault fault) {
                                        Log.i("Failed", "Failed to save table");
                                        Log.e("Fault", ":( " + fault.getMessage());
                                    }
                                });
                            } else {
                                userTableMain = user.getProperties();

                                Log.i("Utm in ma", (userTableMain == null) + " :(((");

                                UserTableMain.userTableMain = userTableMain;
                            }

                        }
                    }, 2500);




                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    retValue = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setCancelable(true);
                    builder.setTitle("Incorrect");
                    builder.setMessage("You have an incorrect email or password typed. Please make sure you typed it in correctly!");

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setContentView(R.layout.activity_login);
                            loginScreen();
                        }
                    });
                    builder.show();
                }
            }, true);
        }

        return retValue;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment contentFragment = new Fragment();
        if (id == R.id.BreakTime)
        {
            contentFragment = new BreakTimeFragment();
        }
        else if (id == R.id.SurvivalGuide)
        {
            contentFragment = new SurvivalGuideFragment();
        }
        else if (id == R.id.Calories)
        {
            contentFragment = new CaloriesFragment();
        }
        else if (id == R.id.Settings)
        {
            contentFragment = new SettingsFragment();
        }

        if (contentFragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, contentFragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class AsyncTassk extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            return null;
        }

        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected void onPostExecute(Void result)
        {
            CreateAppTable.getInstance().createTable();
        }
    }
}
