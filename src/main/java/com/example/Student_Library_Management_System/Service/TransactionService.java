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
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    BookRepository bookRepository;

    @Autowired
    TransactionRepository transactionRepository;
    public String issueBook(IssueBookRequestDto issueBookRequestDto) throws Exception{
        int bookId = issueBookRequestDto.getBookId();
        int cardId = issueBookRequestDto.getCardId();



        Book book = bookRepository.findById(bookId).get();

        Card card = cardRepository.findById(cardId).get();



        Transactions transaction = new Transactions();


        transaction.setBook(book);
        transaction.setCard(card);
        transaction.setTransactionID(UUID.randomUUID().toString());
        transaction.setIssueOperation(true);
        transaction.setTransactionStatus(TransactionStatus.Pending);



        if(book==null || book.isIssued()==true){
            transaction.setTransactionStatus(TransactionStatus.Failed);
            transactionRepository.save(transaction);
            throw new Exception("Book is not available");

        }

        if(card==null || (card.getCardStatus()!= CardStatus.Activated)){

            transaction.setTransactionStatus(TransactionStatus.Failed);
            transactionRepository.save(transaction);
            throw  new Exception("Card is not valid");
        }





        transaction.setTransactionStatus(TransactionStatus.Success);



        book.setIssued(true);

        List<Transactions> listOfTransactionForBook = book.getTransactionsList();
        listOfTransactionForBook.add(transaction);
        book.setTransactionsList(listOfTransactionForBook);



        List<Book> issuedBooksForCard = card.getBooksIssued();
        issuedBooksForCard.add(book);
        card.setBooksIssued(issuedBooksForCard);

        for(Book b: issuedBooksForCard){
            System.out.println(b.getName());
        }


        List<Transactions> transactionsListForCard = card.getTransactionsList();
        transactionsListForCard.add(transaction);
        card.setTransactionsList(transactionsListForCard);


        cardRepository.save(card);


        return "Book issued successfully";
    }

    public String getTransactions(int bookId,int cardId){

        List<Transactions> transactionsList = transactionRepository.getTransactionsForBookAndCard(bookId,cardId);

        String transactionId = transactionsList.get(0).getTransactionID();

        return transactionId;
    }
}
