package jp.artonv.android.twittertlclassify;

import java.util.ArrayList;

//各カテゴリのお気に入りツイートを管理する
public class FavoriteTweetCategoryManager {
    private static FavoriteTweetCategoryManager favoriteTweetCategoryManager = new FavoriteTweetCategoryManager();
    private ArrayList<FavoriteTweetDatas> dataList = new ArrayList<>(); //カテゴリのリスト

    private FavoriteTweetCategoryManager(){};

    public static FavoriteTweetCategoryManager getInstance(){
        return favoriteTweetCategoryManager;
    }

    //カテゴリ別リストの新規追加
    public void addCategory(FavoriteTweetDatas newData) {
        if(newData==null){
            return;
        }

        //既に同じIDのカテゴリがないか確認
        for(FavoriteTweetDatas data: dataList){
            if(data.getCategoryID() == newData.getCategoryID()) {
                return;
            }
        }

        dataList.add(newData);
    }

    public ArrayList<FavoriteTweetDatas> getDataList() {
        return dataList;
    }
}
