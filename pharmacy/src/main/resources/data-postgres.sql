insert into medication (name, manufacturer, prescriptionReq, form, description, content) values ('Probiotik', 'Ivancic i sinovi', 'False', 'PILL', 'Neki opis 1', 'Neki sadrzaj 1');
insert into medication (name, manufacturer, prescriptionReq, form, description, content) values ('Brufen', 'Bosna lijek', 'True', 'PILL', 'Neki opis 2', 'Neki sadrzaj 2');
insert into medication (name, manufacturer, prescriptionReq, form, description, content) values ('Paracetamol', 'Krka', 'True', 'CAPSULE', 'Neki opis 3', 'Neki sadrzaj 3');
insert into medication (name, manufacturer, prescriptionReq, form, description, content) values ('Panadol', 'Jugoremedija', 'False', 'PASTE', 'Neki opis 4', 'Neki sadrzaj 4');
insert into medication (name, manufacturer, prescriptionReq, form, description, content) values ('Panklav', 'Krka', 'True', 'CAPSULE', 'Neki opis 5', 'Neki sadrzaj 5');

insert into alternative_medications (medication_id, alternative_id) values (1, 5);
insert into alternative_medications (medication_id, alternative_id) values (1, 3);

insert into location (latitude, longitude, street, city, zipcode, streetNum) values (30.30,20.20, 'Narodnog fronta','Novi Sad', '21000', 5);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (30.30,20.20, 'Bulevar Oslobođenja','Novi Sad', '21000', 88);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (30.30,20.20, 'Jevrejska','Novi Sad', '21000', 21);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (30.30,20.20, 'Ljube Nesice','Zajecar', '19000', 21);

insert into pharmacy (name, rating, location_id) values ('Apoteka Jankovic', 2.5, 1); --id 1
insert into pharmacy (name, rating, location_id) values ('Benu', 4.5, 2); --id 2
insert into pharmacy (name, rating, location_id) values ('Srbotrade', 1.8, 4); --id 3
insert into pharmacy (name, rating, location_id) values ('Lilly', 3.8, 3); --id 4


insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, deleted) values (3, 2, 1, 'False'); /* tri brufena */
insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, deleted) values (5, 3, 1, 'False'); /* pet paracetamola */
insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, deleted) values (10, 5, 1, 'False'); /* deset panklava */
insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, deleted) values (5, 1, 1, 'False'); /* pet PROBIOTIKA */
insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, deleted) values (5, 2, 2, 'False'); /* pet brufena */
insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, deleted) values (11, 4, 2, 'False'); /* jedanaest panadola */

insert into itemprice (price, current, timeperiod, pharmacystorageitem_id) values (430, true,'{ "startDate": [2021, 4, 2], "startTime": [22, 28, 14], "endDate": null, "endTime": null }', 1);
insert into itemprice (price, current, timeperiod, pharmacystorageitem_id) values (200, true, '{ "startDate": [2021, 4, 2], "startTime": [22, 28, 14], "endDate": null, "endTime": null }', 2);
insert into itemprice (price, current, timeperiod, pharmacystorageitem_id) values (550, true, '{ "startDate": [2021, 4, 2], "startTime": [22, 28, 14], "endDate": null, "endTime": null }', 3);
insert into itemprice (price, current, timeperiod, pharmacystorageitem_id) values (250, false, '{ "startDate": [2021, 4, 2], "startTime": [23, 28, 14], "endDate": [2021, 4, 5], "endTime": [23, 28, 14]}', 1);
insert into itemprice (price, current, timeperiod, pharmacystorageitem_id) values (350, true, '{ "startDate": [2021, 4, 2], "startTime": [0, 28, 14], "endDate": null, "endTime": null}', 4);
insert into itemprice (price, current, timeperiod, pharmacystorageitem_id) values (410, true, '{ "startDate": [2021, 4, 2], "startTime": [23, 28, 14], "endDate": null, "endTime": null}', 5);
insert into itemprice (price, current, timeperiod, pharmacystorageitem_id) values (370, true, '{ "startDate": [2021, 4, 2], "startTime": [0, 28, 14], "endDate": null, "endTime": null}', 6);

 -- TREBA DA SE UBACE ROLE I BLA BLA NADALJE
