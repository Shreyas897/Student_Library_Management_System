package com.example.Student_Library_Management_System.Service;

import com.example.Student_Library_Management_System.DTOs.IssueBookRequestDto;
import com.example.Student_Library_Management_System.Enums.CardStatus;
import com.example.Student_Library_Management_System.Enums.TransactionStatus;
import com.example.Student_Library_Management_System.Models.Book;
import com.example.Student_Library_Management_System.Models.Card;
import com.example.Student_Library_Management_System.Models.Transactions;
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

    @Autowired
    TransactionRepository transactionRepository;
    public String issueBook(IssueBookRequestDto issueBookRequestDto) throws Exception{
        Card card=cardRepository.findById(issueBookRequestDto.getCardId()).get();
        Book book=bookRepository.findById(issueBookRequestDto.getBookId()).get();
        Transactions transactions= new Transactions();
        transactions.setIssueOperation(true);
        transactions.setBook(book);
        transactions.setCard(card);
        transactions.setTransactionStatus(TransactionStatus.Pending);
        if(book==null|| book.isIssued()==true)
        {
            transactions.setTransactionStatus(TransactionStatus.Failed);
            transactionRepository.save(transactions);
            throw new Exception("Book not available");
        }
        if(card==null||(card.getCardStatus()!= CardStatus.Activated))
        {
            transactions.setTransactionStatus(TransactionStatus.Failed);
            transactionRepository.save(transactions);
            throw new Exception("Card not valid");
        }
        transactions.setTransactionStatus(TransactionStatus.Success);
        List<Book> bookList=card.getBooksIssued();
        bookList.add(book);
        card.setBooksIssued(bookList);
        List<Transactions>transactionsListForCard=card.getTransactionsList();
        transactionsListForCard.add(transactions);
        card.setTransactionsList(transactionsListForCard);
        List<Transactions>transactionsListForBook=book.getTransactionsList();
        transactionsListForBook.add(transactions);
        book.setTransactionsList(transactionsListForBook);
        book.setIssued(true);
        cardRepository.save(card);
        return "Book Issued";
    }
}
