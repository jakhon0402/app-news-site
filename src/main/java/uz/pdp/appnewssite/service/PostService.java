package uz.pdp.appnewssite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssite.entity.Post;
import uz.pdp.appnewssite.payload.ApiResponse;
import uz.pdp.appnewssite.payload.PostDto;
import uz.pdp.appnewssite.repository.PostRepo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {
    @Autowired
    PostRepo postRepo;

    public List<Post> getPosts(){
        return postRepo.findAll();
    }

    public Post getByUrl(String url){
        return postRepo.findPostByUrl(url).orElseThrow(null);
    }

    public ApiResponse addPost(PostDto postDto) {
        String url = UUID.randomUUID().toString();
        Post post = new Post(
                postDto.getTitle(),
                postDto.getText(),
                url
        );
        postRepo.save(post);
        return new ApiResponse("Yangi post qo'shildi!",true);
    }

    public ApiResponse editPost(PostDto postDto, long postId){
        Optional<Post> optionalPost = postRepo.findById(postId);
        if(optionalPost.isEmpty())
            return new ApiResponse("Bunday post mavjud emas !",false);
        Post post = optionalPost.get();
        post.setTitle(postDto.getTitle());
        post.setText(post.getText());
        postRepo.save(post);
        return new ApiResponse("Post tahrirlandi",true);
    }

    public ApiResponse deletePost(long postId){
        Optional<Post> optionalPost = postRepo.findById(postId);
        if(optionalPost.isEmpty())
            return new ApiResponse("Bunday post mavjud emas !",false);
        postRepo.delete(optionalPost.get());
        return new ApiResponse("Post o'chirildi !",true);
    }
}
