package com.example.gaminghubapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gaminghubapp.R;
import com.example.gaminghubapp.activities.LoginRegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Button submitBtn = view.findViewById(R.id.btn_login_submit);
        EditText emailET = view.findViewById(R.id.et_login_email);
        EditText passwordET = view.findViewById(R.id.et_login_password);
        TextView loginToRegisterTV = view.findViewById(R.id.loginToRegisterTV);

        loginToRegisterTV.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.registerFragment));

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRegisterActivity loginRegisterActivity = (LoginRegisterActivity) getActivity();
                String emailT = emailET.getText().toString();
                String passwordT = passwordET.getText().toString();

                boolean isEmailEmpty = emailT.isEmpty();
                boolean isPasswordEmpty = passwordT.isEmpty();
                boolean isFieldValid = !isEmailEmpty && !isPasswordEmpty;
                if(isFieldValid){
                    System.out.println("Condition: True");
                    loginRegisterActivity.login(v);
                } else {
                    System.out.println("Condition: False");
                }
            }
        });
        return view;
    }
}