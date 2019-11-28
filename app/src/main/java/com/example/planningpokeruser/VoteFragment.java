package com.example.planningpokeruser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VoteFragment extends Fragment implements View.OnClickListener
{
    Button button0;
    Button button1_2;
    Button button1;
    Button button2;
    Button button3;
    Button button5;
    Button button8;
    Button button13;
    Button button20;
    Button button40;
    Button button100;
    Button buttoncoffee;
    Button submit;

    TextView answer;
    TextView question;
    String group;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_vote, container, false);

        question=v.findViewById(R.id.votequestion);
        question.setText(getArguments().getString("question"));
        group=getArguments().getString("groupname");

        answer=v.findViewById(R.id.answer);

        button0=v.findViewById(R.id.button0);
        button1_2=v.findViewById(R.id.button1_2);
        button1=v.findViewById(R.id.button1);
        button2=v.findViewById(R.id.button2);
        button3=v.findViewById(R.id.button3);
        button5=v.findViewById(R.id.button5);
        button8=v.findViewById(R.id.button8);
        button13=v.findViewById(R.id.button13);
        button20=v.findViewById(R.id.button20);
        button40=v.findViewById(R.id.button40);
        button100=v.findViewById(R.id.button100);
        buttoncoffee=v.findViewById(R.id.buttoncoffee);
        submit=v.findViewById(R.id.submit);

        button0.setOnClickListener(this);
        button1_2.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button5.setOnClickListener(this);
        button8.setOnClickListener(this);
        button13.setOnClickListener(this);
        button20.setOnClickListener(this);
        button40.setOnClickListener(this);
        button100.setOnClickListener(this);
        buttoncoffee.setOnClickListener(this);
        submit.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.button0:
                answer.setText(button0.getText().toString());
                break;
            case R.id.button1_2:
                answer.setText(button1_2.getText().toString());
                break;
            case R.id.button1:
                answer.setText(button1.getText().toString());
                break;
            case R.id.button2:
                answer.setText(button2.getText().toString());
                break;
            case R.id.button3:
                answer.setText(button3.getText().toString());
                break;
            case R.id.button5:
                answer.setText(button5.getText().toString());
                break;
            case R.id.button8:
                answer.setText(button8.getText().toString());
                break;
            case R.id.button13:
                answer.setText(button13.getText().toString());
                break;
            case R.id.button20:
                answer.setText(button20.getText().toString());
                break;
            case R.id.button40:
                answer.setText(button40.getText().toString());
                break;
            case R.id.button100:
                answer.setText(button100.getText().toString());
                break;
            case R.id.buttoncoffee:
                answer.setText(buttoncoffee.getText().toString());
                break;
            case R.id.submit:
                if(!answer.getText().toString().matches(""))
                {
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("Answers").child(group).child(question.getText().toString()).child(MainActivity.getUser()).setValue(answer.getText().toString());

                    Fragment togroups=new AllGroupsFragment();
                    FragmentTransaction transaction=((FragmentActivity)getContext()).getSupportFragmentManager().beginTransaction();

                    transaction.replace(R.id.mainactivity,togroups);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else
                {
                    Toast.makeText(getActivity(),"You should vote!",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
