package com.arrisdev.blogspringbootproject.payload;

import lombok.Data;

@Data
public class LoginDTO {
    private String usernameOrEmail;
    private String password;
}
