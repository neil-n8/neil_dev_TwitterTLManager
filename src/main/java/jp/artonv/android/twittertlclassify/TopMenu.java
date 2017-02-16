package jp.artonv.android.twittertlclassify;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

//アプリのトップメニュー画面
public class TopMenu extends Activity  {

	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.topmenu);

		new AsyncGetUserName().execute();

		Button b_fav = (Button)findViewById(R.id.btn_fav);
		//ユーザー名取得までボタンは使用不可とする
		b_fav.setEnabled(false);
		b_fav.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						favTweetManager_onClick(view);
					}
				}
		);
	}

	//お気に入りツイート管理ボタン押下
	public void favTweetManager_onClick(View v) {
		Intent intent = new Intent(this, FavoriteTweetMain.class);
		startActivity(intent);
	}

	private void setWelcomeUserName(String userName){
		Button b_fav = (Button)findViewById(R.id.btn_fav);
		//ボタン押下可能
		b_fav.setEnabled(true);
		TextView tv_welcomeUser = (TextView)findViewById(R.id.welcomeusername);
		tv_welcomeUser.setText("ようこそ"+userName+"さん");
	}

	//ユーザー名取得サブスレッド
	private class AsyncGetUserName extends AsyncTask<Void,Void,String> {

		@Override
		protected String doInBackground(Void... params) {
			TwitterAccessManager twitterAccessManager = TwitterAccessManager.getInstance();
			Twitter twitter = TwitterAccessManager.getInstance().getTwitterInstance();
			long userId = twitterAccessManager.getAccessToken().getUserId();
			String userName = "";
			try {
				User user = twitter.showUser(userId);
				userName = user.getName();
			}
			catch(TwitterException e)
			{
				e.printStackTrace();
			}
			return userName;
		}

		@Override
		protected void onPostExecute(String result){
			setWelcomeUserName(result);
		}
	}
}
