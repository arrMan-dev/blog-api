package com.arrisdev.blogspringbootproject.controller;

import com.arrisdev.blogspringbootproject.payload.CommentDTO;
import com.arrisdev.blogspringbootproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**@Author Arris Manduma
 * @Date 11/29/2021
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/comments")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/v1/createComment/{postId}")
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentDTO commentDTO,
                                                    @PathVariable (value = "postId") Long postId){
        log.info("Creating comment in controller: {}",postId, commentDTO);
       return new ResponseEntity<>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/v1/get/{postId}/comment")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable(value = "postId") Long postId){
        log.info("fetching comment by post ID in controller: {}",postId);
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId), HttpStatus.OK);
    }

    @GetMapping("/v1/get/{postId}/post/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable(value = "postId") Long postId,
                                                     @PathVariable(value = "commentId") Long commentId){
        log.info("Fetching comment ID by post ID in controller: {}",postId);
        return new ResponseEntity<>(commentService.getCommentById(postId,commentId), HttpStatus.OK);
    }

    @PutMapping("/v1/put/{postId}/post/{commentId}")
    public ResponseEntity<CommentDTO> updateCommentById(@PathVariable(value = "postId") Long postId,
                                                        @PathVariable(value = "commentId") Long commentId,
                                                        @Valid @RequestBody CommentDTO commentDTO){
        log.info("Updating comment by post ID in controller: {}",postId, commentId, commentDTO);
        CommentDTO updateComment = commentService.updateComment(postId, commentId, commentDTO);
        return new ResponseEntity<>(updateComment, HttpStatus.OK);
    }

    @DeleteMapping("/v1/delete/{postId}/post/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                                @PathVariable(value = "commentId") Long commentId){
        log.info("Deleting comment by post IDin controller: {}",postId, commentId);
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment has been deleted successfully!!!", HttpStatus.OK);
    }
}
