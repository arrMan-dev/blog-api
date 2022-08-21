package com.arrisdev.blogspringbootproject.service;

import com.arrisdev.blogspringbootproject.entity.Post;
import com.arrisdev.blogspringbootproject.exceptions.ResourceNotFoundException;
import com.arrisdev.blogspringbootproject.payload.PostDTO;
import com.arrisdev.blogspringbootproject.payload.PostResponse;
import com.arrisdev.blogspringbootproject.repository.PostRepository;
import javafx.geometry.Pos;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**@Author Arris Manduma
 * @Date 11/29/2021
 */

@Service
public class PostServiceImpl implements PostService{

    private final ModelMapper mapper;
    private final PostRepository postRepository;

    public PostServiceImpl(ModelMapper mapper, PostRepository postRepository) {
        this.mapper = mapper;
        this.postRepository = postRepository;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        // convert DTO to entity
        Post post = mapToEntity(postDTO);
        Post newPost = postRepository.save(post);

        // convert entity to DTO
        PostDTO postResponse = mapToDTO(newPost);
        return postResponse;
    }

    /**using mapperEntity to convert entity to
        to DTO
     */
    private PostDTO mapToDTO(Post post){
        //PostDTO postDto = new PostDTO();
       // postDto.setId(post.getId());
       // postDto.setTitle(post.getTitle());
        //postDto.setDescription(post.getDescription());
        //postDto.setContent(post.getContent());
        PostDTO postDTO = mapper.map(post, PostDTO.class);
        return postDTO;
    }

    /**using mapperEntity to conert DTO
     * to entity
     * @param postDTO
     * @return
     */
    private Post mapToEntity(PostDTO postDTO){
        //Post post = new Post();
        //post.setTitle(postDTO.getTitle());
       // post.setDescription(postDTO.getDescription());
       //post.setContent(postDTO.getContent());
        Post post = mapper.map(postDTO, Post.class);
        return post;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        //condition to sort in ascending or descending order
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> postPage = postRepository.findAll(pageable);

        //get content for page object
        List<Post> postList = postPage.getContent();
        List<PostDTO> content = postList.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalPage(postPage.getTotalPages());
        postResponse.setTotalElement(postPage.getTotalElements());
        postResponse.setLast(postPage.isLast());

        return postResponse;
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long id) {
        //get post by id through the database
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        //save the new post in the repository
        Post updatePost = postRepository.save(post);
        return mapToDTO(updatePost);
    }

    @Override
    public void deleteById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        postRepository.delete(post);
    }


}
