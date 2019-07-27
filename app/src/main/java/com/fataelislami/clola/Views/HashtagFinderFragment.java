package com.fataelislami.clola.Views;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fataelislami.clola.Helper.FileUtils;
import com.fataelislami.clola.Helper.ImageUtils;
import com.fataelislami.clola.Models.MResponseImage;
import com.fataelislami.clola.Models.PostModels;
import com.fataelislami.clola.Models.VisionModels;
import com.fataelislami.clola.Presenter.ClientAPI;
import com.fataelislami.clola.Presenter.HashtagFinder.HashtagFinderAdapter;
import com.fataelislami.clola.Presenter.HashtagFinder.HashtagFinderViewHolder;
import com.fataelislami.clola.Presenter.RestAPI;
import com.fataelislami.clola.R;


import java.io.File;
import java.io.IOException;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HashtagFinderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HashtagFinderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HashtagFinderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Init untuk image
    private static final int PICK_IMAGE = 1;
    private static final int PERMISSION_REQUEST_STORAGE = 2;
    private Uri uri;
    //Init untuk API
    public static final String ROOT_URL = "https://kostlab.id/project/clolandroid/";
    @BindView(R.id.btnUploadImage)
    ImageView imgThumb;
    @BindView(R.id.spin_kit_container)
    ConstraintLayout spin_kit_container;
    @BindView(R.id.rvHashtag)
    RecyclerView rvHashtag;
    @BindView(R.id.tabHashtag) TabLayout tabHashtag;
    @BindView(R.id.edtHashtag)
    EditText edtHashtag;
    @BindView(R.id.btnFindHashtag)
    Button btnFindHashtag;
    @BindView(R.id.btnCopyHashtag) Button btnCopyHashtag;
    @BindView(R.id.lnCaption)
    LinearLayout lnCaption;
    String caption;
    String id_users;
    SharedPreferences.Editor editor;

    //Init Vision
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HashtagFinderFragment() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HashtagFinderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HashtagFinderFragment newInstance(String param1, String param2) {
        HashtagFinderFragment fragment = new HashtagFinderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_hashtag_finder, container, false);
        ButterKnife.bind(this,view);

            setupTab();
        imgThumb.setImageResource(R.drawable.logo);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @OnClick(R.id.btnSaveHashtag)
    public void simpan(){
        SharedPreferences pref = getContext().getSharedPreferences("AuthLogin", 0); // 0 - for private mode
        String statusLogin=pref.getString("status",null);
        if(statusLogin==null){
            Toast.makeText(getContext(),"Harap Login Terlebih Dahulu",Toast.LENGTH_LONG).show();
            Intent i=new Intent(getContext(),LoginActivity.class);
            startActivity(i);
        }else {
            id_users=pref.getString("id",null);
            String caption=edtHashtag.getText().toString();
//            Toast.makeText(getContext(),"Sudah login",Toast.LENGTH_LONG).show();
            if (uri != null) {
                File file = FileUtils.getFile(getContext(), uri);  //uncomment ini kalo mau simpan ke file di server
                uploadMultipartWithPost(file,caption,id_users);
            }else{
                Toast.makeText(getContext(),"Gambar Belum Dipilih",Toast.LENGTH_LONG).show();
            }

        }


    }
    @OnClick(R.id.btnCopyHashtag)
    public void salin(){
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Berhasil Disalin", edtHashtag.getText());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getContext(),"Berhasil Disalin",Toast.LENGTH_LONG).show();

    }
    @OnClick(R.id.btnUploadImage)
    public void pilihphoto(){
        choosePhoto();
    }

    @OnClick(R.id.btnFindHashtag)
    public void proses() {
        if (uri != null) {
//            File file = FileUtils.getFile(getContext(), uri);  //uncomment ini kalo mau simpan ke file di server
//            uploadMultipart(file);
//           Toast.makeText(getContext(), "Test"+file, Toast.LENGTH_SHORT).show();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String encoded = ImageUtils.bitmapToBase64String(bitmap, 100);
                processToVision(encoded);

//                Log.d("base64",encoded);
        } else {
            Toast.makeText(getContext(), "Gambar belum dipilih", Toast.LENGTH_SHORT).show();
        }
    }

    private void choosePhoto() {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_STORAGE);

        }else{
            openGallery();
        }
    }

    public void processToVision(String base64){
        RestAPI service=ClientAPI.get(ROOT_URL).create(RestAPI.class);
        Call<VisionModels> call=service.visionAPI(base64);
        // Nambahin Loading pas lagi get data
//        final ProgressDialog progressDialog;
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMax(100);
//        progressDialog.setMessage("Sedang Mencari Hashtag....");
//        progressDialog.setTitle("Mohon Bersabar");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // Munculin Loading
//        progressDialog.show();
        spin_kit_container.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<VisionModels>() {
            @Override
            public void onResponse(Call<VisionModels> call, Response<VisionModels> response) {
                if(response.body().getResults().size()!=0) {
                    SharedPreferences pref = getContext().getSharedPreferences("caption", 0);
                    editor = pref.edit();
                    editor.clear();
                    editor.putInt("total_caption",response.body().getResults().size());
                    editor.commit();
                    LinearLayoutManager mLinear = new LinearLayoutManager(getContext());
                    rvHashtag.setVisibility(View.VISIBLE);
                    tabHashtag.setVisibility(View.VISIBLE);
                    rvHashtag.setLayoutManager(mLinear);
                    rvHashtag.setHasFixedSize(true);
                    HashtagFinderAdapter hashtagFinderAdapter = new HashtagFinderAdapter(getContext(), response.body().getResults());
                    rvHashtag.setAdapter(hashtagFinderAdapter);
                    String hashtag = response.body().getResults().get(0).getName();
                    int pengguna = response.body().getResults().get(0).getMedia_count();
                    Log.d("Hashtag", hashtag);
                    Log.d("Pengguna", String.valueOf(pengguna));
//                Toast.makeText(getContext(),hashtag,Toast.LENGTH_LONG);
                    spin_kit_container.setVisibility(View.GONE);
//                progressDialog.dismiss();
                }else{
                    spin_kit_container.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"Ups..Hashtag Tidak Ditemukan",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<VisionModels> call, Throwable t) {
                Log.d("Gagal",t.getMessage());
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                spin_kit_container.setVisibility(View.GONE);
//                progressDialog.dismiss();


            }
        });

    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
    }

    private void uploadMultipart(File file) {
        RequestBody photoBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part photoPart = MultipartBody.Part.createFormData("photo",file.getName(), photoBody);
        RestAPI service= ClientAPI.get(ROOT_URL).create(RestAPI.class);
        Call<MResponseImage> call=service.uploadPhotoMultipart(photoPart);
        call.enqueue(new Callback<MResponseImage>() {
            @Override
            public void onResponse(Call<MResponseImage> call, Response<MResponseImage> response) {
                String res=response.body().getResponse();
                Log.d("Message", "onResponse: "+res);
                String url=ROOT_URL+"xfile/posts/"+res;
                Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<MResponseImage> call, Throwable t) {
                Log.e("Message", "onError: "+t.getMessage());
            }
        });
    }
    private void uploadMultipartWithPost(File file,String caption,String id_user) {
        RequestBody photoBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part photoPart = MultipartBody.Part.createFormData("photo",file.getName(), photoBody);
        Log.d("FileName",file.getName());
        MultipartBody.Part captionPart=MultipartBody.Part.createFormData("caption",caption);
        MultipartBody.Part id_userPart=MultipartBody.Part.createFormData("id_user",id_user);
        RestAPI service= ClientAPI.get(ROOT_URL).create(RestAPI.class);
        Call<PostModels> call=service.uploadPhotoMultipartWithPosts(photoPart,captionPart,id_userPart);
        call.enqueue(new Callback<PostModels>() {
            @Override
            public void onResponse(Call<PostModels> call, Response<PostModels> response) {
                String message=response.body().getMessage();
                String photo=response.body().getPhoto();
                Log.d("Message", "onResponse: "+message);
                Log.d("Photo", "onResponse: "+photo);
                String url=ROOT_URL+"xfile/posts/"+photo;
                Toast.makeText(getContext(), "Caption berhasil disimpan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PostModels> call, Throwable t) {
                Log.e("Message", "onError: "+t.getMessage());
            }
        });
    }

    //TabLayout 1 Fragment
    public void setupTab(){
        tabHashtag.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Toast.makeText(getContext(),"Position"+tab.getPosition(),Toast.LENGTH_LONG).show();
                if (tab.getPosition()==0){
                    edtHashtag.setVisibility(View.GONE);
                    lnCaption.setVisibility(View.GONE);
                    rvHashtag.setVisibility(View.VISIBLE);
                    btnFindHashtag.setVisibility(View.VISIBLE);

                }else if(tab.getPosition()==1){
                    edtHashtag.setVisibility(View.VISIBLE);
                    lnCaption.setVisibility(View.VISIBLE);
                    SharedPreferences pref = getContext().getSharedPreferences("caption", 0); // 0 - for private mode
                    int total_caption=pref.getInt("total_caption",0);
//                    Toast.makeText(getContext(),"TOtal Caption"+total_caption,Toast.LENGTH_LONG).show();
                    if(total_caption!=0) {
                        edtHashtag.setText("");
                        for (int i=0;i<total_caption;i++){
                            String captionPref=pref.getString("caption"+i,null);
                            if(captionPref!=null){
                                edtHashtag.append("#"+captionPref+" ");
                            }
                        }
                    }
                    rvHashtag.setVisibility(View.GONE);
                    btnFindHashtag.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Toast.makeText(getContext(),"Ulang Position"+tab.getPosition(),Toast.LENGTH_LONG).show();

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                uri = data.getData();
                imgThumb.setImageURI(uri);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    openGallery();
                }

                return;
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
