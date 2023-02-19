package com.example.Student_Library_Management_System.Service;

import com.example.Student_Library_Management_System.DTOs.BookRequestDto;
import com.example.Student_Library_Management_System.Models.Author;
import com.example.Student_Library_Management_System.Models.Book;
import com.example.Student_Library_Management_System.Repository.AuthorRepository;
import com.example.Student_Library_Management_System.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    AuthorRepository authorRepository;
    public String addBook(BookRequestDto bookRequestDto) throws Exception{
            int author_id=bookRequestDto.getAuthorId();
            Author author= authorRepository.findById(author_id).get();
            Book book=new Book();
            book.setPages(bookRequestDto.getPages());
            book.setName(bookRequestDto.getName());
            book.setGenre(bookRequestDto.getGenre());
            book.setIssued(false);
            book.setAuthor(author);
            List<Book>currentBook=author.getBooksWritten();
            currentBook.add(book);

            authorRepository.save(author);


        return "Book saved";
    }
}
