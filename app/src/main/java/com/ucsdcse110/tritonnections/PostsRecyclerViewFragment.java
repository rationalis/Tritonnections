package com.ucsdcse110.tritonnections;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ucsdcse110.tritonnections.task.LoadCoursesTask;
import com.ucsdcse110.tritonnections.task.LoadCoursesTaskBuilder;
import com.ucsdcse110.tritonnections.task.LoadCoursesTaskBuilder.SourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostsRecyclerViewFragment extends Fragment {
    private List<PostObj> postList = new ArrayList<>();
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_recyclerview, container, false);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        initializeAdapter();
        initializeData();
        return view;
    }

    private void initializeData(){
        System.out.println("Started initializing data for RV");
        mDatabase.child("postobjs").orderByKey().limitToLast(50).addChildEventListener(
                new ChildEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        PostObj post = dataSnapshot.getValue(PostObj.class);
//                        postList.add(post);
//                        adapter.notifyItemInserted(postList.size()-1);
//                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Database error: " + databaseError.toString());
                    }

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                        PostObj post = dataSnapshot.getValue(PostObj.class);
                        postList.add(post);
                        adapter.notifyItemInserted(postList.size()-1);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {}

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}
                });
        System.out.println("Finished initializing data for RV");
    }

    private void initializeAdapter(){
        System.out.println("Started initializing adapter for RV");
        adapter = new PostObjRvAdapter(postList);
        rv.setAdapter(adapter);
        System.out.println("Finished initializing adapter for RV");
    }

    public void setData(List<PostObj> data)
    {
        postList = data;
    }
}
