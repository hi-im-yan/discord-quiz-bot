-- Insert easy math-related questions into the Question table

insert into Question (statement, alternative_a, alternative_b, alternative_c, alternative_d, alternative_e, correct_alternative, category_id)
values
('What is 2 + 2?', '3', '4', '5', '6', '7', 'B', (select id from Category where name = 'MATH')),
('What is the square root of 16?', '2', '3', '4', '5', '6', 'C', (select id from Category where name = 'MATH')),
('What is 10 / 2?', '2', '3', '4', '5', '6', 'D', (select id from Category where name = 'MATH')),
('What is 5 - 3?', '1', '2', '3', '4', '5', 'B', (select id from Category where name = 'MATH')),
('What is 3 * 3?', '6', '7', '8', '9', '10', 'D', (select id from Category where name = 'MATH'));