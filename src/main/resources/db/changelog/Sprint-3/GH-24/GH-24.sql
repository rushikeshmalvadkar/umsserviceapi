-- liquibase formatted sql

--changeset Rushikesh Malvadkar:1-add-column_view_count_in_urls_table
ALTER TABLE urls
    ADD COLUMN view_count BIGINT UNSIGNED NOT NULL DEFAULT 0;

--changeset Rushikesh Malvadkar:2-INSERT-VIEW-DETAIL-IN-HEADER-CONFIG-TABLE
INSERT INTO header_config
(id, header_name, header_type, mapping_name, mapping_table, mapping_column, sortable, filterable, option_source_id, delete_flag, created_by, created_date, last_updated_by, last_updated_date)
VALUES(8,'Views', 'number','viewCount','urls','view_count',1, 1,NULL,0,1,'2026-08-23 08:55:19', NULL, NULL);


--changeset Rushikesh Malvadkar:3-INSERT-VIEW-HEADER-MAPPING-DETAIL-IN-HEADER-MAPPING-TABLE
set @my_urls_customer_role_id = (select rm.id  from role_menu rm where rm.role_id = 3 and rm.menu_id = 3);
set @my_urls_admin_role_id = (select rm.id  from role_menu rm where rm.role_id = 2 and rm.menu_id = 3);

INSERT INTO header_mapping(header_config_id, role_menu_id, editable, display_order, created_by, created_date)
VALUES (8,@my_urls_customer_role_id,0,8,1,UTC_TIMESTAMP()),
(8,@my_urls_admin_role_id,0,8,1,UTC_TIMESTAMP());