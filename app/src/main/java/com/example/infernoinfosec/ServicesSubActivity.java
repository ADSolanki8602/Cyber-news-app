package com.example.infernoinfosec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.infernoinfosec.fragments.AboutFragment;
import com.example.infernoinfosec.fragments.ContactFragment;
import com.example.infernoinfosec.fragments.HomeFragment;
import com.example.infernoinfosec.fragments.ServicesFragment;
import com.example.infernoinfosec.services.CyberDefanseFragment;
import com.example.infernoinfosec.services.CyberResponseFragment;
import com.example.infernoinfosec.services.CyberStratagyFragment;
import com.example.infernoinfosec.services.CyberTrainingFragment;
import com.example.infernoinfosec.services.CyberTransformationFragment;

public class ServicesSubActivity extends AppCompatActivity {

    Toolbar servToolbar;
    FrameLayout cont;
    Fragment temp;
    private int key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_sub);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        servToolbar=(androidx.appcompat.widget.Toolbar) findViewById(R.id.servToolbar);
        servToolbar.setTitle("Services");
        cont=findViewById(R.id.servicesContainer);
        setSupportActionBar(servToolbar);
        key=getIntent().getIntExtra("servKey",1);




        if(key==1){
            servToolbar.setTitle("Cyber Defanse");
            temp=new CyberDefanseFragment();
        }
        else if(key==2){
            servToolbar.setTitle("Cyber Response");
            temp=new CyberResponseFragment();
        }
        else if(key==3){
            servToolbar.setTitle("Cyber Transformation");
            temp=new CyberTransformationFragment();
        }else if(key==4){
            servToolbar.setTitle("Cyber Stratagy");
            temp=new CyberStratagyFragment();
        }else if(key==5){
            servToolbar.setTitle("Cyber Training");
            temp=new CyberTrainingFragment();
        }


       /* switch (key){
            case def:
                temp=new HomeFragment();
                servToolbar.setTitle("Home");
                break;

            case resp:
                temp=new ServicesFragment();
                servToolbar.setTitle("Services");
                break;

            case tras:
                temp=new AboutFragment();
                servToolbar.setTitle("About Us");
                break;

            case stra:
                temp=new ContactFragment();
                servToolbar.setTitle("Contact Us");
                break;
            case train:
                temp=new ContactFragment();
                servToolbar.setTitle("Contact Us");
                break;
        }*/
        getSupportFragmentManager().beginTransaction().replace(R.id.servicesContainer,temp).commit();

        try {

        }catch(Exception e){
            Toast.makeText(this, "Error:"+e, Toast.LENGTH_SHORT).show();
        }


    }
}