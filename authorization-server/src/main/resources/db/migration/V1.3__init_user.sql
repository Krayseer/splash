CREATE TABLE `user`
(
    `id`           bigint        NOT NULL AUTO_INCREMENT,
    `name`         varchar(255)  DEFAULT NULL,
    `surname`      varchar(255)  DEFAULT NULL,
    `username`     varchar(255)  NOT NULL,
    `password`     varchar(255)  DEFAULT NULL,
    `email`        varchar(255)  DEFAULT NULL,
    `phone_number` varchar(255)  DEFAULT NULL,
    `photo_url`    varchar(255)  DEFAULT NULL,
    `created_at`   datetime      DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `user_setting`
(
    `id`            bigint      NOT NULL AUTO_INCREMENT,
    `push_enabled`  tinyint(1)  NOT NULL DEFAULT 0,
    `email_enabled` tinyint(1)  NOT NULL DEFAULT 0,
    `user_id`       bigint,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_user_setting_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `role`
(
    `id`        bigint       NOT NULL AUTO_INCREMENT,
    `role_code` varchar(100) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `permission`
(
    `id`              bigint       NOT NULL AUTO_INCREMENT,
    `permission_name` varchar(100) NOT NULL,
    `permission_code` varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `oauth2_client_role`
(
    `id`                     bigint       NOT NULL AUTO_INCREMENT,
    `client_registration_id` varchar(100) NOT NULL,
    `role_code`              varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `user_mtm_role`
(
    `id`      bigint NOT NULL AUTO_INCREMENT,
    `user_id` bigint NOT NULL,
    `role_id` bigint NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE `oauth2_client_role_mapping`
(
    `id`                   bigint NOT NULL AUTO_INCREMENT,
    `oauth_client_role_id` bigint NOT NULL,
    `role_id`              bigint NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `role_mtm_permission`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `role_id`       bigint NOT NULL,
    `permission_id` bigint NOT NULL,
    PRIMARY KEY (`id`)
);

INSERT INTO `user` (`id`, `name`, `surname`, `username`, `password`, `phone_number`, `email`, `created_at`)
VALUES (1, 'Главный', 'Администратор', 'admin', '{noop}password', '13523456789', '123456@163.com', '2024-05-09 00:45:32');
INSERT INTO `user_setting` (`id`, `push_enabled`, `email_enabled`, `user_id`) VALUES (1, 1, 1, 1);

INSERT INTO `role` (`id`, `role_code`) VALUES (1, 'ROLE_USER');
INSERT INTO `role` (`id`, `role_code`) VALUES (2, 'ROLE_ADMIN');
INSERT INTO `role` (`id`, `role_code`) VALUES (3, 'ROLE_MANAGER');
INSERT INTO `role` (`id`, `role_code`) VALUES (4, 'ROLE_PRODUCT_MANAGER');
INSERT INTO `role` (`id`, `role_code`) VALUES (5, 'ROLE_ANALYST');
INSERT INTO `role` (`id`, `role_code`) VALUES (6, 'ROLE_WASHER');

INSERT INTO `user_mtm_role` (`id`, `user_id`, `role_id`) VALUES (1, 1, 2);

INSERT INTO `permission` (`id`, `permission_name`, `permission_code`) VALUES (1, 'read the article', 'read');
INSERT INTO `permission` (`id`, `permission_name`, `permission_code`) VALUES (2, 'write the article', 'write');

INSERT INTO `role_mtm_permission` (`id`, `role_id`, `permission_id`) VALUES (1, 1, 1);
INSERT INTO `role_mtm_permission` (`id`, `role_id`, `permission_id`) VALUES (2, 1, 2);
INSERT INTO `role_mtm_permission` (`id`, `role_id`, `permission_id`) VALUES (3, 2, 1);
