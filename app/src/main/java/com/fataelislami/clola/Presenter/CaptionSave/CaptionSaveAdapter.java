package com.fataelislami.clola.Presenter.CaptionSave;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fataelislami.clola.Models.ResultCaptionSave;
import com.fataelislami.clola.R;

import java.util.List;

public class CaptionSaveAdapter extends RecyclerView.Adapter<CaptionSaveViewHolder> {
    private Context context;
    private List<ResultCaptionSave> resultCaptionSaveList;

    public CaptionSaveAdapter(Context context,List<ResultCaptionSave> resultCaptionSaveList){
        this.context=context;
        this.resultCaptionSaveList=resultCaptionSaveList;
    }
    @NonNull
    @Override
    public CaptionSaveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_caption_save,viewGroup,false);
        return new CaptionSaveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CaptionSaveViewHolder captionSaveViewHolder, int i) {
        ResultCaptionSave captionSave=resultCaptionSaveList.get(i);
        captionSaveViewHolder.txtCaptionSaveTitle.setText("Caption "+i);
        captionSaveViewHolder.txtCaptionSaveBody.setText(captionSave.getCaption());
        captionSaveViewHolder.txtCaptionSaveTanggal.setText(captionSave.getCreated_on());
        //Play Glide
        Glide.with(context)
                .load("https://kostlab.id/project/clolandroid/xfile/posts/"+captionSave.getPhoto())
                .apply(new RequestOptions().override(100,100).centerCrop())
                .into(captionSaveViewHolder.imgCaptionSave);
        //Play Glide END
        captionSaveViewHolder.setItem(resultCaptionSaveList);
    }

    @Override
    public int getItemCount() {
        return resultCaptionSaveList.size();
    }
}
