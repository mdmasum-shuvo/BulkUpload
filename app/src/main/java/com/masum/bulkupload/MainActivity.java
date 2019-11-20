package com.masum.bulkupload;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.masum.bulkupload.encryptdata.MainEncrypt;
import com.masum.bulkupload.model.insert.Fingerprint;
import com.masum.bulkupload.model.insert.FingerprintRequest;
import com.masum.bulkupload.model.response.FpInsertResponse;
import com.masum.bulkupload.network.FPApiUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "tag";
    private Gson gson = new Gson();

    @SuppressWarnings("deprecation")
    private ProgressDialog progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppConstant.ANDROID_ID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 4);
        }

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File listOfFiles = Environment.getExternalStoragePublicDirectory("finger");
                showProgressDialog();
                new ReadFpTask().execute(listOfFiles);


            }
        });
    }

    public class ReadFpTask extends AsyncTask<File, Void, Void> {

        @Override
        protected Void doInBackground(File... files) {


            File[] fpFiles = files[0].listFiles();

            for (File fpFile : fpFiles) {

                Log.d(TAG, "readFiles: =================================================");


                List<Fingerprint> fingerprints = new ArrayList<>();

                String fileText = getFileText(fpFile);
                //Log.d(TAG, "readFile: Name: " + fpFile.getName());
                String id = getNameWithoutExtension(fpFile.getName());
                //Log.d(TAG, "readFile: Base Name: " + id);
                Log.d(TAG, "readFile: Path: " + fpFile.getAbsolutePath());
                Log.d(TAG, "readFile: Writable: " + fpFile.canWrite());
                Log.d(TAG, "readFile: Readable: " + fpFile.canRead());
                //getFileText(fpFile);

                if (fileText != null) {
                    List<byte[]> fpList = new Gson().fromJson(fileText, new TypeToken<List<byte[]>>() {
                    }.getType());
                    Log.d(TAG, "readFiles: Total " + fpList.size() + " Fingers in " + fpFile.getName() + " File");
                    for (int i = 0; i < fpList.size(); i++) {
                        Log.d(TAG, "readFiles: File Name: " + fpFile.getName() + " Finger No. " + i + " Data: " + Arrays.toString(fpList.get(i)));

                        Fingerprint fingerprint = new Fingerprint();

                        if (i == 0) {
                            fingerprint.setFingerName("leftthumb");
                        } else if (i == 1) {
                            fingerprint.setFingerName("leftindex");
                        } else if (i == 2) {
                            fingerprint.setFingerName("rightthumb");
                        } else if (i == 3) {
                            fingerprint.setFingerName("rightindex");
                        }

                        fingerprint.setIsoTemplate(MainEncrypt.finalEncryptedFP(Base64.encodeToString(fpList.get(i), Base64.NO_WRAP)));
                        fingerprints.add(fingerprint);
                    }


                    uploadFingerToServer(fingerprints, id);

                }


            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            hideProgressDialog();
        }
    }

    private void uploadFingerToServer(List<Fingerprint> fingerprints, String id) {
        FingerprintRequest fingerprintRequest = new FingerprintRequest();
        fingerprintRequest.setUserId(id);

        fingerprintRequest.setCreatedById("10");
        fingerprintRequest.setFingerprints(fingerprints);

        String jsonString = gson.toJson(fingerprintRequest);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();

        final int chunkSize = 2048;
        int count = 0;

        FPApiUtil.getInterface().addFingerResponse(jsonObject).enqueue(new Callback<FpInsertResponse>() {
            @Override
            public void onResponse(Call<FpInsertResponse> call, Response<FpInsertResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData().getResult() != null) {
                        showAlertDialog("Response", "Fingerprint insert successfully");
                    } else {
                        showAlertDialog("Response", response.body().getData().getError());
                    }
                } else {
                    showAlertDialog("Response", response.message());

                }
            }

            @Override
            public void onFailure(Call<FpInsertResponse> call, Throwable t) {
                showAlertDialog("Response", "Internet is not available");

            }
        });


    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .show();
    }

    private String getNameWithoutExtension(String name) {
        //String fileName = name;
        if (name.indexOf(".") > 0)
            name = name.substring(0, name.lastIndexOf("."));
        return name;
    }

    public static String getFileText(File file) {
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {
            return null;
        }

        return text.toString();
    }

    public void showProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = ProgressDialog.show(MainActivity.this, null, getString(R.string.please_wait), true);
            }
        });
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            });
        }
    }
}