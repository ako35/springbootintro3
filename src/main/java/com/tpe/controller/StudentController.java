package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students") // http://localhost:8080/students
public class StudentController {

    @Autowired
    private StudentService studentService;

    // !!! get all students - butun ogrenciler gelsin
    @GetMapping
    public ResponseEntity<List<Student>> getAll(){
        List<Student> students= studentService.getAll();
        return ResponseEntity.ok(students);
    }

    // !!! student objesi olusturalim
    @PostMapping
    public ResponseEntity<String> createStudent(@Valid @RequestBody Student student){
        studentService.createStudent(student);
        return ResponseEntity.ok("Student is created successfully");
    }

    // !!! RequestParam kullanarak id ile student getirme
    @GetMapping("/query") // http://localhost:8080/students/query?id=1
    public ResponseEntity<Student> getStudentById(@RequestParam("id") Long id){
        Student student=studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    // !!! PatVariable kullanarak id ile student getirme
    @GetMapping("/{id}") // http://localhost:8080/students/1
    public ResponseEntity<Student> getStudentByIdPathVariable(@PathVariable("id") Long id){
        Student student=studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    // !!! delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable("id") Long id){
        studentService.deleteStudentById(id);
        return ResponseEntity.ok("Student is deleted successfully");
    }

    // !!! update
    @PutMapping("/{id}")
    public ResponseEntity<String> updateStudentById(@PathVariable("id") Long id,@Valid @RequestBody StudentDTO studentDTO){
        studentService.updateStudentById(id,studentDTO);
        return ResponseEntity.ok("Student is updated successfully");
    }

    // !!! Pageable
    @GetMapping("/page")
    public ResponseEntity<Page<Student>> getAllStudentWithPage(@RequestParam("page") int page,
                                                               @RequestParam("size") int size,
                                                               @RequestParam("sort") String prop,
                                                               @RequestParam("direction")Sort.Direction direction){

        Pageable pageable=PageRequest.of(page,size,Sort.by(direction,prop));
        Page<Student> studentPage=studentService.getAllStudentWithPage(pageable);
        return ResponseEntity.ok(studentPage);

    }


}
