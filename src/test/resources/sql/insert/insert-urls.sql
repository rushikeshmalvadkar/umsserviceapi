SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE urls;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO urls
(title, original_url, slug, url_status_id, delete_flag, created_by, created_date, last_updated_by, last_updated_date)
VALUES
    ('youtube', 'https://www.youtube.com', 'yt', 1, 0, 1, '2026-05-28 08:20:00', NULL, NULL),
    ('github', 'https://www.github.com', 'gh', 2, 0, 1, '2026-05-28 08:25:00', NULL, NULL),
    ('stackoverflow', 'https://stackoverflow.com', 'so', 1, 0, 1, '2026-05-28 08:30:00', NULL, NULL),
    ('openai', 'https://www.openai.com', 'oa', 2, 0, 1, '2026-05-28 08:35:00', NULL, NULL),
    ('wikipedia', 'https://www.wikipedia.org', 'wiki', 1, 0, 1, '2026-05-28 08:40:00', NULL, NULL);