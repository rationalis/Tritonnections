package com.ucsdcse110.tritonnections;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import static com.ucsdcse110.tritonnections.task.LoadCoursesTaskBuilder.SourceType.ENROLLED_CLASSES;
import static com.ucsdcse110.tritonnections.task.LoadCoursesTaskBuilder.SourceType.SCHEDULE_OF_CLASSES;

public class OnClickHandler extends Fragment {
    public void onClickLecture(View view) {
        CoursesRecyclerViewFragment rvf = new CoursesRecyclerViewFragment();
        Bundle args = new Bundle();
        args.putSerializable("course source", SCHEDULE_OF_CLASSES);
        rvf.setArguments(args);
        getFragmentManager().beginTransaction()
                .replace(R.id.flContent, rvf)
                .addToBackStack(null)
                .commit();
    }
}
