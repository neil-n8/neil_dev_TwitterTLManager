package jp.artonv.android.twittertlclassify;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

//お気に入りツイートリスト用ビューホルダー
public class FavoriteTweetListViewHolder extends RecyclerView.ViewHolder{
    View view;
    TextView owner; //ユーザー名
    TextView category; //カテゴリ
    TextView sentence; //つぶやき本文

    public FavoriteTweetListViewHolder(View itemView){
        super(itemView);
        this.view = itemView;
        this.owner = (TextView)view.findViewById(R.id.tweetOwner);
        this.category = (TextView)view.findViewById(R.id.tweetCategory);
        this.sentence = (TextView)view.findViewById(R.id.tweetSentence);
    }
}
