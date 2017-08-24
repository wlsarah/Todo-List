package com.codepath.todo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.name;

public class EditList extends AppCompatActivity implements View.OnClickListener {
    final int requestCode = 1234;
    final String albumName = "L11-camera-external-file";
    String fileName = "";
    EditText title, note;
    int id;
    private EditList thisActivity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);



        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        final String animalName = bundle.getString("title");
        String animalDescription = bundle.getString("note");
        fileName = bundle.getString("aPicPath");



        title = (EditText) findViewById(R.id.editText1);
        note = (EditText) findViewById(R.id.editText2);
        title.setText(animalName);
        note.setText(animalDescription);


        if (fileName != null) {
            if (!fileName.equals("")) {
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageURI(Uri.parse(fileName));
//                imageView.setRotation(180f);

            }
        }

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);


        acquireRunTimePermissions();


    }

    private void save(int id, String aName, String aDescription) {
        ContactDbHelper db = new ContactDbHelper(this);

        if (db.getMaxRecID() < id) {
            if (fileName.equals("")) {
                db.add(new ContactInfo(id, null, aName, aDescription));

            } else {
                db.delete(id);
                db.add(new ContactInfo(id, fileName, aName, aDescription));
            }

            Cursor c = db.fetchAll();

            if (c.moveToNext()) {
                int _id = c.getInt(0);
                String name = c.getString(1);
                String description = c.getString(2);
                String path = c.getString(3);


                System.out.println("ID: " + id + " name : " + name + " DESCRIPTION : " + description + " path: " + path);

            } else {
                System.out.println("nothing in db");
            }

            Intent intent = new Intent(EditList.this, MainActivity.class);
            startActivity(intent);
        }
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detailmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_return) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        } else if (id == R.id.action_save) {
            save(id, title.getText().toString(), note.getText().toString());
        }

        return super.onOptionsItemSelected(item);
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

}

