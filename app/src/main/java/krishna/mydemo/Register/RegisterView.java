package krishna.mydemo.Register;

import java.io.File;

/**
 * Created by Krish on 16/4/2016.
 */
public interface RegisterView {
    void showProgressDialog();
    void hideProgressDialog();
    void doLogin(final File f, final String email, final String password, final String firstname, final String age, final String gender);
    void onLoginSucess(String msg);
    void onLoginFailure(String msg);
}
