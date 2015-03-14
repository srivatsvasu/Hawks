package my.hawks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class LocateMyFriendActivity extends ActionBarActivity implements View.OnClickListener {
    private ViewGroup mFriendsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_my_friend);

        mFriendsListView = (ViewGroup) findViewById(R.id.friendList);
        addModule("Arun", LocateMyFriendMapActivity.class);
        addModule("Vinodh", LocateMyFriendMapActivity.class);
        addModule("Srivatz", LocateMyFriendMapActivity.class);
        addModule("Vinod", LocateMyFriendMapActivity.class);
    }

    private void addModule(String moduleName, Class<? extends Activity> activityClass) {
        Button b = new Button(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        b.setLayoutParams(layoutParams);
        b.setText(moduleName);
        b.setTag(activityClass);
        b.setOnClickListener(this);
        mFriendsListView.addView(b);
    }

    @Override
    public void onClick(View view) {
        Class activityClass = (Class) view.getTag();
        Intent intent = new Intent(this, activityClass);
        intent.putExtra("userId", "G Arun");
        startActivity(intent);
    }
}
