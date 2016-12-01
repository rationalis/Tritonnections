package com.ucsdcse110.tritonnections;

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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


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
    public void chooseLoginFromMenu() {
        // Type text and then press the button.

        onView(withId(R.id.nav_login))
                .perform(click())
                .check(matches(isDisplayed()));

    }

    @Test
    public void inputPID() {
        // Type text and then press the button.
        onView(withId(R.id.pid))
                .perform(typeText(mAccountToBetyped));
        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView(withId(R.id.pid))
                .check(matches(withText(mAccountToBetyped)));
    }

    @Test
    public void inputPassword() {
        // Type text and then press the button.
        onView(withId(R.id.password))
                .perform(typeText(mPasswordToBetyped));
        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView(withId(R.id.password))
                .check(matches(withText(mPasswordToBetyped)));
    }

    @Test
    public void inputPIDandPassword() {
        // Type text and then press the button.
        onView(withId(R.id.pid))
                .perform(typeText(mAccountToBetyped));
        onView(withId(R.id.password))
                .perform(typeText(mPasswordToBetyped));
        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView(withId(R.id.login_progress))
                .check(matches(isDisplayed()));
    }
}
