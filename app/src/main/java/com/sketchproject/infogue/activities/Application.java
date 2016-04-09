package com.sketchproject.infogue.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import com.sketchproject.infogue.R;
import com.sketchproject.infogue.fragments.ArticleFragment;
import com.sketchproject.infogue.fragments.Home;
import com.sketchproject.infogue.fragments.dummy.DummyContent;

import java.lang.reflect.Method;

public class Application extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ArticleFragment.OnArticleFragmentInteractionListener {

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.img_logo_small);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        MenuItem home = navigationView.getMenu().getItem(0).getSubMenu().getItem(0);
        onNavigationItemSelected(home);
        populateMenu();
    }

    private void populateMenu() {
        SubMenu navMenu = navigationView.getMenu().getItem(1).getSubMenu();

        String[] dataNav = {"News", "Economic", "Entertainment", "Sport", "Science", "Technology", "Education", "Photo", "Video", "Others"};
        int[] dataNavId = {11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

        for (int i = 0; i < dataNav.length; i++) {
            navMenu.add(1, dataNavId[i], Menu.CATEGORY_ALTERNATIVE, dataNav[i])
                    .setCheckable(true)
                    .setIcon(R.drawable.ic_circle);
        }
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
        getMenuInflater().inflate(R.menu.application, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
        }

        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_login) {
            Intent loginActivity = new Intent(Application.this, Authentication.class);
            startActivity(loginActivity);
        } else if (id == R.id.action_about) {
            Intent aboutActivity = new Intent(Application.this, About.class);
            startActivity(aboutActivity);
        } else if (id == R.id.action_exit) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onArticleFragmentInteraction(DummyContent.DummyItem item) {
        Log.i("RESULT", item.details);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment;
        String title;
        boolean logo;

        int id = item.getItemId();
        String category = item.getTitle().toString();

        if (id == R.id.nav_website) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://infogue.id"));
            startActivity(browserIntent);
        } else if (id == R.id.nav_rating) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.sketchproject.infogue"));
            startActivity(browserIntent);
        } else if (id == R.id.nav_about) {
            Intent aboutActivity = new Intent(Application.this, About.class);
            startActivity(aboutActivity);
        } else {
            if (id == R.id.nav_home) {
                fragment = new Home();
                title = "";
                logo = true;
            } else if (id == R.id.nav_random) {
                fragment = ArticleFragment.newInstance(1, "random");
                title = "Random";
                logo = false;
            } else if (id == R.id.nav_headline) {
                fragment = ArticleFragment.newInstance(1, "headline");
                title = "Headline";
                logo = false;
            } else {
                fragment = ArticleFragment.newInstance(1, id, category);
                title = category;
                logo = false;
            }

            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();

                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(title);
                    actionBar.setDisplayUseLogoEnabled(logo);
                }
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

}
