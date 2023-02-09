package com.luantan.tratudienanhviet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.luantan.tratudienanhviet.Models.ItemSearchAdapter;
import com.luantan.tratudienanhviet.Models.Utils;
import com.luantan.tratudienanhviet.Models.Word;

import java.util.ArrayList;

public class SearchWordActivity extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
    ItemSearchAdapter wordAdapter;
    ArrayList<Word> arrayList;
    Utils utils;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_word);

        getSupportActionBar().setTitle(getResources().getString(R.string.tSearch));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbHelper = new DBHelper(getApplication());
        listView = findViewById(R.id.listView);
        arrayList = dbHelper.getALLWordHistory();
        //Toast.makeText(this, "" + arrayList.size(), Toast.LENGTH_SHORT).show();
        wordAdapter = new ItemSearchAdapter(SearchWordActivity.this, arrayList);
        listView.setAdapter(wordAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // cap nhat lich su
                dbHelper.UpdateHistory_Click(arrayList.get(position).getMaTu());

//                if (dbHelper.check_primary_key_history(arrayList.get(position).getMaTu())) {
//                    dbHelper.InsertWord_History(arrayList.get(position));
//                    Toast.makeText(SearchWordActivity.this, "Da them", Toast.LENGTH_SHORT).show();
//                }

                Intent intent = new Intent(SearchWordActivity.this, DetailWordActivity.class);
                intent.putExtra("Word", arrayList.get(position));
                startActivity(intent);

//                    dbHelper.DeleteWord_History(arrayList.get(position).getMaTu());
//                wordAdapter.clear();
//                wordAdapter.addAll(dbHelper.getALLWordHistory());
//                    wordAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_searchword, menu);
        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_word).getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchWord(newText);
                return false;
            }
        });
        return true;
    }

    private void searchWord(String newText) {
        ArrayList<Word> tmp = new ArrayList<>();
        if (newText.isEmpty() || newText == null) {
            //Toast.makeText(this, "rong ne" + utils.lstLichSu.size(), Toast.LENGTH_SHORT).show();
            wordAdapter.clear();
            wordAdapter.addAll(dbHelper.getALLWordHistory());
            wordAdapter.notifyDataSetChanged();
        }
        else {
            int sl = 0;
            if(dbHelper.timTu(newText.trim()) != null)
            {
                tmp.add(dbHelper.timTu(newText));
            }
            else
            {
                for (Word word : utils.lstTuVung) {
                    if (word.getTenTu().toLowerCase().contains(newText.toLowerCase())) {
                        tmp.add(word);
                        sl++;
                        if (sl > 30)
                            break;
                    }
                }
            }
            if (tmp.size() > 0) {
                wordAdapter.clear();
                wordAdapter.addAll(tmp);
                wordAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(SearchWordActivity.this, MainActivity.class));
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}