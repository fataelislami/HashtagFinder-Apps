package com.fataelislami.clola.Presenter.HashtagFinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fataelislami.clola.Models.ResultVision;
import com.fataelislami.clola.R;

import java.util.ArrayList;
import java.util.List;

public class HashtagFinderAdapter extends RecyclerView.Adapter<HashtagFinderViewHolder> {
    private Context context;
    private List<ResultVision> visionList;

    public HashtagFinderAdapter(Context context,List<ResultVision> visionList){
        this.context=context;
        this.visionList=visionList;
    }
    @NonNull
    @Override
    public HashtagFinderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_hashtag,viewGroup,false);
        return new HashtagFinderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HashtagFinderViewHolder hashtagFinderViewHolder, int i) {
        ResultVision visionObjek=visionList.get(i);
        hashtagFinderViewHolder.txtHashtagTitle.setText("#"+visionObjek.getName());
        hashtagFinderViewHolder.txtJumlahPenggunaHashtag.setText(visionObjek.getMedia_count().toString());
        hashtagFinderViewHolder.txtFormatHashtag.setText(visionObjek.getFormatted_media_count());
        //Kirim value ke view holder
        hashtagFinderViewHolder.setItem(visionList);

    }

    @Override
    public int getItemCount() {
        return visionList.size();
    }
}
