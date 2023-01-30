package com.tpe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "Please provide firstname")
    private String firstName;

    @NotBlank(message = "Please provide lastname")
    private String lastName;

    @NotBlank(message = "Please provide username")
    @Size(min = 5,max = 10,message = "Please provide username min={min}, max={10} chars long")
    private String userName;

    @NotBlank(message = "Please provide password")
    private String password;

}
