CREATE TABLE `flag`
(
    `id`                 bigint              NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `description`        varchar(255)        NOT NULL,
    `enabled`            bit(1)              NOT NULL,
    `name`               varchar(255) UNIQUE NOT NULL,
    `created_date`       datetime(6) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL
) ENGINE = InnoDB;

CREATE TABLE `variant`
(
    `id`                 bigint       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`               varchar(255) NOT NULL,
    `flag_id`            bigint      DEFAULT NULL,
    `created_date`       datetime(6) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    KEY (`flag_id`),
    UNIQUE (`flag_id`, `name`),
    CONSTRAINT FOREIGN KEY (`flag_id`) REFERENCES `flag` (`id`)
) ENGINE = InnoDB;

CREATE TABLE `segment`
(
    `id`                 bigint       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `cons`               varchar(255) DEFAULT NULL, -- constraint is a reserved keyword in MySQL
    `name`               varchar(255) NOT NULL,
    `priority`           int          NOT NULL,
    `rollout_percentage` int          NOT NULL,
    `flag_id`            bigint       DEFAULT NULL,
    `created_date`       datetime(6)  DEFAULT NULL,
    `last_modified_date` datetime(6)  DEFAULT NULL,
    KEY (`flag_id`),
    CONSTRAINT FOREIGN KEY (`flag_id`) REFERENCES `flag` (`id`)
) ENGINE = InnoDB;

CREATE TABLE `distribution`
(
    `id`                 bigint       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`               varchar(255) NOT NULL,
    `percent`            int          NOT NULL,
    `flag_id`            bigint       NOT NULL,
    `segment_id`         bigint       NOT NULL,
    `variant_id`         bigint       NOT NULL,
    `created_date`       datetime(6) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    KEY (`flag_id`),
    KEY (`segment_id`),
    KEY (`variant_id`),
    CONSTRAINT FOREIGN KEY (`flag_id`) REFERENCES `flag` (`id`),
    CONSTRAINT FOREIGN KEY (`segment_id`) REFERENCES `segment` (`id`),
    CONSTRAINT FOREIGN KEY (`variant_id`) REFERENCES `variant` (`id`)
) ENGINE = InnoDB;