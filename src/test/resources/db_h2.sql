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
                          PRIMARY KEY (student_id),
                          FOREIGN KEY (university_id)  REFERENCES universities (university_id) ON DELETE CASCADE
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
                                 PRIMARY KEY (student_subject_id)
);
INSERT INTO universities (university_name) VALUES
                                               ('Oxford'),
                                               ('Harvard'),
                                               ('Stanford'),
                                               ('Cambridge');

INSERT INTO students (student_name, university_id) VALUES
                                                       ('Adam', 1),
                                                       ('Andy', 2),
                                                       ('Joe', 3),
                                                       ('Sandy', 4),
                                                       ('Rebeka', 1),
                                                       ('Maksim', 2),
                                                       ('Chendler',3),
                                                       ('Monika',4);

INSERT INTO subjects (subject_name) VALUES
                                        ('Maths'),
                                        ('History'),
                                        ('Biology'),
                                        ('Art'),
                                        ('Energy'),
                                        ('Chemistry'),
                                        ('Physics'),
                                        ('Astronomy');


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
