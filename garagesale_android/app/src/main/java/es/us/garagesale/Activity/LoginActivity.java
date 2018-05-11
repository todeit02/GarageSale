package es.us.garagesale.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Debug;
import android.provider.ContactsContract;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import es.us.garagesale.DataAccess.DatabaseManager;
import es.us.garagesale.DataAccess.ILoginResponseConsumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.us.garagesale.DataAccess.ISuccessConsumer;
import es.us.garagesale.DataAccess.PhotoUploader;
import es.us.garagesale.R;

public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private ProgressDialog progressDialog = null;

    @BindView(es.us.garagesale.R.id.input_birth_date) EditText _usernameText;
    @BindView(es.us.garagesale.R.id.input_password) EditText _passwordText;
    @BindView(es.us.garagesale.R.id.btn_login) Button _loginButton;
    @BindView(es.us.garagesale.R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // REMOVE in production.
        boolean debug_loginIsJumped = false;
        if(debug_loginIsJumped)
        {
            continueToMainActivity();
            return;
        }

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                intent.putExtra("username", _usernameText.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if(!validate()) return;

        _loginButton.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this,
                es.us.garagesale.R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autentificando...");
        progressDialog.show();

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        DatabaseManager.checkLoginCredentials(this, username, password, new ILoginResponseConsumer() {
            @Override
            public void consume(boolean areCredentialsValid, String username, String password) {
                onLoginResponse(areCredentialsValid, username, password);
            }
        });
    }

    private void onLoginResponse(boolean areCredentialsValid, String username, String password)
    {
        progressDialog.dismiss();

        if(!areCredentialsValid)
        {
            onLoginFailed();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString("username", username);
        sharedPreferencesEditor.commit();

        onLoginSuccess();

        /*

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
                */
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_SIGNUP && resultCode == RESULT_OK)
        {
            //this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the OfferListActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        continueToMainActivity();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), getString(R.string.login_error), Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty())
        {
            _usernameText.setError(getString(R.string.login_username_missing_error));
            valid = false;
        }
        else {
            _usernameText.setError(null);
        }

        if (password.isEmpty()) {
            _passwordText.setError(getString(R.string.login_password_missing_error));
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    private void continueToMainActivity()
    {
        Intent intent = new Intent(LoginActivity.this, OfferListActivity.class);
        startActivity(intent);
        finish();
    }
}