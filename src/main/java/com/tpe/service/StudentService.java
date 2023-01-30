package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public void createStudent(Student student) {
        boolean isExistsByEmail=studentRepository.existsByEmail(student.getEmail());
        if (isExistsByEmail){
            throw new ConflictException("Email is already exist");
        }
        studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Resource Not Found Exception"));
    }

    public void deleteStudentById(Long id) {
        studentRepository.deleteById(getStudentById(id).getId());
    }

    public void updateStudentById(Long id, StudentDTO studentDTO) {
        boolean existsEmail=studentRepository.existsByEmail(studentDTO.getEmail());
        Student student=getStudentById(id);

        if (existsEmail && !studentDTO.getEmail().equals(student.getEmail())){
            throw new ConflictException("Email is already exist");
        }

        student.setName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setGrade(studentDTO.getGrade());
        student.setEmail(studentDTO.getEmail());
        student.setPhoneNumber(studentDTO.getPhoneNumber());

        studentRepository.save(student);

    }

    public Page<Student> getAllStudentWithPage(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }


    public List<Student> getStudentByLastName(String lastName) {
        return studentRepository.findByLastName(lastName);
    }

    public List<Student> findAllEqualsGrade(Integer grade) {
        return studentRepository.findallEqualsGrade(grade);
    }

    public StudentDTO findStudentDTOById(Long id) {
        return studentRepository.findStudentDTOById(id).orElseThrow(()->new ResourceNotFoundException("Student not found with id: "+id));
    }
}
