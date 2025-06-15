INSERT INTO training_type (id, training_type_name) VALUES (1, 'Cardio');
INSERT INTO training_type (id, training_type_name) VALUES (2, 'Yoga');
INSERT INTO training_type (id, training_type_name) VALUES (3, 'Pilates');
INSERT INTO training_type (id, training_type_name) VALUES (4, 'HIIT');
INSERT INTO training_type (id, training_type_name) VALUES (5, 'Strength Training');

INSERT INTO user (id, first_name, last_name, username, password, is_active) VALUES (1, 'John', 'Doe', 'john.doe', 'hashed_password_1', true);
INSERT INTO user (id, first_name, last_name, username, password, is_active) VALUES (2, 'Anna', 'Smith', 'anna.smith', 'hashed_password_2', true);
INSERT INTO user (id, first_name, last_name, username, password, is_active) VALUES (3, 'Michael', 'Brown', 'michael.brown', 'hashed_password_3', true);

INSERT INTO trainee (id, user_id, date_of_birth, address) VALUES (1, 1, '1995-06-15', '742 Evergreen Terrace, Springfield, IL');
INSERT INTO trainee (id, user_id, date_of_birth, address) VALUES (2, 2, '1998-11-23', '1600 Pennsylvania Ave NW, Washington, DC');
INSERT INTO trainee (id, user_id, date_of_birth, address) VALUES(3, 3, '2000-04-09', '221B Baker Street, New York, NY');

INSERT INTO user (id, first_name, last_name, username, password, is_active) VALUES (4, 'David', 'Johnson', 'david.johnson', 'hashed_password_4', true);
INSERT INTO user (id, first_name, last_name, username, password, is_active) VALUES (5, 'Emily', 'Williams', 'emily.williams', 'hashed_password_5', true);
INSERT INTO user (id, first_name, last_name, username, password, is_active) VALUES (6, 'Chris', 'Taylor', 'chris.taylor', 'hashed_password_6', true);

INSERT INTO trainer (id, user_id, specialization) VALUES (1, 4, 2);
INSERT INTO trainer (id, user_id, specialization) VALUES (2, 5, 4);
INSERT INTO trainer (id, user_id, specialization) VALUES (3, 6, 3);

INSERT INTO user (id, first_name, last_name, username, password, is_active) VALUES (7,  'Laura',  'Green',   'laura.green',   'hashed_password_7',  true);
INSERT INTO user (id, first_name, last_name, username, password, is_active) VALUES (8,  'James',  'White',   'james.white',   'hashed_password_8',  true);
INSERT INTO user (id, first_name, last_name, username, password, is_active) VALUES (9,  'Olivia', 'Davis',   'olivia.davis',  'hashed_password_9',  true);
INSERT INTO user (id, first_name, last_name, username, password, is_active) VALUES (10, 'Ethan',  'Lee',     'ethan.lee',     'hashed_password_10', true);
INSERT INTO user (id, first_name, last_name, username, password, is_active) VALUES (11, 'Sophia', 'Hall',    'sophia.hall',   'hashed_password_11', true);
INSERT INTO user (id, first_name, last_name, username, password, is_active) VALUES (12, 'Daniel', 'Clark',   'daniel.clark',  'hashed_password_12', true);
INSERT INTO user (id, first_name, last_name, username, password, is_active) VALUES (13, 'Mia',    'Young',   'mia.young',     'hashed_password_13', true);
INSERT INTO user (id, first_name, last_name, username, password, is_active) VALUES (14, 'Noah',   'King',    'noah.king',     'hashed_password_14', true);
INSERT INTO user (id, first_name, last_name, username, password, is_active) VALUES (15, 'Isabella','Scott',  'isabella.scott','hashed_password_15', true);
INSERT INTO user (id, first_name, last_name, username, password, is_active) VALUES (16, 'Liam',   'Adams',   'liam.adams',    'hashed_password_16', true);
INSERT INTO user (id, first_name, last_name, username, password, is_active) VALUES (17, 'Ava',    'Nelson',  'ava.nelson',    'hashed_password_17', true);
INSERT INTO user (id, first_name, last_name, username, password, is_active) VALUES (18, 'Henry',  'Baker',   'henry.baker',   'hashed_password_18', true);

