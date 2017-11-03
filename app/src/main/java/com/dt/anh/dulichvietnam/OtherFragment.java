package com.dt.anh.dulichvietnam;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class OtherFragment extends Fragment implements View.OnClickListener {
    private LinearLayout lnlInfPerson, lnlVoiceSpeech, lnlRatingApp, lnlShareApp, lnlLogOut;
    private View view;

    public OtherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_other, container, false);
        initViews();

        return view;
    }

    private void initViews() {
        lnlInfPerson = (LinearLayout) view.findViewById(R.id.lnl_inf_person);
        lnlVoiceSpeech = (LinearLayout) view.findViewById(R.id.lnl_voice_speech);
        lnlRatingApp = (LinearLayout) view.findViewById(R.id.lnl_rate_app);
        lnlLogOut = (LinearLayout) view.findViewById(R.id.lnl_log_out);
        lnlShareApp = (LinearLayout) view.findViewById(R.id.lnl_share_app);
        lnlVoiceSpeech.setOnClickListener(this);
        lnlInfPerson.setOnClickListener(this);
        lnlRatingApp.setOnClickListener(this);
        lnlShareApp.setOnClickListener(this);
        lnlLogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_inf_person:
                changeInfPerson();
                break;
            case R.id.lnl_voice_speech:
                voiceSpeech();
                break;
            case R.id.lnl_rate_app:
                rateApp();
                break;
            case R.id.lnl_share_app:
                shareApp();
                break;
            case R.id.lnl_log_out:
                logOut();
                break;
            default:
                break;
        }
    }

    private void voiceSpeech() {
        startActivity(new Intent(getActivity().getBaseContext(), VoiceSpeechActivity.class));
    }

    private void rateApp() {

    }

    private void shareApp() {

    }

    private void logOut() {

    }

    private void changeInfPerson() {
        startActivity(new Intent(getActivity().getBaseContext(), PersonalAct.class));
    }
}
