package com.codepath.todo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Sarahwang on 8/23/17.
 */

public class Camera extends AppCompatActivity implements View.OnClickListener {

    final int requestCode=1234;
    final String albumName = "L11-camera-external-file";
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        acquireRunTimePermissions();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(getApplicationContext(), "Cannot take pictures on this device!", Toast.LENGTH_SHORT).show();
            return;
        }

        fileName = getOutputFileName();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(fileName));

        startActivityForResult(intent, 1234);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 1234 || resultCode != RESULT_OK) return;

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageURI(Uri.parse(fileName));
    }


    private String getOutputFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename =
                "file://"
                        + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        + "/JPEG_"
                        + timeStamp
                        + ".jpg";
        Log.i("lwang", filename);
        return filename;
    }

    private void acquireRunTimePermissions() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    111);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode != 111) return;
        if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Great! We have the permission!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Cannot write to external storage! App will not work properly!", Toast.LENGTH_SHORT).show();
        }
    }
}

