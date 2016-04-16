package krishna.mydemo.Main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import krishna.mydemo.Helpers.PrefUtils;
import krishna.mydemo.Login.LoginActivity;
import krishna.mydemo.R;

public class MainActivity extends AppCompatActivity implements MainView {

    private TextView txtEmail,txtName;
    private ImageView imgProfile;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Fetching your account details...");
        dialog.setCancelable(false);

        imgProfile = (ImageView)findViewById(R.id.imgProfile);
        txtName = (TextView)findViewById(R.id.txtName);
        txtEmail = (TextView)findViewById(R.id.txtEmail);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                PrefUtils.setLogin(MainActivity.this,false);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    @Override
    public void onLoginSucess(String msg) {

    }

    @Override
    public void onLoginFailure(String msg) {

    }
}
