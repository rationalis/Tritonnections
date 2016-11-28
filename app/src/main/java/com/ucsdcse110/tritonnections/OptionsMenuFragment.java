package com.ucsdcse110.tritonnections;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Jimmy on 11/28/2016.
 */

public abstract class OptionsMenuFragment extends Fragment {
    protected boolean isSearchable;
    protected boolean isSelectable;

    public void setupUI(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.navigation_drawer, menu);
        //setupUI(getView().findViewById(R.id.root_layout));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu1) {
            return true;
        } else if (id == R.id.menu2) {
            return true;
        } else if (id == R.id.menu3) {
            return true;
        } else if (id == R.id.menu4) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.search).setVisible(isSearchable);
        if (isSearchable) {
            MenuItem menuItem = menu.findItem(R.id.search);
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
            searchView.setOnQueryTextListener(getSearchListener());
        }

        if (isSelectable) {
            int[] ids = new int[]{
                    R.id.menu1, R.id.menu2, R.id.menu3, R.id.menu4, R.id.menu5, R.id.menu6,
                    R.id.menu7, R.id.menu8};
            populateDropdown(menu, ids);
        }
        /**
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            public boolean onQueryTextSubmit(String query) {
                Log.i("tag",query);
                Toast.makeText(getContext(), "begin search", Toast.LENGTH_SHORT).show();
                return true;
            }
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        int[] ids = new int[]{R.id.menu1, R.id.menu2, R.id.menu3, R.id.menu4, R.id.menu5, R.id.menu6, R.id.menu7, R.id.menu8};
        menu.findItem(R.id.menu1).setVisible(isSelectable);
        menu.findItem(R.id.menu2).setVisible(isSelectable);
        menu.findItem(R.id.menu3).setVisible(isSelectable);
        menu.findItem(R.id.menu4).setVisible(isSelectable);
        if (isSearchable) {
            menu.findItem(R.id.menu1).setTitle("CSE 110");
            menu.findItem(R.id.menu2).setTitle("CSE 120");
            menu.findItem(R.id.menu3).setTitle("CSE 101");
            menu.findItem(R.id.menu4).setTitle("CSE 105");
        }
         **/
        super.onPrepareOptionsMenu(menu);
    }

    public abstract SearchView.OnQueryTextListener getSearchListener();
    public abstract void populateDropdown(Menu m, int[] ids);
}
