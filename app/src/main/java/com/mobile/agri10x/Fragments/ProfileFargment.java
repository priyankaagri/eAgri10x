package com.mobile.agri10x.Fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.agri10x.R;
import com.mobile.agri10x.activities.HomePageActivity;
import com.mobile.agri10x.models.GetUser;
import com.mobile.agri10x.models.UpdateUser;
import com.mobile.agri10x.retrofit.AgriInvestor;
import com.mobile.agri10x.retrofit.ApiHandler;
import com.mobile.agri10x.retrofit.SSLCertificateManagment;
import com.mobile.agri10x.utils.SessionManager;

import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFargment extends Fragment {
    AlertDialog dialog, dialog2;
    EditText et_firstname, et_lastname, et_email, et_mobilenumber;
    Button save_btn;
    ImageView mBackButton;
    private TextView mFirstNameErrTV, mLastNameErrTV, mEmailErrTV;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public ProfileFargment() {
// Required empty public constructor
    }

    private void removeFragment() {
        HomePageActivity.removeFragment(new ProfileFargment());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_fargment, container, false);
        et_firstname = view.findViewById(R.id.et_firstname);
        et_lastname = view.findViewById(R.id.et_lastname);
        et_email = view.findViewById(R.id.et_email);
        et_mobilenumber = view.findViewById(R.id.et_mobilenumber);
        et_mobilenumber.setEnabled(false);
        save_btn = view.findViewById(R.id.save_btn);
        mEmailErrTV = view.findViewById(R.id.tv_email_error_id);
        mLastNameErrTV = view.findViewById(R.id.tv_last_error_id);
        mFirstNameErrTV = view.findViewById(R.id.tv_first_error_id);
        getUserProfileData();
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserProfile();
            }
        });

        mBackButton = view.findViewById(R.id.btn_back_profile_id);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFragment();
            }
        });

        return view;
    }

    private void updateUserProfile() {
        String firstname = et_firstname.getText().toString();
        String lastname = et_lastname.getText().toString();
        String email = et_email.getText().toString();
        String mobilenumber = et_mobilenumber.getText().toString();
        if (validatefirstname(firstname) && validatelastname(lastname) && validateemail(email) &&validateemailformate(email) ) {
            callapiUpadteUser(firstname, lastname, email, mobilenumber);
        }
    }

    private boolean validateemailformate(String email) {
        if (!email.matches(emailPattern))
        {

            Toast.makeText(getActivity(),"Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private boolean validatemobilenumber(String mobilenumber) {
        if (mobilenumber.isEmpty() || mobilenumber.length() < 10) {
            Toast.makeText(getActivity(),
                    "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateemail(String email) {
        if (email.isEmpty() || email == null) {
            Toast.makeText(getActivity(),
                    "Email is Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatelastname(String lastname) {
        if (lastname.isEmpty() || lastname == null) {
            Toast.makeText(getActivity(),
                    "Last Name is Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatefirstname(String firstname) {
        if (firstname.isEmpty() || firstname == null) {
            Toast.makeText(getActivity(),
                    "First Name is Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getUserProfileData() {
        dialog = new Alert().pleaseWait();
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("userID", SessionManager.getKeyTokenUser(getActivity()));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
// AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<GetUser> loginCall = apiService.wsGetUserById("123456", body);
        loginCall.enqueue(new Callback<GetUser>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<GetUser> call,
                                   Response<GetUser> response) {

                Log.d("getnameapi", response.toString());
                dialog.dismiss();
                if (response.isSuccessful()) {

                    et_firstname.setText(response.body().getData().getFirstname());
                    et_lastname.setText(response.body().getData().getLastname());
                    et_email.setText(response.body().getData().getEmail());
                    if(response.body().getData().getEmail().equals(""))
                    {
                        et_email.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }else {
                        et_email.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.iconverify, 0);
                    }
                    String number = response.body().getData().getTelephone().substring(2, 12);
                    et_mobilenumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.iconverify, 0);
                    et_mobilenumber.setText(number);
                } else {
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetUser> call,
                                  Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void callapiUpadteUser(String firstname, String lastname, String email, String mobilenumber) {
        dialog2 = new Alert().pleaseWait();
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("id", SessionManager.getKeyTokenUser(getActivity()));
        jsonParams.put("fname", firstname);
        jsonParams.put("lname", lastname);
        jsonParams.put("email", email);
// jsonParams.put("id", mobilenumber);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        AgriInvestor apiService = ApiHandler.getApiService();
        try {
            SSLCertificateManagment.trustAllHosts();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
// AgriInvestor apiService = ApiHandler.getClient(getApplicationContext()).create(AgriInvestor.class);
        final Call<UpdateUser> loginCall = apiService.wsGetUpdateUser("123456", body);
        loginCall.enqueue(new Callback<UpdateUser>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<UpdateUser> call,
                                   Response<UpdateUser> response) {

                Log.d("getnameapi", response.toString());
                dialog2.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    ProfileFargment fragment = (ProfileFargment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    getActivity().getSupportFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();
                } else {

                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateUser> call,
                                  Throwable t) {
                dialog2.dismiss();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class Alert {
        public void alert(String title, String body) {
            final AlertDialog.Builder Alert = new AlertDialog.Builder(getActivity());
            Alert.setCancelable(false)
                    .setTitle(title)
                    .setMessage(body);
            Alert.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            Alert.create().show();
        }


        public AlertDialog pleaseWait() {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View mView = getLayoutInflater().inflate(R.layout.alert_progress_spinning, null);
            ProgressBar pb = mView.findViewById(R.id.progressBar);
            mBuilder.setView(mView);
            mBuilder.setCancelable(false);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            return dialog;
        }


    }


}