package com.tpe.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull(message = "first name can not be null")
    @NotBlank(message = "first name can not be white space")
    @Size(min = 2,max = 25,message = "first name '${validatedValue}' must be {min} and {max} long")
    @Column(nullable = false,length = 25)
    private String name;

    @Column(nullable = false,length = 25)
    private String lastName;

    private Integer grade;

    @Column(nullable = false,length = 50,unique = true)
    @Email(message = "Provide valid email")
    private String email;

    private String phoneNumber;

    @Setter(AccessLevel.NONE)
    private LocalDateTime createDate=LocalDateTime.now();

}
