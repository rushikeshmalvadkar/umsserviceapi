-- liquibase formatted sql

--changeset Rushikesh Malvadkar:1-create-roles-table
CREATE TABLE roles
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(20)  NOT NULL,
    description VARCHAR(255) NOT NULL
);


--changeset Rushikesh Malvadkar:2-insert-roles
INSERT INTO roles(id, name, description)
VALUES (1, 'System', 'Responsible to automate things'),
       (2, 'Admin', 'Responsible for everything with all permissions'),
       (3, 'Customer', 'Responsible to manage URLs with only required permissions');


--changeset Rushikesh Malvadkar:3-create-menu-table
CREATE TABLE menu
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(45) NOT NULL,
    parent_id bigint,
    CONSTRAINT fk_menu_parent_id
        FOREIGN KEY (parent_id)
            REFERENCES menu (id)
);


--changeset Rushikesh Malvadkar:4-insert-menu-data
INSERT INTO menu(id, name, parent_id)
VALUES (1, 'URLs', null),
       (2, 'Create URL', 1),
       (3, 'My Short URLs', 1);


--changeset Rushikesh Malvadkar:5-create-users-table
CREATE TABLE users
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(45) NOT NULL,
    email             VARCHAR(45) NOT NULL UNIQUE,
    role_id           BIGINT      NOT NULL,
    delete_flag       BIT(1)      NOT NULL DEFAULT 0,
    created_by        BIGINT NULL,
    created_date      DATETIME    NOT NULL,
    last_updated_by   BIGINT NULL,
    last_updated_date DATETIME NULL,
    CONSTRAINT fk_users_role_id
        FOREIGN KEY (role_id)
            REFERENCES roles (id),
    CONSTRAINT fk_users_created_by
        FOREIGN KEY (created_by)
            REFERENCES users (id),
    CONSTRAINT fk_users_last_updated_by
        FOREIGN KEY (last_updated_by)
            REFERENCES users (id)
);

--changeset Rushikesh Malvadkar:15-insert-system-user
INSERT INTO users(id, name, email, role_id, created_by, created_date)
VALUES (1, 'System', 'system@xyz.com', 1, 1, UTC_TIMESTAMP());


--changeset Rushikesh Malvadkar:6-create-role-menu-table
CREATE TABLE role_menu
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_id           BIGINT   NOT NULL,
    menu_id           BIGINT   NOT NULL,
    delete_flag       BIT(1)   NOT NULL DEFAULT 0,
    created_by        BIGINT NULL,
    created_date      DATETIME NOT NULL,
    last_updated_by   BIGINT NULL,
    last_updated_date DATETIME NULL,
    CONSTRAINT fk_role_menu_role_id
        FOREIGN KEY (role_id)
            REFERENCES roles (id),
    CONSTRAINT fk_role_menu_menu_id
        FOREIGN KEY (menu_id)
            REFERENCES menu (id),
    CONSTRAINT fk_role_menu_created_by
        FOREIGN KEY (created_by)
            REFERENCES users (id),
    CONSTRAINT fk_role_menu_last_updated_by
        FOREIGN KEY (last_updated_by)
            REFERENCES users (id)
);


--changeset Rushikesh Malvadkar:7-create-option-source-table
CREATE TABLE option_source
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    mapping_name VARCHAR(50) NOT NULL
);


--changeset Rushikesh Malvadkar:8-insert-option-source-data
INSERT INTO option_source(id, mapping_name)
VALUES (1, 'urlStatusList');


--changeset Rushikesh Malvadkar:9-create-header-config-table
CREATE TABLE header_config
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    header_name       VARCHAR(100) NOT NULL,
    header_type       VARCHAR(100) NOT NULL,
    mapping_name      VARCHAR(100) NOT NULL,
    mapping_table     VARCHAR(100) NULL,
    mapping_column    VARCHAR(100) NULL,
    sortable          BIT(1)       NOT NULL DEFAULT 0,
    filterable        BIT(1)       NOT NULL DEFAULT 0,
    option_source_id  BIGINT NULL,
    delete_flag       BIT(1)       NOT NULL DEFAULT 0,
    created_by        BIGINT NULL,
    created_date      DATETIME     NOT NULL,
    last_updated_by   BIGINT NULL,
    last_updated_date DATETIME NULL,
    CONSTRAINT fk_header_config_option_source_id
        FOREIGN KEY (option_source_id)
            REFERENCES option_source (id),
    CONSTRAINT fk_header_config_created_by
        FOREIGN KEY (created_by)
            REFERENCES users (id),
    CONSTRAINT fk_header_config_last_updated_by
        FOREIGN KEY (last_updated_by)
            REFERENCES users (id)
);


