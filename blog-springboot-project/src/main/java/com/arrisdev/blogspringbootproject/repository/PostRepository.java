package com.arrisdev.blogspringbootproject.repository;

import com.arrisdev.blogspringbootproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/**@Author Arris Manduma
 * @Date 11/29/2021
 */

public interface PostRepository extends JpaRepository<Post, Long> {

}
