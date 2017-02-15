package com.example.jcaruso.fishproject.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import com.example.jcaruso.fishproject.MainActivity;
import com.example.jcaruso.fishproject.R;
import com.example.jcaruso.fishproject.signin.SigninActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends MvpViewStateActivity<LoginView, LoginPresenter> implements LoginView {

    private static final int REQUEST_CODE = 1;

    @BindView(R.id.login_username)
    TextInputEditText mUsernameInput;

    @BindView(R.id.login_password)
    TextInputEditText mPasswordInput;

    @BindView(R.id.login_login_button)
    View mLoginButton;

    @BindView(R.id.login_signin_button)
    View mSigninButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        setRetainInstance(true);

        ButterKnife.bind(this);

        mLoginButton.setOnClickListener(onClickLogin);
        mSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this, SigninActivity.class), REQUEST_CODE);
            }
        });
    }


    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    private View.OnClickListener onClickLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean valid = true;

            String username = mUsernameInput.getText().toString();
            if (username.isEmpty()) {
                valid = false;
                mUsernameInput.setError(v.getContext().getString(R.string.error_empty));
            }

            String password = mPasswordInput.getText().toString();
            if (password.isEmpty()) {
                valid = false;
                mPasswordInput.setError(v.getContext().getString(R.string.error_empty));
            }

            if (valid)
                presenter.doLogin(username, password);
            else
                showError(new Exception(""));
        }
    };

    @Override
    public ViewState<LoginView> createViewState() {
        return new LoginViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        presenter.onNewInstance();
    }

    @Override
    public void showLoginForm() {
        ((LoginViewState) viewState).setShowLoginForm();
        mUsernameInput.setError(null);
        mPasswordInput.setError(null);
        mSigninButton.setVisibility(View.INVISIBLE);
        mLoginButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(Throwable exception) {
        ((LoginViewState) viewState).setShowError(exception);
        mSigninButton.setVisibility(View.VISIBLE);
        mLoginButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        ((LoginViewState) viewState).setShowLoading();
        mLoginButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void loginSuccessful() {
        mLoginButton.setVisibility(View.VISIBLE);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                showLoginForm();
            }
        }
    }
}