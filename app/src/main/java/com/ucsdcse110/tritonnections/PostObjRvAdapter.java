package com.ucsdcse110.tritonnections;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ucsdcse110.tritonnections.databinding.PostObjBinding;

import java.util.List;

public class PostObjRvAdapter extends RecyclerView.Adapter<PostObjRvAdapter.BindingHolder> {
    public static class BindingHolder extends RecyclerView.ViewHolder {
        private PostObjBinding binding;
        public BindingHolder(PostObjBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public PostObjBinding getBinding() {
            return binding;
        }
    }

    private List<PostObj> posts;

    public PostObjRvAdapter(List<PostObj> posts) {
        this.posts = posts;
    }
    
    @Override
    public BindingHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        PostObjBinding binding = PostObjBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder bindingHolder, int i) {
        final PostObj post = posts.get(i);
        bindingHolder.getBinding().setVariable(BR.post, post);
        bindingHolder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setData(List<PostObj> list) {
        posts = list;
    }
}