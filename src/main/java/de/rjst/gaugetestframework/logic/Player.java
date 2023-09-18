package de.rjst.gaugetestframework.logic;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Player {
    private Long id;
    private LocalDate birthDay;
    private String firstName;
    private String password;
    private String gender;
    private String surname;
}