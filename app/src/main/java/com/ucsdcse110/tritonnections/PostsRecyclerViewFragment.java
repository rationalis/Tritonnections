package com.ucsdcse110.tritonnections;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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

import static com.ucsdcse110.tritonnections.task.LoadCoursesTaskBuilder.SourceType.SCHEDULE_OF_CLASSES;

public class PostsRecyclerViewFragment extends OptionsMenuFragment {
    private List<PostObj> postList = new ArrayList<>();
    private RecyclerView rv;
    private PostObjRvAdapter adapter;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum_view, container, false);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForumFragment ff = new ForumFragment();
                getFragmentManager().beginTransaction()
                        .replace(container.getId(), ff)
                        .addToBackStack(null)
                        .commit();
            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference();
        initializeAdapter();
        initializeData();

        isSearchable = true;
        isSelectable = true;
        setHasOptionsMenu(true);
        return view;
    }

    public SearchView.OnQueryTextListener getSearchListener() {
        return new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    adapter.setData(postList);
                    adapter.notifyDataSetChanged();
                    return true;
                }

                List<PostObj> list = new ArrayList<>();
                for (PostObj o : postList) {
                    if (o.title.contains(newText)) {
                        list.add(o);
                    }
                }
                adapter.setData(list);
                adapter.notifyDataSetChanged();
                return true;
            }
        };
    }
    public void populateDropdown(int[] ids) {
    }

    private void initializeData(){
        System.out.println("Started initializing data for RV");
        mDatabase.child("postobjs").orderByKey().limitToLast(1000).addChildEventListener(
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
                        postList.add(0, post);
                        adapter.notifyItemInserted(0);
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
