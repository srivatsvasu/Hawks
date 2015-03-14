package my.hawks;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;

/**
 * Created by vinod on 3/14/2015.
 */
public class AccountUtil {

    public static MapEntity getMyAccount(Context context) {
        AccountManager am = AccountManager.get(context);
        Account[] accounts = am.getAccounts();
        MapEntity mapEntity = new MapEntity();
        for (Account ac : accounts) {
            String acname = ac.name;
            String actype = ac.type;
            if (actype.equals("com.google")) {
                mapEntity.setId(ac.name);
                mapEntity.setUserName(ac.name.length()>5?ac.name.substring(0,5):"Unknown");
                break;
            }
        }


        return mapEntity;
    }
}
