INSERT INTO universities (university_id, university_name) VALUES
                                                              (1, 'Oxford'),
                                                              (2, 'Harvard'),
                                                              (3, 'Stanford'),
                                                              (4, 'Cambridge');

INSERT INTO students (student_id, student_name, university_id) VALUES
                                                                   (1, 'Adam', 1),
                                                                   (2, 'Andy', 2),
                                                                   (3, 'Joe', 3),
                                                                   (4, 'Sandy', 4),
                                                                   (5, 'Rebeka', 1),
                                                                   (6, 'Maksim', 2),
                                                                   (7, 'Chendler',3),
                                                                   (8, 'Monika',4);

INSERT INTO subjects (subject_id, subject_name) VALUES
                                                    (1, 'Maths'),
                                                    (2, 'History'),
                                                    (3, 'Biology'),
                                                    (4, 'Art'),
                                                    (5, 'Energy'),
                                                    (6, 'Chemistry'),
                                                    (7, 'Physics'),
                                                    (8, 'Astronomy');


INSERT INTO student_subject (student_id, subject_id) VALUES
                                                         (1, 1),
                                                         (1, 7),
                                                         (2, 1),
                                                         (2, 2),
                                                         (3, 3),
                                                         (3, 6),
                                                         (3, 7),
                                                         (4, 4),
                                                         (4, 8),
                                                         (5, 2),
                                                         (6, 5),
                                                         (6, 7),
                                                         (7, 3),
                                                         (7, 7),
                                                         (7, 6),
                                                         (7, 5),
                                                         (8, 1);
