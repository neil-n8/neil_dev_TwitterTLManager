package jp.artonv.android.twittertlclassify;

import android.app.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.AsyncTask;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.*;

import android.content.Intent;

/**
 * 当アプリの開始アクティビティ
 * アプリの認証画面、アプリメニューに遷移する
 */
public class LoginTop extends Activity {

    private TwitterAccessManager twitterAccessManager;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainlogintop);

        twitterAccessManager = TwitterAccessManager.getInstance();
        twitterAccessManager.init(getApplicationContext());
        if(twitterAccessManager.isAccessTokenEnable()){
			//認証済み。メニュー画面に即遷移
            transitionTopMenu();
		}

        Button b_login = (Button)findViewById(R.id.login);
        b_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //サブスレッドで認証画面を表示する
                        new AsyncTwitterLogin().execute();
                    }
                }
        );
	}

    public void transitionTopMenu(){
        Intent intentToMenu = new Intent(this, TopMenu.class);
        startActivity(intentToMenu);
        //戻り遷移不可
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        //認証画面からの戻り。サブスレッドでアクセストークン取得
        Uri responseUri = intent.getData();
        String oauthVerifier = responseUri.getQueryParameter("oauth_verifier");
        new AsyncGetAccessToken().execute(oauthVerifier);
    }

    //認証開始用サブスレッド
    private class AsyncTwitterLogin extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Twitter twitter = twitterAccessManager.getTwitterInstance();
                twitterAccessManager.setRequestToken(twitter.getOAuthRequestToken(ConstAppInfo.CALLBACK_URL));
                Uri requestUri = Uri.parse(twitterAccessManager.getRequestToken().getAuthenticationURL());
                Intent intent = new Intent(Intent.ACTION_VIEW, requestUri);
                LoginTop.this.startActivity(intent);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    //認証完了後のアクセストークン取得サブスレッド
    private class AsyncGetAccessToken extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... params) {
            String oauthVerifier = params[0];
            try {
                Twitter twitter = twitterAccessManager.getTwitterInstance();
                AccessToken accessToken = twitter.getOAuthAccessToken(twitterAccessManager.getRequestToken(), oauthVerifier);
                twitterAccessManager.setAccessToken(accessToken);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            transitionTopMenu();
        }
    }
}

