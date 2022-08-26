package uz.pdp.appnewssite.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName; //Role

    private String resourceField; //name

    private Object obj; // USER, ADMIN ...



}
