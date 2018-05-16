package es.us.garagesale.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import es.us.garagesale.DataAccess.DatabaseManager;
import es.us.garagesale.DataAccess.ISignupResponseConsumer;
import es.us.garagesale.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.us.garagesale.Src.Card;
import es.us.garagesale.Src.Person;
import es.us.garagesale.Src.SignupValidator;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private String nationalityPreselection = "";
    private SignupValidator validator = null;

    @BindView(R.id.input_username) EditText _usernameText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_real_name) EditText _realNameText;
    @BindView(R.id.input_phone) EditText _phoneText;
    @BindView(R.id.input_birth_date) EditText _birthDateText;
    @BindView(R.id.spn_nationality) Spinner _nationalitySelector;
    @BindView(R.id.input_credit_card) EditText _creditCardNumberText;
    @BindView(R.id.input_cc_validation_code) EditText _ccValidationCodeText;
    @BindView(R.id.input_cc_end_month) EditText _ccEndMonthText;
    @BindView(R.id.input_cc_end_year) EditText _ccEndYearText;
    @BindView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        CharSequence usernameFromLogin = getIntent().getStringExtra("username");
        if((usernameFromLogin != null) && (usernameFromLogin.length() > 0))
        {
            _usernameText.setText(usernameFromLogin);
        }

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        _birthDateText.setHint(getString(R.string.signup_birth_date_hint) + " - " + getString(R.string.signup_birth_date_format));

        nationalityPreselection = getString(R.string.signup_nationality_default);
        _nationalitySelector.setPrompt(nationalityPreselection);

        validator = new SignupValidator(
                this,
                nationalityPreselection,
                _usernameText,
                _passwordText,
                _emailText,
                _realNameText,
                _birthDateText,
                _phoneText,
                _nationalitySelector,
                _creditCardNumberText,
                _ccValidationCodeText,
                _ccEndMonthText,
                _ccEndYearText,
                _reEnterPasswordText
        );
    }


    public void signup()
    {
        Log.d(TAG, "Signup");

        if (!validator.validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);
        final ProgressDialog progressDialog = showSignupProgressDialog();

        Person registeringPerson = getPersonFromUI();

        DatabaseManager.createPerson(registeringPerson, this, new ISignupResponseConsumer() {
            @Override
            public void consume(boolean wasSignupSuccessful, boolean isUsernameAlreadyTaken) {
                onSignupResponse(wasSignupSuccessful, isUsernameAlreadyTaken);
                progressDialog.dismiss();
                _signupButton.setEnabled(true);
            }
        });
    }


    private void onSignupResponse(boolean wasSignupSuccessful, boolean isUsernameAlreadyTaken)
    {
        if(wasSignupSuccessful) onSignupSuccess();
        else onSignupFailed();

        if(isUsernameAlreadyTaken)
        {
            _usernameText.setError(getString(R.string.signup_username_taken_error));
        }
    }


    public void onSignupSuccess()
    {
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed()
    {
        Toast.makeText(getBaseContext(), getString(R.string.signup_error), Toast.LENGTH_LONG).show();
    }


    private Person getPersonFromUI()
    {
        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();
        String email = _emailText.getText().toString();
        String realName = _realNameText.getText().toString();
        String birthDateString = _birthDateText.getText().toString();
        String phone = _phoneText.getText().toString();
        Date birthDate = new Date();
        try
        {
            SimpleDateFormat birthDateFormat = new SimpleDateFormat(getString(R.string.signup_birth_date_format), Locale.US);
            birthDate = birthDateFormat.parse(birthDateString);
        }
        catch (Exception e) {}

        String nationality = _nationalitySelector.getSelectedItem().toString();
        String creditCardNumber = _creditCardNumberText.getText().toString();
        String ccValidationCode = _ccValidationCodeText.getText().toString();
        int ccEndMonth = Integer.parseInt( _ccEndMonthText.getText().toString() );
        int ccEndYear = Integer.parseInt( _ccEndYearText.getText().toString() );

        final int ccEndDay = 1;
        GregorianCalendar ccEndDateCalendar = new GregorianCalendar(2000 + ccEndYear, (ccEndMonth-1), ccEndDay);
        Date ccEndDate = ccEndDateCalendar.getTime();

        Card enteredCard = new Card(creditCardNumber, ccEndDate, ccValidationCode, "");
        Person enteredPerson = new Person(username, password, realName, email, birthDate, phone, nationality, enteredCard);
        return enteredPerson;
    }


    private ProgressDialog showSignupProgressDialog()
    {
        ProgressDialog progressDialog =  new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.signup_creating_account_progress_message));
        progressDialog.show();
        return progressDialog;
    }


    private Person createMockPerson()
    {
        SimpleDateFormat birthDateFormat = new SimpleDateFormat(getString(R.string.signup_birth_date_format), Locale.US);
        Date birthDate = new Date();
        try {
            birthDate = birthDateFormat.parse("09.10.1995");
        }
        catch (Exception e) {}

        GregorianCalendar ccEndDateCalendar = new GregorianCalendar(2021, 3, 1);
        Date ccEndDate = ccEndDateCalendar.getTime();

        Card registeringCard = new Card("1234123412341234", ccEndDate, "456", "");
        return new Person("Peter", "abc123", "Peter Paúl Peterson", "peter@planet.pl", birthDate, "2313", "Polish", registeringCard);
    }
}