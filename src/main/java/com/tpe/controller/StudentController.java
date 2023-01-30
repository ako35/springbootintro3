package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students") // http://localhost:8080/students
public class StudentController {

    Logger logger=LoggerFactory.getLogger(StudentController.class);

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

    // !!! get by lastName
    @GetMapping("/query/lastname")
    public ResponseEntity<List<Student>> getStudentByLastName(@RequestParam("lastName") String lastName){
        List<Student> studentList=studentService.getStudentByLastName(lastName);
        return ResponseEntity.ok(studentList);
    }

    // !!! get all student by grade
    @GetMapping("/grade/{grade}") // http://localhost:8080/students/grade/75
    public ResponseEntity<List<Student>> getStudentEqualsGrade(@PathVariable("grade") Integer grade){
        List<Student> studentList= studentService.findAllEqualsGrade(grade);
        return  ResponseEntity.ok(studentList);
    }

    // !!! DB den direk DTO olarak data alabilir miyim
    @GetMapping("/query/dto") // http://localhost:8080/students/query/dto?id=1
    public ResponseEntity<StudentDTO> getStudentDTO(@RequestParam("id") Long id){
        StudentDTO studentDTO=studentService.findStudentDTOById(id);
        return ResponseEntity.ok(studentDTO);
    }

    @GetMapping("/welcome") // http://localhost:8080/students/welcome
    public String welcome(HttpServletRequest request){
        logger.warn("---------------------- Welcome {}",request.getServletPath());
        return "Student Controller a Hos Geldiniz!!!";
    }

}
