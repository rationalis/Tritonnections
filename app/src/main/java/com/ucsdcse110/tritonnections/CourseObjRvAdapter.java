package com.ucsdcse110.tritonnections;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ucsdcse110.tritonnections.databinding.CourseObjBinding;

import java.util.List;

public class CourseObjRvAdapter extends RecyclerView.Adapter<CourseObjRvAdapter.BindingHolder> {
    public static class BindingHolder extends RecyclerView.ViewHolder {
        private CourseObjBinding binding;
        public BindingHolder(CourseObjBinding binding) {
            super(binding.getRoot());
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
    public BindingHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        CourseObjBinding binding = CourseObjBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder bindingHolder, int i) {
        final CourseObj course = courses.get(i);
        switch (course.type)
        {
            case LE:

        }
        bindingHolder.getBinding().setVariable(BR.course, course);
        bindingHolder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}