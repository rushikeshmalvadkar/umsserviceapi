-- liquibase formatted sql

--changeset Rushikesh Malvadkar:1-add-column_view_count_in_urls_table
ALTER TABLE urls
    ADD COLUMN view_count BIGINT UNSIGNED NOT NULL DEFAULT 0;
