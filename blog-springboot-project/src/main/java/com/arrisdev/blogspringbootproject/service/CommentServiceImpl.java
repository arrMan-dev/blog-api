package com.arrisdev.blogspringbootproject.service;

import com.arrisdev.blogspringbootproject.entity.Comment;
import com.arrisdev.blogspringbootproject.entity.Post;
import com.arrisdev.blogspringbootproject.exceptions.BlogApiException;
import com.arrisdev.blogspringbootproject.exceptions.ResourceNotFoundException;
import com.arrisdev.blogspringbootproject.payload.CommentDTO;
import com.arrisdev.blogspringbootproject.repository.CommentRepository;
import com.arrisdev.blogspringbootproject.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**@Author Arris Manduma
 * @Date 11/29/2021
 */

@Service
public class CommentServiceImpl implements CommentService {

    private final ModelMapper mapper;
    private final CommentRepository repository;
    private final PostRepository postRepository;

    public CommentServiceImpl(ModelMapper mapper, CommentRepository repository, PostRepository postRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.postRepository = postRepository;

    }

    /**Using ModelMapper to convert
     * entity to DTO
     * @param comment
     * @return
     */
    private CommentDTO mapToDto(Comment comment){
        //CommentDTO commentDTO = new CommentDTO();
        //commentDTO.setId(comment.getId());
        //commentDTO.setName(comment.getName());
        //commentDTO.setEmail(comment.getEmail());
       // commentDTO.setBody(comment.getBody());
        CommentDTO commentDTO = mapper.map(comment, CommentDTO.class);
        return commentDTO;
    }

    /**using mapperEntity to
     * covertDTO to entity
     * @param commentDTO
     * @return
     */
    private Comment mapToEntity(CommentDTO commentDTO){
        //Comment comment = new Comment();
        //comment.setId(commentDTO.getId());
        //comment.setName(commentDTO.getName());
        //comment.setEmail(commentDTO.getEmail());
        //comment.setBody(commentDTO.getBody());
        Comment comment = mapper.map(commentDTO, Comment.class);
        return comment;
    }

    @Override
    public CommentDTO createComment(Long id, CommentDTO commentDTO) {
        Comment comment = mapToEntity(commentDTO);

        //retrieve post entity by id
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        comment.setPost(post);

        Comment newComment = repository.save(comment);
        return mapToDto(newComment);

    }


    @Override
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        //retrieve comment by post id
        List<Comment> commentList = repository.findByPostId(postId);

        return commentList.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {
        Post getPost = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        Comment getComment = repository.findById(commentId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "id", commentId));
        if (!getComment.getPost().getId().equals(getPost.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return mapToDto(getComment);
    }

    @Override
    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = repository.findById(commentId)
                .orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post.");
        }

        comment.setName(commentRequest.getName());
        comment.setBody(commentRequest.getBody());
        comment.setEmail(commentRequest.getEmail());

        Comment updateComment = repository.save(comment);
        return mapToDto(updateComment) ;
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = repository.findById(commentId)
                .orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post.");
        }

        repository.delete(comment);
    }
}
