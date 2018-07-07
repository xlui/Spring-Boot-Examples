package me.xlui.example.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_tag")
public class Tag {
    @Id
    @GeneratedValue
    private Long id;
    private String tagName;

    /**
     * @ManyToMany 表示多对多关系，fetch 属性设置更新选项，有 FetchType.EAGER 和 FetchType.LAZY 两种选择
     * @JoinTable 是自定义数据库中用于维护多对多关系的表，name 属性定义了表名，joinColumns 属性是本表中加入维护关系表的字段，可以有多个
     * @JoinColumn 是行，name属性是字段名，referencedColumnName 是本类中代表主键的属性，inverseJoinColumns 属性是其他字段，也可以有多个
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "t_article_tag", joinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"))
    @JsonIgnore
    // Json 序列化的时候忽略 Tag 对象中的 articleList，避免无限循环
    private List<Article> articleList;

    public Tag() {
        super();
    }

    public Tag(String tagName, List<Article> articleList) {
        this.tagName = tagName;
        this.articleList = articleList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
