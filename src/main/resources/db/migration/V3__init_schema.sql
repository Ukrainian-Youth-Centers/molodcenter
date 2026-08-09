CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    youth_center_id BIGINT,
    name VARCHAR(255) NOT NULL
);

ALTER TABLE events ADD COLUMN category_id BIGINT NOT NULL;
