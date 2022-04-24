package com.example.infernoinfosec.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.infernoinfosec.R;


public class ContactFragment extends Fragment {
    EditText name,email,ph,msg;
    Button submit;
    String nameStr,emailStr,phoneStr,msgStr;
    ImageView insta,fb,twit,web,lin,youtube;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_contact, container, false);


       //casting
        name=view.findViewById(R.id.nameEditText);
        email=view.findViewById(R.id.emailEditText);
        ph=view.findViewById(R.id.phoneEditText);
        msg=view.findViewById(R.id.messageEditText);

        submit=view.findViewById(R.id.submitBtn);

        insta=view.findViewById(R.id.instaIcon);
        fb=view.findViewById(R.id.fbIcon);
        twit=view.findViewById(R.id.twitIcon);
        web=view.findViewById(R.id.webIcon);
        lin=view.findViewById(R.id.linkedInIcon);
        youtube=view.findViewById(R.id.youTubeIcon);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameStr=name.getText().toString();
                emailStr=email.getText().toString();
                phoneStr=ph.getText().toString();
                msgStr=msg.getText().toString();

                if (TextUtils.isEmpty(nameStr)) {
                    name.setError("Enter your name");
                    name.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(emailStr)) {
                    email.setError("Enter your email");
                    email.requestFocus();
                    return;
                }else if (TextUtils.isEmpty(msgStr)) {
                    msg.setError("Type any massage!");
                    msg.requestFocus();
                    return;
                }
                else {
                    sendMail();
                }
            }


        });

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUri("https://www.instagram.com/inferno_infosec/");

            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUri("https://www.facebook.com/infernoinfosec/");

            }
        });

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUri("https://www.infernoinfosec.in/");

            }
        });

        twit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUri("https://twitter.com/InfernoInfosec");

            }
        });

        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUri("https://www.linkedin.com/in/inferno-infosec-502213191/");

            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUri("https://www.youtube.com/channel/UC4JDDW1g9lDbtzsy7z0vsDg");

            }
        });





        return view;
    }

    private void gotoUri(String s) {
        Uri uri=Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    private void sendMail() {
        String recipientList = "infernoinfosec@gmail.com";
        String[] recipients = recipientList.split(",");
        String subject = "INFERNO APP SUPPORT";
        String message = "Hello I am " + name.getText().toString() + ",\n\n"+msg.getText().toString()+"\n\n"+"Contact me through\nEmail:"+email.getText().toString()+"\nPhone:"+ph.getText().toString()+"\n\nThanks Regards.";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }
}