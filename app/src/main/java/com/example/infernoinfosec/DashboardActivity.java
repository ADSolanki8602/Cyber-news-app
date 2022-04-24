package com.example.infernoinfosec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.infernoinfosec.fragments.AboutFragment;
import com.example.infernoinfosec.fragments.AdminLoginFragment;
import com.example.infernoinfosec.fragments.ContactFragment;
import com.example.infernoinfosec.fragments.HomeFragment;
import com.example.infernoinfosec.fragments.ServicesFragment;
import com.example.infernoinfosec.recycle_view.model;
import com.example.infernoinfosec.recycle_view.myadepter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity  {

    NavigationView NV;
    ActionBarDrawerToggle toggle;
    myadepter adepter;
    RecyclerView recview;
    ArrayList<model> datalist;
    DrawerLayout drawerLayout;
    FirebaseAuth fAuth;


    androidx.appcompat.widget.Toolbar toolbar;
    private long backPressedTime;
    private Toast backToast;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

       if(!isConnected(this)){
            showDialog();
        }



        datalist=new ArrayList<model>();

        adepter=new myadepter(datalist,this);

        //HOOKS
        toolbar= (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        NV=(NavigationView)findViewById(R.id.navmenu);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);

        //CREATE TOGGLE BUTTON ON TOOLBAR
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState(); //READ THE TOGGLE STATE



        //SET DEFAULT FRAGMENT IN CONTAINER

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment() ).commit();
        NV.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            Fragment temp;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        temp=new HomeFragment();
                        toolbar.setTitle("Home");
                        break;

                    case R.id.menu_services:
                        temp=new ServicesFragment();
                        toolbar.setTitle("Services");
                        break;

                    case R.id.menu_about:
                        temp=new AboutFragment();
                        toolbar.setTitle("About Us");
                        break;

                    case R.id.menu_contact:
                        temp=new ContactFragment();
                        toolbar.setTitle("Contact Us");
                        break;
                    /*case R.id.menu_Login:
                        temp=new AdminLoginFragment();
                        toolbar.setTitle("Admin Login");
                        break;*/
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,temp).commit();
                drawerLayout.closeDrawer(GravityCompat.START); //CLOSE DRAWER WHEN A ITEM IS SELECTED

                return true;
            }
        });
    }

    //CHECK INTERNET CONNECTIVITY

    private boolean isConnected(DashboardActivity dashboardActivity) {
        ConnectivityManager connectivityManager= (ConnectivityManager) dashboardActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo= connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn= connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifiInfo!= null && wifiInfo.isConnected() || (mobileConn != null && mobileConn.isConnected()))){
            return true;
        }
        else {
            return false;
        }


    }

    private void showDialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(DashboardActivity.this);
        builder.setMessage("Please connect to internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                });
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        fAuth=(FirebaseAuth) FirebaseAuth.getInstance();
        int Login_item_id = 101;
        if(fAuth.getCurrentUser() == null) {

            MenuItem AdLoginItem=menu.add(Menu.NONE,Login_item_id,5,"Admin Login");
            AdLoginItem.setIcon(R.drawable.ic_baseline_login_24);
            AdLoginItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
            AdLoginItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Fragment temp=new AdminLoginFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,temp).commit();
                    drawerLayout.closeDrawer(GravityCompat.START); //CLOSE DRAWER WHEN A ITEM IS SELECTED
                    return true;
                }
            });
        }else {
            MenuItem AdLogoutItem=menu.add(Menu.NONE,Login_item_id,5,"Admin Login");
           AdLogoutItem.setIcon(R.drawable.ic_baseline_logout_24);
           AdLogoutItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
           AdLogoutItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(DashboardActivity.this);
                    builder.setTitle("Sign Out");
                    builder.setMessage("Do you want to sign out?");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseAuth.getInstance().signOut();
                            Toast.makeText(DashboardActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
                            restartActivity();
                            //Firestore.getInstance().getReference().child("students")
                            //      .child(getRef(position).getKey()).removeValue();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();

                    //Fragment temp=new HomeFragment();
                   // getSupportFragmentManager().beginTransaction().replace(R.id.container,temp).commit();
                   // drawerLayout.closeDrawer(GravityCompat.START);
                   // AdLogoutItem.setIcon(R.drawable.ic_baseline_login_24);
                    return true;
                }
            });
        }

        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }
    //Restart activity
    public void restartActivity(){
        Intent mIntent = getIntent();
        finish();
        startActivity(mIntent);
    }
    public void SetTollbarTitle(String s){
        toolbar.setTitle(s);
    }



}