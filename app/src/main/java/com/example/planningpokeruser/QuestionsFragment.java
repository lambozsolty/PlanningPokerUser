package com.example.planningpokeruser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class QuestionsFragment extends Fragment
{
    static String groupchild;
    ArrayList<Question> questions=new ArrayList<>();
    private RecyclerView recyclerview;
    private RecyclerView.Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_questions, container, false);
        groupchild=getArguments().getString("groupname");


        loadData();

        recyclerview=v.findViewById(R.id.questionsrecyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.addItemDecoration(new DividerItemDecoration(v.getContext(), DividerItemDecoration.VERTICAL));

        return v;
    }

    private void loadData()
    {
        questions.clear();

        FirebaseDatabase.getInstance().getReference().child("Groups").child(groupchild).child("Questions").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Question question=new Question();

                    question.setName(snapshot.getKey());
                    question.setStatus(snapshot.getValue().toString());

                    questions.add(question);
                }

                adapter=new QuestionsAdapter(getActivity(),questions);
                recyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public static String getGroup()
    {
        return groupchild;
    }
}
