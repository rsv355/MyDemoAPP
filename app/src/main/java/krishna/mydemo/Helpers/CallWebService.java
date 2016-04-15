package krishna.mydemo.Helpers;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public abstract class CallWebService implements IService {


    public abstract void response(String response);

    public abstract void error(String error);

    private String url;
    String response = null;

    JSONObject userObject;

    public static int TYPE_GET = 100;
    public static int TYPE_POST = 200;

    public static int TYPE_JSONOBJECT = 0;
    public static int TYPE_JSONARRAY = 1;
    public static int TYPE_STRING = 2;
    public int type = 0, methodType = 0;
    public static int TIME_OUT = 30 * 1000;

    public CallWebService(String url, int type) {
        super();
        this.url = url;
        this.methodType = type;
    }

    public CallWebService(String url, int methodType, JSONObject userObject) {
        super();
        this.url = url;
        this.methodType = methodType;
        this.userObject = userObject;
    }


    public synchronized final CallWebService start() {
        call();
        return this;
    }

    public void call() {

        switch (methodType) {

            // case  for requesting json object, GET type
            case 100:
                JsonObjectRequest request = new JsonObjectRequest(url, null,
                        new Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject jobj) {
                                try {

                                    response(jobj.toString());

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    error("Server error,"+e.toString());
                                }

                            }
                        }, new ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError e) {
                        // TODO Auto-generated method stub
                        error("Server error,"+e.toString());
                    }
                });
                request.setRetryPolicy(
                        new DefaultRetryPolicy(
                                TIME_OUT,
                                0,
                                0));


                MyApplication.getInstance().addToRequestQueue(request);

                break;

            // case for requesting json object, POST Type
            case 200:

                JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.POST, url, userObject, new Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jobj) {

                        try {
                            response(jobj.toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                            error("Server error,"+e.toString());
                        }

                    }
                }, new ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError e) {

                        error("Server error,"+e.toString());
                    }
                });
                request2.setRetryPolicy(
                        new DefaultRetryPolicy(
                                TIME_OUT,
                                0,
                                0));
                MyApplication.getInstance().addToRequestQueue(request2);


                break;

            case 2:

                break;

        }

    }


}
