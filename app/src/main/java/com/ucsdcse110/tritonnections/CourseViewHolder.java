package com.ucsdcse110.tritonnections;


import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static com.ucsdcse110.tritonnections.task.LoadCoursesTaskBuilder.SourceType.SCHEDULE_OF_CLASSES;

public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected CardView cardView;

        public CourseViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            cardView = (CardView) view.findViewById(R.id.cv);
            }

            @Override
            public void onClick(View v) {
                CoursesRecyclerViewFragment rvf = new CoursesRecyclerViewFragment();
                Bundle args = new Bundle();
                args.putSerializable("course source", SCHEDULE_OF_CLASSES);
                rvf.setArguments(args);
            }
}
