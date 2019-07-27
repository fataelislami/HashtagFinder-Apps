package com.fataelislami.clola.Presenter.HashtagFinder;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fataelislami.clola.Models.ResultVision;
import com.fataelislami.clola.R;
import com.fataelislami.clola.Views.HashtagFinderFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

public class HashtagFinderViewHolder extends RecyclerView.ViewHolder {
//    static HashtagFinderViewHolder INSTANCE;
    @BindView(R.id.txtHashtagTitle)
    TextView txtHashtagTitle;
    @BindView(R.id.txtJumlahPenggunaHashtag) TextView txtJumlahPenggunaHashtag;
    @BindView(R.id.txtFormatHashtag) TextView txtFormatHashtag;
    @BindView(R.id.item_hashtag)
    RelativeLayout item_hashtag;
    private List<ResultVision> visionList=new ArrayList<>();
    private List<String> captionList=new ArrayList<>();
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences.Editor editor;



    public HashtagFinderViewHolder(@NonNull View itemView) {
        super(itemView);
//        INSTANCE=this;
        ButterKnife.bind(this,itemView);
//        SharedPreferences pref = itemView.getContext().getSharedPreferences("caption", 0);
//        editor = pref.edit();
    }
    //Siap mengirim value
//    public static HashtagFinderViewHolder getActivityInstance(){
//        return INSTANCE;
//    }

    public void setItem(List<ResultVision> item){//penerima value dari adapter

        visionList=item;
    }
    @OnClick()
    void klik(@NonNull View itemView){
        SharedPreferences pref = itemView.getContext().getSharedPreferences("caption", 0);
        editor = pref.edit();
        int position=getAdapterPosition();
        String check=pref.getString("caption"+position,null);
        if(check!=null){
            Toast.makeText(itemView.getContext(), "Berhasil Menghapus #"+visionList.get(position).getName(),Toast.LENGTH_LONG).show();
            editor.remove("caption"+position);
            item_hashtag.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else{
            Toast.makeText(itemView.getContext(), "Berhasil Menambahkan #"+visionList.get(position).getName(),Toast.LENGTH_LONG).show();
            editor.putString("caption"+position,visionList.get(position).getName());
            item_hashtag.setBackgroundColor(Color.parseColor("#06B5E3"));
        }
        editor.commit();
// set Fragmentclass Arguments
    }
}
