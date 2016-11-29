package com.ucsdcse110.tritonnections;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import static com.ucsdcse110.tritonnections.task.LoadCoursesTaskBuilder.SourceType.ENROLLED_CLASSES;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation_drawer);
        showFragment(new SearchFragment());

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public static void hideSoftKeyboard(Activity activity)
    {
        InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_schedule) {
            if (!TritonlinkLoginManager.isLoggedIn()) {
                AlertDialog alertDialog = new AlertDialog.Builder(NavigationDrawer.this).create();
                alertDialog.setTitle("Login Required");
                alertDialog.setMessage("Please login first.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            } else {
                Bundle args = new Bundle();
                args.putSerializable("course source", ENROLLED_CLASSES);
                showFragment(new CoursesRecyclerViewFragment(), args);
            }
        } else if (id == R.id.nav_search) {
            showFragment(new SearchFragment());
        } else if (id == R.id.nav_postboard) {
            showFragment (new PostsRecyclerViewFragment());
        } else if (id == R.id.nav_login) {
            showFragment(new LoginFragment());
        } else if (id == R.id.nav_logout) {
            TritonlinkLoginManager.logout();
            AlertDialog alertDialog = new AlertDialog.Builder(NavigationDrawer.this).create();
            alertDialog.setTitle("Logged Out");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }

    private void showFragment(Fragment f)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContent, f)
                .addToBackStack(null)
                .commit();
        invalidateOptionsMenu();
    }

    private void showFragment(Fragment f, Bundle b) {
        f.setArguments(b);
        showFragment(f);
    }
}
