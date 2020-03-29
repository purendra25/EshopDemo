package com.example.eshopdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.eshopdemo.databinding.ActivityMainBinding;
import com.example.eshopdemo.model.Book;
import com.example.eshopdemo.model.Category;
import com.example.eshopdemo.model.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView booksRecyclerView;
    private BooksAdapter booksAdapter;
    private ActivityMainBinding activityMainBinding;
    private ArrayList<Book> booksList;
    private int selectedBookId;

    MainActivityViewModel mainActivityViewModel;
    private List<Category>  categoriesList;
    private Category selectedCategory;

    private MainActivityClickHandlers handlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        handlers = new MainActivityClickHandlers();
        activityMainBinding.setClickHandlers(handlers);

        mainActivityViewModel = new MainActivityViewModel(getApplication());
        mainActivityViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoriesList = categories;
                showOnSpinner();
            }
        });



    }





    private void loadRecyclerView() {

        booksRecyclerView = activityMainBinding.secondaryLayout.rvBooks;
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        booksRecyclerView.setHasFixedSize(true);

        booksAdapter = new BooksAdapter();
        booksRecyclerView.setAdapter(booksAdapter);

        booksAdapter.setBooks(booksList);

        booksAdapter.setListener(new BooksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                selectedBookId = book.getBookId();


            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Book bookToDelete = booksList.get(viewHolder.getAdapterPosition());
//                mainActivityViewModel.deleteBook(bookToDelete);
            }
        }).attachToRecyclerView(booksRecyclerView);


    }


    private void showOnSpinner() {

        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<Category>(this, R.layout.spinner_item, categoriesList);
        categoryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityMainBinding.setCategoryAdapter(categoryArrayAdapter);


    }

    public class MainActivityClickHandlers {

        public void onFABClicked(View view) {

            //Toast.makeText(getApplicationContext()," FAB Clicked",Toast.LENGTH_LONG).show();



        }

        public void onSelectItem(AdapterView<?> parent, View view, int pos, long id) {

            selectedCategory = (Category) parent.getItemAtPosition(pos);

            String message = " id is " + selectedCategory.getId() + "\n name is " + selectedCategory.getCategoryName() + "\n email is " + selectedCategory.getCategoryDescription();

            loadBooksArrayList(selectedCategory.getId());
        }

    }

    private void loadBooksArrayList(int id) {

        mainActivityViewModel.getAllBooks(id).observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                booksList = (ArrayList<Book>) books;
                loadRecyclerView();

            }
        });
    }


}
