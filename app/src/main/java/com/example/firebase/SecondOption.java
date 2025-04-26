package com.example.firebase;

import static java.lang.Integer.parseInt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebase.databinding.FragmentSecondOptionBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondOption#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondOption extends Fragment {
    private FragmentSecondOptionBinding binding;
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
        binding = FragmentSecondOptionBinding.inflate(inflater, container, false);

        final String[] s = {"0"};

        if (getArguments() != null) {
           upd(s);
        }

        binding.okBtn.setOnClickListener(v ->{
            String ans = binding.inputtext.getText().toString();
            if (ans.toLowerCase().equals(s[0].toLowerCase())){
                binding.correct.setText(R.string.correct);
            } else{
                binding.correct.setText("Неверно, правильный ответ: "+s[0]+".");
            }
            upd(s);
            binding.inputtext.setText("");
        });
        return binding.getRoot();
    }
    public void upd(String[] s){
        WordSkipper ts = new WordSkipper();
        if (getArguments() != null) {
            String text= getArguments().getString(ARG_PARAM1);
            String[] text_2 = ts.doSkip(text.replace("<br>", " \n "));
            binding.tv.setText(text_2[0]);
            s[0] = text_2[1];
        }
    }
}