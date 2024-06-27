package com.sg.govtech.Assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsersDto {

    private Integer id;
    private String username;
    private String password;
    private String email;


}
