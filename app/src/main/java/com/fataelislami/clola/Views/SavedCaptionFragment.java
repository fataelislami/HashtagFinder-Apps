package com.fataelislami.clola.Views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.fataelislami.clola.Models.CaptionSaveModels;
import com.fataelislami.clola.Presenter.CaptionSave.CaptionSaveAdapter;
import com.fataelislami.clola.Presenter.ClientAPI;
import com.fataelislami.clola.Presenter.HashtagFinder.HashtagFinderAdapter;
import com.fataelislami.clola.Presenter.RestAPI;
import com.fataelislami.clola.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SavedCaptionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SavedCaptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedCaptionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String ROOT_URL = "https://kostlab.id/project/clolandroid/";

    @BindView(R.id.rvCaptionSave)
    RecyclerView rvCaptionSave;
    @BindView(R.id.imgCaptionEmpty)
    ImageView imgCaptionEmpty;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SavedCaptionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SavedCaptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SavedCaptionFragment newInstance(String param1, String param2) {
        SavedCaptionFragment fragment = new SavedCaptionFragment();
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
        SharedPreferences pref = getContext().getSharedPreferences("AuthLogin", 0); // 0 - for private mode
        String statusLogin=pref.getString("status",null);
        if(statusLogin==null){
            Toast.makeText(getContext(),"Harap Login Terlebih Dahulu",Toast.LENGTH_LONG).show();
            Intent i=new Intent(getContext(),LoginActivity.class);
            startActivity(i);
        }
        String id_user=pref.getString("id",null);
        View view=inflater.inflate(R.layout.fragment_my_caption, container, false);
        ButterKnife.bind(this,view);
        loadCaptionSave(id_user);
        return view;
    }

    private void loadCaptionSave(String id_user) {
        RestAPI service= ClientAPI.get(ROOT_URL).create(RestAPI.class);
        Call<CaptionSaveModels> call=service.getdata(id_user);
        call.enqueue(new Callback<CaptionSaveModels>() {
            @Override
            public void onResponse(Call<CaptionSaveModels> call, Response<CaptionSaveModels> response) {
                try {

                    if (response.body().getTotal_result()!=0){
                        imgCaptionEmpty.setVisibility(View.GONE);
                        rvCaptionSave.setVisibility(View.VISIBLE);
                        LinearLayoutManager mLinear = new LinearLayoutManager(getContext());

                        rvCaptionSave.setLayoutManager(mLinear);
                        rvCaptionSave.setHasFixedSize(true);
                        CaptionSaveAdapter captionSaveAdapter = new CaptionSaveAdapter(getContext(), response.body().getResults());
                        rvCaptionSave.setAdapter(captionSaveAdapter);
                    }else{
                        rvCaptionSave.setVisibility(View.GONE);
                        imgCaptionEmpty.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    Log.d("Response 200",e.getMessage());
                }


            }

            @Override
            public void onFailure(Call<CaptionSaveModels> call, Throwable t) {
                Log.d("Response",t.getMessage());
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
