-- liquibase formatted sql

--changeset Rushikesh Malvadkar:1-add-columns_start_at_and_expired_at_in_urls_table
ALTER TABLE urls
    ADD COLUMN start_at date;

ALTER TABLE urls
    ADD COLUMN expire_at date;


--changeset Rushikesh Malvadkar:2-insert-start-at-and-expire-at-details-in-header-config-table
INSERT INTO header_config
(id, header_name, header_type, mapping_name, mapping_table, mapping_column, sortable, filterable, option_source_id, delete_flag, created_by, created_date, last_updated_by, last_updated_date)
VALUES(9,'Start Date', 'date','startAt','urls','start_at',1, 1,NULL,0,1,'2026-08-25 08:55:19', NULL, NULL);
INSERT INTO header_config
(id, header_name, header_type, mapping_name, mapping_table, mapping_column, sortable, filterable, option_source_id, delete_flag, created_by, created_date, last_updated_by, last_updated_date)
VALUES(10,'Expiration Date', 'date','expireAt','urls','expire_at',1, 1,NULL,0,1,'2026-08-25 08:55:19', NULL, NULL);




--changeset Rushikesh Malvadkar:3-insert-start-at-expire-date-detail-in-header_mapping_table
set @my_urls_customer_role_id = (select rm.id  from role_menu rm where rm.role_id = 3 and rm.menu_id = 2);
set @my_urls_admin_role_id = (select rm.id  from role_menu rm where rm.role_id = 2 and rm.menu_id = 2);

INSERT INTO header_mapping(header_config_id, role_menu_id, editable, display_order, created_by, created_date)
VALUES (9,@my_urls_customer_role_id,1,9,1,UTC_TIMESTAMP()),
       (9,@my_urls_admin_role_id,1,9,1,UTC_TIMESTAMP()),
       (10,@my_urls_customer_role_id,1,10,1,UTC_TIMESTAMP()),
       (10,@my_urls_admin_role_id,1,10,1,UTC_TIMESTAMP());