package jp.artonv.android.twittertlclassify;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Context;

//Twitterへのアクセスを中継するクラス
public class TwitterAccessManager {
    private static TwitterAccessManager twitterAccessManager = new TwitterAccessManager();
    private PreferenceManager preferenceManager = null;
    private TwitterFactory twitterFactory;
    private RequestToken requestToken = null;
    private AccessToken accessToken = null;

    private TwitterAccessManager(){}

    public static TwitterAccessManager getInstance(){
        return twitterAccessManager;
    }

    //当クラスインスタンスの初期設定
    public void init(Context context) {
        if(preferenceManager != null)
        {
            //初期化は一度のみ
            return;
        }
        preferenceManager = new PreferenceManager(context);
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(ConstSecretKey.CONSUMER_KEY);
        builder.setOAuthConsumerSecret(ConstSecretKey.CONSUMER_SECRET);
        twitterFactory = new TwitterFactory(builder.build());
        String token = preferenceManager.getAccessToken();
        String tokenSecret = preferenceManager.getAccessSecret();
        if (!(token.equals("")) && !(tokenSecret.equals(""))) {
            accessToken = new AccessToken(token, tokenSecret);
        }
    }

    public Twitter getTwitterInstance() {
        return twitterFactory.getInstance(accessToken);
    }

    public boolean isAccessTokenEnable() {
        return accessToken != null;
    }

    public RequestToken getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(RequestToken requestToken) {
        this.requestToken = requestToken;
    }
    public AccessToken getAccessToken() {
        return accessToken ;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
        preferenceManager.setAccessTokenAndSecret(accessToken.getToken(), accessToken.getTokenSecret());
    }
}
