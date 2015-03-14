package my.hawks;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private ViewGroup mListView;
    private static final String TAG = "MainClassss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        //final ActionBar actionBar = getActionBar();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        //actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, locationListener);

        mListView = (ViewGroup) findViewById(R.id.list);

        setListener(R.id.btn_locate_my_friend, LocateMyFriendActivity.class);
        setListener(R.id.btn_friends_near_by, NearbyEntitiesActivity.class);
        /*addDemo("Clustering: Custom Look", CustomMarkerClusteringDemoActivity.class);
        addDemo("Clustering: 2K markers", BigClusteringDemoActivity.class);
        addDemo("PolyUtil.decode", PolyDecodeDemoActivity.class);
        addDemo("IconGenerator", IconGeneratorDemoActivity.class);
        addDemo("SphericalUtil.computeDistanceBetween", DistanceDemoActivity.class);
        addDemo("Generating tiles", TileProviderAndProjectionDemo.class);
        addDemo("Heatmaps", HeatmapsDemoActivity.class);
        addDemo("Heatmaps with Places API", HeatmapsPlacesDemoActivity.class);*/
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
        startActivity(new Intent(this, activityClass));
    }

    public void postMyLocation(MapEntity entity){
        HttpManager.postData(entity);
    }

    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            MapEntity entity = new MapEntity();
            entity.setUserId("");
            entity.setUserName("");
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
