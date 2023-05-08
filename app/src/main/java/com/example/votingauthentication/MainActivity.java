package com.example.votingauthentication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.votingauthentication.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {



    private static final String COMPARE_URL = "http://192.168.1.6:5000/compare";
    String camera_image1=camerapage.camera_image;
    String firebase_image1=camerapage.firebase_image;


    Button ver;
    TextView result;

    private void makePostRequest() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, COMPARE_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonobj = new JSONObject(response);
                            String data = jsonobj.getString("output");
                            System.out.println(data);
                            result.setText(data);

                        } catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        System.out.println(error);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("camera_image", camera_image1);
                params.put("firebase_image", firebase_image1);
                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(postRequest);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ver = findViewById(R.id.verify1);
        result = findViewById(R.id.result);

//        String a=camimgg;
//        storageref = FirebaseStorage.getInstance().getReference().child(a);
//        // Get the download URL asynchronously
//        storageref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                // Handle successful download URL generation
//                camera_image1 = uri.toString();
//                // Use the download URL as needed
//                System.out.println(camera_image);
//            }
//        });

//        String b=aadh1+"/"+"FIREBASE.JPG"; //"828935857356/FIREBASE.JPG"
//        storageref = FirebaseStorage.getInstance().getReference().child(b);
//
//        // Get the download URL asynchronously
//        storageref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                // Handle successful download URL generation
//                firebase_image = uri.toString();
//                // Use the download URL as needed
//                System.out.println(firebase_image);
//            }
//        });
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePostRequest();
            }
        });
        //makePostRequest();






//        try{
//            final File localFile = File.createTempFile("abcd","jpg");
//            storageref.getFile(localFile)
//                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                            Toast.makeText(MainActivity.this,"picture",Toast.LENGTH_SHORT).show();
//                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//                            imgv.setImageBitmap(bitmap);
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(MainActivity.this,"picture error",Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        } catch(IOException e){
//            e.printStackTrace();
//        }



    }

}