package com.example.project;


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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
public class PrescriptionFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView mlistview;
    ArrayAdapter<String> adapter;
    ArrayList<String> entries;
    public PrescriptionFragment()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_prescription, container, false);
        mlistview =v.findViewById(android.R.id.list);
        entries = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, entries);
        mlistview.setAdapter(adapter);
        mlistview.setOnItemClickListener(this);
        updateData();
        return v;
    }
    public void updateData()
    {
       DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("PRESCRIPTION");
       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot)
           {
               for(DataSnapshot snapshot:dataSnapshot.getChildren())
               {
                  String DocId= snapshot.getKey();
                  final String Medicines=snapshot.child("MEDICINES").getValue(String.class);
                  FirebaseDatabase.getInstance().getReference().child("Users").child(DocId).addValueEventListener(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot Snapshot)
                      {
                      String Fname=Snapshot.child("FNAME").getValue(String.class);
                      String Lname=Snapshot.child("LNAME").getValue(String.class);
                      adapter.setNotifyOnChange(true);
                      adapter.add(Fname+" "+Lname + " Prescribed " + Medicines +" to you");
                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError databaseError) {

                      }
                  });
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