--changeset Rushikesh Malvadkar:10-insert-header-config-data
INSERT INTO header_config(id,
                          header_name,
                          header_type,
                          mapping_name,
                          mapping_table,
                          mapping_column,
                          sortable,
                          filterable,
                          option_source_id,
                          created_by,
                          created_date)
VALUES (1, 'Title', 'text', 'title', 'urls', 'title', 1, 1, NULL, 1, UTC_TIMESTAMP()),
       (2, 'Original Url', 'text', 'originalUrl', 'urls', 'original_url', 1, 1, NULL, 1, UTC_TIMESTAMP()),
       (3, 'Slug', 'text', 'slug', 'urls', 'slug', 1, 1, NULL, 1, UTC_TIMESTAMP()),
       (4, 'Status', 'dropdown', 'urlStatusId', 'urls', 'url_status_id', 1, 1, 1, 1, UTC_TIMESTAMP()),
       (5, 'Created On', 'date', 'createdDate', 'urls', 'created_date', 1, 0, NULL, 1, UTC_TIMESTAMP()),
       (6, '', 'copy', '', NULL, NULL, 0, 0, NULL, 1, UTC_TIMESTAMP()),
       (7, '', 'visit', '', NULL, NULL, 0, 0, NULL, 1, UTC_TIMESTAMP());


--changeset Rushikesh Malvadkar:11-create-header-mapping-table
CREATE TABLE header_mapping
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    header_config_id  BIGINT         NOT NULL,
    role_menu_id      BIGINT         NOT NULL,
    editable          BIT(1)         NOT NULL DEFAULT 0,
    display_order     DECIMAL(15, 2) NOT NULL,
    delete_flag       BIT(1)         NOT NULL DEFAULT 0,
    created_by        BIGINT NULL,
    created_date      DATETIME       NOT NULL,
    last_updated_by   BIGINT NULL,
    last_updated_date DATETIME NULL,
    CONSTRAINT fk_header_mapping_header_config_id
        FOREIGN KEY (header_config_id)
            REFERENCES header_config (id),
    CONSTRAINT fk_header_mapping_role_menu_id
        FOREIGN KEY (role_menu_id)
            REFERENCES role_menu (id),
    CONSTRAINT fk_header_mapping_created_by
        FOREIGN KEY (created_by)
            REFERENCES users (id),
    CONSTRAINT fk_header_mapping_last_updated_by
        FOREIGN KEY (last_updated_by)
            REFERENCES users (id)
);

--changeset Rushikesh Malvadkar:16-insert-role-menu-and-header-mappings
INSERT INTO role_menu(role_id, menu_id, created_by, created_date)
VALUES (3, 2, 1, UTC_TIMESTAMP());

set
@create_url_menu_customer_role_menu_id = last_insert_id();

INSERT INTO header_mapping(header_config_id, role_menu_id, editable, display_order, created_by, created_date)
VALUES (1, @create_url_menu_customer_role_menu_id, 1, 1, 1, UTC_TIMESTAMP()),
       (2, @create_url_menu_customer_role_menu_id, 1, 2, 1, UTC_TIMESTAMP()),
       (3, @create_url_menu_customer_role_menu_id, 1, 3, 1, UTC_TIMESTAMP());

INSERT INTO role_menu(role_id, menu_id, created_by, created_date)
VALUES (3, 3, 1, UTC_TIMESTAMP());

set
@my_short_urls_menu_customer_role_menu_id = last_insert_id();

INSERT INTO header_mapping(header_config_id, role_menu_id, editable, display_order, created_by, created_date)
VALUES (1, @my_short_urls_menu_customer_role_menu_id, 1, 1, 1, UTC_TIMESTAMP()),
       (2, @my_short_urls_menu_customer_role_menu_id, 1, 2, 1, UTC_TIMESTAMP()),
       (3, @my_short_urls_menu_customer_role_menu_id, 1, 3, 1, UTC_TIMESTAMP()),
       (4, @my_short_urls_menu_customer_role_menu_id, 1, 4, 1, UTC_TIMESTAMP()),
       (5, @my_short_urls_menu_customer_role_menu_id, 1, 5, 1, UTC_TIMESTAMP()),
       (6, @my_short_urls_menu_customer_role_menu_id, 1, 6, 1, UTC_TIMESTAMP()),
       (7, @my_short_urls_menu_customer_role_menu_id, 1, 7, 1, UTC_TIMESTAMP());

