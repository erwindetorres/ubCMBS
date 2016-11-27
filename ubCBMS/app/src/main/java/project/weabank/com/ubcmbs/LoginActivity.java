package project.weabank.com.ubcmbs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import project.weabank.com.ubcmbs.helpers.Commons;
import project.weabank.com.ubcmbs.helpers.ConfigHelper;
import project.weabank.com.ubcmbs.helpers.PreferenceHelper;

/**
 * Created by Kira on 11/26/2016.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private EditText editTextUsername, editTextPassword;
    private ImageView smileUsername, smilePassword;
    boolean isUsernameValid = false;
    boolean isPasswordValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        editTextUsername = (EditText) findViewById(R.id.loginMobileNumber);
        editTextPassword = (EditText) findViewById(R.id.loginPassword);
        smileUsername = (ImageView) findViewById(R.id.smileUsername);
        smilePassword = (ImageView) findViewById(R.id.smilePassword);
        btnLogin.setOnClickListener(this);

        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String msisdn = editTextUsername.getText().toString();

                if(!Commons.isValidMobileNumber(msisdn)){
                    editTextUsername.setError(String.valueOf("Invalid mobile number"));
                    isUsernameValid = false;
                    smileUsername.setVisibility(View.GONE);
                    btnLogin.setBackgroundColor(getResources().getColor(R.color.divider));
                }else{
                    smileUsername.setVisibility(View.VISIBLE);
                    isUsernameValid = true;
                    if(!isPasswordValid){
                        btnLogin.setEnabled(false);
                        btnLogin.setBackgroundColor(getResources().getColor(R.color.divider));
                    }else{
                        btnLogin.setEnabled(true);
                        btnLogin.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_selector));
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = editTextPassword.getText().toString();
                if(!Commons.isValidPassword(password)){
                    editTextPassword.setError(String.valueOf("Invalid Password"));
                    isPasswordValid = false;
                    smilePassword.setVisibility(View.GONE);
                    btnLogin.setBackgroundColor(getResources().getColor(R.color.divider));
                }else{
                    smilePassword.setVisibility(View.VISIBLE);
                    isPasswordValid = true;
                    if(!isUsernameValid){
                        btnLogin.setEnabled(false);
                        btnLogin.setBackgroundColor(getResources().getColor(R.color.divider));
                    }else{
                        btnLogin.setEnabled(true);
                        btnLogin.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default_selector));
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                initLogin(String.valueOf(editTextUsername.getText().toString()), String.valueOf(editTextPassword.getText().toString()));
                break;
            default:
                break;
        }
    }

    private void initLogin(String username, String pass){
        //Commons.hideProgressDialog();
        switch (username){
            case "09064190555":
                PreferenceHelper.setCurrentNo("09064190555");
                PreferenceHelper.setTempLoginID(ConfigHelper.ACCOUNT_KIRA);
                break;
            case "09061234445":
                PreferenceHelper.setCurrentNo("09061234445");
                PreferenceHelper.setTempLoginID(ConfigHelper.ACCOUNT_ERWIN);
                break;
        }
        PreferenceHelper.setCurrentNo(username);
        Intent i =  new Intent(LoginActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();


    }
}