INSERT INTO role (name) VALUES ('ROLE_GUEST');
INSERT INTO role (name) VALUES ('ROLE_SYSTEM_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_PATIENT');
INSERT INTO role (name) VALUES ('ROLE_DERMATOLOGIST');
INSERT INTO role (name) VALUES ('ROLE_PHARMACIST');
INSERT INTO role (name) VALUES ('ROLE_PHARMACY_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_SUPPLIER');

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted) values ('admin', '$2a$10$k/iwZ4.ZX3RQ.7k2qpT3yeLKmfF0IpGKoRz8bgXvoi58vXwaFsthi', 'admin@gmail.com', 'Admin', 'Adminic', 1, 'MALE', 'ACTIVATED', 'False');
insert into systemadmin (id) values (1);

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted) values ('patient', '$2a$10$s9YM05JPaH8hZARhK1c0wex4g4FmqXbgNX1pKy8uvbyhhIbyWkIn2', 'patient@maildrop.cc', 'Patient', 'Patientic', 1, 'MALE', 'ACTIVATED', 'False');
insert into patient (id, penaltypoints) values (2, 0);

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted) values ('padmin', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'padmin@email.com', 'Padmin', 'Padminic', 1, 'MALE', 'ACTIVATED', 'False');
insert into pharmacyadmin (id, pharmacy_id) values (3, 1);

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted) values ('milica', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'milicam@email.com', 'Milica', 'Milic', 1, 'FEMALE', 'ACTIVATED', 'False');
insert into employees (id, rating ) values (4, 2.2);
insert into dermatologist (id, dermatologistNickname) values (4, 'mica');

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted) values ('vanja', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'vanjav@email.com', 'Vanja', 'Vanjic', 1, 'FEMALE', 'ACTIVATED', 'False');
insert into employees (id, rating ) values (5, 4.4);
insert into dermatologist (id, dermatologistNickname) values (5, 'vanjuska');

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted) values ('pera', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'perap@email.com', 'Pera', 'Perovic', 1, 'MALE', 'ACTIVATED', 'False');
insert into employees (id, rating ) values (6, 3.5);
insert into dermatologist (id, dermatologistNickname) values (6, 'pera');

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted) values ('mirko', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'mirkov@email.com', 'Mirko', 'Visnjic', 1, 'MALE', 'ACTIVATED', 'False');
insert into employees (id, rating ) values (7, 4.5);
insert into pharmacist (id) values (7);

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted) values ('ana', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'anat@email.com', 'Ana', 'Tot', 1, 'FEMALE', 'ACTIVATED', 'False');
insert into employees (id, rating ) values (8, 2.5);
insert into pharmacist (id) values (8);

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted) values ('zarko', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'zarkoz@email.com', 'Zarko', 'Zrenjanin', 1, 'MALE', 'ACTIVATED', 'False');
insert into employees (id, rating ) values (9, 2.5);
insert into dermatologist (id, dermatologistNickname) values (9, 'zare');

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted) values ('marko', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'markok@email.com', 'Marko', 'Kraljevic', 1, 'MALE', 'ACTIVATED', 'False');
insert into employees (id, rating ) values (10, 3.9);
insert into dermatologist (id, dermatologistNickname) values (10, 'mare');

insert into employment (employee_id, workTime, contractType, pharmacy_id, deleted) values (4, '{ "startDate": [2021, 4, 3], "startTime": [7, 0, 0], "endDate": [2021, 4, 3], "endTime": [12, 0, 0]}', 'DERMATOLOGIST_CONTRACT', 1, 'False');
insert into employment (employee_id, workTime, contractType, pharmacy_id, deleted) values (5, '{ "startDate": [2021, 4, 3], "startTime": [14, 0, 0], "endDate": [2021, 4, 3], "endTime": [19, 0, 0]}', 'DERMATOLOGIST_CONTRACT', 1, 'False');
insert into employment (employee_id, workTime, contractType, pharmacy_id, deleted) values (6, '{ "startDate": [2021, 4, 3], "startTime": [8, 0, 0], "endDate": [2021, 4, 3], "endTime": [16, 0, 0]}', 'DERMATOLOGIST_CONTRACT', 2, 'False');
insert into employment (employee_id, workTime, contractType, pharmacy_id, deleted) values (7, '{ "startDate": [2021, 4, 3], "startTime": [9, 0, 0], "endDate": [2021, 4, 3], "endTime": [16, 0, 0]}', 'PHARMACIST_CONTRACT', 1, 'False');
-- update farmaceuta da mu podesimo employment_id
UPDATE pharmacist SET employment_id = 4  WHERE id = 7;
insert into employment (employee_id, workTime, contractType, pharmacy_id, deleted) values (8, '{ "startDate": [2021, 4, 3], "startTime": [10, 0, 0], "endDate": [2021, 4, 3], "endTime": [18, 0, 0]}', 'PHARMACIST_CONTRACT', 2, 'False');
-- update farmaceuta da mu podesimo employment_id
UPDATE pharmacist SET employment_id = 5  WHERE id = 8;

INSERT INTO user_role (user_id, role_id) VALUES (1, 2); --  ROLE_SYSTEM_ADMIN
INSERT INTO user_role (user_id, role_id) VALUES (2, 3); --  ROLE_PATIENT
INSERT INTO user_role (user_id, role_id) VALUES (3, 6); --  ROLE_PHARMACY_ADMIN
INSERT INTO user_role (user_id, role_id) VALUES (4, 4); --  ROLE_DERMATOLOGIST
INSERT INTO user_role (user_id, role_id) VALUES (5, 4); --  ROLE_DERMATOLOGIST
INSERT INTO user_role (user_id, role_id) VALUES (6, 4); --  ROLE_DERMATOLOGIST
INSERT INTO user_role (user_id, role_id) VALUES (7, 5); --  ROLE_PHARMACIST
INSERT INTO user_role (user_id, role_id) VALUES (8, 5); --  ROLE_PHARMACIST
INSERT INTO user_role (user_id, role_id) VALUES (9, 4); --  ROLE_DERMATOLOGIST
INSERT INTO user_role (user_id, role_id) VALUES (10, 4); --  ROLE_DERMATOLOGIST

