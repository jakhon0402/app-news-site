package uz.pdp.appnewssite.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appnewssite.entity.template.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment extends AbstractEntity {

    @Column(nullable = false, columnDefinition = "text ")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

}
