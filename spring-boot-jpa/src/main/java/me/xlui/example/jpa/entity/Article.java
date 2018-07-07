package me.xlui.example.jpa.entity;

import javax.persistence.*;
import java.util.List;

@Entity
// 自定义表名
@Table(name = "t_article")
public class Article {
    @Id
    @GeneratedValue
    private Long id;
    private String content;

    // 设置级联状态
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // ManyToOne 表示多对一关系
    private Sort sort;

    /**
     * @ManyToMany 表示多对多关系，fetch 属性设置更新选项，有 FetchType.EAGER 和 FetchType.LAZY 两种选择
     * @JoinTable 是自定义数据库中用于维护多对多关系的表，name 属性定义了表名，joinColumns 属性是本表中加入维护关系表的字段，可以有多个
     * @JoinColumn 是行，name属性是字段名，referencedColumnName 是本类中代表主键的属性，inverseJoinColumns 属性是其他字段，也可以有多个
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "t_article_tag", joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private List<Tag> tagList;

    public Article() {
        super();
    }

    public Article(String content, Sort sort, List<Tag> tagList) {
        this.content = content;
        this.sort = sort;
        this.tagList = tagList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }
}
