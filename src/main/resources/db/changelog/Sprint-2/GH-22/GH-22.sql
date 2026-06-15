-- liquibase formatted sql

--changeset Rushikesh Malvadkar:1-mark-not-editable-url-columns-as-editable-false-for-all-roles
update header_mapping hm
set hm.editable = false
where hm.header_config_id in (5,6,7); -- created_on, copy and visit icon for all menus

update header_mapping hm
    join role_menu rm on rm.id = hm.role_menu_id and rm.delete_flag = false
    set hm.editable = false
where hm.header_config_id = 3 -- slug
  and rm.menu_id = 3; -- my short url menu