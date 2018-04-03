insert into `test_user` (id, username, password) values
(1, 'xlui', 'xlui'),
(2, 'admin', 'admin');

-- 默认判断 ROLE 的方式是
-- 对于形如 ROLE_USER 的角色值，直接返回 ROLE_USER
-- 对于形如 user 的角色值，返回 ROLE_user，如果使用 hasRole 验证，参数应为 ROLE_user，如果使用 hasAuthority 验证，参数为 user 即可
insert into `test_role` (id, name) values
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

insert into `test_user_roles` (test_user_id, roles_id) values
(1, 1),
(2, 2);
