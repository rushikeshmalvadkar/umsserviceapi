SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE urls;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO urls
(id, title, original_url, slug, url_status_id, delete_flag, created_by, created_date, last_updated_by, last_updated_date)
VALUES
    (1, 'youtube', 'https://www.youtube.com', 'yt', 1, 0, 1, '2026-05-28 08:20:00', NULL, NULL);

INSERT INTO urls
(id, title, original_url, slug, url_status_id, delete_flag, created_by, created_date, last_updated_by, last_updated_date,start_at, expire_at)
VALUES
    (2, 'github', 'https://www.github.com', 'gh', 1, 0, 1, '2026-05-28 08:25:00', NULL, NULL,'2026-08-23 00:00:00', '2026-08-24 00:00:00');

INSERT INTO urls
(id, title, original_url, slug, url_status_id, delete_flag, created_by, created_date, last_updated_by, last_updated_date)
VALUES
    (3, 'stackoverflow', 'https://stackoverflow.com', 'so', 2, 0, 1, '2026-05-28 08:30:00', NULL, NULL);

INSERT INTO urls
(id, title, original_url, slug, url_status_id, delete_flag, created_by, created_date, last_updated_by, last_updated_date)
VALUES
    (4, 'openai', 'https://www.openai.com', 'oa', 2, 0, 1, '2026-05-28 08:35:00', NULL, NULL);

INSERT INTO urls
(id, title, original_url, slug, url_status_id, delete_flag, created_by, created_date, last_updated_by, last_updated_date)
VALUES
    (5, 'wikipedia', 'https://www.wikipedia.org', 'wiki', 2, 0, 1, '2026-05-28 08:40:00', NULL, NULL);

INSERT INTO urls
(id, title, original_url, slug, url_status_id, delete_flag, created_by, created_date, last_updated_by, last_updated_date,start_at,expire_at)
VALUES
    (6, 'reddit', 'https://www.reddit.com', 're', 1, 0, 1, '2026-05-28 08:45:00', NULL, NULL,'2026-08-28 00:00:00', '2026-08-29 00:00:00');
