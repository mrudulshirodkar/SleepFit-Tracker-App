package com.example.assignment12;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.assignment12.databinding.FragmentLoginBinding;
import com.example.assignment12.databinding.FragmentSelectExerciseTimeBinding;

import java.util.ArrayList;

public class SelectExerciseTimeFragment extends Fragment {

    public SelectExerciseTimeFragment() {
        // Required empty public constructor
    }

    FragmentSelectExerciseTimeBinding binding;
    ArrayList<String> hrsList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    String hrs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectExerciseTimeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (double i = 0.5; i <= 15.0; i += 0.5) {
            hrsList.add(Double.toString(i));
        }

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, hrsList);
        binding.listView.setAdapter(adapter);
        getActivity().setTitle("Select Hrs");

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hrs = adapter.getItem(position);
                mListener.sendExerciseTime(hrs);
            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelSelection();
            }
        });
    }
    ExerciseTimeListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ExerciseTimeListener) context;
    }

    public interface ExerciseTimeListener{
        void cancelSelection();
        void sendExerciseTime(String hrs);
    }
}