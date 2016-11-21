package com.ucsdcse110.tritonnections;

import android.content.DialogInterface;
import android.os.Bundle;
import android.app.AlertDialog; //was import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.Fragment;

import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
//import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

// TODO: Complete Post objects with title, user, etc.
// TODO: Use FirebaseRecyclerAdapter and generalize RecyclerViewFragment
// TODO: Authentication based on PID

public class ForumFragment extends Fragment {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private EditText messageBody;
    private Button submitButton;
    private Button checkLastButton;

    private String lastPost;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        System.out.println("reached onCreateView");
        final View view = inflater.inflate(R.layout.fragment_forum, container, false);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        messageBody = (EditText) view.findViewById(R.id.forum_edit_message);
        submitButton = (Button) view.findViewById(R.id.forum_new_post_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });

        checkLastButton = (Button) view.findViewById(R.id.forum_check_last_post_button);
        checkLastButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Latest Post Submitted");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                mDatabase.child("posts").orderByKey().limitToLast(1).addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() != null) {
                                    alertDialog.setMessage(((Map<String, Object>)dataSnapshot.getValue())
                                            .values()
                                            .toArray(new String[0])[0]);
                                } else {
                                    alertDialog.setMessage("No posts available");
                                }
                                alertDialog.show();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("Database error: " + databaseError.toString());
                            }
                        });
            }
        });

        return view;
    }

    private void submitPost() {
        //final String title = mTitleField.getText().toString();
        final String body = messageBody.getText().toString();

        // Title is required
//        if (TextUtils.isEmpty(title)) {
//            mTitleField.setError(REQUIRED);
//            return;
//        }

        // Body is required
        if (TextUtils.isEmpty(body)) {
            messageBody.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(getActivity(), "Posting...", Toast.LENGTH_SHORT).show();

        writeNewPost(body);
        setEditingEnabled(true);
    }

    private void setEditingEnabled(boolean enabled) {
//        mTitleField.setEnabled(enabled);
        messageBody.setEnabled(enabled);
        if (enabled) {
            submitButton.setVisibility(View.VISIBLE);
        } else {
            submitButton.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]
//    private void writeNewPost(String userId, String username, String title, String body) {
    private void writeNewPost(String body) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("posts").push().getKey();
        //Post post = new Post(userId, username, title, body);
        //Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/"+key, body);
//        childUpdates.put("/posts/" + key, postValues);
//        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }
    // [END write_fan_out]
}