package com.example.studycompanion;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addTask#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addTask extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView textView1;
    TextView textView2;
    ImageButton imageButton1;
    ImageButton imageButton2;
    Button button;
    EditText editText;
    String startstr, endstr;
    ArrayList<Integer> dayCheck;
    CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7;

    public addTask() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addTask.
     */
    // TODO: Rename and change types and number of parameters
    public static addTask newInstance(String param1, String param2) {
        addTask fragment = new addTask();
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
        View rootView = inflater.inflate(R.layout.fragment_add_task, container, false);
        textView1 = rootView.findViewById(R.id.showStartTime);
        textView2 = rootView.findViewById(R.id.showEndTime);
        imageButton1 = rootView.findViewById(R.id.selectStartTime);
        imageButton2 = rootView.findViewById(R.id.selectEndTime);
        button = rootView.findViewById(R.id.saveButton);
        editText = rootView.findViewById(R.id.taskName);
        dayCheck = new ArrayList<>(Collections.nCopies(7,0));
        cb1 = rootView.findViewById(R.id.monCheck);
        cb2 = rootView.findViewById(R.id.tueCheck);
        cb3 = rootView.findViewById(R.id.wedCheck);
        cb4 = rootView.findViewById(R.id.thuCheck);
        cb5 = rootView.findViewById(R.id.friCheck);
        cb6 = rootView.findViewById(R.id.satCheck);
        cb7 = rootView.findViewById(R.id.sunCheck);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog startTime = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startstr = hourOfDay +":"+ minute;
                        textView1.setText(startstr);
                    }
                }, 12, 0, false);
                startTime.show();
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog EndTime = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endstr = hourOfDay +":"+ minute;
                        textView2.setText(endstr);
                    }
                }, 12, 0, false);
                EndTime.show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();

                if(cb1.isChecked()){
                    dayCheck.set(0, 1);
                }
                if(cb2.isChecked()){
                    dayCheck.set(1, 1);
                }
                if(cb3.isChecked()){
                    dayCheck.set(2, 1);
                }
                if(cb4.isChecked()){
                    dayCheck.set(3, 1);
                }
                if(cb5.isChecked()){
                    dayCheck.set(4, 1);
                }
                if(cb6.isChecked()){
                    dayCheck.set(5, 1);
                }
                if(cb7.isChecked()){
                    dayCheck.set(6, 1);
                }
                Log.e("RK1225", "onClick: " + dayCheck);
                Intent intent = new Intent(getActivity(), schedule.class);
                intent.putExtra("name",name);
                intent.putExtra("startstr",startstr);
                intent.putExtra("endstr",endstr);
                intent.putExtra("dayCheck",dayCheck);
                startActivity(intent);
                requireActivity().finish();
            }
        });
        return rootView;
    }
}