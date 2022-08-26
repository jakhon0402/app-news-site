package uz.pdp.appnewssite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssite.entity.Comment;
import uz.pdp.appnewssite.entity.Post;
import uz.pdp.appnewssite.entity.User;
import uz.pdp.appnewssite.payload.ApiResponse;
import uz.pdp.appnewssite.payload.CommentDto;
import uz.pdp.appnewssite.repository.CommentRepo;
import uz.pdp.appnewssite.repository.PostRepo;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    CommentRepo commentRepo;

    @Autowired
    PostRepo postRepo;

    public List<Comment> getCommentsByPostId(long postId){
        return commentRepo.findAllByPostId(postId);
    }

    public ApiResponse addComment(CommentDto commentDto){
        Optional<Post> optionalPost = postRepo.findById(commentDto.getPostId());
        if(optionalPost.isEmpty())
            return new ApiResponse("Bunday post mavjud emas !",false);
        Comment comment = new Comment(
                commentDto.getText(),
                optionalPost.get()
        );
        commentRepo.save(comment);
        return new ApiResponse("Comment yozildi !", true);
    }

    public ApiResponse editComment(CommentDto commentDto,long commentId){
        Optional<Comment> optionalComment = commentRepo.findById(commentId);
        if(optionalComment.isEmpty())
            return new ApiResponse("Bunday idlik comment mavjud emas!", false);
        Optional<Post> optionalPost = postRepo.findById(commentDto.getPostId());
        if(optionalPost.isEmpty())
            return new ApiResponse("Bunday post mavjud emas !",false);
        Comment comment = optionalComment.get();
        comment.setText(commentDto.getText());
        comment.setPost(optionalPost.get());
        commentRepo.save(comment);
        return new ApiResponse("Comment tahrirlandi !", true);
    }

    public ApiResponse deleteComment(long commentId){
        Optional<Comment> optionalComment = commentRepo.findById(commentId);
        if(optionalComment.isEmpty())
            return new ApiResponse("Bunday idlik comment mavjud emas!", false);
        commentRepo.delete(optionalComment.get());
        return new ApiResponse("Comment o'chirildi!",true);
    }

    public ApiResponse deleteOwnComment(long commentId){
        Optional<Comment> optionalComment = commentRepo.findById(commentId);
        if(optionalComment.isEmpty())
            return new ApiResponse("Bunday idlik comment mavjud emas!", false);
        Comment comment = optionalComment.get();
        User createdBy = comment.getCreatedBy();
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!currentUser.equals(createdBy))
            return new ApiResponse("Ushbu comment sizga tegishli emas !", false);
        commentRepo.delete(comment);
        return new ApiResponse("Comment o'chirildi !",true);
    }
}
