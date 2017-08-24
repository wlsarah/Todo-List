package com.codepath.todo;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int maxRecId;
    ContactDbHelper dbHelper;
    Cursor cursor;
    RecyclerView recList;
    ContactAdapter adapter;
    ArrayList<ContactInfo> contactsInfo = new ArrayList<>();
    private MainActivity thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recList = (RecyclerView) findViewById(R.id.listView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recList.setLayoutManager(llm);
        adapter = new ContactAdapter(this, contactsInfo);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewContact();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Todo List");
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.actionbarbackground));

        TouchHelperCallback callback = new TouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recList);

        retrieve();

        maxRecId = dbHelper.getMaxRecID();
        toastShow("MacRecID is " + maxRecId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_uninstall:
                toastShow("uninstall action ...");
                Uri packageURI = Uri.parse("package:" + MainActivity.class.getPackage().getName());
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                startActivity(uninstallIntent);
                break;
            default:
                toastShow("unknown action ...");
        }

        return super.onOptionsItemSelected(item);
    }


    private void retrieve() {

        dbHelper = new ContactDbHelper(this);

        cursor = dbHelper.fetchAll();
        contactsInfo.clear();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            String path = cursor.getString(3);

            System.out.println("ID: " + id + " name : " + name + " DESCRIPTION : " + description + " path: " + path);

            ContactInfo contactI = new ContactInfo(id, path, name, description);
            contactsInfo.add(contactI);
        }

        if (!(contactsInfo.size() < 1)) {
            recList.setAdapter(adapter);
        }

    }


    private void addNewContact() {
        Intent intent = new Intent(MainActivity.this, EditList.class);

        Bundle bundle = new Bundle();
        bundle.putInt("id", contactsInfo.size() + 1);
        bundle.putString("title", "");
        bundle.putString("note", "");
        bundle.putString("aPicPath", "");

        intent.putExtras(bundle);

        //START ACTIVITY
        startActivity(intent);
    }

    private void toastShow(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}

