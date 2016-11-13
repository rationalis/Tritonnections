package com.ucsdcse110.tritonnections.task;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.ucsdcse110.tritonnections.CoursesRecyclerViewFragment;
import com.ucsdcse110.tritonnections.NavigationDrawer;
import android.support.v4.app.Fragment;

import static com.ucsdcse110.tritonnections.task.LoadCoursesTaskBuilder.SourceType.SCHEDULE_OF_CLASSES;

/**
 * Created by Chris_Ozawa on 11/13/16.
 */
public class ForumFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        System.out.println("reached onCreateView");
        final View view = inflater.inflate(R.layout.activity_search, container, false);

        Button button = (Button) view.findViewById(R.id.forum_new_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(NavigationDrawer.this).create();
                alertDialog.setTitle("Login Required");
                alertDialog.setMessage("Please login first.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        return view;
    }
}
