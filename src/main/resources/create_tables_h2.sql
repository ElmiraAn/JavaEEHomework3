DROP TABLE IF EXISTS universities CASCADE;
DROP TABLE IF EXISTS students CASCADE;
DROP TABLE IF EXISTS subjects CASCADE;
DROP TABLE IF EXISTS student_subject CASCADE;

create table universities (
                              university_id  int NOT NULL AUTO_INCREMENT,
                              university_name varchar(120) NOT NULL,
                              PRIMARY KEY (university_id)
);

create table students (
                          student_id  int NOT NULL AUTO_INCREMENT,
                          student_name varchar(120) NOT NULL,
                          university_id INT NOT NULL,
                          FOREIGN KEY (university_id)  REFERENCES universities (university_id) ON DELETE CASCADE,
                          PRIMARY KEY (student_id)
);

create table subjects (
                          subject_id  int NOT NULL AUTO_INCREMENT,
                          subject_name varchar(120) NOT NULL,
                          PRIMARY KEY (subject_id)
);

create table student_subject (
                                 student_subject_id  int NOT NULL AUTO_INCREMENT,
                                 student_id INT,
                                 subject_id INT,
                                 FOREIGN KEY (student_id)  REFERENCES students (student_id),
                                 FOREIGN KEY (subject_id)  REFERENCES subjects (subject_id),
                                 PRIMARY KEY (id)
);
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
