package com.example.project;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class AppointmentFragment extends Fragment implements AdapterView.OnItemClickListener{
    ListView mlistview;
    ArrayAdapter<String> adapter;
    ArrayList<String> entries;
    ArrayList<String> DocId;
    public final static String EXTRA_selectedDoc = "com.example.myfirstapp.DOCNAME";
    public AppointmentFragment()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View v= inflater.inflate(R.layout.fragment_appointment, container, false);
        mlistview = v.findViewById(android.R.id.list);
        entries = new ArrayList<String>();
        DocId=new ArrayList<>();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, entries);
        mlistview.setAdapter(adapter);
        mlistview.setOnItemClickListener(this);
        updateData();
        return v;
    }
    public void updateData()
    {
      DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull final DataSnapshot dataSnapshot)
           {
               final String Date=dataSnapshot.child("APPOINTMENT").child("DATE").getValue(String.class);
               final String Time=dataSnapshot.child("APPOINTMENT").child("TIME").getValue(String.class);
               final String Reason= dataSnapshot.child("APPOINTMENT").child("REASON").getValue(String.class);
               String Id="";
               if(dataSnapshot.child("USERTYPE").getValue(String.class).equals("DOCTOR"))
               {
                  Id+= dataSnapshot.child("APPOINTMENT").child("PATIENT ID").getValue(String.class);
               }
               else
               {
                   Id+= dataSnapshot.child("APPOINTMENT").child("DOCTOR ID").getValue(String.class);
               }

              DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(Id);
               ref.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot)
                  {
                      String Fname=snapshot.child("FNAME").getValue(String.class);
                      String Lname=snapshot.child("LNAME").getValue(String.class);
                      adapter.setNotifyOnChange(true);
                      DocId.add(Fname+" "+ Lname);
                      adapter.add("Appointment "+"with "+Fname+" "+Lname+" at "+Time+" on "+Date+" due to "+Reason);
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError dataError) {

                  }
              });


           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {

       String res = DocId.get(i);
        Toast.makeText(getActivity(), res + " selected", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(), DisplayDoc.class);
        intent.putExtra(AppointmentFragment.EXTRA_selectedDoc, res);
        startActivity(intent);

    }
}
