package com.example.infernoinfosec.fragments;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infernoinfosec.DashboardActivity;
import com.example.infernoinfosec.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.github.ybq.android.spinkit.style.Pulse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class AdminLoginFragment extends Fragment {

    ImageView Logo_img;
    TextView DonateInd,Sign;
    TextInputLayout EmailId,Pwd;
    Button SignIn,New,ForgotPwd;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_admin_login, container, false);


        //HOOKS



        Sign=view.findViewById(R.id.SigninTxt);
        EmailId=view.findViewById(R.id.reg_email);
        Pwd=view.findViewById(R.id.reg_password);
        SignIn=view.findViewById(R.id.reg_btn);

        ProgressBar progressBar = view.findViewById(R.id.spin_kit);
        Sprite doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);



        fAuth=(FirebaseAuth) FirebaseAuth.getInstance();
        fStore=(FirebaseFirestore) FirebaseFirestore.getInstance();

        //FOR OLD USER
        //SIGN IN BUTTON ON CLICK EVENT
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String email = EmailId.getEditText().getText().toString();
                String pass = Pwd.getEditText().getText().toString();
                String user = "user";

                if(TextUtils.isEmpty(email)) {
                    EmailId.setError("Email is required");
                    EmailId.requestFocus();
                    return;
                }else if (TextUtils.isEmpty(pass)) {
                    Pwd.setError("Password is required");
                    Pwd.requestFocus();
                    return;
                } else if (pass.length() <= 6) {
                    Pwd.setError("Pasword must be >= 6 characters");
                    Pwd.requestFocus();
                    return;
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    getActivity().getWindow().setBackgroundDrawableResource(
                            android.R.color.transparent);


                    //USER SING IN TO THE FIREBASE
                    fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.INVISIBLE);
                                //progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getContext(), "logged successfully", Toast.LENGTH_SHORT).show();
                                //VIEW ANIMATION
                                // progressBar.setVisibility(View.INVISIBLE);
                                DocumentReference documentReference=fStore.collection("users").document(fAuth.getCurrentUser().getUid());

                                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        Log.d("TAG","ON_SUCESS"+documentSnapshot.getData());
                                        Intent intent = new Intent(getContext(), DashboardActivity.class);
                                        startActivity(intent);
                                    }
                                });



                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                //progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getContext(), "Erorr:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });








        return view;
    }
}