package com.qmul.nminoiu.tunein;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MediaPlayerTest {

    @Rule
    public ActivityTestRule<LogoActivity> mActivityTestRule = new ActivityTestRule<>(LogoActivity.class);

    @Test
    public void mediaPlayerTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.email_sign_in_button), withText("Tune In!"),
                        childAtPosition(
                                allOf(withId(R.id.layoutlogin),
                                        childAtPosition(
                                                withId(R.id.login_form),
                                                0)),
                                3)));
        appCompatButton.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_search), withContentDescription("Search..."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.slistView),
                        childAtPosition(
                                withId(R.id.searchLayout),
                                1)))
                .atPosition(2);
        appCompatTextView.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button),
                        childAtPosition(
                                allOf(withId(R.id.play_toolbar),
                                        childAtPosition(
                                                withId(R.id.cLayout),
                                                6)),
                                2),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.button),
                        childAtPosition(
                                allOf(withId(R.id.play_toolbar),
                                        childAtPosition(
                                                withId(R.id.cLayout),
                                                6)),
                                2),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.play_toolbar),
                        childAtPosition(
                                allOf(withId(R.id.cLayout),
                                        childAtPosition(
                                                withId(R.id.drawer_layout),
                                                0)),
                                6),
                        isDisplayed()));
        linearLayout.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.btnForward),
                        childAtPosition(
                                allOf(withId(R.id.controls),
                                        childAtPosition(
                                                withId(R.id.layout),
                                                2)),
                                3),
                        isDisplayed()));
        imageButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageButton2 = onView(
                allOf(withId(R.id.btnForward),
                        childAtPosition(
                                allOf(withId(R.id.controls),
                                        childAtPosition(
                                                withId(R.id.layout),
                                                2)),
                                3),
                        isDisplayed()));
        imageButton2.perform(click());

        ViewInteraction imageButton3 = onView(
                allOf(withId(R.id.btnForward),
                        childAtPosition(
                                allOf(withId(R.id.controls),
                                        childAtPosition(
                                                withId(R.id.layout),
                                                2)),
                                3),
                        isDisplayed()));
        imageButton3.perform(click());

        ViewInteraction imageButton4 = onView(
                allOf(withId(R.id.btnForward),
                        childAtPosition(
                                allOf(withId(R.id.controls),
                                        childAtPosition(
                                                withId(R.id.layout),
                                                2)),
                                3),
                        isDisplayed()));
        imageButton4.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageButton5 = onView(
                allOf(withId(R.id.btnBackward),
                        childAtPosition(
                                allOf(withId(R.id.controls),
                                        childAtPosition(
                                                withId(R.id.layout),
                                                2)),
                                1),
                        isDisplayed()));
        imageButton5.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageButton6 = onView(
                allOf(withId(R.id.btnBackward),
                        childAtPosition(
                                allOf(withId(R.id.controls),
                                        childAtPosition(
                                                withId(R.id.layout),
                                                2)),
                                1),
                        isDisplayed()));
        imageButton6.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageButton7 = onView(
                allOf(withId(R.id.btnNext),
                        childAtPosition(
                                allOf(withId(R.id.controls),
                                        childAtPosition(
                                                withId(R.id.layout),
                                                2)),
                                4),
                        isDisplayed()));
        imageButton7.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageButton8 = onView(
                allOf(withId(R.id.btnShuffle),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.layout),
                                                5)),
                                1),
                        isDisplayed()));
        imageButton8.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageButton9 = onView(
                allOf(withId(R.id.btnRepeat),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.layout),
                                                5)),
                                0),
                        isDisplayed()));
        imageButton9.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageButton10 = onView(
                allOf(withId(R.id.btnPrevious),
                        childAtPosition(
                                allOf(withId(R.id.controls),
                                        childAtPosition(
                                                withId(R.id.layout),
                                                2)),
                                0),
                        isDisplayed()));
        imageButton10.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageButton11 = onView(
                allOf(withId(R.id.btnPlay),
                        childAtPosition(
                                allOf(withId(R.id.controls),
                                        childAtPosition(
                                                withId(R.id.layout),
                                                2)),
                                2),
                        isDisplayed()));
        imageButton11.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
