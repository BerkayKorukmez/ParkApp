package com.example.parkapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ParkListFragment extends Fragment {
    private RecyclerView recyclerView;
    private ParkAdapter parkAdapter;
    private List<Park> allParks;
    private List<Park> filteredParks;
    private EditText searchEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_park_list, container, false);
        
        recyclerView = view.findViewById(R.id.recyclerViewParks);
        searchEditText = view.findViewById(R.id.searchEditText);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        allParks = Park.getAllMalatyaParks();
        filteredParks = new ArrayList<>(allParks);
        
        parkAdapter = new ParkAdapter(filteredParks, park -> {
            showParkDetails(park);
        });
        
        recyclerView.setAdapter(parkAdapter);
        
        // Arama özelliği
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterParks(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        
        return view;
    }

    private void filterParks(String query) {
        filteredParks.clear();
        
        if (query.isEmpty()) {
            filteredParks.addAll(allParks);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (Park park : allParks) {
                if (park.getName().toLowerCase().contains(lowerCaseQuery) ||
                    park.getAddress().toLowerCase().contains(lowerCaseQuery) ||
                    park.getDescription().toLowerCase().contains(lowerCaseQuery)) {
                    filteredParks.add(park);
                }
            }
        }
        
        parkAdapter.notifyDataSetChanged();
    }

    private void showParkDetails(Park park) {
        ParkDetailsFragment fragment = ParkDetailsFragment.newInstance(park);
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
} 