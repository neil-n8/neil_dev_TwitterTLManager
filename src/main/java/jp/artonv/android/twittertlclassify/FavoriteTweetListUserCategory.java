package jp.artonv.android.twittertlclassify;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import twitter4j.Status;

//ユーザーで作成したカテゴリに属するお気に入りツイートリスト画面
public class FavoriteTweetListUserCategory extends Activity{
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

        initList();
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
}
