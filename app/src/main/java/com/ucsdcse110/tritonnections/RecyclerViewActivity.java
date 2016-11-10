package com.ucsdcse110.tritonnections;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ucsdcse110.tritonnections.task.LoadCapeGpaTask;
import com.ucsdcse110.tritonnections.task.LoadScheduleCardsTask;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends Activity {

    private List<CourseObj> courseList = new ArrayList<CourseObj>();
    private RecyclerView rv;
    //private RVAdapter adapter;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recyclerview);

        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        //initializeAdapter();
    }

    private void initializeData(){
        System.out.println("Started initializing data for RV");
        LoadScheduleCardsTask task = new LoadScheduleCardsTask();
        task.execute(getIntent().getStringExtra("query"));
        try {
            courseList = task.get();
            initializeAdapter();
            for (CourseObj course : courseList) {
                if (course.department == null ||
                        course.courseCode == null ||
                        course.instructor == null ||
                        course.department.isEmpty() ||
                        course.courseCode.isEmpty() ||
                        course.instructor.isEmpty()) continue;

                new LoadCapeGpaTask(course, adapter).execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //CourseObj.DayOfWeek[] a = {CourseObj.DayOfWeek.Tu, CourseObj.DayOfWeek.F};
            //courseList.add(new CourseObj("123456",CourseObj.MeetingType.LE, "ABC", a, "1PM", "2PM", "erc", "abc", 10, 100));
        }
        System.out.println("Finished initializing data for RV");
    }

    private void initializeAdapter(){
        System.out.println("Started initializing adapter for RV");
        // adapter = new RVAdapter(courseList);
        adapter = new CourseObjRvAdapter(courseList);
        rv.setAdapter(adapter);
        System.out.println("Finished initializing adapter for RV");
    }

    public void setData(List<CourseObj> data)
    {
        courseList = data;
    }
}
