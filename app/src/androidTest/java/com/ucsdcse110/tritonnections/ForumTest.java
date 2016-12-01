package com.ucsdcse110.tritonnections;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.view.KeyEvent;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

public class ForumTest {
    @Rule
    public ActivityTestRule<NavigationDrawerMainActivity> mActivityRule = new ActivityTestRule<>(
            NavigationDrawerMainActivity.class);

    @Test
    public void testForum() {
        DrawerActions.openDrawer(R.id.drawer_layout);
        onView(withText("Forum")).perform(click());
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
        onView(withId(R.id.search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("hello"));
        onView(withText("world")).check(matches(isDisplayed()));
    }
}
