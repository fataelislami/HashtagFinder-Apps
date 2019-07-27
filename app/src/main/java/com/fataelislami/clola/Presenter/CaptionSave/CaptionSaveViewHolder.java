package com.fataelislami.clola.Presenter.CaptionSave;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fataelislami.clola.Models.ResultCaptionSave;
import com.fataelislami.clola.R;
import com.fataelislami.clola.Views.DetailCaptionActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CaptionSaveViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.txtCaptionSaveTitle)
    TextView txtCaptionSaveTitle;
    @BindView(R.id.txtCaptionSaveBody)
    TextView txtCaptionSaveBody;
    @BindView(R.id.txtCaptionSaveTanggal)
    TextView txtCaptionSaveTanggal;
    @BindView(R.id.imgCaptionSave)
    ImageView imgCaptionSave;
    private List<ResultCaptionSave> resultCaptionSaveList;
    public CaptionSaveViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void setItem(List<ResultCaptionSave> item){
        resultCaptionSaveList=item;
    }

    @OnClick()
    public void klik(@NonNull View itemView){
        int position=getAdapterPosition();
        String caption=resultCaptionSaveList.get(position).getCaption();
        String photo=resultCaptionSaveList.get(position).getPhoto();
        Intent i=new Intent(itemView.getContext(), DetailCaptionActivity.class);
        i.putExtra("caption",caption);
        i.putExtra("photo",photo);
        itemView.getContext().startActivity(i);
    }
}
