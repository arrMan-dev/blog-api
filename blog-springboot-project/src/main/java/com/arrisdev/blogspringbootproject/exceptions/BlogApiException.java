package com.arrisdev.blogspringbootproject.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**@Author Arris Manduma
 * @Date 11/29/2021
 */

@Getter
public class BlogApiException extends RuntimeException{

    private HttpStatus status;
    private String error_message;

    public BlogApiException(HttpStatus status, String error_message) {
        this.status = status;
        this.error_message = error_message;
    }

    public BlogApiException(String message, HttpStatus status, String error_message) {
        super(message);
        this.status = status;
        this.error_message = error_message;
    }
}
