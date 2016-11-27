package com.ucsdcse110.tritonnections;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ucsdcse110.tritonnections.databinding.CourseObjBinding;

import java.util.List;

import static com.ucsdcse110.tritonnections.task.LoadCoursesTaskBuilder.SourceType.SCHEDULE_OF_CLASSES;

public class CourseObjRvAdapter extends RecyclerView.Adapter<CourseObjRvAdapter.BindingHolder> {
    public static class BindingHolder extends RecyclerView.ViewHolder {
        private CourseObjBinding binding;
        public BindingHolder(CourseObjBinding binding) {
            super(binding.getRoot());
            OnClickHandler handlers = new OnClickHandler();
            binding.setHandlers(handlers);
            this.binding = binding;
        }
        public CourseObjBinding getBinding() {
            return binding;
        }
    }

    private List<CourseObj> courses;

    public CourseObjRvAdapter(List<CourseObj> courses) {
        this.courses = courses;
    }
    
    @Override
    public BindingHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        CourseObjBinding binding = CourseObjBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        /*
        viewGroup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CoursesRecyclerViewFragment rvf = new CoursesRecyclerViewFragment();
                Bundle args = new Bundle();
                //args.putString("query", query);
                args.putSerializable("course source", SCHEDULE_OF_CLASSES);
                rvf.setArguments(args);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(viewGroup.getId(), rvf)
                        .addToBackStack(null)
                        .commit();
            }
        });
        */
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder bindingHolder, int i) {
        final CourseObj course = courses.get(i);
        bindingHolder.getBinding().setVariable(BR.course, course);
        bindingHolder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}