package com.arrisdev.blogspringbootproject.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**@Author Arris Manduma
 * @Date 11/29/2021
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private List<PostDTO> content;
    private int pageNo;
    private int pageSize;
    private int totalPage;
    private long totalElement;
    private boolean last;
}
