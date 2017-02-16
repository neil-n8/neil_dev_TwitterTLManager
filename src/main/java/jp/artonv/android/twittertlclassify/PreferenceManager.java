package jp.artonv.android.twittertlclassify;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

//SharedPrefencesのデータ管理用
public class PreferenceManager {
    private static final String OAUTH_TOKEN = "oauth_token";
    private static final String OAUTH_SECRETTOKEN = "oauth_token_secret";

    private SharedPreferences sharedPreferences;

    PreferenceManager(Context context){
        this.sharedPreferences = context.getSharedPreferences(ConstAppInfo.PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public String getAccessToken() {
        return sharedPreferences.getString(OAUTH_TOKEN, "");
    }

    public String getAccessSecret() {
        return sharedPreferences.getString(OAUTH_SECRETTOKEN, "");
    }

    public void setAccessTokenAndSecret(String accessToken, String accessSecret) {
        Editor editor = sharedPreferences.edit();
        editor.putString(OAUTH_TOKEN, accessToken);
        editor.putString(OAUTH_SECRETTOKEN, accessSecret);
        editor.commit();
    }
}
