package com.example.Student_Library_Management_System.Service;

import com.example.Student_Library_Management_System.DTOs.StudentUpdateMobNoDtr;
import com.example.Student_Library_Management_System.Enums.CardStatus;
import com.example.Student_Library_Management_System.Models.Card;
import com.example.Student_Library_Management_System.Models.Student;
import com.example.Student_Library_Management_System.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    public String createStudent(Student student){
        Card card=new Card();
        card.setCardStatus(CardStatus.Activated);
        card.setStudent(student);
        student.setCard(card);
        studentRepository.save(student);
        return "Student Created Successfully";

    }
    public String getNameByEmail(String email){
        Student student= studentRepository.findByEmail(email);
        return student.getName();
    }
    public String updateMobNo(StudentUpdateMobNoDtr studentUpdateMobNoDtr){
        Student student= studentRepository.findById(studentUpdateMobNoDtr.getId()).get();
        student.setMob_no(studentUpdateMobNoDtr.getMob_no());
        studentRepository.save(student);
        return "Mobile No updated";
    }
}
