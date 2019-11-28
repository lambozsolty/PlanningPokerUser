package com.example.planningpokeruser;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.GroupsViewHolder>
{
    Context context;
    ArrayList<String> groups;
    private DatabaseReference mDatabase;

    public static class GroupsViewHolder extends RecyclerView.ViewHolder
    {
        public TextView groupname;
        public Button joinbutton;

        public GroupsViewHolder(View v)
        {
            super(v);

            groupname=itemView.findViewById(R.id.groupname);
            joinbutton=itemView.findViewById(R.id.join);
        }
    }

    public GroupsAdapter(Context context,ArrayList<String> dataset)
    {
        this.context=context;
        this.groups=dataset;
    }

    @NonNull
    @Override
    public  GroupsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.allgroups,parent,false);

        return new GroupsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final GroupsViewHolder holder, final int position)
    {
        holder.groupname.setText(groups.get(position));
        holder.joinbutton.setText("Get in");

        holder.joinbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("Groups").child(groups.get(position)).child("Users").child(MainActivity.getUser()).setValue(MainActivity.getUserId());

                Bundle groupnametoquestions=new Bundle();
                groupnametoquestions.putString("groupname",groups.get(position));

                Fragment questions=new QuestionsFragment();
                questions.setArguments(groupnametoquestions);
                FragmentTransaction transaction=((FragmentActivity)context).getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.mainactivity,questions);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return groups.size();
    }
}
