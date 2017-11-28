package com.karthik.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by android on 14/10/17.
 */
public class ContactAdapter extends BaseAdapter {

   private List<Contact> contactList;
   private Context context;
   public ContactAdapter(Context context){
       contactList = new LinkedList<>();
       this.context = context;
   }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Contact getItem(int i) {
        return contactList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public void addAll(List<Contact> list){
        contactList.clear();
        contactList.addAll(list);
        notifyDataSetChanged();
    }
    public class ViewHolder{

        public TextView mobnoTextView;
        public TextView cnameTextview ;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.item_contact,viewGroup,false);
            holder = new ViewHolder();
            holder.mobnoTextView= (TextView) view.findViewById(R.id.text_view_lno);
            holder.cnameTextview = (TextView) view.findViewById(R.id.text_view_lname);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        Contact contact =contactList.get(i);
        holder.mobnoTextView.setText(contact.mobileNo);
        holder.cnameTextview.setText(contact.name);
        return view;


    }
}
