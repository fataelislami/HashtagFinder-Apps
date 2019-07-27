package com.fataelislami.clola.Views;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fataelislami.clola.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailCaptionActivity extends AppCompatActivity {
    @BindView(R.id.captionBody)
    EditText captionBody;
    @BindView(R.id.DetailCaptionImage)
    ImageView DetailCaptionImage;
    @BindView(R.id.DetailCaptionImageBig)
    ImageView DetailCaptionImageBig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_caption);
        ButterKnife.bind(this);
        String caption;
        String photo;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                caption= null;
            } else {
                caption= extras.getString("caption");
                photo=extras.getString("photo");
                //Play Glide
                Glide.with(this)
                        .load("https://kostlab.id/project/clolandroid/xfile/posts/"+photo)
//                        .apply(new RequestOptions().override(100,100).centerCrop())
                        .into(DetailCaptionImageBig);
                //Play Glide END

                //Play Glide
                Glide.with(this)
                        .load("https://kostlab.id/project/clolandroid/xfile/posts/"+photo)
                        .apply(new RequestOptions().override(100,100).centerCrop())
                        .into(DetailCaptionImage);
                //Play Glide END
            }
        } else {
            caption= (String) savedInstanceState.getSerializable("caption");
        }

        captionBody.setText(caption);

    }
    @OnClick(R.id.DetailCaptionCopy)
    public void salin(){
        ClipboardManager clipboard = (ClipboardManager) getApplication().getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Berhasil Disalin", captionBody.getText());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this,"Berhasil Disalin",Toast.LENGTH_LONG).show();

    }
}
