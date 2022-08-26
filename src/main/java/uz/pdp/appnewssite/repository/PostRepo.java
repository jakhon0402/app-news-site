package uz.pdp.appnewssite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appnewssite.entity.Post;

import java.util.Optional;

public interface PostRepo extends JpaRepository<Post,Long> {
    Optional<Post> findPostByUrl(String url);
}
