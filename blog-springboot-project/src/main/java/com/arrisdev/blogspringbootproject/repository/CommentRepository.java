package com.arrisdev.blogspringbootproject.repository;

import com.arrisdev.blogspringbootproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**@Author Arris Manduma
 * @Date 11/29/2021
 */

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByPostId(Long postId);
}
