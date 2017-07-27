package com.calamity_assist.calamityassist;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Interpolator;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.calamity_assist.calamityassist.Constant.Constant;
import com.calamity_assist.calamityassist.Constant.SessionManager;
import com.google.android.gms.identity.intents.AddressConstants;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.bitmap;
import static android.R.attr.cursorVisible;
import static android.R.attr.manageSpaceActivity;
import static android.app.Activity.RESULT_OK;
import static org.json.JSONObject.NULL;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {


    ProgressDialog progressDialog;
    ImageView user_profile;
    ImageView btn_select;
    TextView email_profile;
    private Bitmap bitmap;
    private Uri filePath;
    CircleImageView imageView;

    public static final String UPLOAD_URL="http://192.168.2.103/calamity/update_user_profile.php";


    String login_user;
    String selecetedFile;

    SessionManager sm;

    public Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        View h=inflater.inflate(R.layout.nav_header_main,null);
        imageView=(CircleImageView)h.findViewById(R.id.imageView_drawer);



        email_profile=(TextView)view.findViewById(R.id.user_profile_email);
        btn_select=(ImageView) view.findViewById(R.id.add_photo);
        user_profile=(ImageView)view.findViewById(R.id.user_profile_photo);

        sm=new SessionManager(getActivity());
        email_profile.setText(sm.getEmail());

        login_user=sm.getEmail();
        Picasso.with(getActivity()).load(Constant.ImagePath+sm.getImg()).into(user_profile);

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFileChooser();

            }
        });
        return view;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Toast.makeText(this, path , Toast.LENGTH_SHORT).show();
        if(requestCode == 1 && resultCode== RESULT_OK && data!=NULL)
        {   filePath = data.getData();
            try
            {
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filePath);
            user_profile.setImageBitmap(bitmap);
            imageView.setImageBitmap(bitmap);
            upload();

        } catch (IOException e) {
            e.printStackTrace();
        }
        }
        else
        {
            Toast.makeText(getActivity(),"error hello", Toast.LENGTH_SHORT).show();

            System.out.print("not image");
        }
    }


    public void upload()   {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.show();


        //converting image to base64 string
        user_profile.setImageBitmap(bitmap);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

       // Toast.makeText(getActivity(), imageString, Toast.LENGTH_SHORT).show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, UPLOAD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();

                    }
                }
        ){
            @Override
            protected Map<String,String> getParams() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("imageString", imageString);
                params.put("user_email",login_user);
               // Toast.makeText(getActivity(), params.put("user_email",login_user) , Toast.LENGTH_SHORT).show();
                return params;
            }
        };
        RequestQueue rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(stringRequest);
    }


}
