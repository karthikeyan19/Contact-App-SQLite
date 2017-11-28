package com.karthik.contacts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ContactListActivity extends ActionBarActivity {

    /**
     * Called when the activity is first created.
     */
    List<Contact> contactsList;
    SQLiteDatabase sqLiteDatabase;
    ContactAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        sqLiteDatabase=ContactApplication.getInstance().getDatabase();
        sqLiteDatabase.execSQL("create table if not exists contacts (con_id int auto_increment, cname varchar(30),type varchar(30),mobno varchar(50),phoneno varchar(50),email_id varchar(50))");
        adapter = new ContactAdapter(this);
        ListView listView= (ListView) findViewById(R.id.list_view_contacts);
        listView.setAdapter(adapter);
        updateList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact contact = contactsList.get(i);
                Intent intent = new Intent(ContactListActivity.this, DisplayContactActivity.class);
                intent.putExtra("dname", contact.name);
                intent.putExtra("dtype", contact.type);
                intent.putExtra("dmob", contact.mobileNo);
                intent.putExtra("dphone", contact.phoneNo);
                intent.putExtra("demail", contact.emailId);
                intent.putExtra("d_cid",contact.id);

                startActivity(intent);

            }
        });


    }
    public void updateList(){


        Cursor c=sqLiteDatabase.rawQuery("select * from contacts",null);
        if(contactsList==null) {
            contactsList = new LinkedList<>();
        }else{
            contactsList.clear();
        }
        if(c.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.mobileNo = c.getString(3);
                contact.name = c.getString(1);
                contact.type=c.getString(2);
                contact.emailId = c.getString(5);
                contact.phoneNo = c.getString(4);
                contact.id = c.getInt(0);
                contactsList.add(contact);
                Log.d("msg",contact.name);
            }while (c.moveToNext());
        }
        Collections.sort(contactsList, new Comparator<Contact>() {
            @Override
            public int compare(Contact contact, Contact t1) {
                return contact.name.compareToIgnoreCase(t1.name);
            }
        });
        adapter.addAll(contactsList);
        Toast.makeText(this,contactsList.size()+"",Toast.LENGTH_SHORT).show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


         switch (item.getItemId()){
             case R.id.menu_add:
                 startActivity(new Intent(ContactListActivity.this,AddContactActivity.class));
                 break;
         }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"Refersh",Toast.LENGTH_LONG).show();
        updateList();
    }
}
