CREATE TABLE promotions
(
    id           BIGINT PRIMARY KEY,
    description  TEXT     NOT NULL,
    type         NVARCHAR NOT NULL,
    total_points INT      NOT NULL,
    status       NVARCHAR NOT NULL,
    profile_id   BIGINT   NOT NULL,

    FOREIGN KEY (profile_id) REFERENCES PROFILES (id)
);