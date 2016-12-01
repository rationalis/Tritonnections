package com.ucsdcse110.tritonnections;

import android.support.test.espresso.matcher.ViewMatchers;
import android.test.suitebuilder.annotation.LargeTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.test.AndroidTestCase;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import android.support.test.espresso.contrib.DrawerActions;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class TritonLinkTest {

    private String mAccountToBetyped;
    private String mPasswordToBetyped;

    @Rule
    public ActivityTestRule<NavigationDrawerMainActivity> mActivityRule = new ActivityTestRule<>(
            NavigationDrawerMainActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        mAccountToBetyped = "A14279093";
        mPasswordToBetyped = "123456";
    }

    @Test
    public void testLogin() {
        DrawerActions.openDrawer(R.id.drawer_layout);
        onView(withText("Login")).perform(click());
        onView(withId(R.id.login_form)).check(matches(isDisplayed()));
        onView(withId(R.id.pid)).perform(typeText(mAccountToBetyped));
        onView(withId(R.id.password)).perform(typeText(mPasswordToBetyped));
        onView(withId(R.id.email_sign_in_button)).perform(click());
        //onView(withId(R.id.login_progress)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }
}
