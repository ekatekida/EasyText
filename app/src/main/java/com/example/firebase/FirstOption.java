package com.example.firebase;

import static java.lang.Integer.parseInt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebase.databinding.FragmentFirstOptionBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstOption#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstOption extends Fragment {
    private FragmentFirstOptionBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    public FirstOption() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment FirstOption.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstOption newInstance(String param1) {
        FirstOption fragment = new FirstOption();
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
        binding = FragmentFirstOptionBinding.inflate(inflater, container, false);
        if (getArguments() != null) {
            String text= getArguments().getString(ARG_PARAM1);
            binding.tv.setText(text.replace("<br>", "\n"));
        }
        final String[] s = {"0"};
        TextShortener ts = new TextShortener();
        binding.next.setOnClickListener(v -> {
            DoneFragment done = DoneFragment.newInstance("","");
            if (parseInt(s[0]) != 5) {
                s[0] = String.valueOf((parseInt(s[0])+1));

            }else{
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragmentContainerView2, done);
                ft.addToBackStack(null);
                ft.commit();
            }
            if (getArguments() != null) {
                String text= getArguments().getString(ARG_PARAM1);
                binding.tv.setText(ts.doShortening(parseInt(s[0]), text.replace("<br>", " \n ")));
            }
        });
        binding.back.setOnClickListener(v -> {
            if (parseInt(s[0]) != 0) {
                s[0] = String.valueOf((parseInt(s[0])-1));
            }
            if (getArguments() != null) {
                String text= getArguments().getString(ARG_PARAM1);
                binding.tv.setText(ts.doShortening(parseInt(s[0]), text.replace("<br>", " \n ")));
            }
        });
        return binding.getRoot();
    }
}