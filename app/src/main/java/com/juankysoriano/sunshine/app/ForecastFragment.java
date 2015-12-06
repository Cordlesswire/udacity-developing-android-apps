package com.juankysoriano.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    private ArrayAdapter<String> adapter;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                updateWeather();
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_location:
                openPreferredLocationInMap();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void updateWeather() {
        String key = getString(R.string.pref_location_key);
        String defaultValue = getString(R.string.pref_location_defaultValue);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String postCode = preferences.getString(key, defaultValue);
        new FetchWeatherTask(getContext(), adapter).execute("http://api.openweathermap.org/data/2.5/forecast/daily", postCode);
    }

    private void openPreferredLocationInMap() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String locationKey = getString(R.string.pref_location_key);
        String locationDefaultValue = getString(R.string.pref_location_defaultValue);
        String location = sharedPreferences.getString(locationKey, locationDefaultValue);
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", location)
                .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        adapter = new ArrayAdapter<>(
                getContext(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview
        );

        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String forecast = adapter.getItem(position);
                        Intent intent = new Intent(getContext(), DetailActivity.class);
                        intent.putExtra(Intent.EXTRA_TEXT, forecast);
                        startActivity(intent);
                    }
                }
        );

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }

}
