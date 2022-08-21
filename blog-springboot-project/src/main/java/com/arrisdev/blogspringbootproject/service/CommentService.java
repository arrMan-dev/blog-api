package com.arrisdev.blogspringbootproject.service;

import com.arrisdev.blogspringbootproject.payload.CommentDTO;

import java.util.List;

/**@Author Arris Manduma
 * @Date 11/29/2021
 */

public interface CommentService {

    CommentDTO createComment(Long id, CommentDTO commentDTO);
    List<CommentDTO> getCommentsByPostId(Long postId);
    CommentDTO getCommentById(Long postId, Long commentId);
    CommentDTO updateComment(Long postId,Long commentId, CommentDTO commentRequest);
    void deleteComment(Long postId, Long commentId);
}
