package com.example.firebase;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TextFr#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TextFr extends Fragment {
    TextView text_tv;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    public TextFr() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Text.
     */
    // TODO: Rename and change types and number of parameters
    public static TextFr newInstance(String param1) {
        TextFr fragment = new TextFr();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);

        String text ="";
        if (getArguments() != null) {
            text= getArguments().getString(ARG_PARAM1);
            Log.d("data", text);

            text_tv = view.findViewById(R.id.tv);
            text_tv.setText(text);
        }

        Button first_opt, second_opt;
        first_opt = view.findViewById(R.id.button1);
        second_opt = view.findViewById(R.id.button2);
        FirstOption Opt_1 = FirstOption.newInstance(text);
        first_opt.setOnClickListener(v -> {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragmentContainerView2, Opt_1);
            ft.addToBackStack(null);
            ft.commit();
        });
        SecondOption Opt_2 = SecondOption.newInstance(text);
        second_opt.setOnClickListener(v -> {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragmentContainerView2,  Opt_2);
            ft.addToBackStack(null);
            ft.commit();
        });
        return view;

    }
}