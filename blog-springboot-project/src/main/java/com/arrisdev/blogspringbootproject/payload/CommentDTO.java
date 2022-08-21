package com.arrisdev.blogspringbootproject.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**@Author Arris Manduma
 * @Date 11/29/2021
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Long id;
    //name should not be empty
    @NotEmpty(message = "Name should not be empty!!")
    private String name;

    @NotEmpty(message = "Email should not be empty and must be a real email.")
    @Email
    private String email;

    @NotEmpty(message = "The body cannot be empty.")
    @Size(min = 3, message = "the body can have at least 3 characters.")
    private String body;
}
