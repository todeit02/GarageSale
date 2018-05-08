package es.us.garagesale.Src;

import android.content.Context;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import es.us.garagesale.R;

/**
 * Created by Tobias on 08/05/2018.
 */

public class SignupValidator
{
    private Context appContext = null;
    private String nationalityPreselection = null;
    private EditText usernameText = null;
    private EditText passwordText = null;
    private EditText emailText = null;
    private EditText realNameText = null;
    private EditText birthDateText = null;
    private Spinner nationalitySelector = null;
    private EditText creditCardNumberText = null;
    private EditText ccValidationCodeText = null;
    private EditText ccEndMonthText = null;
    private EditText ccEndYearText = null;
    private EditText reEnterPasswordText = null;

    public boolean validate()
    {
        boolean valid = validateUsername();
        valid &= validatePassword();
        valid &= validateEmail();
        valid &= validateRealName();
        valid &= validateBirthDate();
        valid &= validateNationality();
        valid &= validateCreditCard();
        valid &= validateReEnterPassword();

        return valid;
    }

    private boolean validateUsername()
    {
        String usernameRegex = appContext.getString(R.string.signup_username_validation_regex);
        String errorText = appContext.getString(R.string.signup_short_username_error);

        boolean isValid = validateRegex(usernameText, usernameRegex, errorText);
        return isValid;
    }

    private boolean validatePassword()
    {
        String passwordRegex = appContext.getString(R.string.signup_password_validation_regex);
        String errorText = appContext.getString(R.string.signup_password_length_error);

        boolean isValid = validateRegex(passwordText, passwordRegex, errorText);
        return isValid;
    }

    private boolean validateEmail()
    {
        String mailRegex = appContext.getString(R.string.signup_mail_validation_regex);
        String errorText = appContext.getString(R.string.signup_invalid_entry);

        boolean isValid = validateRegex(emailText, mailRegex, errorText);
        return isValid;
    }

    private boolean validateRealName()
    {
        String realNameRegex = appContext.getString(R.string.signup_real_name_validation_regex);
        String errorText = appContext.getString(R.string.signup_invalid_entry);

        boolean isValid = validateRegex(realNameText, realNameRegex, errorText);
        return isValid;
    }

    private boolean validateBirthDate()
    {
        String birthDatePattern = appContext.getString(R.string.signup_birth_date_format);

        boolean isValid = validateDate(birthDateText, birthDatePattern);
        return isValid;
    }


    private boolean validateNationality()
    {
        String nationality = nationalitySelector.getSelectedItem().toString();
        return (!nationality.equals(nationalityPreselection));
    }


    private boolean validateCreditCard()
    {
        boolean isValid = validateCreditCardNumber();
        isValid &= validateCCValidationCode();
        isValid &= validateCCEndMonth();
        isValid &= validateCCEndYear();

        return isValid;
    }


    private boolean validateCreditCardNumber()
    {
        String creditCardNumberRegex = appContext.getString(R.string.signup_credit_card_number_validation_regex);
        String errorText = appContext.getString(R.string.signup_invalid_entry);

        boolean isValid = validateRegex(creditCardNumberText, creditCardNumberRegex, errorText);
        return isValid;
    }


    private boolean validateCCValidationCode()
    {
        String ccValidationCodeRegex = appContext.getString(R.string.signup_credit_card_cvc_regex);
        String errorText = appContext.getString(R.string.signup_invalid_entry);

        boolean isValid = validateRegex(ccValidationCodeText, ccValidationCodeRegex, errorText);
        return isValid;
    }


    private boolean validateCCEndMonth()
    {
        String ccEndMonthPattern = appContext.getString(R.string.signup_cc_month_format);

        boolean isValid = validateDate(ccEndMonthText, ccEndMonthPattern);
        return isValid;
    }


    private boolean validateCCEndYear()
    {
        String ccEndYearPattern = appContext.getString(R.string.signup_cc_year_format);

        boolean isValid = validateDate(ccEndYearText, ccEndYearPattern);
        return isValid;
    }


    private boolean validateReEnterPassword()
    {
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        boolean doPasswordsMatch = (!reEnterPassword.isEmpty() && reEnterPassword.length() >= 4 && reEnterPassword.length() <= 10 && reEnterPassword.equals(password));
        String errorText = doPasswordsMatch ? null : appContext.getString(R.string.signup_password_mismatch_error);

        reEnterPasswordText.setError(errorText);
        return doPasswordsMatch;
    }


    private boolean validateDate(EditText dateText, String pattern)
    {
        String date = dateText.getText().toString();
        boolean isValid = (dateText.length() == pattern.length());

        try
        {
            if(!isValid) throw new Exception();
            DateFormat format = new SimpleDateFormat(pattern, Locale.US);
            format.setLenient(false);
            format.parse(date);
        }
        catch(Exception e)
        {
            isValid = false;
        }

        if(isValid) dateText.setError(null);
        else dateText.setError(pattern);

        return isValid;
    }


    private boolean validateRegex(EditText validatingText, String regex, String errorMessage)
    {
        String validatingString = validatingText.getText().toString();

        boolean isValid = validatingString.matches(regex);

        if(isValid) validatingText.setError(null);
        else validatingText.setError(errorMessage);

        return isValid;
    }
    
    public SignupValidator(
            Context appContext,
            String nationalityPreselection,
            EditText usernameText,
            EditText passwordText,
            EditText emailText,
            EditText realNameText,
            EditText birthDateText,
            Spinner nationalitySelector,
            EditText creditCardNumberText,
            EditText ccValidationCodeText,
            EditText ccEndMonthText,
            EditText ccEndYearText,
            EditText reEnterPasswordText
    )
    {
        this.appContext = appContext;
        this.nationalityPreselection = nationalityPreselection;
        this.usernameText = usernameText;
        this.passwordText = passwordText;
        this.emailText = emailText;
        this.realNameText = realNameText;
        this.birthDateText = birthDateText;
        this.nationalitySelector = nationalitySelector;
        this.creditCardNumberText = creditCardNumberText;
        this.ccValidationCodeText = ccValidationCodeText;
        this.ccEndMonthText = ccEndMonthText;
        this.ccEndYearText = ccEndYearText;
        this.reEnterPasswordText = reEnterPasswordText;
    }
}
