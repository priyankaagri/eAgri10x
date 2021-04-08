package com.mobile.agri10x.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.utils.LocaleHelper;
import com.mobile.agri10x.utils.SessionManager;

public class MyLanguageFragment extends Fragment {


    private Button mOnClickChangeLanguage;
    private String languagePref="en";
    RadioGroup radioGroup;
    RadioButton rb,rb2;
    private ImageView mBackImage;
    private TextView mChooseLanguage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_my_language, container, false);
        mOnClickChangeLanguage=view.findViewById(R.id.btnSelectLanguage);
        mChooseLanguage=view.findViewById(R.id.text_choose_language_id);
        mOnClickChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager.setLanguage(languagePref);
                setApplicationLanguage();
                HomePageActivity.removeFragment(new MyLanguageFragment());
                Intent intent = getActivity().getIntent();
                getActivity().finish();
                startActivity(intent);
            }
        });

        init(view);
        return view;
    }

    private void init(View view) {
        mBackImage=view.findViewById(R.id.but_back_my_lang);
        radioGroup = (RadioGroup) view .findViewById(R.id.radio_group);
        rb=view.findViewById(R.id.radio_english);
        rb2=view.findViewById(R.id.radio_marathi);
        languagePref=SessionManager.getAppLanguagePref(getActivity());

        if (!languagePref.isEmpty()&&languagePref.equals("en")) {
            rb.setChecked(true);
            rb2.setChecked(false);
            LocaleHelper.setLocale(getActivity(), languagePref);
            mOnClickChangeLanguage.setText(getResources().getString(R.string.set_lang));
            //recreate();
        } else if(!languagePref.isEmpty()&&languagePref.equals("mr")){
            rb.setChecked(false);
            rb2.setChecked(true);
            LocaleHelper.setLocale(getActivity(), languagePref);
            mOnClickChangeLanguage.setText(getResources().getString(R.string.set_lang));
        }

        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity.removeFragment(new MyLanguageFragment());
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.radio_english:
                        // switch to fragment 1
                        if(rb.isChecked())
                            languagePref = "en";
                        mOnClickChangeLanguage.setText(getResources().getString(R.string.set_lang_1));
                        mChooseLanguage.setText(getResources().getString(R.string.chooseYourLang1));
                        break;
                    case R.id.radio_marathi:
                        if(rb2.isChecked())
                            languagePref = "mr";
                        mOnClickChangeLanguage.setText(getResources().getString(R.string.set_lang_1));
                        mChooseLanguage.setText(getResources().getString(R.string.chooseYourLang1));

                        break;
                }

                SessionManager.setLanguage(languagePref);
                //storeLanguage(languagePref);

                if (!languagePref.isEmpty()) {

                    LocaleHelper.setLocale(getActivity(), languagePref);

                    //recreate();
                }
            }
        });
    }

    private void setApplicationLanguage()
    {
        String lang=SessionManager.getAppLanguagePref(getActivity());
        if (!lang.isEmpty()) {
            LocaleHelper.setLocale(getActivity(), lang);
            //recreate();
        }
    }
}