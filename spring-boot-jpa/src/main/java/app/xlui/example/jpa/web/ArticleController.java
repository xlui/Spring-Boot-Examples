package app.xlui.example.jpa.web;

import app.xlui.example.jpa.entity.Article;
import app.xlui.example.jpa.entity.Sort;
import app.xlui.example.jpa.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {
    @Autowired
	ArticleRepository articleRepository;

    @RequestMapping(value = {"/", "/all", "/articles"})
    public List<Article> all() {
        return articleRepository.findAll();
    }

    @RequestMapping("/article")
    public Article article(Long id) {
        return articleRepository.findArticleById(id);
    }

    @RequestMapping("/bySort")
    public List<Article> sort(Long id, String sortName) {
        return articleRepository.findArticleBySort(new Sort(id, sortName));
    }

//	@RequestMapping("/q3")
//	public Article q3(Long id) {
//		return articleRepository.findArticleUsingQuery(id);
//	}
//
//	@RequestMapping("/q4")
//	public Article q4(Long id) {
//		return articleRepository.findArticleUsingQueryParam(id);
//	}

    @RequestMapping("/sort")
    public List<Article> sort() {
        return articleRepository.findAll(new org.springframework.data.domain.Sort(org.springframework.data.domain.Sort.Direction.DESC, "id"));
    }

    @RequestMapping("/page")
    public Page<Article> page() {
        return articleRepository.findAll(new PageRequest(1, 2));
    }
}