insert into reservation (patient_id, medication_id, pharmacy_id, quantity, duedate, status) values (2, 1, 1, 3, '1.25.2021.', 'COMPLETED'); --za sad americki format dok ne skontamo kako drugacije
insert into reservation (patient_id, medication_id, pharmacy_id, quantity, duedate, status) values (2, 2, 2, 10, '5.21.2021.', 'CREATED'); --za sad americki format dok ne skontamo kako drugacije
insert into reservation (patient_id, medication_id, pharmacy_id, quantity, duedate, status) values (2, 3, 1, 1, '6.13.2021.', 'CREATED'); --za sad americki format dok ne skontamo kako drugacije
insert into reservation (patient_id, medication_id, pharmacy_id, quantity, duedate, status) values (2, 4, 1, 1, '4.28.2021.', 'CREATED'); --za sad americki format dok ne skontamo kako drugacije

insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted) values ('AVAILABLE', 'DERMATOLOGIST_EXAMINATION', '{ "startDate":  [2021, 7, 26], "startTime": [8, 0, 0], "endDate": [2021, 7, 26], "endTime": [9, 0, 0]}', 4, 1500, 1, 'False');
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted) values ('AVAILABLE', 'DERMATOLOGIST_EXAMINATION', '{ "startDate":  [2021, 8, 10], "startTime": [13, 0, 0], "endDate": [2021, 8, 10], "endTime": [14, 0, 0]}', 4, 1500, 1, 'False');
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted) values ('AVAILABLE', 'DERMATOLOGIST_EXAMINATION', '{ "startDate":  [2021, 8, 15], "startTime": [9, 0, 0], "endDate": [2021, 8, 15], "endTime": [10, 0, 0]}', 4, 1500, 1, 'False');
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted, patient_id) values ('CONCLUDED', 'DERMATOLOGIST_EXAMINATION', '{ "startDate":  [2021, 4, 11], "startTime": [9, 0, 0], "endDate": [2021, 4, 11], "endTime": [9, 30, 0]}', 4, 1500, 1, 'False', 2);
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted, patient_id) values ('CONCLUDED', 'DERMATOLOGIST_EXAMINATION', '{ "startDate":  [2021, 3, 18], "startTime": [10, 0, 0], "endDate": [2021, 3, 18], "endTime": [11, 0, 0]}', 4, 1400, 1, 'False', 2);
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted, patient_id) values ('PENALED', 'DERMATOLOGIST_EXAMINATION', '{ "startDate":  [2021, 2, 5], "startTime": [12, 0, 0], "endDate": [2021, 2, 5], "endTime": [13, 30, 0]}', 4, 1800, 1, 'False', 2);
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted, patient_id) values ('CONCLUDED', 'PHARMACIST_CONSULTATION', '{ "startDate":  [2021, 2, 25], "startTime": [12, 0, 0], "endDate": [2021, 2, 25], "endTime": [13, 0, 0]}', 7, 1200, 1, 'False', 2);
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted, patient_id) values ('CONCLUDED', 'PHARMACIST_CONSULTATION', '{ "startDate":  [2021, 4, 3], "startTime": [12, 0, 0], "endDate": [2021, 4, 3], "endTime": [13, 30, 0]}', 8, 1700, 2, 'False', 2);

-- narudzbenice
insert into orders (dueDate, pharmacyadmin_id, pharmacy_id, deleted) values ('2021-05-10', 3, 1, 'False');
insert into orders (dueDate, pharmacyadmin_id, pharmacy_id, deleted) values ('2021-05-11', 3, 1, 'False');
insert into orders (dueDate, pharmacyadmin_id, pharmacy_id, deleted) values ('2021-05-12', 3, 1, 'False');
insert into orders (dueDate, pharmacyadmin_id, pharmacy_id, deleted) values ('2021-05-13', 3, 1, 'False');

-- orderitem
insert into orderitem (quantity, medication_id, order_id, deleted) values (2, 5, 1, 'False');
insert into orderitem (quantity, medication_id, order_id, deleted) values (5, 2, 1, 'False');
insert into orderitem (quantity, medication_id, order_id, deleted) values (3, 1, 2, 'False');
insert into orderitem (quantity, medication_id, order_id, deleted) values (14, 4, 2, 'False');
insert into orderitem (quantity, medication_id, order_id, deleted) values (12, 2, 2, 'False');
insert into orderitem (quantity, medication_id, order_id, deleted) values (7, 3, 3, 'False');
insert into orderitem (quantity, medication_id, order_id, deleted) values (4, 1, 3, 'False');
insert into orderitem (quantity, medication_id, order_id, deleted) values (15, 1, 4, 'False');
insert into orderitem (quantity, medication_id, order_id, deleted) values (3, 3, 4, 'False');
insert into orderitem (quantity, medication_id, order_id, deleted) values (5, 5, 4, 'False');
insert into orderitem (quantity, medication_id, order_id, deleted) values (8, 4, 4, 'False');
