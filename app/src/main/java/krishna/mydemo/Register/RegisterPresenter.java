package krishna.mydemo.Register;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import krishna.mydemo.Helpers.PrefUtils;
/**
 * Created by Krish on 16/4/2016.
 */
public class RegisterPresenter implements RegisterView {
    private Context _ctx;
    private RegisterView registerView;
    private String Register_URL = "http://alphademo.in/interview/services/users/userRegistration.php";
    private boolean isRegisterSucesfull = false;
    private String responseString = "";
    public RegisterPresenter(Context context, RegisterView registerView) {
        this._ctx = context;
        this.registerView = registerView;
    }


    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void doLogin(final File f, final String email, final String password, final String firstname, final String age, final String gender) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                registerView.showProgressDialog();
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                registerView.hideProgressDialog();

                if(responseString.isEmpty()){
                    registerView.onLoginFailure("Error While Registration.");
                }else{
                    try {
                        JSONObject obj = new JSONObject(responseString);
                        if (obj.getString("STATUS").equals("1")){
                            registerView.onLoginSucess(obj.getString("MESSAGE"));
                        }else{
                            registerView.onLoginFailure(obj.getString("MESSAGE"));
                        }
                    }catch (Exception e){}
                }

            }

            @Override
            protected Void doInBackground(Void... params) {

                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
                    HttpPost httppost = new HttpPost(Register_URL);
                    String boundary = "--";
                    httppost.setHeader("Content-type", "multipart/form-data; boundary=" + boundary);

                    Bitmap b = BitmapFactory.decodeFile(f.getAbsolutePath());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    b.compress(Bitmap.CompressFormat.JPEG, 85, baos);
                    byte[] imageBytes = baos.toByteArray();
                    ByteArrayBody bab = new ByteArrayBody(imageBytes, new File(f.getAbsolutePath()).getName() + ".jpg");

                    HttpEntity entity = MultipartEntityBuilder.create()
                            .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                            .setBoundary(boundary)
                            .addPart("email", new StringBody(email))
                            .addPart("photo", bab)
                            .addPart("password", new StringBody(password))
                            .addPart("firstname", new StringBody(firstname))
                            .addPart("age", new StringBody(age))
                            .addPart("gender", new StringBody(gender))
                            .addPart("user_type", new StringBody("2"))
                            .addPart("devicetoken", new StringBody(PrefUtils.deviceID(_ctx)))
                            .addPart("devicetype", new StringBody("android"))
                            .addPart("data", new StringBody("form-multi-part"))
                            .addPart("action", new StringBody("userRegistration"))
                            .build();

                    httppost.setEntity(entity);
                    try {
                        HttpResponse response = httpclient.execute(httppost);

                        entity = response.getEntity();
                        final String response_str = EntityUtils.toString(entity);
                        if (entity != null) {
                            Log.e("RESPONSE", response_str);
                            responseString = response_str;
                        }
                    } catch (Exception e) {
                        registerView.hideProgressDialog();
                        Log.e("Exception1", e.toString());
                    }
                } catch (Exception e) {
                    registerView.hideProgressDialog();
                    Log.e("Exception2", e.toString());
                }
            return null;
            }

        }.execute();
    }

    @Override
    public void onLoginSucess(String msg) {

    }

    @Override
    public void onLoginFailure(String msg) {

    }

}
