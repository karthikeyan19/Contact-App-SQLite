package com.karthik.contacts;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

/**
 * Created by android on 27/10/17.
 */


public class AddContactActivity extends ActionBarActivity {
private  int cid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        final EditText nameEditText= (EditText) findViewById(R.id.edit_text_name);
        final EditText mobnoEditText= (EditText) findViewById(R.id.edit_text_mob);
        final EditText phoneNoEditText= (EditText) findViewById(R.id.edit_text_phone);
        final EditText emailIdEditText = (EditText) findViewById(R.id.edit_text_email);
        final Spinner typeSpinner = (Spinner) findViewById(R.id.spinner_type);

        final ArrayAdapter<String> types = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new String[]{"Home",
                "office"});
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        typeSpinner.setAdapter(types);

        final Button addButton = (Button) findViewById(R.id.button_add);
        Intent intent = getIntent();
        if(intent.getExtras()!=null){

            if(intent.getStringExtra("dtype").equals("Home")){
               typeSpinner.setSelection(0);
             }else{
                typeSpinner.setSelection(1);
            }
            cid = intent.getIntExtra("d_cid", -1);
            addButton.setText("Update Contact");
            addButton.setTag("1");
            nameEditText.setText(intent.getStringExtra("dname"));
            phoneNoEditText.setText(intent.getStringExtra("dphone"));
            mobnoEditText.setText(intent.getStringExtra("dmob"));
            emailIdEditText.setText(intent.getStringExtra("demail"));
        }



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase sqLiteDatabase = ContactApplication.getInstance().getDatabase();

                if(addButton.getTag().toString().equals("0")){
                Cursor r = sqLiteDatabase.rawQuery("select * from " + ContactApplication.TABLE_NAME, null);
                r.moveToLast();


                String q = "insert into contacts(con_id,cname,type,mobno,phoneno,email_id) values("+(r.getPosition()+1)+",'"+nameEditText.getText()+"','"
                        +typeSpinner.getSelectedItem()+"','"
                        +mobnoEditText.getText()+"','"
                        +phoneNoEditText.getText()+"','"
                        +emailIdEditText.getText()+"')";
                try{
                sqLiteDatabase.execSQL(q);
                    Toast.makeText(getApplicationContext(),"sucessfully Added"+r.getPosition()+1,Toast.LENGTH_SHORT).show();


                }catch (SQLException e){
                    Toast.makeText(AddContactActivity.this,"Error in Insertion"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.e("dad",e.getMessage());

                }
                }else{
                    String q = "update contacts set type='"+typeSpinner.getSelectedItem()+"',"
                            +"mobno='"+mobnoEditText.getText()+"',"
                            +"phoneno='"+phoneNoEditText.getText()+"',"
                            +"email_id='"+emailIdEditText.getText()+"' where con_id="+cid;
                    try{
                        sqLiteDatabase.execSQL(q);
                        Toast.makeText(getApplicationContext(),"sucessfully Updated",Toast.LENGTH_SHORT).show();
                        onBackPressed();

                    }catch (SQLException e){
                        Toast.makeText(AddContactActivity.this,"Error in Updation"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        Log.e("dad",e.getMessage());

                    }

                }
            }
        });







    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();

        }
        return super.onOptionsItemSelected(item);

    }
}
