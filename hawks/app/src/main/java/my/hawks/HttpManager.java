package my.hawks;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by C807601 on 3/8/2015.
 */
public class HttpManager {

    private static final String TAG = "HttpManager";

    public static List<MapEntity> getEntityLocations(MapEntity entity) {
        List<MapEntity> entities = new ArrayList<MapEntity>();
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("");
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();


            JSONObject jsonobj = new JSONObject();
            jsonobj.put("UserID", entity.getUserId());
            jsonobj.put("UserName", entity.getUserName());
            jsonobj.put("Latitude", entity.getLatitude());
            jsonobj.put("NearMiles", entity.getNearMile());
            pairs.add(new BasicNameValuePair("findMyFriends", jsonobj.toString()));

            post.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));


            HttpResponse httpresponse = client.execute(post);


            String responseText = null;

            responseText = EntityUtils.toString(httpresponse.getEntity());

          /*
            MapEntity entity1 = new MapEntity();
            entity1.setId("1");
            entity1.setUserName("Arun");
            entity1.setLatitude(32.8698);
            entity1.setLongitude(-97.453);

            MapEntity entity2 = new MapEntity();
            entity2.setId("1");
            entity2.setUserName("Vinod");
            entity2.setLatitude(32.8598);
            entity2.setLongitude(-97.253);

            MapEntity entity3 = new MapEntity();
            entity3.setId("1");
            entity3.setUserName("Mruthula");
            entity3.setLatitude(32.83598);
            entity3.setLongitude(-97.3453);

            MapEntity entity4 = new MapEntity();
            entity4.setId("1");
            entity4.setUserName("Viga");
            entity4.setLatitude(32.8398);
            entity4.setLongitude(-97.483);

            entities.add(entity1);

            entities.add(entity2);
            entities.add(entity3);
            entities.add(entity4);

*/

        } catch (Exception e) {
            e.printStackTrace();
        }


        return entities;
    }

    public static String callWebService(String url, JSONObject jsonobj) {

        String responseText = null;

        try {

            Log.d(TAG, "callWebService");
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();

            pairs.add(new BasicNameValuePair("latLongData", jsonobj.toString()));

            post.setEntity(new UrlEncodedFormEntity(pairs,"UTF-8"));
          /*  StringEntity se = new StringEntity(jsonobj.toString());
            post.setContentType("application/json;charset=UTF-8");
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));*/

            //post.setEntity(se);


            HttpResponse httpresponse = client.execute(post);
            responseText = EntityUtils.toString(httpresponse.getEntity());
            Log.d(TAG, "postData - Complete: " + responseText);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseText;
    }

}
