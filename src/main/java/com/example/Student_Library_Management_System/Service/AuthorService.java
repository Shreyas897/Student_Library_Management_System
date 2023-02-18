package com.example.Student_Library_Management_System.Service;

import com.example.Student_Library_Management_System.Models.Author;
import com.example.Student_Library_Management_System.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;
    public String createAuthor(Author author){
        authorRepository.save(author);
        return "Author created successfully";
    }
}
