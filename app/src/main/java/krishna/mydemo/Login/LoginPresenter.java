package krishna.mydemo.Login;

import android.content.Context;

/**
 * Created by krishnakumar on 15-04-2016.
 */
public class LoginPresenter implements LoginView{
    private Context _ctx;
    private LoginView loginView;

    public LoginPresenter(Context context, LoginView loginView) {
        this._ctx = context;
        this.loginView = loginView;
    }



    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void doLogin() {
        loginView.showProgressDialog();
    }
}
