package com.example.Student_Library_Management_System.Service;

import com.example.Student_Library_Management_System.DTOs.IssueBookRequestDto;
import com.example.Student_Library_Management_System.Models.Book;
import com.example.Student_Library_Management_System.Models.Card;
import com.example.Student_Library_Management_System.Repository.BookRepository;
import com.example.Student_Library_Management_System.Repository.CardRepository;
import com.example.Student_Library_Management_System.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    BookRepository bookRepository;
    public String issueBook(IssueBookRequestDto issueBookRequestDto){
        Card card=cardRepository.findById(issueBookRequestDto.getCardId()).get();
        Book book=bookRepository.findById(issueBookRequestDto.getBookId()).get();
        List<Book> bookList=card.getBooksIssued();
        bookList.add(book);
        book.setIssued(true);
        cardRepository.save(card);
        bookRepository.save(book);
        return "Book Issued";
    }
}
