package com.example.planningpokeruser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    ArrayList<String> usernames=new ArrayList<>();
    private static String user;

    private DatabaseReference mDatabase;
    private Integer id=0;
    private static Integer userid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState==null)
        {
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.mainactivity,new LoginFragment()).addToBackStack(null).commit();
        }

        loadData();
    }

    private void loadData()
    {
        usernames.clear();

        FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    usernames.add(snapshot.getKey());
                    Integer num=snapshot.getValue(Integer.class);

                    if( num > id)
                    {
                        id=num;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void addUserOrLogin(View v)
    {
        EditText username=findViewById(R.id.username);

        if(username.getText().toString().matches(""))
        {
            Toast.makeText(this,"You should write a username!",Toast.LENGTH_SHORT).show();
        }
        else if(userExistence(username.getText().toString()))
        {
            Fragment viewgroups = new AllGroupsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            user=username.getText().toString();
            username.setText("");

            transaction.replace(R.id.mainactivity, viewgroups);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else
        {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Users").child(username.getText().toString()).setValue(id+1);
            user=username.getText().toString();
            username.setText("");

            userid=id+1;

            Fragment viewgroups = new AllGroupsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.mainactivity, viewgroups);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        loadData();
    }

    public boolean userExistence(String username)
    {
        for (int i=0;i<usernames.size();i++)
        {
            if(usernames.get(i).matches(username))
            {
                return true;
            }
        }

        return false;
    }

    public static String getUser()
    {
        return user;
    }

    public static void loadUserId()
    {
        FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if(snapshot.getKey().equals(user))
                    {
                        userid=snapshot.getValue(Integer.class);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public static Integer getUserId()
    {
        return userid;
    }
}
