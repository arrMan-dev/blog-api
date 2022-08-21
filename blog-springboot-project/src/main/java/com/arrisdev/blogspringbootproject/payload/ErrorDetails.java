package com.arrisdev.blogspringbootproject.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**@Author Arris Manduma
 * @Date 11/29/2021
 */

@Data
@AllArgsConstructor
public class ErrorDetails {

    private Date timestamp;
    private String message;
    private String details;
    private String errorCode;


}
