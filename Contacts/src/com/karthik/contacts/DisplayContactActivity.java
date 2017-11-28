package com.karthik.contacts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by android on 22/11/17.
 */
public class DisplayContactActivity extends ActionBarActivity{


    private TextView nameTextView;
    private TextView phoneTextView;
    private TextView mobileTextView;
    private TextView emailTextView;
    private int cid;
    private String cname;
    private String cphone;
    private String cmobile;
    private String cemail;
    private String ctype;
    private boolean isRefersh=false;
    private  SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Intent intent = getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameTextView = (TextView) findViewById(R.id.text_view_dname);
        phoneTextView = (TextView) findViewById(R.id.text_view_dphone_no);
        mobileTextView = (TextView) findViewById(R.id.text_view_cmob);
        emailTextView = (TextView) findViewById(R.id.text_view_email);

        if(intent!=null&&intent.getExtras()!=null){

            cname = intent.getStringExtra("dname");
            cphone=intent.getStringExtra("dphone");
            cmobile=intent.getStringExtra("dmob");
            cemail=intent.getStringExtra("demail");
            cid = intent.getIntExtra("d_cid", -1);
            ctype = intent.getStringExtra("dtype");
            updateView();

        }



    }

    public  void updateView(){
        if(ctype.equals("Home")){
            nameTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_ic_supervisor_account_white_48dp,0,R.drawable.ic_ic_home_white_48dp,0);

        }else{
            nameTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_ic_supervisor_account_white_48dp,0,R.drawable.ic_ic_business_white_48dp,0);
        }


        nameTextView.setText(cname);
        phoneTextView.setText(cphone);
        mobileTextView.setText(cmobile);
        emailTextView.setText(cemail);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.user_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isRefersh)
        onRefersh();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog dialog  =null;
      sqLiteDatabase = ContactApplication.getInstance().getDatabase();
        switch (item.getItemId()){

            case R.id.menu_pdelete:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage("Are want to delete this contact?")
                        .setTitle("Delete Contact")
                        .setPositiveButton("delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (sqLiteDatabase.delete(ContactApplication.TABLE_NAME, ContactApplication._ID+"=?", new String[]{mobileTextView.getText().toString()}) > 0) {
                                    Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();


                                } else {
                                    Toast.makeText(getApplicationContext(), " not Deleted"+cid, Toast.LENGTH_LONG).show();

                                }
                                onBackPressed();
                            }
                        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                dialog = builder.create();
                dialog.show();
                break;



            case android.R.id.home:

                onBackPressed();
                break;
            case R.id.menu_pupdate:
                Intent intent = new Intent(DisplayContactActivity.this,AddContactActivity.class);

                intent.putExtra("dname",cname );
                intent.putExtra("dtype", ctype);
                intent.putExtra("dmob", cmobile);
                intent.putExtra("dphone", cphone);
                intent.putExtra("demail", cemail);
                intent.putExtra("d_cid", cid);
                isRefersh =true;
                startActivity(intent);
                break;




        }
        return super.onOptionsItemSelected(item);

    }

    private void onRefersh() {

        Cursor c = sqLiteDatabase.rawQuery("select * from contacts where con_id="+cid,null);
        if(c.moveToFirst()){

            cmobile = c.getString(3);
            cname = c.getString(1);
            ctype=c.getString(2);
            cemail= c.getString(5);
            cphone = c.getString(4);
            cid = c.getInt(0);
            updateView();
        }
        isRefersh = false;


    }
}
