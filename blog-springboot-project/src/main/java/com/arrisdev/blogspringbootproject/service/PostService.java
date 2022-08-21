package com.arrisdev.blogspringbootproject.service;

import com.arrisdev.blogspringbootproject.payload.PostDTO;
import com.arrisdev.blogspringbootproject.payload.PostResponse;

/**@Author Arris Manduma
 * @Date 11/29/2021
 */

public interface PostService {

    PostDTO createPost(PostDTO postDTO);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDTO getPostById(Long id);
    PostDTO updatePost(PostDTO postDTO, Long id);
    void deleteById(Long id);
}
