CREATE TABLE Category (
    id serial PRIMARY KEY, -- Unique identifier for each category
    name VARCHAR(255) NOT NULL, -- Name of the category
    description TEXT, -- Optional description of the category
    created_at TIMESTAMP, -- Timestamp for when the category was created
    updated_at TIMESTAMP -- Timestamp for last update
);
