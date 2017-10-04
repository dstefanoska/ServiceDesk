package com.daka.servicedesk.base.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.daka.sdk.models.User;
import com.daka.servicedesk.R;
import com.daka.servicedesk.login.LoginActivityIntentBuilder;
import com.daka.servicedesk.modules.services.ServicesActivity;
import com.daka.servicedesk.modules.sos.SosActivity;
import com.daka.servicedesk.modules.tasks.TasksActivity;
import com.daka.servicedesk.utils.Store;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.tajchert.nammu.Nammu;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Dana on 22-Sep-17.
 */

public class BaseActivity extends AppCompatActivity{

    private CompositeSubscription subscriptionDestroy;
    private CompositeSubscription subscriptionStop;
    private CompositeSubscription subscriptionPause;

    public Toolbar toolbar;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle drawerToggle;
    public NavigationView navigationView;
    public Context mContext;
    private HeaderView headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = BaseActivity.this;
        setContentView(R.layout.layout_drawer);
    }

    @Override
    public void setContentView(int layoutResID) {
        DrawerLayout fullView = (DrawerLayout)getLayoutInflater().inflate(R.layout.layout_drawer, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);

        initToolbar();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setUpNav() {
        drawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
        drawerToggle = new ActionBarDrawerToggle(BaseActivity.this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.navigationView);
        headerView = new HeaderView(navigationView.getHeaderView(0));

        // Setting Navigation View Item Selected Listener to handle the item
        // click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Checking if the item is in checked state or not, if not make
                // it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }

                // Closing drawer on item click
                drawerLayout.closeDrawers();
                selectOpenMenu(navigationView);

                // Check to see which item was being clicked and perform
                // appropriate action
                switch (menuItem.getItemId()) {
                    case R.id.menu_log_out:
                        Store.removeUser();
                        new LoginActivityIntentBuilder(mContext).start();
                        finish();
                        return true;
                    case R.id.menu_tasks:
                        Intent tasks = new Intent(mContext, TasksActivity.class);
                        startActivity(tasks);
                        return true;
                    case R.id.menu_services:
                        Intent services = new Intent(mContext, ServicesActivity.class);
                        startActivity(services);
                        return true;
                    case R.id.menu_sos:
                        Intent sos = new Intent(mContext, SosActivity.class);
                        startActivity(sos);
                        return true;
                }
                return false;
            }
        });
        drawerToggle.syncState();

    }

    private void selectOpenMenu(NavigationView navigation) {
        if(navigation.getMenu().findItem(R.id.menu_tasks).isChecked()) {
            navigation.setCheckedItem(R.id.menu_tasks);
        }  else if(navigation.getMenu().findItem(R.id.menu_services).isChecked()) {
            navigation.setCheckedItem(R.id.menu_services);
        }  else if(navigation.getMenu().findItem(R.id.menu_sos).isChecked()) {
            navigation.setCheckedItem(R.id.menu_sos);
        }  else if(navigation.getMenu().findItem(R.id.menu_log_out).isChecked()) {
            navigation.setCheckedItem(R.id.menu_log_out);
        }
    }

    class HeaderView {
        View headerView;
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.textViewTitle)
        TextView title;
        @BindView(R.id.textViewSubtitle)
        TextView subtitle;

        public HeaderView(View headerView) {
            this.headerView = headerView;
            ButterKnife.bind(this, headerView);

            User user = Store.user();
            title.setText(user.getRoleName() != null ? user.getRoleName() : "");
            subtitle.setText(user.getRoleId() != 0 ? String.valueOf(user.getRoleId()) : "");
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setUpNav();
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (subscriptionStop != null) {
            subscriptionStop.unsubscribe();
            subscriptionStop = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (subscriptionPause != null) {
            subscriptionPause.unsubscribe();
            subscriptionPause = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscriptionDestroy != null) {
            subscriptionDestroy.unsubscribe();
            subscriptionDestroy = null;
        }
    }

    public CompositeSubscription subscriptionDestroy() {
        if(subscriptionDestroy == null) {
            subscriptionDestroy = new CompositeSubscription();
        }
        return subscriptionDestroy;
    }

    public CompositeSubscription subscriptionStop() {
        if(subscriptionStop == null) {
            subscriptionStop = new CompositeSubscription();
        }
        return subscriptionStop;
    }

    public CompositeSubscription subscriptionPause() {
        if(subscriptionPause == null) {
            subscriptionPause = new CompositeSubscription();
        }
        return subscriptionPause;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}