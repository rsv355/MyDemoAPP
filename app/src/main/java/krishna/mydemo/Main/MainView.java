package krishna.mydemo.Main;

import java.io.File;

/**
 * Created by Krish on 16/4/2016.
 */
public interface MainView {

    void showProgressDialog();
    void hideProgressDialog();
    void doLogin();
    void onLoginSucess(String msg);
    void onLoginFailure(String msg);

}
