package com.example.infernoinfosec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.OnProgressListener;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.example.infernoinfosec.recycle_view.model;

import java.security.ProtectionDomain;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreatePostActivity extends AppCompatActivity {

    //widgets

    private Button uploadBtn, cancleBtn;
    ImageView imageView;
    private EditText Headline,desc;
    ProgressBar progressBar;
    ProgressDialog progressDialog;

    //vars
    private CollectionReference root = FirebaseFirestore.getInstance().collection("News");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private Uri imageUri;
    private long backPressedTime;
    private Toast backToast;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        uploadBtn = findViewById(R.id.postBtn);
        Headline=findViewById(R.id.Header);
        desc=findViewById(R.id.description);
        cancleBtn=findViewById(R.id.cancelBtn);

        //progressBar=(ProgressBar) findViewById(R.id.spin_kit);
        Sprite doubleBounce = new CubeGrid();
       // progressBar.setIndeterminateDrawable(doubleBounce);
       // progressBar.setVisibility(View.VISIBLE);

        imageView =(ImageView)findViewById(R.id.addImage);
        imageView.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);


        //progressDialog.dismiss();


     /*   showAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(CreatePostActivity.this , ShowActivity.class));
            }
        });*/

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent , 2);
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (imageUri == null){
                    Toast.makeText(CreatePostActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Headline.getText().toString())){
                    Headline.setError("Enter your news headline!");
                    Headline.requestFocus();
                    return;
                }else if(TextUtils.isEmpty(desc.getText().toString())){
                    desc.setError("Enter your news content!");
                    desc.requestFocus();
                    return;
                }else{
                   AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                   builder.setTitle("Confirmation dialog");
                   builder.setMessage("Do you want to post it?");

                   builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           progressDialogDisplay();
                           uploadBtn.setEnabled(false);
                           uploadToFirebase(imageUri);
                       }
                   });


                   builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {

                       }
                   });
                   builder.show();
                }
            }
        });
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CreatePostActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //Progress Dialog box
    private void progressDialogDisplay() {

        progressDialog = new ProgressDialog(CreatePostActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2 && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();
            imageView.setImageURI(imageUri);

        }
    }

    private void uploadToFirebase(Uri uri){

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        progressDialog.dismiss();
                        String heading=Headline.getText().toString();
                        String description=desc.getText().toString();
                        String postedOn= DateFormat.getDateInstance(DateFormat.LONG).format(Calendar.getInstance().getTime());

                        //GENERATE UNIQUE KEY FOR EACH POST
                        Date dateAndTime=Calendar.getInstance().getTime();
                        SimpleDateFormat dateFormat =new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                        SimpleDateFormat timeFormat =new SimpleDateFormat("hhmmss", Locale.getDefault());
                        String d=dateFormat.format(dateAndTime);
                        String t=timeFormat.format(dateAndTime);
                        //KEY
                        String key=d+t;
                       // Toast.makeText(CreatePostActivity.this, "key="+key, Toast.LENGTH_SHORT).show();
                       model model=new model(heading,uri.toString(),description,postedOn,key);
                       root.orderBy("key", Query.Direction.ASCENDING)
                               .orderBy("postedOn", Query.Direction.ASCENDING);
                       root.document(key)
                               .set(model)
                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid) {
                                       Toast.makeText(CreatePostActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                       startActivity(getParentActivityIntent());
                                       finish();
                                   }
                               }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(CreatePostActivity.this, "Error:"+e, Toast.LENGTH_SHORT).show();
                           }
                       });

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(CreatePostActivity.this, "Error:"+e , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));



    }
    @Override
    public void onBackPressed() {
       getSupportParentActivityIntent();
    }

}