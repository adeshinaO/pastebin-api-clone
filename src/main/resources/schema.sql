CREATE TABLE texts (
    id UUID NOT NULL DEFAULT random_uuid(),
    content TEXT NOT NULL,
    reference VARCHAR(20) NOT NULL UNIQUE,
    has_expiry_date TINYINT NOT NULL,
    expiry_date TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE INDEX idx_reference ON texts (reference);
