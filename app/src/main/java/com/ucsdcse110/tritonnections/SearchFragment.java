package com.ucsdcse110.tritonnections;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.ucsdcse110.tritonnections.task.LoadCoursesTaskBuilder;
import com.ucsdcse110.tritonnections.task.LoadQuartersTask;

import java.util.HashMap;
import java.util.Map;

import static com.ucsdcse110.tritonnections.task.LoadCoursesTaskBuilder.SourceType.SCHEDULE_OF_CLASSES;


public class SearchFragment extends OptionsMenuFragment {
    private Map<String, String> terms;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        System.out.println("reached onCreateView");
        final View view = inflater.inflate(R.layout.activity_search, container, false);

        Button button = (Button) view.findViewById(R.id.search_button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText editText = (EditText) getActivity().findViewById(R.id.edit_message);

                String query = editText.getText().toString();
                try {
                    CoursesRecyclerViewFragment rvf = new CoursesRecyclerViewFragment();
                    Bundle args = new Bundle();
                    args.putString("query",query);
                    args.putSerializable("course source", SCHEDULE_OF_CLASSES);
                    rvf.setArguments(args);

                    getFragmentManager().beginTransaction()
                            .replace(container.getId(), rvf)
                            .addToBackStack(null)
                            .commit();

                    final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        isSearchable = false;
        isSelectable = true;
        setHasOptionsMenu(true);

        return view;
    }

    public SearchView.OnQueryTextListener getSearchListener() {
        return null;
    }

    public void populateDropdown(final Menu menu, final int[] ids) {
        new LoadQuartersTask() {
            @Override
            public void onPostExecute(Map<String, String> terms) {
                SearchFragment.this.terms = terms;
                int i = 0;
                for (String s : terms.keySet()) {
                    if (i >= ids.length) break;

                    menu.findItem(ids[i]).setVisible(true);
                    menu.findItem(ids[i]).setTitle(s);
                    i++;
                }
                System.out.println(terms.toString());
            }
        }.execute();
    }
}