INSERT INTO trainee (id, user_id, date_of_birth, address) VALUES (4,  7,  '1994-08-12', '10 Downing St, London, UK');
INSERT INTO trainee (id, user_id, date_of_birth, address) VALUES (5,  8,  '1997-02-03', '742 Maple Drive, Austin, TX');
INSERT INTO trainee (id, user_id, date_of_birth, address) VALUES (6,  9,  '2001-07-21', '456 Oak Lane, Denver, CO');
INSERT INTO trainee (id, user_id, date_of_birth, address) VALUES (7, 10,  '1996-12-05', '123 Beach Ave, Miami, FL');
INSERT INTO trainee (id, user_id, date_of_birth, address) VALUES (8, 11,  '1999-03-14', '88 Sunset Blvd, LA, CA');
INSERT INTO trainee (id, user_id, date_of_birth, address) VALUES (9, 12,  '1995-09-09', '99 Main Street, Boston, MA');
INSERT INTO trainee (id, user_id, date_of_birth, address) VALUES (10,13,  '2000-05-30', '11 Liberty Plaza, NYC, NY');
INSERT INTO trainee (id, user_id, date_of_birth, address) VALUES (11,14,  '1998-10-18', '33 River Rd, Portland, OR');
INSERT INTO trainee (id, user_id, date_of_birth, address) VALUES (12,15,  '1997-04-22', '17 Broadway, San Francisco, CA');
INSERT INTO trainee (id, user_id, date_of_birth, address) VALUES (13,16,  '1996-06-06', '25 Lincoln Blvd, Chicago, IL');
INSERT INTO trainee (id, user_id, date_of_birth, address) VALUES (14,17,  '1994-11-29', '90 King Street, Seattle, WA');
INSERT INTO trainee (id, user_id, date_of_birth, address) VALUES (15,18,  '2002-01-11', '61 Elm Street, Houston, TX');

INSERT INTO training (training_name, training_type_id, trainer_id, trainee_id, training_date, training_duration) VALUES ('Morning Yoga', 2, 1, 3, '2025-06-01', 60);
INSERT INTO training (training_name, training_type_id, trainer_id, trainee_id, training_date, training_duration) VALUES ('Evening Pilates', 3, 3, 3, '2025-06-08', 45);
INSERT INTO training (training_name, training_type_id, trainer_id, trainee_id, training_date, training_duration) VALUES ('Cardio Blast', 1, 2, 2, '2025-06-04', 30);
INSERT INTO training (training_name, training_type_id, trainer_id, trainee_id, training_date, training_duration) VALUES ('Strength Starter', 5, 2, 4, '2025-06-05', 50);
INSERT INTO training (training_name, training_type_id, trainer_id, trainee_id, training_date, training_duration) VALUES ('Power HIIT', 4, 2, 4, '2025-06-10', 40);
INSERT INTO training (training_name, training_type_id, trainer_id, trainee_id, training_date, training_duration) VALUES ('Quick HIIT', 4, 2, 5, '2025-06-07', 35);
INSERT INTO training (training_name, training_type_id, trainer_id, trainee_id, training_date, training_duration) VALUES ('Flex Yoga', 2, 1, 6, '2025-06-01', 60);
INSERT INTO training (training_name, training_type_id, trainer_id, trainee_id, training_date, training_duration) VALUES ('Burn & Build', 5, 3, 6, '2025-06-09', 45);
INSERT INTO training (training_name, training_type_id, trainer_id, trainee_id, training_date, training_duration) VALUES ('Fast Cardio', 1, 2, 6, '2025-06-11', 30);
INSERT INTO training (training_name, training_type_id, trainer_id, trainee_id, training_date, training_duration) VALUES ('Morning Stretch', 3, 3, 8, '2025-06-03', 40);
INSERT INTO training (training_name, training_type_id, trainer_id, trainee_id, training_date, training_duration) VALUES ('Intense Strength', 5, 3, 9, '2025-06-06', 60);
INSERT INTO training (training_name, training_type_id, trainer_id, trainee_id, training_date, training_duration) VALUES ('Sweat Session', 4, 2, 11, '2025-06-02', 30);
INSERT INTO training (training_name, training_type_id, trainer_id, trainee_id, training_date, training_duration) VALUES ('Cardio Power', 1, 2, 11, '2025-06-08', 35);
INSERT INTO training (training_name, training_type_id, trainer_id, trainee_id, training_date, training_duration) VALUES ('Zen Pilates', 3, 3, 12, '2025-06-04', 45);
INSERT INTO training (training_name, training_type_id, trainer_id, trainee_id, training_date, training_duration) VALUES ('Core Strength', 5, 3, 13, '2025-06-05', 60);
INSERT INTO training (training_name, training_type_id, trainer_id, trainee_id, training_date, training_duration) VALUES ('Balance Flow', 2, 1, 13, '2025-06-09', 50);
INSERT INTO training (training_name, training_type_id, trainer_id, trainee_id, training_date, training_duration) VALUES ('High Impact', 4, 2, 15, '2025-06-06', 40);
