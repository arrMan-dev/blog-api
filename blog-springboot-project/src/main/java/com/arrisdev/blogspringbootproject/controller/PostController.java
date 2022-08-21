package com.arrisdev.blogspringbootproject.controller;

import com.arrisdev.blogspringbootproject.payload.PostDTO;
import com.arrisdev.blogspringbootproject.payload.PostResponse;
import com.arrisdev.blogspringbootproject.service.PostService;
import com.arrisdev.blogspringbootproject.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**@Author Arris Manduma
 * @Date 11/29/2021
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/posts")
@Slf4j
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/v1/create")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
        log.info("Creating post in controller:{}", postDTO);
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    /**This method will be using also
     * for pagination
     * @return
     */
    @GetMapping("/v1/getAll")
    public PostResponse getAllPosts(@RequestParam(value = "pageNo", defaultValue = Constant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = Constant.DEFAULT_PAGE_SIZE,required = false) int pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = Constant.DEFAULT_SORT_BY, required = false) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = Constant.DEFAULT_SORT_DIR, required = false) String sortDir){
        log.info("Fetching all post with pagination in controller: {}", pageNo, pageSize, sortBy);
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/v1/get/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(value = "id") Long id){
        log.info("Fetching post by id in the controller: {}", id);
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/v1/put/{id}")
    public ResponseEntity<PostDTO> updatePosts(@Valid @RequestBody PostDTO postDTO,
                                               @PathVariable (value = "id") Long id ){
        log.info("Updating post in the controller: {}", postDTO, id);
        PostDTO postResponse = postService.updatePost(postDTO, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/v1/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable (value = "id") Long id){
        log.info("deleting post in controller:{}", id);
        postService.deleteById(id);
        return new ResponseEntity<>("post entity deleted successfully!!!", HttpStatus.OK);
    }
}
