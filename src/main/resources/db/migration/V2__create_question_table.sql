
CREATE TABLE Question (
    id serial PRIMARY KEY, -- Unique identifier for each question
    statement TEXT NOT NULL, -- The question text
    alternative_a TEXT NOT NULL, -- Option A
    alternative_b TEXT NOT NULL, -- Option B
    alternative_c TEXT NOT NULL, -- Option C
    alternative_d TEXT NOT NULL, -- Option D
    alternative_e TEXT NOT NULL, -- Option E
    correct_alternative TEXT NOT NULL CHECK (correct_alternative IN ('A', 'B', 'C', 'D', 'E')), -- Correct option
    created_at TIMESTAMP, -- Timestamp for when the question was created
    updated_at TIMESTAMP -- Timestamp for last update
);

-- Add a foreign key constraint to the category_id column
ALTER TABLE Question
ADD COLUMN category_id BIGINT REFERENCES Category(id) ON DELETE CASCADE;