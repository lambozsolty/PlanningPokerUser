package com.example.planningpokeruser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder>
{
    Context context;
    ArrayList<Answer> answers;

    public static  class AnswerViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name;
        public TextView answer;

        public AnswerViewHolder(View v)
        {
            super(v);

            name=itemView.findViewById(R.id.qusername);
            answer=itemView.findViewById(R.id.qanswer);
        }
    }

    public AnswerAdapter(Context context, ArrayList<Answer> dataset)
    {
        this.context=context;
        this.answers=dataset;
    }

    @NonNull
    @Override
    public  AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.answer,parent,false);

        return new AnswerAdapter.AnswerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerViewHolder holder, final int position)
    {
        holder.answer.setText(answers.get(position).getAnswer());
        holder.name.setText(answers.get(position).getName());
    }

    @Override
    public int getItemCount()
    {
        return answers.size();
    }
}
