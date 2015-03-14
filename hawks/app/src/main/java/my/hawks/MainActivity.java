package my.hawks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONObject;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private ViewGroup mListView;
    private static MapEntity entity = null;
    private NetworkInfo netInfo;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entity =  AccountUtil.getMyAccount(this);

        // Set up the action bar.
        //final ActionBar actionBar = getActionBar();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        //actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, locationListener);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 0, locationListener);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();

        mListView = (ViewGroup) findViewById(R.id.list);

        setListener(R.id.btn_locate_my_friend, LocateMyFriendActivity.class);
        setListener(R.id.btn_friends_near_by, NearbyEntitiesActivity.class);
    }

    private void setListener(int id, Class<? extends Activity> activityClass) {
        Button button = (Button) findViewById(id);
        button.setOnClickListener(this);
        button.setTag(activityClass);

        /*
        Button b = new Button(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        b.setLayoutParams(layoutParams);
        b.setText(demoName);
        b.setTag(activityClass);
        b.setOnClickListener(this);*/
        //mListView.addView(button);
    }

    @Override
    public void onClick(View view) {
        Class activityClass = (Class) view.getTag();
        Intent intent = new Intent(this, activityClass);
        intent.putExtra("UserId", entity.getUserId());
        startActivity(intent);
    }

    public void postMyLocation(MapEntity entity){
        Log.d(TAG, "postMyLocation");
        JSONObject jsonobj = new JSONObject();

        try{
            jsonobj.put("UserName", entity.getUserName());
            jsonobj.put("UserID", entity.getUserId());
            jsonobj.put("Latitude", entity.getLatitude().toString());
            jsonobj.put("Longitude", entity.getLongitude().toString());

            BackGroundToDo toDo = new BackGroundToDo();
            toDo.setServiceURL("http://javagrasp.info/hawks/postMyLoc/postIt");
            toDo.setInputJSONObj(jsonobj);
            toDo.setNetworkInfo(netInfo);
            String outputData = new ServiceUtil().callWebService(toDo);

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            entity.setLatitude(location.getLatitude());
            entity.setLongitude(location.getLongitude());

            postMyLocation(entity);

            Log.d(TAG, "Entering location lat :" + location.getLatitude() + " lng:" + location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