INSERT INTO role_menu(role_id, menu_id, created_by, created_date)
VALUES (2, 2, 1, UTC_TIMESTAMP());

set
@create_url_menu_admin_role_menu_id = last_insert_id();

INSERT INTO header_mapping(header_config_id, role_menu_id, editable, display_order, created_by, created_date)
VALUES (1, @create_url_menu_admin_role_menu_id, 1, 1, 1, UTC_TIMESTAMP()),
       (2, @create_url_menu_admin_role_menu_id, 1, 2, 1, UTC_TIMESTAMP()),
       (3, @create_url_menu_admin_role_menu_id, 1, 3, 1, UTC_TIMESTAMP());

INSERT INTO role_menu(role_id, menu_id, created_by, created_date)
VALUES (2, 3, 1, UTC_TIMESTAMP());

set
@my_short_urls_menu_admin_role_menu_id = last_insert_id();

INSERT INTO header_mapping(header_config_id, role_menu_id, editable, display_order, created_by, created_date)
VALUES (1, @my_short_urls_menu_admin_role_menu_id, 1, 1, 1, UTC_TIMESTAMP()),
       (2, @my_short_urls_menu_admin_role_menu_id, 1, 2, 1, UTC_TIMESTAMP()),
       (3, @my_short_urls_menu_admin_role_menu_id, 1, 3, 1, UTC_TIMESTAMP()),
       (4, @my_short_urls_menu_admin_role_menu_id, 1, 4, 1, UTC_TIMESTAMP()),
       (5, @my_short_urls_menu_admin_role_menu_id, 1, 5, 1, UTC_TIMESTAMP()),
       (6, @my_short_urls_menu_admin_role_menu_id, 1, 6, 1, UTC_TIMESTAMP()),
       (7, @my_short_urls_menu_admin_role_menu_id, 1, 7, 1, UTC_TIMESTAMP());


--changeset Rushikesh Malvadkar:12-create-url-status-table
CREATE TABLE url_status
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(50)  NOT NULL,
    description       VARCHAR(255) NOT NULL,
    delete_flag       BIT(1)       NOT NULL DEFAULT 0,
    created_by        BIGINT       NOT NULL,
    created_date      DATETIME     NOT NULL,
    last_updated_by   BIGINT NULL,
    last_updated_date DATETIME NULL,
    CONSTRAINT fk_url_status_created_by
        FOREIGN KEY (created_by)
            REFERENCES users (id),
    CONSTRAINT fk_url_status_last_updated_by
        FOREIGN KEY (last_updated_by)
            REFERENCES users (id)
);


--changeset Rushikesh Malvadkar:13-insert-url-status-data
INSERT INTO url_status(id,
                       name,
                       description,
                       created_by,
                       created_date)
VALUES (1, 'Active', 'Represents active urls', 1, UTC_TIMESTAMP()),
       (2, 'Inactive', 'Represents inactive urls', 1, UTC_TIMESTAMP());


--changeset Rushikesh Malvadkar:14-create-urls-table
CREATE TABLE urls
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    title             VARCHAR(128)  NOT NULL,
    original_url      VARCHAR(2048) NOT NULL,
    slug              VARCHAR(50)   NOT NULL,
    url_status_id     BIGINT        NOT NULL,
    delete_flag       BIT(1)        NOT NULL DEFAULT 0,
    created_by        BIGINT        NOT NULL,
    created_date      DATETIME      NOT NULL,
    last_updated_by   BIGINT NULL,
    last_updated_date DATETIME NULL,
    CONSTRAINT uq_urls_slug UNIQUE (slug),
    CONSTRAINT fk_urls_url_status_id
        FOREIGN KEY (url_status_id)
            REFERENCES url_status (id),
    CONSTRAINT fk_urls_created_by
        FOREIGN KEY (created_by)
            REFERENCES users (id),
    CONSTRAINT fk_urls_last_updated_by
        FOREIGN KEY (last_updated_by)
            REFERENCES users (id)
);