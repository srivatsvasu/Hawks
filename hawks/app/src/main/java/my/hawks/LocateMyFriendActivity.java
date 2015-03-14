package my.hawks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class LocateMyFriendActivity extends ActionBarActivity implements View.OnClickListener {
    private ViewGroup mFriendsListView;
    private static final String TAG = "LocateMyFriendActivity";
    private NetworkInfo netInfo;
    private ArrayList<MapEntity> friendsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_my_friend);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();

        mFriendsListView = (ViewGroup) findViewById(R.id.friendList);

        String userId = getIntent().getExtras().getString("UserId");

        friendsList = getAllMyFriends(userId);

        for(int i=0;i<friendsList.size();i++){
            addModule(friendsList.get(i).getUserName(), i, LocateMyFriendMapActivity.class);
        }
    }

    private void addModule(String friendName, int buttonId, Class<? extends Activity> activityClass) {
        Button b = new Button(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        b.setLayoutParams(layoutParams);
        b.setText(friendName);
        b.setId(buttonId);
        b.setTag(activityClass);
        b.setOnClickListener(this);
        mFriendsListView.addView(b);
    }

    @Override
    public void onClick(View view) {
        Class activityClass = (Class) view.getTag();
        Intent intent = new Intent(this, activityClass);
        intent.putExtra("userId", friendsList.get(view.getId()).getUserId());
        startActivity(intent);
    }

    public ArrayList<MapEntity> getAllMyFriends(String userId){
        ArrayList<MapEntity> friendsList = null;

        JSONObject jsonobj = new JSONObject();
        JSONArray jsonArray = null;
        MapEntity entity = null;

        try{

            jsonobj.put("UserID", userId);

            BackGroundToDo toDo = new BackGroundToDo();
            toDo.setServiceURL("http://javagrasp.info/hawks/postMyLoc/postIt");
            toDo.setInputJSONObj(jsonobj);
            toDo.setNetworkInfo(netInfo);
            String outputData = new ServiceUtil().callWebService(toDo);

            if(outputData != null){
                jsonArray = new JSONArray(outputData);
                friendsList = new ArrayList<MapEntity>();
            }

            for(int i=0; i<jsonArray.length(); i++){
                entity = new MapEntity();
                entity.setUserName(jsonArray.getJSONObject(i).getString("UserName"));
                entity.setUserId(jsonArray.getJSONObject(i).getString("UserId"));
                friendsList.add(entity);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return friendsList;
    }

}
