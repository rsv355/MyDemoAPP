package krishna.mydemo.Login;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import krishna.mydemo.R;

public class LoginActivity extends AppCompatActivity implements LoginView{
    private TextView txtRegister,txtLogin;
    private EditText etUserName,etPassword;
    private LoginPresenter presenter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle(R.string.login);
        init();

        presenter = new LoginPresenter(LoginActivity.this,this);


        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.doLogin();
            }
        });

    }

    private void init(){
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        txtLogin = (TextView)findViewById(R.id.txtLogin);
        txtRegister = (TextView)findViewById(R.id.txtRegister);
        etUserName = (EditText)findViewById(R.id.etUserName);
        etPassword = (EditText)findViewById(R.id.etPassword);
    }

    @Override
    public void showProgressDialog() {
    dialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if(dialog.isShowing())dialog.dismiss();
    }

    @Override
    public void doLogin() {

    }
}
