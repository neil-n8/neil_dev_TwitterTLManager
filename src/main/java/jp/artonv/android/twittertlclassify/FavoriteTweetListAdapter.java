package jp.artonv.android.twittertlclassify;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

//お気に入りツイートリスト用アダプター
public class FavoriteTweetListAdapter extends RecyclerView.Adapter<FavoriteTweetListViewHolder>{

    private ArrayList<FavoriteTweetCardData> dataList;
    private onItemClickListener listener;

    public FavoriteTweetListAdapter(ArrayList<FavoriteTweetCardData> list){
        this.dataList = list;
    }

    @Override
    public FavoriteTweetListViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favoritetweetcard,parent, false);
        return new FavoriteTweetListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FavoriteTweetListViewHolder holder, int position){
        holder.owner.setText(dataList.get(position).getOwner());
        holder.category.setText(dataList.get(position).getCategory());
        holder.sentence.setText(dataList.get(position).getSentence());
    }

    @Override
    public int getItemCount(){
        return dataList.size();
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    //押下イベント
    public interface onItemClickListener {
        public void onClick(View v);
    }
}
