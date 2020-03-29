package com.example.eshopdemo.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.eshopdemo.Repository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {


    Repository repository;

    public MainActivityViewModel(Application application) {
        super(application);
         repository = new Repository(application);
    }



    public LiveData<List<Category>> getAllCategories(){
        return repository.getAllCategories();
    }

    public LiveData<List<Book>> getAllBooks(int categoryId){
        return repository.getAllBookList(categoryId);
    }


}
