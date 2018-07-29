package app.xlui.example.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_sort")
public class Sort {
    @Id
    @GeneratedValue
    private Long id;
    private String sortName;

    /**
     * @OneToMany 是一对多关系，与 Article 类中定义的 @ManyToOne 对应
     * mappedBy 属性指明了 Article 类中维护这一关系的字段是 sort
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "sort")
    // 在 json 化的时候忽略
    @JsonIgnore
    private List<Article> articleList;

    public Sort() {
        super();
    }

    public Sort(Long id, String sortName) {
        this.id = id;
        this.sortName = sortName;
    }

    public Sort(String sortName, List<Article> articleList) {
        this.sortName = sortName;
        this.articleList = articleList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
