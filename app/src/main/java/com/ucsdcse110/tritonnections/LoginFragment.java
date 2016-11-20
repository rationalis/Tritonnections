package com.ucsdcse110.tritonnections;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ucsdcse110.tritonnections.task.HTTPRequestTask;
import com.ucsdcse110.tritonnections.task.TritonlinkLoginTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * A login screen that offers login via email/password.
 */
public class LoginFragment extends Fragment {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private LoginTestTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mPidView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);
        mPidView = (AutoCompleteTextView) view.findViewById(R.id.pid);

        mPasswordView = (EditText) view.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) view.findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = view.findViewById(R.id.login_form);
        mProgressView = view.findViewById(R.id.login_progress);

        return view;
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mPidView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String pid = mPidView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check if password was entered.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(pid)) {
            mPidView.setError(getString(R.string.error_field_required));
            focusView = mPidView;
            cancel = true;
        } else if (!isPidValid(pid)) {
            mPidView.setError("This PID is invalid");
            focusView = mPidView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            //mAuthTask = new LoginTestTask(pid, password);
            //mAuthTask.execute();
            new LoginTestTask(TritonlinkLoginManager.login(pid, password)).execute();
        }
    }

    private boolean isPidValid(String pid) {
        return pid.matches("A\\d*");
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public class LoginTestTask extends HTTPRequestTask<String, String> {
        private TritonlinkLoginTask task;

        public LoginTestTask(TritonlinkLoginTask task) {
            this.task = task;
        }

        protected String doInBackground(String... params) {
            try {
                return task.get();
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }

        protected void onPostExecute(String response) {
            showProgress(false);

            try {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                String title;
                String message;
                final boolean successful = exception == null;
                if (successful) {
                    Document doc = Jsoup.parse(response);
                    Element nameElement = doc.select("div > h2").first();
                    if (nameElement == null) {
                        nameElement = doc.select("#tdr_login_content > strong").first();
                    }
                    String name = nameElement.ownText();
                    System.out.println(name);

                    title = "Login Successful";
                    message = "Hello, " + name + "!";
                } else {
                    title = "Login Failed";
                    message = exception.getMessage();
                }

                alertDialog.setTitle(title);
                alertDialog.setMessage(message);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (successful) {
                                    getFragmentManager().popBackStack();
                                }
                            }
                        });
                alertDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

