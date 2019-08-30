package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment  implements AdapterView.OnItemClickListener
{
    public final static String EXTRA_selectedDoc = "com.example.myfirstapp.DOCNAME";
    ListView mlistview;
    EditText mSearchInput;
    Button mSearchBtn;
    ArrayAdapter<String> adapter;
    ArrayList<String> entries;

    public HomeFragment()
    {

    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_home, container, false);
        mlistview = v.findViewById(android.R.id.list);
        mSearchInput = v.findViewById(R.id.search_input);
        mSearchBtn=v.findViewById(R.id.submit_button);
        entries = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, entries);
        mlistview.setAdapter(adapter);
        mlistview.setOnItemClickListener(this);
        updateData();
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                final String sInput = mSearchInput.getText().toString().toUpperCase();
                DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Users");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for(DataSnapshot snapshot: dataSnapshot.getChildren())
                        {
                            if(snapshot.child("USERTYPE").getValue().equals("DOCTOR")&&snapshot.child("SPECIALITY").getValue().equals(sInput))
                            {
                                String fname=snapshot.child("FNAME").getValue(String.class);
                                String lname=snapshot.child("LNAME").getValue(String.class);
                                adapter.setNotifyOnChange(true);
                                adapter.add(fname + " " + lname);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });

            }
        });
        return  v;
    }
    public void updateData()
    {
        adapter.clear();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    if(snapshot.child("USERTYPE").getValue().equals("DOCTOR"))
                    {
                        String fname=snapshot.child("FNAME").getValue(String.class);
                        String lname=snapshot.child("LNAME").getValue(String.class);
                        adapter.setNotifyOnChange(true);
                        adapter.add(fname + " " + lname);
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        String res2 = adapter.getItem(i);
        Toast.makeText(getActivity(), res2 + " selected", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(), DisplayDoc.class);
        intent.putExtra(EXTRA_selectedDoc, res2);
        startActivity(intent);

    }
}
