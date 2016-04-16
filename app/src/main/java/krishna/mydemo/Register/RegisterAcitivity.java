package krishna.mydemo.Register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import krishna.mydemo.Helpers.PrefUtils;
import krishna.mydemo.R;

public class RegisterAcitivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmail,etName,etPassword,etCnfPassword,etAge;
    private RadioButton rdMale,rdFeMale;
    private TextView txtRegister;
    private boolean isProfilePicSelected=false;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_acitivity);
        getSupportActionBar().setTitle(R.string.reg);

        init();
    }

    private void init() {
        txtRegister = (TextView)findViewById(R.id.txtRegister);
        rdMale = (RadioButton)findViewById(R.id.rdMale);
        rdFeMale = (RadioButton)findViewById(R.id.rdFeMale);

        etEmail = (EditText)findViewById(R.id.etEmail);
        etName = (EditText)findViewById(R.id.etName);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etCnfPassword = (EditText)findViewById(R.id.etCnfPassword);
        etAge = (EditText)findViewById(R.id.etAge);
        txtRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtRegister:
                if(PrefUtils.isInternetConnected(RegisterAcitivity.this)){
                    if(!etEmail.getText().toString().matches(emailPattern) || etEmail.getText().toString().isEmpty()){
                        Toast.makeText(RegisterAcitivity.this, "Please enter valid email.", Toast.LENGTH_SHORT).show();

                    }else  if(etName.getText().toString().isEmpty()){
                        Toast.makeText(RegisterAcitivity.this, "Please enter name.", Toast.LENGTH_SHORT).show();

                    }else  if(etPassword.getText().toString().isEmpty()){
                        Toast.makeText(RegisterAcitivity.this, "Please enter password.", Toast.LENGTH_SHORT).show();

                    }else  if(etCnfPassword.getText().toString().isEmpty()){
                        Toast.makeText(RegisterAcitivity.this, "Please enter confirm password.", Toast.LENGTH_SHORT).show();

                    }else if(!etPassword.getText().toString().equals(etCnfPassword.getText().toString())){
                        Toast.makeText(RegisterAcitivity.this, "Password do not match.", Toast.LENGTH_SHORT).show();

                    }else  if(etAge.getText().toString().isEmpty()){
                        Toast.makeText(RegisterAcitivity.this, "Please enter age.", Toast.LENGTH_SHORT).show();

                    }else if(!isProfilePicSelected){
                        Toast.makeText(RegisterAcitivity.this, "Please select Profile picture.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisterAcitivity.this, "all validation.", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(RegisterAcitivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
