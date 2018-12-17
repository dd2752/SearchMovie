package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    EditText editText;
    Search search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        editText = (EditText) findViewById(R.id.editTitle);
        search = new Search();




    }

    public void search(View view) {
        if(editText.getText().toString().matches("")){
            Toast.makeText(this, "영화제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        else{
            String movieTitle = editText.getText().toString();
            SearchTask searchTask = new SearchTask();
            searchTask.execute(movieTitle);
        }
    }

    private class SearchTask extends AsyncTask<String,String,List<Movie>>{

        @Override
        protected List<Movie> doInBackground(String... strings) {
            return search.getMovieList("yNopDY3Ewz9SN6LsNfpG","N6Py__ACW0",strings[0]);
        }

        @Override
        protected void onPostExecute(List<Movie> movieList) {
            super.onPostExecute(movieList);
            Adapter adapter = new Adapter(movieList);
            recyclerView.setAdapter(adapter);

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }
}
