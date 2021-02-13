package com.ale.gttt.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.ale.gttt.R;
import com.ale.gttt.Session.SharedPreferenceManager;
import com.ale.gttt.entities.User;


public class UserFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam;

    private OnFragmentInteractionListener mListener;

    private EditText etnickperfil, etemailperfil, etpass1, etpass2;
    private Button btnaceptar, btncancelar;


    public UserFragment() {

    }


    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_user, container, false);
        Init(v);
        Config(v);

        return v;
    }

    private void Init(View v) {
        etnickperfil=v.findViewById(R.id.etnickperfil);
        etemailperfil=v.findViewById(R.id.etmailperfil);
        etpass1=v.findViewById(R.id.etpassperfil1);
        etpass2=v.findViewById(R.id.etpass2perfil);
        btnaceptar=v.findViewById(R.id.btneditarperfil);
        btncancelar=v.findViewById(R.id.btncancelarperfil);


    }

    private void Config(View v) {
        User u = SharedPreferenceManager.getInstance(getContext()).GetUser();
        etnickperfil.setText(u.getNick());
        etpass1.setText(u.getPassword());
        etpass2.setText(u.getPassword());
        etemailperfil.setText(u.getEmail());
        btnaceptar.setOnClickListener(this);
        btncancelar.setOnClickListener(this);
    }
    public void onClick(View v){
        if (v==btnaceptar){
          EditUserProfile();
        }
        if (v==btncancelar){


        }
    }
    private void EditUserProfile() {
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
