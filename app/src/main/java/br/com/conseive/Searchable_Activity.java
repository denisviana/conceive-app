package br.com.conseive;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Denis Viana on 07/12/2016.
 */

public class Searchable_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void handleSearch(Intent intent) {

        if (intent.ACTION_SEARCH.equalsIgnoreCase(intent.getAction())) {
            String q = intent.getStringExtra(SearchManager.QUERY);

            setTitle(q);

        }
    }




}
