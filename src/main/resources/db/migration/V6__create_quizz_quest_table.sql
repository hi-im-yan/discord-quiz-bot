-- create a intermediary table that contains the quizz id, question id and a selected answer
CREATE TABLE Quiz_Question (
    quiz_id serial NOT NULL, -- The quiz id
    question_id BIGINT NOT NULL, -- The question id
    selected_alternative TEXT CHECK (selected_alternative IN ('A', 'B', 'C', 'D', 'E')), -- The selected alternative
    PRIMARY KEY (quiz_id, question_id), -- Composite primary key
    FOREIGN KEY (quiz_id) REFERENCES Quiz(id) ON DELETE CASCADE, -- Foreign key constraint to the quiz_id column
    FOREIGN KEY (question_id) REFERENCES Question(id) ON DELETE CASCADE -- Foreign key constraint to the question_id column
);