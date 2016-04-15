package krishna.mydemo.Login;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import krishna.mydemo.Helpers.CallWebService;
import krishna.mydemo.Helpers.PrefUtils;

/**
 * Created by krishnakumar on 15-04-2016.
 */
public class LoginPresenter implements LoginView{
    private Context _ctx;
    private LoginView loginView;
    private String Login_URL = "http://alphademo.in/interview/services/users/userAuthentication.php";

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


    private void resendOTP(){
        JSONObject userObject = null;


        try {
            userObject = new JSONObject();

         /*   userObject.put("edubuzz_id", PrefUtils.getEduBuzzID(OTPActivity.this));
            userObject.put("device_id",PrefUtils.getDeviceID(OTPActivity.this));
            userObject.put("school_code",""+ PrefUtils.getSchoolCode1(OTPActivity.this));
            userObject.put("studentId",""+ PrefUtils.getStudent1(OTPActivity.this));
*/

        } catch (Exception e) {
            e.printStackTrace();
        }

        loginView.showProgressDialog();
        Log.e("obj", userObject.toString());

        final JSONObject finalUserObject = userObject;
        new CallWebService(Login_URL, CallWebService.TYPE_POST, finalUserObject) {
            @Override
            public void response(String response) {
                loginView.hideProgressDialog();

                JSONObject data;
                Log.e("response", response + "");

                try {

                    JSONObject mainObj = new JSONObject(response);




                } catch (Exception e) {
                    Log.e("Exc####", e.toString());
                    e.printStackTrace();
                }


            }

            @Override
            public void error(String error) {
                //pd.dismiss();
                loginView.hideProgressDialog();
                Log.e("error", error);
            }
        }.call();
    }
}
