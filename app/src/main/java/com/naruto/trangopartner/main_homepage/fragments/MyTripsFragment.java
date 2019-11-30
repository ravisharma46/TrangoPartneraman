package com.naruto.trangopartner.main_homepage.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naruto.trangopartner.R;
import com.naruto.trangopartner.main_homepage.adapters.MyTripsRecyclerAdapter;
import com.naruto.trangopartner.main_homepage.data.MyTripsData;

import java.util.ArrayList;
import java.util.List;

public class MyTripsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public MyTripsFragment() {
        // Required empty public constructor
    }

    public static MyTripsFragment newInstance() {
        return new MyTripsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_my_trips, container, false);

        List<MyTripsData> myTripsDataList = initData();

        RecyclerView tripsRecyclerView = view.findViewById(R.id.my_trips_recyclerView);
        tripsRecyclerView.hasFixedSize();
        tripsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView.Adapter tripsAdapter = new MyTripsRecyclerAdapter(tripsRecyclerView.getContext(), myTripsDataList);
        tripsRecyclerView.setAdapter(tripsAdapter);
        return view;
    }

    private List<MyTripsData> initData() {
        List<MyTripsData> myTripsDataList = new ArrayList<>();
        myTripsDataList.add(new MyTripsData("WED, 31 JUL", "10 KM", "05:55 PM",
                "9999155103", true));
        myTripsDataList.add(new MyTripsData("WED, 31 JUL", "25 KM", "05:55 PM",
                "9999155103", false));
        myTripsDataList.add(new MyTripsData("TUE, 30 JUL", "10 KM", "05:55 PM",
                "9999155103", false));
        myTripsDataList.add(new MyTripsData("TUE, 30 JUL", "25 KM", "05:55 PM",
                "9999155103", false));
        return myTripsDataList;
    }

    public void onButtonPressed(View view) {
        if (mListener != null) {
            mListener.onFragmentInteraction(view);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(View view);
    }
}
