package com.example.a1.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.myapplication.apihandlers.App;
import com.example.a1.myapplication.models.containerModel;
import com.example.a1.myapplication.models.loginModel;
import com.example.a1.myapplication.models.orderModel;
import com.example.a1.myapplication.models.updateContainerModel;
import com.example.a1.myapplication.models.updateOrderModel;
import com.example.a1.myapplication.models.userModel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.parseInt;

public class OrderActivity extends AppCompatActivity {

    private IntentIntegrator qrScan;
    private String SID, email, location;
    public int trackID;
    private boolean container = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);
        SID = getIntent().getExtras().getString("SID");
        email = getIntent().getExtras().getString("email");
        location = getIntent().getExtras().getString("location");
        TextView greet = findViewById(R.id.textView);
        TextView loc = findViewById(R.id.textView2);
        greet.setText("Hello, " + email);
        if(location.length()>0) loc.setText("Location: " + location);
        else loc.setText("Permission: Admin");
        qrScan = new IntentIntegrator(this);
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            userModel req = new userModel();
            req.setSID(SID);

            App.getApi().logout(req).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    if(response.code()==200){
                        SharedPreferences settings = getSharedPreferences("Login", Context.MODE_PRIVATE);
                        settings.edit().clear().apply();
                        finish();
                        Toast.makeText(getApplicationContext(), "Successfully logged out", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Error occured while logging out", Toast.LENGTH_LONG).show();
                        SharedPreferences settings = getSharedPreferences("Login", Context.MODE_PRIVATE);
                        settings.edit().clear().apply();
                        finish();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    public void scan(View view) {
        qrScan.initiateScan();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        ConstraintLayout ll = findViewById(R.id.orderlayout);
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        Button showInfo = findViewById(R.id.button3);
        Button accept = findViewById(R.id.button6);
        Button giveOutOrder = findViewById(R.id.button7);
        Button returnOrder = findViewById(R.id.button8);
        Button cancelOrder = findViewById(R.id.button9);
        TextView qrresult = findViewById(R.id.scanresult);
        if (result != null) {
            if (result.getContents() == null || result.getContents().length()!=13 || !result.getContents().startsWith("SSD")) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                try{
                    if(result.getContents().charAt(3)=='C'){
                        trackID = parseInt(result.getContents().substring(4));
                        container = true;

                        showInfo.setVisibility(View.VISIBLE);
                        showInfo.setEnabled(true);
                        accept.setText("Accept container");
                        accept.setVisibility(View.VISIBLE);
                        accept.setEnabled(true);
                        giveOutOrder.setVisibility(View.GONE);
                        giveOutOrder.setEnabled(false);
                        returnOrder.setVisibility(View.GONE);
                        returnOrder.setEnabled(false);
                        cancelOrder.setVisibility(View.GONE);
                        cancelOrder.setEnabled(false);

                        qrresult.setText("Container: " + result.getContents());
                    }else{
                        trackID = parseInt(result.getContents().substring(3));
                        container = false;

                        showInfo.setVisibility(View.VISIBLE);
                        showInfo.setEnabled(true);
                        accept.setText("Accept order");
                        accept.setVisibility(View.VISIBLE);
                        accept.setEnabled(true);
                        giveOutOrder.setVisibility(View.VISIBLE);
                        giveOutOrder.setEnabled(true);
                        returnOrder.setVisibility(View.VISIBLE);
                        returnOrder.setEnabled(true);
                        cancelOrder.setVisibility(View.VISIBLE);
                        cancelOrder.setEnabled(true);

                        qrresult.setText("Order: " + result.getContents());
                    }
                }catch(NumberFormatException ex){
                    Toast.makeText(getApplicationContext(), "QR code doesn't contain right data", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void acceptOrder(View view) {
        if(!container) updateStatus(SID, trackID, "accept");
        else acceptContainer(SID, trackID);
    }

    public void showInfo(View view) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Info");


        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

        if(container){
            App.getApi().getContainerInfo(SID, trackID).enqueue(new Callback<List<containerModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<containerModel>> call, @NonNull Response<List<containerModel>> response) {
                    if(response.code()==200&&!response.body().isEmpty()){
                        List<containerModel> resp = response.body();
                        alert.setMessage(resp.get(0).toString());
                        alert.show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Can't perform this action", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<containerModel>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            App.getApi().getOrderInfo(SID, trackID).enqueue(new Callback<List<orderModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<orderModel>> call, @NonNull Response<List<orderModel>> response) {
                    if(response.code()==200&&!response.body().isEmpty()){
                        List<orderModel> resp = response.body();
                        alert.setMessage(resp.get(0).toString());
                        alert.show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Can't perform this action", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<orderModel>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void giveOut(View view) {
        if(!container) updateStatus(SID, trackID, "giveout");
    }

    public void returnOrder(View view) {
        if(!container) updateStatus(SID, trackID, "return");
    }

    public void cancelOrder(View view) {
        if(!container) updateStatus(SID, trackID, "cancel");
    }

    private void updateStatus(String SID, int trackID, String action){
        updateOrderModel oM = new updateOrderModel();
        oM.setSID(SID);
        oM.setAction(action);
        oM.setTrackID(trackID);
        App.getApi().updateStatus(oM).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if(response.code()==200){
                    Toast.makeText(getApplicationContext(), "Successfully created return order", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Can't perform this action", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void acceptContainer(String SID, int containerID){
        updateContainerModel oM = new updateContainerModel();
        oM.setSID(SID);
        oM.setcontainerID(trackID);
        App.getApi().acceptContainer(oM).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if(response.code()==200){
                    Toast.makeText(getApplicationContext(), "Successfully created return order", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Can't perform this action", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
