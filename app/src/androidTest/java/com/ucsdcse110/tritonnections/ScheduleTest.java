package com.ucsdcse110.tritonnections;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

public class ScheduleTest {
    @Rule
    public ActivityTestRule<NavigationDrawerMainActivity> mActivityRule = new ActivityTestRule<>(
            NavigationDrawerMainActivity.class);

    @Before
    public void setupSchedule() {
        DrawerActions.openDrawer(R.id.drawer_layout);
        onView(withText("Schedule of classes")).perform(click());
    }

    @Test
    public void testSchedule() {
        onView(withId(R.id.edit_message)).perform(typeText("CSE 110"));
        onView(withId(R.id.search_button1)).perform(click());
        onView(withId(R.id.rv)).check(matches(hasDescendant(withText("Software Engineering"))));
    }

    @Test
    public void testSchedule2() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Winter Quarter 2017")).perform(click());
        onView(withId(R.id.edit_message)).perform(typeText("CSE 110"));
        onView(withId(R.id.search_button1)).perform(click());
        onView(withId(R.id.rv)).check(matches(hasDescendant(withText("Gillespie, Gary N"))));
    }
}
