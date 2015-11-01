package com.juankysoriano.sunshine.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        List<String> weekForecast = new ArrayList<>(5);
        weekForecast.add("Sunday cold");
        weekForecast.add("Saturday cold");
        weekForecast.add("Monday cold");
        weekForecast.add("Friday cold");
        weekForecast.add("Tuesday cold");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                weekForecast
        );

        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(adapter);

        return rootView;
    }
}
