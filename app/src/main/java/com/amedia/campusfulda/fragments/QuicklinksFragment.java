package com.amedia.campusfulda.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amedia.campusfulda.R;


public class QuicklinksFragment extends Fragment {


    private Button webFulda, webHorstl, webElearning, webSystem;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public QuicklinksFragment() {

    }

    public static QuicklinksFragment newInstance(String param1, String param2) {
        QuicklinksFragment fragment = new QuicklinksFragment();
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


        View view = inflater.inflate(R.layout.fragment_quicklinks, container, false);

        //Holt Buttons
        webFulda = view.findViewById(R.id.hsfuldaweb_button);
        webHorstl = view.findViewById(R.id.horstl_button);
        webElearning = view.findViewById(R.id.elearning_button);
        webSystem = view.findViewById(R.id.system2teach_button);


        //Setzt für jeden Button einen OnclickListener
        webFulda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.hs-fulda.de"));
                startActivity(browserIntent);
            }
        });

        webHorstl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://horstl.hs-fulda.de/qisserver/pages/cs/sys/portal/hisinoneStartPage.faces?chco=y"));
                startActivity(browserIntent);
            }
        });

        webElearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://elearning.hs-fulda.de/help/"));
                startActivity(browserIntent);
            }
        });

        webSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.system2teach.de/hfg/login.jsp"));
                startActivity(browserIntent);
            }
        });

        return view;
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
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
