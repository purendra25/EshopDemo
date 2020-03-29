package com.example.eshopdemo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.eshopdemo.model.Book;
import com.example.eshopdemo.model.BookDAO;
import com.example.eshopdemo.model.BooksDatabase;
import com.example.eshopdemo.model.Category;
import com.example.eshopdemo.model.CategoryDAO;

import java.util.List;

public class Repository {

    CategoryDAO categoryDAO;
    BookDAO bookDAO;

    public Repository(Application application) {

        BooksDatabase booksDatabase = BooksDatabase.getInstance(application);
        categoryDAO=  booksDatabase.categoryDAO();
        bookDAO = booksDatabase.bookDAO();
    }

    public LiveData<List<Category>> getAllCategories(){
        return categoryDAO.getAllCategories();
    }

    public LiveData<List<Book>> getAllBookList(int categoryId){
        return bookDAO.getBooks(categoryId);
    }
}
