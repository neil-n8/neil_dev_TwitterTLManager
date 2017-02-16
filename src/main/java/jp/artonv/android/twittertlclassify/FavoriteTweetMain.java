package jp.artonv.android.twittertlclassify;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import java.util.ArrayList;

import twitter4j.ResponseList;
import twitter4j.Status;

public class FavoriteTweetMain extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favoritetweetmain);

        //暫定処理:カテゴリを3つ作成する
        FavoriteTweetCategoryManager.getInstance().addCategory(new FavoriteTweetDatas(ConstCategoryID.LATEST,new ArrayList<Status>()));
        FavoriteTweetCategoryManager.getInstance().addCategory(new FavoriteTweetDatas(ConstCategoryID.INFO,new ArrayList<Status>()));
        FavoriteTweetCategoryManager.getInstance().addCategory(new FavoriteTweetDatas(ConstCategoryID.MEDIA,new ArrayList<Status>()));
        initTabs();
    }

    private void initTabs(){
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        //新着(未分類のリスト用タブ)
        intent = new Intent(this, FavoriteTweetList.class);
        intent.putExtra("categoryID", ConstCategoryID.LATEST);
        spec = tabHost.newTabSpec("Tab1");
        spec.setIndicator(getString(R.string.latest));
        spec.setContent(intent);
        tabHost.addTab(spec);

        //情報カテゴリタブ
        intent = new Intent(this, FavoriteTweetListUserCategory.class);
        intent.putExtra("categoryID", ConstCategoryID.INFO);
        spec = tabHost.newTabSpec("Tab2");
        spec.setIndicator(getString(R.string.infomationTab));
        spec.setContent(intent);
        tabHost.addTab(spec);

        //メディアカテゴリタブ
        intent = new Intent(this, FavoriteTweetListUserCategory.class);
        intent.putExtra("categoryID", ConstCategoryID.MEDIA);
        spec = tabHost.newTabSpec("Tab3");
        spec.setIndicator(getString(R.string.mediaTab));
        spec.setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
    }

}
