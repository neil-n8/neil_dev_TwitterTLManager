package jp.artonv.android.twittertlclassify;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

//新着(未分類)のお気に入りツイート管理画面
public class FavoriteTweetList extends Activity {

    private int categoryID;
    private ArrayList<FavoriteTweetCardData> listData;
    private FavoriteTweetListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favoritetweetlist);

        //初期設定
        categoryID = this.getIntent().getExtras().getInt("categoryID");
        listData = new ArrayList<>();
        RecyclerView rv = (RecyclerView)findViewById(R.id.favTweetListBody);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        adapter = new FavoriteTweetListAdapter(listData);
        rv.setAdapter(adapter);

        //お気に入りツイートの取得
        new AsyncGetFavoriteTweets().execute();
    }

    //リストを初期化してから更新する
    private void initList(){
        listData.clear();
        //カテゴリリストから読み込み
        for(Status status : FavoriteTweetCategoryManager.getInstance().getDataList().get(categoryID).getDataList()){
            FavoriteTweetCardData data = new FavoriteTweetCardData(status.getUser().getName(), ConstCategoryName.NAME[categoryID], status.getText(),categoryID);
            listData.add(data);
        }
        adapter.notifyDataSetChanged();
    }
    //お気に入りツイート取得サブスレッド
    private class AsyncGetFavoriteTweets extends AsyncTask<Void,Void,ResponseList<twitter4j.Status>> {
        @Override
        protected ResponseList<twitter4j.Status> doInBackground(Void... params) {
            try {
                TwitterAccessManager twitterAccessManager = TwitterAccessManager.getInstance();
                Twitter twitter = TwitterAccessManager.getInstance().getTwitterInstance();
                return twitter.getFavorites(twitterAccessManager.getAccessToken().getUserId());
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponseList<twitter4j.Status> result){
            if(result == null){
                return;
            }

            //新着リストの実体をここで記録
            FavoriteTweetCategoryManager.getInstance().getDataList().get(ConstCategoryID.LATEST).addTweetList(result);
            //暫定処理ユーザーカテゴリにツイートを強制的に割り振る
            for(int i=0;i<result.size();i++){
                if(0 == i % 2){
                    FavoriteTweetCategoryManager.getInstance().getDataList().get(ConstCategoryID.INFO).addTweetSingle(result.get(i));
                }
                if(0 == i % 3){
                    FavoriteTweetCategoryManager.getInstance().getDataList().get(ConstCategoryID.MEDIA).addTweetSingle(result.get(i));
                }
            }
            initList();
        }
    }
}
