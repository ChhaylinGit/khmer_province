package com.example.khmerprovince.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khmerprovince.R;
import com.example.khmerprovince.ShowSearchView;
import com.example.khmerprovince.adapter.ListViewAdapter;
import com.example.khmerprovince.adapter.ProvinceAdapter;
import com.example.khmerprovince.adapter.SearchAdapter;
import com.example.khmerprovince.model.District;
import com.example.khmerprovince.model.Province;
import com.example.khmerprovince.model.SearchModelClass;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchAdapter.ItemListener{


    private Toolbar toolbar;
    private ImageView image_search_back;
    private String text = "";
    private CardView card_search;
    private RecyclerView recyclerView;
    private RelativeLayout view_search;
    private EditText edit_text_search;
    private View line_divider, toolbar_shadow;
    private List<SearchModelClass> modelsList,filterModels;
    private List<SearchModelClass> cuisinesModels;
    private SearchAdapter searchAdapter;
    private ListViewAdapter listViewAdapter;
    private ShowSearchView searchView;
    private ListView listView;

    boolean editTextChangedFromClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = new ShowSearchView();

        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        image_search_back = findViewById(R.id.image_search_back);
        card_search = findViewById(R.id.card_search);
        recyclerView = findViewById(R.id.recyclerView);
        view_search = findViewById(R.id.view_search);
        edit_text_search = findViewById(R.id.edit_text_search);
        line_divider = findViewById(R.id.line_divider);
        toolbar_shadow = findViewById(R.id.toolbar_shadow);
        listView = findViewById(R.id.listView);

        TextView no_results = (TextView) findViewById(R.id.txtNoResultsFound);
        listView.setEmptyView(no_results);
        loadModel();

        image_search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = "";
                searchView.handleToolBar(SearchActivity.this, card_search, toolbar, view_search, recyclerView, edit_text_search, line_divider);
                toolbar_shadow.setVisibility(View.VISIBLE);
            }
        });

        edit_text_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                text = charSequence.toString();

                if (editTextChangedFromClick) {
                    editTextChangedFromClick = false;

                    if (recyclerView.getVisibility() == View.VISIBLE)
                        recyclerView.setVisibility(View.GONE);

                } else {

                    if (recyclerView.getVisibility() != View.VISIBLE)
                        recyclerView.setVisibility(View.VISIBLE);

                    if (charSequence.toString().length() > 0) {
                        performFiltering(filterModels);
                    } else {
                        SearchAdapter cuisineSearchAdapter = new SearchAdapter(cuisinesModels, modelsList, SearchActivity.this, SearchActivity.this, text);
                        cuisineSearchAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(cuisineSearchAdapter);

                        listViewAdapter = new ListViewAdapter(modelsList);
                        listView.setAdapter(listViewAdapter);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edit_text_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Your piece of code on keyboard search click
                    recyclerView.setVisibility(View.GONE);
                    ((InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view_search.getWindowToken(), 0);

                    listViewAdapter.getFilter().filter(v.getText().toString());

                    return true;
                }
                return false;
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItem = item.getItemId();
                switch (menuItem) {
                    case R.id.action_search:
                        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                        searchAdapter = new SearchAdapter(cuisinesModels, filterModels, SearchActivity.this, SearchActivity.this, text);
                        recyclerView.setAdapter(searchAdapter);
                        searchView.handleToolBar(SearchActivity.this, card_search, toolbar, view_search, recyclerView, edit_text_search, line_divider);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void loadModel()
    {
        modelsList = new ArrayList<>();
        cuisinesModels = new ArrayList<>();

        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("District");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    District districts = snapshot.getValue(District.class);
                    modelsList.add(new SearchModelClass(districts.getProid(),districts.getDistrict(),new ArrayList<>(Arrays.asList("Cafes", "Burgers")),false,-1));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){

            }
        });

        filterModels = new ArrayList<>(modelsList);
        listViewAdapter = new ListViewAdapter(filterModels);
        listView.setAdapter(listViewAdapter);

        for (SearchModelClass modelClass : modelsList)
        {
            Log.e("ooooop",modelClass.name);
        }
    }

    @Override
    public void onItemClick(SearchModelClass model) {
        editTextChangedFromClick = true;

        if (model.isCuisine) {
            edit_text_search.setText(model.name);
            listViewAdapter.getFilter().filter(model.name);

        } else {

            edit_text_search.setText(model.name);
            searchView.handleToolBar(SearchActivity.this, card_search, toolbar, view_search, recyclerView, edit_text_search, line_divider);
            Toast.makeText(getApplicationContext(), model.name + " was selected.", Toast.LENGTH_LONG).show();
        }
    }

    public void performFiltering(List<SearchModelClass> filteredSuggestions) {

        filteredSuggestions.clear();
        for (SearchModelClass model : modelsList) {
            if (model.name.toLowerCase().contains(text.toLowerCase())) {
                filteredSuggestions.add(model);
            }
        }

        SearchAdapter cuisineSearchAdapter = new SearchAdapter(cuisinesModels, filteredSuggestions, SearchActivity.this, SearchActivity.this, text);
        cuisineSearchAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(cuisineSearchAdapter);
    }
}
