package com.example.firebase;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondOption#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondOption extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private String mParam1;

    public SecondOption() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment SecondOption.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondOption newInstance(String param1) {
        SecondOption fragment = new SecondOption();
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
        TextView text_tv;
        View view = inflater.inflate(R.layout.fragment_second_option, container, false);
               if (getArguments() != null) {
            String text= getArguments().getString(ARG_PARAM1);
            text_tv = view.findViewById(R.id.tv);
            text_tv.setText(text);
        }
        return view;
    }
}