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
insert into pharmacyadmin (id) values (3);

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

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted) values ('janko', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'jankoh@email.com', 'Janko', 'Hajduk', 1, 'MALE', 'ACTIVATED', 'False');
insert into employees (id, rating ) values (11, 4.1);
insert into dermatologist (id, dermatologistNickname) values (11, 'jane');

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted) values ('petar', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'petarb@email.com', 'Petar', 'Babic', 1, 'MALE', 'ACTIVATED', 'False');
insert into employees (id, rating ) values (12, 1.2);
insert into dermatologist (id, dermatologistNickname) values (12, 'pele');

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted) values ('dmitar', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'dmitar@email.com', 'Mitar', 'Miric', 1, 'MALE', 'ACTIVATED', 'False');
insert into employees (id, rating ) values (13, 3.5);
insert into dermatologist (id, dermatologistNickname) values (13, 'dres bajerna');

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted) values ('supplier', '$2a$10$JP2z80y0qoRUFUb7KiT31OiwlX66Pso0dF/oRqRi9t0IWVLP731X6', 'supplier@email.com', 'Supplier', 'Supplieric', 1, 'MALE', 'ACTIVATED', 'False');
insert into supplier (id) values (14);
INSERT INTO user_role (user_id, role_id) VALUES (14, 7); --  ROLE_SUPPLIER

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted) values ('supplier2', '$2a$10$8goTXhVe385ifP/jkkWMyOqEpQ3fDZwby8q0VGViOn/Bd9Ovl/DMq', 'supplier2@email.com', 'Supplier', 'Supplierov', 1, 'MALE', 'ACTIVATED', 'False');
insert into supplier (id) values (15);
INSERT INTO user_role (user_id, role_id) VALUES (15, 7); --  ROLE_SUPPLIER

insert into employment (employee_id, workTime, contractType, pharmacy_id, deleted) values (4, '{ "startDate": [2021, 4, 3], "startTime": [7, 0, 0], "endDate": [2021, 4, 3], "endTime": [12, 0, 0]}', 'DERMATOLOGIST_CONTRACT', 1, 'False');
insert into employment (employee_id, workTime, contractType, pharmacy_id, deleted) values (5, '{ "startDate": [2021, 4, 3], "startTime": [14, 0, 0], "endDate": [2021, 4, 3], "endTime": [19, 0, 0]}', 'DERMATOLOGIST_CONTRACT', 1, 'False');
insert into employment (employee_id, workTime, contractType, pharmacy_id, deleted) values (6, '{ "startDate": [2021, 4, 3], "startTime": [8, 0, 0], "endDate": [2021, 4, 3], "endTime": [16, 0, 0]}', 'DERMATOLOGIST_CONTRACT', 2, 'False');
insert into employment (employee_id, workTime, contractType, pharmacy_id, deleted) values (7, '{ "startDate": [2021, 4, 3], "startTime": [9, 0, 0], "endDate": [2021, 4, 3], "endTime": [16, 0, 0]}', 'PHARMACIST_CONTRACT', 1, 'False');
insert into employment (employee_id, workTime, contractType, pharmacy_id, deleted) values (8, '{ "startDate": [2021, 4, 3], "startTime": [10, 0, 0], "endDate": [2021, 4, 3], "endTime": [18, 0, 0]}', 'PHARMACIST_CONTRACT', 2, 'False');

INSERT INTO user_role (user_id, role_id) VALUES (1, 2); --  ROLE_SYSTEM_ADMIN
INSERT INTO user_role (user_id, role_id) VALUES (2, 3); --  ROLE_PATIENT
INSERT INTO user_role (user_id, role_id) VALUES (3, 6); --  ROLE_PHARMACY_ADMIN
INSERT INTO user_role (user_id, role_id) VALUES (4, 4); --  DERMATOLOGIST
INSERT INTO user_role (user_id, role_id) VALUES (5, 4); --  DERMATOLOGIST
INSERT INTO user_role (user_id, role_id) VALUES (6, 4); --  DERMATOLOGIST

insert into reservation (patient_id, medication_id, pharmacy_id, quantity, duedate, status) values (2, 1, 1, 3, '1.25.2021.', 'COMPLETED'); --za sad americki format dok ne skontamo kako drugacije
insert into reservation (patient_id, medication_id, pharmacy_id, quantity, duedate, status) values (2, 2, 2, 10, '5.21.2021.', 'CREATED'); --za sad americki format dok ne skontamo kako drugacije
insert into reservation (patient_id, medication_id, pharmacy_id, quantity, duedate, status) values (2, 3, 1, 1, '6.13.2021.', 'CREATED'); --za sad americki format dok ne skontamo kako drugacije
insert into reservation (patient_id, medication_id, pharmacy_id, quantity, duedate, status) values (2, 4, 1, 1, '4.28.2021.', 'CREATED'); --za sad americki format dok ne skontamo kako drugacije

insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted) values ('AVAILABLE', 'DERMATOLOGIST_EXAMINATION', '{ "startDate":  [2021, 4, 26], "startTime": [8, 0, 0], "endDate": [2021, 4, 26], "endTime": [9, 0, 0]}', 4, 1500, 1, 'False');
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted) values ('AVAILABLE', 'DERMATOLOGIST_EXAMINATION', '{ "startDate":  [2021, 6, 10], "startTime": [13, 0, 0], "endDate": [2021, 6, 10], "endTime": [14, 0, 0]}', 4, 1500, 1, 'False');
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted) values ('AVAILABLE', 'DERMATOLOGIST_EXAMINATION', '{ "startDate":  [2021, 6, 15], "startTime": [9, 0, 0], "endDate": [2021, 6, 15], "endTime": [10, 0, 0]}', 4, 1500, 1, 'False');

insert into supplierstorageitem (deleted, quantity, medication_id, supplier_id) values('False', 4, 1, 14);
insert into supplierstorageitem (deleted, quantity, medication_id, supplier_id) values('False', 2, 2, 14);
insert into supplierstorageitem (deleted, quantity, medication_id, supplier_id) values('False', 1, 3, 15);
insert into supplierstorageitem (deleted, quantity, medication_id, supplier_id) values('False', 3, 4, 15);
