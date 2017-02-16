package jp.artonv.android.twittertlclassify;

import java.util.ArrayList;

import twitter4j.ResponseList;
import twitter4j.Status;

//カテゴリ別お気に入りツイートリストを持つ
public class FavoriteTweetDatas {
    private int categoryID;
    private ArrayList<Status> dataList;

    public FavoriteTweetDatas(int categoryID, ArrayList<Status> dataList) {
        this.categoryID = categoryID; //カテゴリID
        this.dataList = dataList; //カテゴリに属するツイートリスト
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public ArrayList<Status> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<Status> dataList) {
        this.dataList = dataList;
    }


    //リストにツイートを追加する(単一)
    public void addTweetSingle(Status newTweet){
        if(dataList == null || newTweet == null){
            return;
        }

        //追加対象と既存ツイートの重複チェック
        for(Status status : dataList){
            if(status.getId() == newTweet.getId()){
                return;
            }
        }
        dataList.add(newTweet);
    }

    //リストにツイートを追加する(リスト単位)
    public void addTweetList(ResponseList<Status> newTweet){
        if(dataList == null || newTweet == null){
            return;
        }

        //追加対象と既存ツイートの重複チェック
        Boolean isExist = false;
        for (Status newStatus : newTweet){
            isExist = false;
            for(Status status : dataList){
                if(status.getId() == newStatus.getId()){
                    isExist = true;
                    break;
                }
            }
            if(!isExist){
                dataList.add(newStatus);
            }
        }
    }
}
