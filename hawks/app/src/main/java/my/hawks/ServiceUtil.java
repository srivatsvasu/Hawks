package my.hawks;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by VINODHIVYA on 3/14/2015.
 */
public class ServiceUtil {

    private static final String TAG = "ServiceUtil";

    String outputData = null;

    public String callWebService(BackGroundToDo toDo){
        Log.d(TAG, "callWebService");
        if(isOnline(toDo.getNetworkInfo())){
            Log.d(TAG, "Network available");
            MyTask task = new MyTask();
            task.execute(toDo);
        } else {
            Log.d(TAG, "Not Online");
        }

        return outputData;
    }

    protected boolean isOnline(NetworkInfo netInfo){

        if( netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        } else {
            return false;
        }
    }

    private class MyTask extends AsyncTask<BackGroundToDo, String, String> {

        @Override
        protected void onPreExecute() {
            System.out.println("Starting task ..");
        }

        @Override
        protected String doInBackground(BackGroundToDo... params) {

            Log.d(TAG, "doInBackground");
            if(params[0].getServiceMethodCall().equals("postMyLocation")){
                HttpManager.postData(params[0].getMapEntity());
            } /*else if(params[0].getServiceMethodCall().equals("postMyLocation")){
                HttpManager.postData(params[0].getMapEntity());
            }*/

            return outputData;
        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println("Output Data: " + outputData);
        }
    }

}
