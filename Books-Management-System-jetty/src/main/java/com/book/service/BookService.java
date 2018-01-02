package com.book.service;

import com.book.dao.BookMapper;
import com.book.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookService {

    @Autowired
    private BookMapper bookMapper;


    public List<Book> queryBook(String searchWord) {
        Set<Book> set = new HashSet<Book>();

        List<Book> list = new ArrayList<Book>();
        /*String word1 = searchWord + "%";
        list.addAll(bookMapper.selectBookByWord(word1));
        String word2 = "%" + searchWord;*/
        /*list.addAll(bookMapper.selectBookByWord(word2));*/
        list.addAll(bookMapper.selectBookByWord(searchWord));
        /*set.addAll(list);
        list.clear();
        list.addAll(set);*/
        return list;
    }

    public List<Book> getAllBooks() {
        return bookMapper.selectAllBook();
    }

    public int deleteBook(long bookId) {
        return bookMapper.deleteBookByBookId(bookId);
    }

    public boolean addBook(Book book) {
        return bookMapper.insertBook(book) > 0;
    }

    public Book getBook(Long bookId) {
        Book book = bookMapper.selectBookByBookId(bookId);
        return book;
    }

    public boolean updateBook(Book book) {
        int i = bookMapper.updateBooK(book);
        System.out.println("book:" + book.toString());
        System.out.println("update result:" + i);
        Book b = bookMapper.selectBookByBookId(book.getBookId());
        System.out.println("get " + b.toString());
        return i > 0;
    }

    public boolean matchBook(String word) {
        return bookMapper.selectBookByWord(word).size() > 0;
    }

    public Long getMaxBookId(){
        return bookMapper.selectMaxBookId();
    }
}
