package krishna.mydemo.Main;

import android.content.Context;

import krishna.mydemo.Register.RegisterView;

/**
 * Created by Krish on 16/4/2016.
 */
public class MainPresenter implements MainView {
    private Context _ctx;
    private MainView mainView;
    private String USER_INFO_URL = "http://alphademo.in/interview/services/users/getUserDetails.php";
    public MainPresenter(Context context, MainView mainView) {
        this._ctx = context;
        this.mainView = mainView;
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

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
