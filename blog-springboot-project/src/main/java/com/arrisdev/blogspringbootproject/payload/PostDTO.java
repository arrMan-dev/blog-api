package com.arrisdev.blogspringbootproject.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

/**@Author Arris Manduma
 * @Date 11/29/2021
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private Long id;

    @NotEmpty(message = "Title cannot be empty.")
    private String title;
    @NotEmpty(message = "Description cannot be empty.")
    private String description;

    @NotEmpty
    @Size(min = 3, message = "The content must have at least 3 characters.")
    private String content;
    private Set<CommentDTO> comments;
}
