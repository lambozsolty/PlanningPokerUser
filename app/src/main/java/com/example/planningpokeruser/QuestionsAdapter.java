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

import java.util.ArrayList;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder>
{
    Context context;
    ArrayList<Question> questions;

    public static class QuestionsViewHolder extends RecyclerView.ViewHolder
    {
        public TextView questionname;
        public Button votebutton;
        public Button answersbutton;

        public QuestionsViewHolder(View v)
        {
            super(v);

            questionname=v.findViewById(R.id.questionname);
            votebutton=v.findViewById(R.id.vote);
            answersbutton=v.findViewById(R.id.answers);
        }
    }

    public QuestionsAdapter(Context context,ArrayList<Question> dataset)
    {
        this.context=context;
        this.questions=dataset;
    }

    @NonNull
    @Override
    public  QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.question,parent,false);

        return new QuestionsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionsViewHolder holder, final int position)
    {
        holder.questionname.setText(questions.get(position).getName());

        if(questions.get(position).getStatus().equals("Inactive"))
        {
           holder.votebutton.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.votebutton.setVisibility(View.VISIBLE);
        }

        holder.votebutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Bundle groupnametovote=new Bundle();
                groupnametovote.putString("groupname",QuestionsFragment.getGroup());
                groupnametovote.putString("question",questions.get(position).getName());

                Fragment vote = new VoteFragment();
                vote.setArguments(groupnametovote);
                FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.mainactivity, vote);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        holder.answersbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Bundle groupnametoanswers=new Bundle();
                groupnametoanswers.putString("groupname",QuestionsFragment.getGroup());
                groupnametoanswers.putString("question",questions.get(position).getName());

                Fragment answers=new AnswersFragment();
                answers.setArguments(groupnametoanswers);
                FragmentTransaction transaction=((FragmentActivity)context).getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.mainactivity, answers);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return questions.size();
    }
}
