package com.example.votingauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    private StorageReference storageref;
    private static final String COMPARE_URL = "http://10.0.2.2:5000/compare";
    String camera_image;
    String firebase_image;
    public Long phone;
    public String aadh1 = login.aadhar;
    public String camimgg = camerapage.camimg;
    Button ver;

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
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("camera_image", camera_image);
                params.put("firebase_image", firebase_image);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(MainActivity2.this);
        queue.add(postRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ver = findViewById(R.id.verify1);



        storageref = FirebaseStorage.getInstance().getReference().child("828935857356/20230508_120502");

        // Get the download URL asynchronously
        storageref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Handle successful download URL generation
                camera_image = uri.toString();
                // Use the download URL as needed
                System.out.println(camera_image);
            }
        });

        storageref = FirebaseStorage.getInstance().getReference().child( "828935857356/FIREBASE.JPG");

        // Get the download URL asynchronously
        storageref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Handle successful download URL generation
                firebase_image = uri.toString();
                // Use the download URL as needed
                System.out.println(firebase_image);
            }
        });
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        ver.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                makePostRequest();
//            }
//        });

        makePostRequest();






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
