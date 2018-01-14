use test;

INSERT INTO shiro_user (id, password, salt, username) VALUES
  (1, "dev", "salt", "admin");

INSERT INTO shiro_role (id, role) VALUES
  (1, "admin"),
  (2, "normal");

INSERT INTO shiro_permission (id, permission) VALUES
  (1, "user info"),
  (2, "user add"),
  (3, "user del");

INSERT INTO shiro_user_role (user_id, role_id) VALUES
  (1, 1);

INSERT INTO shiro_role_permission (permission_id, role_id) VALUES
  (1, 1),
  (2, 1);