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
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        Button submitBtn = view.findViewById(R.id.btn_register_submit);
        EditText usernameET = view.findViewById(R.id.et_register_username);
        EditText emailET = view.findViewById(R.id.et_register_email);
        EditText passwordET = view.findViewById(R.id.et_register_password);
        EditText confirmPasswordET = view.findViewById(R.id.et_register_confirm_password);
        EditText phoneET = view.findViewById(R.id.et_register_phone);
        TextView registerToLoginTV = view.findViewById(R.id.registerToLoginTV);

        registerToLoginTV.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.loginFragment));

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRegisterActivity loginRegisterActivity = (LoginRegisterActivity) getActivity();
                String usernameT = usernameET.getText().toString();
                String emailT = emailET.getText().toString();
                String passwordT = passwordET.getText().toString();
                String confirmPasswordT = confirmPasswordET.getText().toString();
                String phoneT = phoneET.getText().toString();

                boolean isUsernameEmpty = usernameT.isEmpty();
                boolean isEmailEmpty = emailT.isEmpty();
                boolean isPasswordEmpty = passwordT.isEmpty();
                boolean isPhoneEmpty = phoneT.isEmpty();
                boolean isPasswordsEqual = passwordT.equals(confirmPasswordT);
                boolean isPasswordValid = !isPasswordEmpty && isPasswordsEqual;
                boolean isSomeFieldEmpty = isUsernameEmpty || isEmailEmpty || isPasswordEmpty || isPhoneEmpty;
                boolean isFieldValid = !isSomeFieldEmpty && isPasswordValid;
                if(isFieldValid){
                    System.out.println("Condition: True");
                    loginRegisterActivity.register(v);
                } else {
                    System.out.println("Condition: False");
                }
            }
        });

        return view;
    }
}