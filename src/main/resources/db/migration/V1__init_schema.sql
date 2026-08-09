CREATE TABLE youth_centers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL
);

CREATE TABLE events (
    id BIGSERIAL PRIMARY KEY,
    youth_center_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT
)