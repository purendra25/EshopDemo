package com.example.eshopdemo;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eshopdemo.databinding.BookListItemBinding;
import com.example.eshopdemo.model.Book;
import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder>{
    private OnItemClickListener listener;
    private ArrayList<Book> books=new ArrayList<>();

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        BookListItemBinding bookListItemBinding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.book_list_item,viewGroup,false);
        return new BookViewHolder(bookListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, int i) {
         Book book=books.get(i);
         bookViewHolder.bookListItemBinding.setBook(book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(ArrayList<Book> newBooks) {
       // this.books = books;
       // notifyDataSetChanged();
       final DiffUtil.DiffResult result=DiffUtil.calculateDiff(new BooksDiffCallback(books,newBooks),false);
       books=newBooks;
       result.dispatchUpdatesTo(BooksAdapter.this);

    }

    class BookViewHolder extends RecyclerView.ViewHolder{
    private BookListItemBinding bookListItemBinding;

    public BookViewHolder(@NonNull BookListItemBinding bookListItemBinding) {
        super(bookListItemBinding.getRoot());
        this.bookListItemBinding=bookListItemBinding;
        bookListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              int clickedPosition=getAdapterPosition();

              if(listener!=null && clickedPosition!=RecyclerView.NO_POSITION) {
                  listener.onItemClick(books.get(clickedPosition));
              }
            }
        });

    }

}

public interface OnItemClickListener{
    void onItemClick(Book book);
}

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
