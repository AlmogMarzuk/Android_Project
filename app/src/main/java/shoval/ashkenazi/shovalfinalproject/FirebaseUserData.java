package shoval.ashkenazi.shovalfinalproject;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;


@IgnoreExtraProperties
public class FirebaseUserData {
    public String userId;
    public String userName;
    public String url;

    public FirebaseUserData() {
        // dont delete its will kill you! for firebase FirebaseUserData.class!
    }

    public FirebaseUserData(String userId, String userName, String url) {
        this.userId = userId;
        this.userName = userName;
        this.url = url;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",this.userId);
        map.put("userName",this.userName);
        map.put("url",this.url);
        return map;
    }

    public FirebaseUserData(HashMap<String, Object> map) {
        this.userId = String.valueOf(map.get("userId"));
        this.userName = String.valueOf(map.get("userName"));
        this.url = String.valueOf(map.get("url"));
    }
}
