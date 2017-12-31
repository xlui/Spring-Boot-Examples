INSERT INTO t_sort (id, sort_name) VALUES
  (1, "默认分类");

INSERT INTO t_sort (id, sort_name) VALUES
  (2, "新分类");

INSERT INTO t_article (content, sort_id) VALUES
  ("Hello! This is my first article", 1),
  ("这是第二篇文章", 2),
  ("Welcome to see the third article", 1);

INSERT INTO t_tag (id, tag_name) VALUES
  (1, "First"),
  (2, "Common");

INSERT INTO t_article_tag (article_id, tag_id) VALUES
  (1, 1),
  (1, 2),
  (2, 2),
  (3, 2);
