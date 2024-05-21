package com.example.assignment12;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.assignment12.databinding.FragmentAddLogBinding;

import java.util.Calendar;

public class AddLogFragment extends Fragment {

    public AddLogFragment() {
        // Required empty public constructor
    }

    FragmentAddLogBinding binding;
    String date;
    String sleepHrs, sleepQuality, exerciseTime;

    public void setSleepHrs(String sleepHrs) {
        this.sleepHrs = sleepHrs;
    }

    public void setSleepQuality(String sleepQuality) {
        this.sleepQuality = sleepQuality;
    }

    public void setExerciseTime(String exerciseTime) {
        this.exerciseTime = exerciseTime;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddLogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(sleepHrs == null){
            binding.textViewSleepHrs.setText("Not Set");
        }else{
            binding.textViewSleepHrs.setText(sleepHrs);
        }

        if(sleepQuality == null){
            binding.textViewQualitySleep.setText("Not Set");
        }else{
            binding.textViewQualitySleep.setText(sleepQuality);
        }

        if(exerciseTime == null){
            binding.textViewExerciseTime.setText("Not Set");
        }else{
            binding.textViewExerciseTime.setText(exerciseTime);
        }

        binding.textViewDate.setText(date);
        binding.buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });
        binding.buttonHrsOfSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectHrsOfSleep();
            }
        });
        binding.buttonQualitySleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectQuality();
            }
        });
        binding.buttonExerciseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectExerciseTime();
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancel();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.textViewDate.getText().toString().equals("Not Set")){
                    Toast.makeText(getActivity(), "Enter date", Toast.LENGTH_SHORT).show();
                } else if (binding.textViewSleepHrs.getText().toString().equals("Not Set")) {
                    Toast.makeText(getActivity(), "Select Sleep Hours", Toast.LENGTH_SHORT).show();
                } else if (binding.textViewQualitySleep.getText().toString().equals("Not Set")) {
                    Toast.makeText(getActivity(), "Select Quality of Sleep", Toast.LENGTH_SHORT).show();
                } else if (binding.textViewExerciseTime.getText().toString().equals("Not Set")) {
                    Toast.makeText(getActivity(), "Select Exercise Time", Toast.LENGTH_SHORT).show();
                } else if (binding.editTextNumber.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Enter Weight", Toast.LENGTH_SHORT).show();
                } else{
                    date = binding.textViewDate.getText().toString();
                    int weight = Integer.parseInt(binding.editTextNumber.getText().toString());

                    AppDatabase db = Room.databaseBuilder(getActivity(), AppDatabase.class, "details-db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();

                    //String date, String sleepHrs, String sleepQuality, String exerciseTime, int weight
                    DetailsModel detailsModel = new DetailsModel(date, sleepHrs, sleepQuality, exerciseTime, weight);
                    db.dao().insertAll(detailsModel);
                    mListener.Submit();
                }
            }
        });

    }
    private void openDatePicker(){
//        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext() , new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//
//                //Showing the picked value in the textView
//                binding.textViewDate.setText(String.valueOf(year)+ "."+String.valueOf(month)+ "."+String.valueOf(day));
//                date = binding.textViewDate.getText().toString();
//            }
//        }, 2024, 01, 20);
//
//        datePickerDialog.show();

        Calendar currentDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH);
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // Check if the selected date is in the future
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, day);
                if (selectedDate.after(currentDate)) {
                    // Show a toast message indicating that future dates are not allowed
                    Toast.makeText(getContext(), "Future dates are not allowed", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update the date TextView with the selected date
                binding.textViewDate.setText(String.valueOf(year) + "." + String.valueOf(month) + "." + String.valueOf(day));
                date = binding.textViewDate.getText().toString();
            }
        }, currentYear, currentMonth, currentDay);

        // Set the maximum date limit to the current date
        datePickerDialog.getDatePicker().setMaxDate(currentDate.getTimeInMillis());

        // Show the DatePickerDialog
        datePickerDialog.show();

    }

    AddLogFragListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddLogFragListener) context;
    }

    public interface AddLogFragListener{
        void gotoSelectHrsOfSleep();
        void gotoSelectQuality();
        void gotoSelectExerciseTime();
        void cancel();
        void Submit();

    }
}