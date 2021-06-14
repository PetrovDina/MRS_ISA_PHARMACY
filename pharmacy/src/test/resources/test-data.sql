insert into medication (rating, name, manufacturer, prescriptionReq, form, description, content, loyaltyPoints) values (0, 'Probiotik', 'Ivancic i sinovi', 'False', 'PILL', 'Neki opis 1', 'Neki sadrzaj 1', 2);
insert into medication (rating, name, manufacturer, prescriptionReq, form, description, content, loyaltyPoints) values (0, 'Brufen', 'Bosna lijek', 'False', 'PILL', 'Neki opis 2', 'Neki sadrzaj 2', 3);
insert into medication (rating, name, manufacturer, prescriptionReq, form, description, content, loyaltyPoints) values (0, 'Paracetamol', 'Krka', 'False', 'CAPSULE', 'Neki opis 3', 'Neki sadrzaj 3', 2);
insert into medication (rating, name, manufacturer, prescriptionReq, form, description, content, loyaltyPoints) values (0, 'Panadol', 'Jugoremedija', 'False', 'PASTE', 'Neki opis 4', 'Neki sadrzaj 4', 4);
insert into medication (rating, name, manufacturer, prescriptionReq, form, description, content, loyaltyPoints) values (0, 'Panklav', 'Krka', 'True', 'CAPSULE', 'Neki opis 5', 'Neki sadrzaj 5', 9);
insert into medication (rating, name, manufacturer, prescriptionReq, form, description, content, loyaltyPoints) values (0, 'Tylolhot', 'Nobel', 'False', 'POWDER', 'Za efikasno uklanjanje simptoma prehlade i gripe.', 'Neki sadrzaj 3', 5);
insert into medication (rating, name, manufacturer, prescriptionReq, form, description, content, loyaltyPoints) values (0, 'Fervex', 'Upsa', 'False', 'POWDER', 'Za efikasno uklanjanje simptoma prehlade i gripe.', 'Neki sadrzaj 4', 7);
insert into medication (rating, name, manufacturer, prescriptionReq, form, description, content, loyaltyPoints) values (0, 'Amoksicilin', 'Belupo', 'True', 'CAPSULE', 'Antibiotski lek za ...', 'Neki sadrzaj 5', 1);

insert into loyalty_program (afterAppointment, maxPointsRegular, maxPointsSilver, silverDis, goldDis, version) values (5, 200, 500, 10, 30, 0);

insert into alternative_medications (medication_id, alternative_id) values (1, 5);
insert into alternative_medications (medication_id, alternative_id) values (1, 3);
insert into alternative_medications (medication_id, alternative_id) values (2, 5);
insert into alternative_medications (medication_id, alternative_id) values (2, 3);

insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.2413873, 19.8435649, 'Bulevar Oslobođenja','Novi Sad', '21000', 135);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.2485425, 19.8384505, 'Bulevar Oslobođenja','Novi Sad', '21000', 88);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.2525228, 19.8389281, 'Jevrejska','Novi Sad', '21000', 21);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (44.75482475, 20.567192906210508, 'Nemanjina','Beograd', '11130', 10);

insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.2454806, 19.8473289, 'Cara Lazara','Novi Sad', '21000', 135);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.239222, 19.8480987, 'Despota Stefana','Novi Sad', '21000', 135);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.2555321, 19.7507025, 'Zeleznicka','Novi Sad', '21000', 45);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.2490288, 19.8403669, 'Zeleznicka','Novi Sad', '21000', 42);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.2573439, 19.8335956, 'Bulevar Oslobodjenja','Novi Sad', '21000', 50);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.2548982, 19.8357779, 'Bulevar Oslobodjenja','Novi Sad', '21000', 75);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.2479303, 19.8393242, 'Bulevar Oslobodjenja','Novi Sad', '21000', 120);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.2636655, 19.8310184, 'Bulevar Oslobodjenja','Novi Sad', '21000', 5);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.2584818, 19.832983, 'Bulevar Oslobodjenja','Novi Sad', '21000', 34);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.2641324, 19.8313731, 'Bulevar Oslobodjenja','Novi Sad', '21000', 1);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.2634325, 19.8389176, 'Kisacka','Novi Sad', '21000', 34);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.2419969, 19.8432666, 'Narodnog fronta','Novi Sad', '21000', 10);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.2384995, 19.8341637, 'Narodnog fronta','Novi Sad', '21000', 55);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.2660795, 19.8350156, 'Kisacka','Novi Sad', '21000', 100);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.2419391, 19.8439066, 'Narodnog fronta','Novi Sad', '21000', 7);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.2417565, 19.8442126, 'Narodnog fronta','Novi Sad', '21000', 15);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.2395136, 19.8487563, 'Fruskogorska','Novi Sad', '21000', 45);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.2506569, 19.8472803, 'Strazilovska','Novi Sad', '21000', 4);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (45.2498205, 19.8482465, 'Strazilovska','Novi Sad', '21000', 49);

insert into pharmacy (name, rating, location_id, appointmentPriceCatalog) values ('Apoteka Jankovic', 0, 1, '{ "examinationPrice": 2000.0, "consultationPrice": 1000.0 }'); --id 1
insert into pharmacy (name, rating, location_id, appointmentPriceCatalog) values ('Benu', 0, 2, '{ "examinationPrice": 2200.0, "consultationPrice": 1400.0 }'); --id 2
insert into pharmacy (name, rating, location_id, appointmentPriceCatalog) values ('Srbotrade', 0, 4, '{ "examinationPrice": 1200.0, "consultationPrice": 800.0 }'); --id 3
insert into pharmacy (name, rating, location_id, appointmentPriceCatalog) values ('Lilly', 0, 3, '{ "examinationPrice": 2500.0, "consultationPrice": 1600.0 }'); --id 4

-- promocije
insert into promotion (dueDate, deleted) values ('2021-05-25', 'False');
insert into promotion (dueDate, deleted) values ('2021-05-27', 'False');

insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, promotion_id, deleted, counter) values (3, 2, 1, 1, 'False', 0); /* tri brufena */
insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, promotion_id, deleted, counter) values (5, 3, 1, 2, 'False', 0); /* pet paracetamola */
insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, promotion_id, deleted, counter) values (10, 5, 1, null, 'False', 0); /* deset panklava */
insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, promotion_id, deleted, counter) values (5, 1, 1, null, 'False', 0); /* pet PROBIOTIKA */
insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, promotion_id, deleted, counter) values (5, 2, 2, null, 'False', 0); /* pet brufena */
insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, promotion_id, deleted, counter) values (11, 4, 2, null, 'False', 0); /* jedanaest panadola */

insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, promotion_id, deleted, counter) values (20, 5, 3, null, 'False', 0); /* 20 panklava */
insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, promotion_id, deleted, counter) values (20, 4, 3, null, 'False', 0); /* 20 panadola */
insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, promotion_id, deleted, counter) values (20, 8, 3, null, 'False', 0); /* 20 amoksicilina */
insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, promotion_id, deleted, counter) values (20, 7, 3, null, 'False', 0); /* 20 fervexa */
insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, promotion_id, deleted, counter) values (20, 7, 4, null, 'False', 0); /* 20 fervexa */
insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, promotion_id, deleted, counter) values (20, 6, 4, null, 'False', 0); /* 20 tylolhota */

insert into itemprice (price, current, promotion, timeperiod, pharmacystorageitem_id) values (430, false, false,'{ "startDate": [2021, 4, 2], "startTime": [22, 28, 14], "endDate": [2021, 4, 5], "endTime": [23, 28, 14]}', 1);
insert into itemprice (price, current, promotion, timeperiod, pharmacystorageitem_id) values (200, false, false, '{ "startDate": [2021, 4, 2], "startTime": [22, 28, 14], "endDate": [2021, 5, 22], "endTime": [17, 38, 0]}', 2);
insert into itemprice (price, current, promotion, timeperiod, pharmacystorageitem_id) values (550, true, false, '{ "startDate": [2021, 4, 2], "startTime": [22, 28, 14], "endDate": null, "endTime": null }', 3);
insert into itemprice (price, current, promotion, timeperiod, pharmacystorageitem_id) values (250, false, false, '{ "startDate": [2021, 4, 5], "startTime": [23, 28, 14], "endDate": [2021, 5, 22], "endTime": [17, 38, 0]}', 1);
insert into itemprice (price, current, promotion, timeperiod, pharmacystorageitem_id) values (350, true, false, '{ "startDate": [2021, 1, 1], "startTime": [0, 28, 14], "endDate": null, "endTime": null}', 4);
insert into itemprice (price, current, promotion, timeperiod, pharmacystorageitem_id) values (410, true, false, '{ "startDate": [2021, 4, 2], "startTime": [23, 28, 14], "endDate": [2021, 5, 19], "endTime": [0, 0, 0]}', 5);
insert into itemprice (price, current, promotion, timeperiod, pharmacystorageitem_id) values (370, true, false, '{ "startDate": [2021, 4, 2], "startTime": [0, 28, 14], "endDate": [2021, 5, 19], "endTime": [0, 0, 0]}', 6);
insert into itemprice (price, current, promotion, timeperiod, pharmacystorageitem_id) values (225, true, true, '{ "startDate": [2021, 5, 22], "startTime": [17, 38, 0], "endDate": [2021, 5, 25], "endTime": [18, 0, 0]}', 1);
insert into itemprice (price, current, promotion, timeperiod, pharmacystorageitem_id) values (166, true, true, '{ "startDate": [2021, 5, 22], "startTime": [17, 38, 0], "endDate": [2021, 5, 27], "endTime": [18, 0, 0]}', 2);

insert into itemprice (price, current, promotion, timeperiod, pharmacystorageitem_id) values (250, true, false, '{ "startDate": [2021, 4, 2], "startTime": [0, 28, 14], "endDate": [2021, 6, 19], "endTime": [0, 0, 0]}', 7);
insert into itemprice (price, current, promotion, timeperiod, pharmacystorageitem_id) values (210, true, false, '{ "startDate": [2021, 4, 2], "startTime": [0, 28, 14], "endDate": [2021, 6, 19], "endTime": [0, 0, 0]}', 8);
insert into itemprice (price, current, promotion, timeperiod, pharmacystorageitem_id) values (350, true, false, '{ "startDate": [2021, 4, 2], "startTime": [0, 28, 14], "endDate": [2021, 6, 19], "endTime": [0, 0, 0]}', 9);
insert into itemprice (price, current, promotion, timeperiod, pharmacystorageitem_id) values (325, true, false, '{ "startDate": [2021, 4, 2], "startTime": [0, 28, 14], "endDate": [2021, 6, 19], "endTime": [0, 0, 0]}', 10);
insert into itemprice (price, current, promotion, timeperiod, pharmacystorageitem_id) values (345, true, false, '{ "startDate": [2021, 4, 2], "startTime": [0, 28, 14], "endDate": [2021, 6, 19], "endTime": [0, 0, 0]}', 11);
insert into itemprice (price, current, promotion, timeperiod, pharmacystorageitem_id) values (345, true, false, '{ "startDate": [2021, 4, 2], "startTime": [0, 28, 14], "endDate": [2021, 6, 19], "endTime": [0, 0, 0]}', 12);

 -- TREBA DA SE UBACE ROLE I BLA BLA NADALJE
INSERT INTO role (name) VALUES ('ROLE_GUEST');
INSERT INTO role (name) VALUES ('ROLE_SYSTEM_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_PATIENT');
INSERT INTO role (name) VALUES ('ROLE_DERMATOLOGIST');
INSERT INTO role (name) VALUES ('ROLE_PHARMACIST');
INSERT INTO role (name) VALUES ('ROLE_PHARMACY_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_SUPPLIER');

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted,  loggedFirstTime) values ('admin', '$2a$10$k/iwZ4.ZX3RQ.7k2qpT3yeLKmfF0IpGKoRz8bgXvoi58vXwaFsthi', 'admin@gmail.com', 'Admin', 'Adminic', 5, 'MALE', 'ACTIVATED', 'False', 'True');
insert into systemadmin (id) values (1);

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted, loggedFirstTime) values ('patient', '$2a$10$s9YM05JPaH8hZARhK1c0wex4g4FmqXbgNX1pKy8uvbyhhIbyWkIn2', 'patient@maildrop.cc', 'Patient', 'Patientic', 6, 'MALE', 'ACTIVATED', 'False', 'True');
insert into patient (id, penaltypoints, loyaltyPoints, category) values (2, 0, 499, 'SILVER');

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted, loggedFirstTime) values ('padmin', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'padmin@email.com', 'Padmin', 'Padminic', 7, 'MALE', 'ACTIVATED', 'False', 'True');
insert into pharmacyadmin (id, pharmacy_id) values (3, 1);

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted, loggedFirstTime) values ('milica', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'milicam@email.com', 'Milica', 'Milic', 8, 'FEMALE', 'ACTIVATED', 'False', 'True');
insert into employees (id, rating ) values (4, 4.0);
insert into dermatologist (id, dermatologistNickname) values (4, 'mica');

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted, loggedFirstTime) values ('vanja', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'vanjav@email.com', 'Vanja', 'Vanjic', 9, 'FEMALE', 'ACTIVATED', 'False', 'True');
insert into employees (id, rating ) values (5, 0);
insert into dermatologist (id, dermatologistNickname) values (5, 'vanjuska');

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted, loggedFirstTime) values ('pera', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'perap@email.com', 'Pera', 'Perovic', 10, 'MALE', 'ACTIVATED', 'False', 'True');
insert into employees (id, rating ) values (6, 0);
insert into dermatologist (id, dermatologistNickname) values (6, 'pera');


insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted, loggedFirstTime) values ('mirko', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'mirkov@email.com', 'Mirko', 'Visnjic', 11, 'MALE', 'ACTIVATED', 'False', 'True');
insert into employees (id, rating ) values (7, 2.5);
insert into pharmacist (id) values (7);

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted, loggedFirstTime) values ('ana', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'anat@email.com', 'Ana', 'Tot', 12, 'FEMALE', 'ACTIVATED', 'False', 'True');
insert into employees (id, rating ) values (8, 0);
insert into pharmacist (id) values (8);

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted, loggedFirstTime) values ('zarko', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'zarkoz@email.com', 'Zarko', 'Zrenjanin', 13, 'MALE', 'ACTIVATED', 'False', 'True');
insert into employees (id, rating ) values (9, 0);
insert into dermatologist (id, dermatologistNickname) values (9, 'zare');

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted, loggedFirstTime) values ('marko', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'markok@email.com', 'Marko', 'Kraljevic', 14, 'MALE', 'ACTIVATED', 'False', 'True');
insert into employees (id, rating ) values (10, 0);
insert into dermatologist (id, dermatologistNickname) values (10, 'mare');

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted, loggedFirstTime) values ('mica', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'mica@email.com', 'Mica', 'Micic', 15, 'MALE', 'ACTIVATED', 'False', 'True');
insert into patient (id, penaltypoints, loyaltyPoints, category) values (11, 0, 0, 'REGULAR');

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted, loggedFirstTime) values ('supplier', '$2a$10$JP2z80y0qoRUFUb7KiT31OiwlX66Pso0dF/oRqRi9t0IWVLP731X6', 'supplier@email.com', 'Supplier', 'Supplieric', 16, 'MALE', 'ACTIVATED', 'False', 'True');
insert into supplier (id) values (12);
INSERT INTO user_role (user_id, role_id) VALUES (12, 7); --  ROLE_SUPPLIER

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted, loggedFirstTime) values ('supplier2', '$2a$10$8goTXhVe385ifP/jkkWMyOqEpQ3fDZwby8q0VGViOn/Bd9Ovl/DMq', 'supplier2@email.com', 'Supplier', 'Supplierov', 17, 'MALE', 'ACTIVATED', 'False', 'True');
insert into supplier (id) values (13);
INSERT INTO user_role (user_id, role_id) VALUES (13, 7); --  ROLE_SUPPLIER



insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted, loggedFirstTime) values ('milojko', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'milojko@email.com', 'Milojko', 'Pantic', 18, 'MALE', 'ACTIVATED', 'False', 'True');
insert into employees (id, rating ) values (14, 0);
insert into pharmacist (id) values (14);

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted, loggedFirstTime) values ('jelisaveta', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'anat@email.com', 'Jelisaveta', 'Salvetic', 19, 'MALE', 'ACTIVATED', 'False', 'True');
insert into employees (id, rating ) values (15, 0);
insert into pharmacist (id) values (15);



insert into employment (employee_id, workTime, contractType, pharmacy_id, deleted) values (4, '{ "startDate": [2021, 4, 3], "startTime": [7, 0, 0], "endDate": [2021, 4, 3], "endTime": [12, 0, 0]}', 'DERMATOLOGIST_CONTRACT', 1, 'False');
insert into employment (employee_id, workTime, contractType, pharmacy_id, deleted) values (4, '{ "startDate": [2021, 4, 3], "startTime": [14, 0, 0], "endDate": [2021, 4, 3], "endTime": [20, 0, 0]}', 'DERMATOLOGIST_CONTRACT', 2, 'False');
insert into employment (employee_id, workTime, contractType, pharmacy_id, deleted) values (5, '{ "startDate": [2021, 4, 3], "startTime": [14, 0, 0], "endDate": [2021, 4, 3], "endTime": [19, 0, 0]}', 'DERMATOLOGIST_CONTRACT', 1, 'False');
insert into employment (employee_id, workTime, contractType, pharmacy_id, deleted) values (6, '{ "startDate": [2021, 4, 3], "startTime": [8, 0, 0], "endDate": [2021, 4, 3], "endTime": [16, 0, 0]}', 'DERMATOLOGIST_CONTRACT', 2, 'False');
insert into employment (employee_id, workTime, contractType, pharmacy_id, deleted) values (7, '{ "startDate": [2021, 4, 3], "startTime": [9, 0, 0], "endDate": [2021, 4, 3], "endTime": [16, 0, 0]}', 'PHARMACIST_CONTRACT', 1, 'False');
-- update farmaceuta da mu podesimo employment_id
UPDATE pharmacist SET employment_id = 4  WHERE id = 7;
insert into employment (employee_id, workTime, contractType, pharmacy_id, deleted) values (8, '{ "startDate": [2021, 4, 3], "startTime": [10, 0, 0], "endDate": [2021, 4, 3], "endTime": [18, 0, 0]}', 'PHARMACIST_CONTRACT', 2, 'False');
-- update farmaceuta da mu podesimo employment_id
UPDATE pharmacist SET employment_id = 5  WHERE id = 8;

insert into employment (employee_id, workTime, contractType, pharmacy_id, deleted) values (14, '{ "startDate": [2021, 4, 3], "startTime": [7, 0, 0], "endDate": [2021, 4, 3], "endTime": [18, 0, 0]}', 'PHARMACIST_CONTRACT', 1, 'False');
-- update farmaceuta da mu podesimo employment_id
UPDATE pharmacist SET employment_id = 6  WHERE id = 14;

insert into employment (employee_id, workTime, contractType, pharmacy_id, deleted) values (15, '{ "startDate": [2021, 4, 3], "startTime": [8, 0, 0], "endDate": [2021, 4, 3], "endTime": [21, 0, 0]}', 'PHARMACIST_CONTRACT', 1, 'False');
-- update farmaceuta da mu podesimo employment_id
UPDATE pharmacist SET employment_id = 7  WHERE id = 15;

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted, loggedFirstTime) values ('zikica', '$2a$10$JP2z80y0qoRUFUb7KiT31OiwlX66Pso0dF/oRqRi9t0IWVLP731X6', 'zikicam@email.com', 'Zivorad', 'Markovic', 20, 'MALE', 'ACTIVATED', 'False', 'True');
insert into supplier (id) values (16);

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted, loggedFirstTime) values ('mojadmin', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'baskalo@email.com', 'Baki', 'Baskalo', 21, 'MALE', 'ACTIVATED', 'False', 'True');
insert into pharmacyadmin (id, pharmacy_id) values (17, 1);

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted, loggedFirstTime) values ('andjelko', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'andjelko@email.com', 'Andjelko', 'Bojanic', 22, 'MALE', 'ACTIVATED', 'False', 'True');
insert into patient (id, penaltypoints, loyaltyPoints, category) values (18, 3, 0, 'REGULAR'); 

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted,  loggedFirstTime) values ('admin2', '$2a$10$k/iwZ4.ZX3RQ.7k2qpT3yeLKmfF0IpGKoRz8bgXvoi58vXwaFsthi', 'admin2@maildrop.cc', 'Admin', 'Adminovic', 23, 'MALE', 'ACTIVATED', 'False', 'False');
insert into systemadmin (id) values (19);

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
INSERT INTO user_role (user_id, role_id) VALUES (11, 3); --  ROLE_PATIENT
INSERT INTO user_role (user_id, role_id) VALUES (14, 5); --  ROLE_PHARMACIST
INSERT INTO user_role (user_id, role_id) VALUES (15, 5); --  ROLE_PHARMACIST
INSERT INTO user_role (user_id, role_id) VALUES (16, 7); --  ROLE_SUPPLIER
INSERT INTO user_role (user_id, role_id) VALUES (17, 6); --  ROLE_SUPPLIER
INSERT INTO user_role (user_id, role_id) VALUES (18, 3); --  ROLE_PATIENT
INSERT INTO user_role (user_id, role_id) VALUES (19, 2); --  ROLE_SYSTEM_ADMIN

insert into reservation (patient_id, medication_id, pharmacy_id, quantity, medicationPrice, duedate, status, code) values (2, 1, 1, 3, 350, '1.25.2021.', 'COMPLETED', 'rsrvtion01'); --za sad americki format dok ne skontamo kako drugacije
insert into reservation (patient_id, medication_id, pharmacy_id, quantity, medicationPrice, duedate, status, code) values (2, 2, 2, 10, 410, '5.21.2021.', 'CREATED', 'rsrvtion02'); --za sad americki format dok ne skontamo kako drugacije
insert into reservation (patient_id, medication_id, pharmacy_id, quantity, medicationPrice, duedate, status, code) values (2, 3, 1, 1, 200, '8.13.2021.', 'CREATED', 'rsrvtion03'); --za sad americki format dok ne skontamo kako drugacije
insert into reservation (patient_id, medication_id, pharmacy_id, quantity, medicationPrice, duedate, status, code) values (2, 4, 2, 1, 370, '4.28.2021.', 'CREATED', 'rsrvtion04'); --za sad americki format dok ne skontamo kako drugacije

insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted) values ('AVAILABLE', 'DERMATOLOGIST_EXAMINATION', '{ "startDate":  [2021, 7, 26], "startTime": [8, 0, 0], "endDate": [2021, 7, 26], "endTime": [9, 0, 0]}', 4, 2000, 1, 'False');
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted) values ('AVAILABLE', 'DERMATOLOGIST_EXAMINATION', '{ "startDate":  [2021, 8, 10], "startTime": [10, 0, 0], "endDate": [2021, 8, 10], "endTime": [11, 0, 0]}', 4, 2000, 1, 'False');
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted) values ('AVAILABLE', 'DERMATOLOGIST_EXAMINATION', '{ "startDate":  [2021, 8, 15], "startTime": [9, 0, 0], "endDate": [2021, 8, 15], "endTime": [10, 0, 0]}', 4, 2000, 1, 'False');
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted, patient_id, report) values ('CONCLUDED', 'DERMATOLOGIST_EXAMINATION', '{ "startDate":  [2021, 4, 11], "startTime": [9, 0, 0], "endDate": [2021, 4, 11], "endTime": [9, 30, 0]}', 4, 2000, 1, 'False', 2, 'Pacijent pati od depresije.');
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted, patient_id, report) values ('CONCLUDED', 'DERMATOLOGIST_EXAMINATION', '{ "startDate":  [2021, 3, 18], "startTime": [10, 0, 0], "endDate": [2021, 3, 18], "endTime": [11, 0, 0]}', 4, 2000, 1, 'False', 2, 'Pacijent je zdrav.');
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted, patient_id) values ('PENALED', 'DERMATOLOGIST_EXAMINATION', '{ "startDate":  [2021, 2, 5], "startTime": [12, 0, 0], "endDate": [2021, 2, 5], "endTime": [13, 30, 0]}', 4, 1000, 1, 'False', 2);
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted, patient_id, report) values ('CONCLUDED', 'PHARMACIST_CONSULTATION', '{ "startDate":  [2021, 2, 25], "startTime": [12, 0, 0], "endDate": [2021, 2, 25], "endTime": [13, 0, 0]}', 7, 1000, 1, 'False', 2, 'Pacijent nema koronu.');
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted, patient_id, report) values ('CONCLUDED', 'PHARMACIST_CONSULTATION', '{ "startDate":  [2021, 4, 3], "startTime": [12, 0, 0], "endDate": [2021, 4, 3], "endTime": [13, 30, 0]}', 8, 1400, 2, 'False', 2, 'Pacijent ima koronu.');
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted, patient_id) values ('RESERVED', 'PHARMACIST_CONSULTATION', '{ "startDate":  [2021, 8, 20], "startTime": [12, 0, 0], "endDate": [2021, 8, 20], "endTime": [13, 30, 0]}', 8, 1000, 1, 'False', 2);
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted, patient_id) values ('RESERVED', 'PHARMACIST_CONSULTATION', '{ "startDate":  [2021, 7, 25], "startTime": [12, 0, 0], "endDate": [2021, 7, 25], "endTime": [13, 0, 0]}', 8, 1000, 1, 'False', 2);
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted, patient_id) values ('RESERVED', 'PHARMACIST_CONSULTATION', '{ "startDate":  [2021, 6, 3], "startTime": [12, 0, 0], "endDate": [2021, 6, 3], "endTime": [13, 30, 0]}', 8, 1400, 2, 'False', 11);
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted, patient_id, report) values ('CONCLUDED', 'PHARMACIST_CONSULTATION', '{ "startDate":  [2021, 2, 13], "startTime": [12, 0, 0], "endDate": [2021, 2, 13], "endTime": [13, 0, 0]}', 7, 1000, 1, 'False', 11,'Pacijent pati od depresije.');
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted, patient_id, report) values ('CONCLUDED', 'DERMATOLOGIST_EXAMINATION', '{ "startDate":  [2021, 3, 19], "startTime": [10, 0, 0], "endDate": [2021, 3, 19], "endTime": [11, 0, 0]}', 4, 2000, 1, 'False', 11, 'Pacijent ima koronu.');


insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted, patient_id) values ('RESERVED', 'DERMATOLOGIST_EXAMINATION', '{ "startDate":  [2021, 7, 5], "startTime": [12, 0, 0], "endDate": [2021, 7, 5], "endTime": [13, 30, 0]}', 4, 2000, 1, 'False', 2);
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted, patient_id) values ('RESERVED', 'DERMATOLOGIST_EXAMINATION', '{ "startDate":  [2021, 7, 28], "startTime": [11, 0, 0], "endDate": [2021, 7, 28], "endTime": [12, 0, 0]}', 4, 2000, 1, 'False', 11);
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted, patient_id) values ('RESERVED', 'DERMATOLOGIST_EXAMINATION', '{ "startDate":  [2021, 7, 23], "startTime": [12, 0, 0], "endDate": [2021, 7, 23], "endTime": [13, 30, 0]}', 4, 2000, 1, 'False', 2);
insert into appointment (appointmentStatus, appointmentType, timePeriod, employee_id, price, pharmacy_id, deleted, patient_id) values ('RESERVED', 'DERMATOLOGIST_EXAMINATION', '{ "startDate":  [2021, 5, 23], "startTime": [11, 0, 0], "endDate": [2021, 5, 23], "endTime": [12, 0, 0]}', 4, 2000, 1, 'False', 11);


-- alergije
insert into allergies (patient_id, medication_id) values (2, 6);
insert into allergies (patient_id, medication_id) values (2, 8);

--pretplate na apoteke
insert into subscriptions (patient_id, pharmacy_id) values (2, 1);
insert into subscriptions (patient_id, pharmacy_id) values (2, 2);
insert into subscriptions (patient_id, pharmacy_id) values (11, 1);

-- narudzbenice
insert into orders (status, dueDate, pharmacyadmin_id, pharmacy_id, deleted) values ('NEW', '2021-07-10', 3, 1, 'False');
insert into orders (status, dueDate, pharmacyadmin_id, pharmacy_id, deleted) values ('NEW', '2021-07-11', 3, 1, 'False');
insert into orders (status, dueDate, pharmacyadmin_id, pharmacy_id, deleted) values ('HAS_OFFERS', '2021-06-11', 3, 1, 'False');
insert into orders (status, dueDate, pharmacyadmin_id, pharmacy_id, deleted) values ('NEW', '2021-07-13', 3, 1, 'False');

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

insert into supplierstorageitem (deleted, quantity, reservedQuantity, medication_id, supplier_id) values('False', 200, 4, 1, 12);
insert into supplierstorageitem (deleted, quantity, reservedQuantity, medication_id, supplier_id) values('False', 200, 0, 2, 12);
insert into supplierstorageitem (deleted, quantity, reservedQuantity, medication_id, supplier_id) values('False', 200, 7, 3, 12);
insert into supplierstorageitem (deleted, quantity, reservedQuantity, medication_id, supplier_id) values('False', 200, 0, 4, 12);
insert into supplierstorageitem (deleted, quantity, reservedQuantity, medication_id, supplier_id) values('False', 200, 0, 3, 13);
insert into supplierstorageitem (deleted, quantity, reservedQuantity, medication_id, supplier_id) values('False', 200, 0, 4, 13);

insert into offers (deliveryDueDate, price, order_id, supplier_id, status) values ('2021-05-14', 2000, 3, 12, 'PENDING');
insert into offers (deliveryDueDate, price, order_id, supplier_id, status) values ('2021-05-17', 4000, 3, 13, 'PENDING');
insert into offers (deliveryDueDate, price, order_id, supplier_id, status) values ('2021-05-16', 7000, 3, 16, 'PENDING');

insert into complaint (patient_id, system_admin_id, content, response, version) values (2, null, 'Losa usluga generalno.', null, 0);
insert into ComplaintPharmacy(pharmacy_id, id) values (1, 1);

insert into complaint (patient_id, system_admin_id, content, response, version) values (2, null, 'Losa higijena.', null, 0);
insert into ComplaintPharmacy(pharmacy_id, id) values (2, 2);

insert into complaint (patient_id, system_admin_id, content, response, version) values (2, null, 'Farmaceut hteo da vrsi fizicko nasilje nadamnome.', null, 0);
insert into ComplaintPharmacy(pharmacy_id, id) values (3, 3);

insert into complaint (patient_id, system_admin_id, content, response, version) values (2, 1, 'Farmaceut hteo da vrsi psihicko nasilje nadamnome.', 'Farmaceut kaze nije.', 0);
insert into ComplaintPharmacy(pharmacy_id, id) values (3, 4);

insert into complaint (patient_id, system_admin_id, content, response, version) values (2, 1, 'Lekovima istekao rok.', 'Ma to nije tacno.', 0);
insert into ComplaintPharmacy(pharmacy_id, id) values (2, 5);

insert into complaint (patient_id, system_admin_id, content, response, version) values (2, null, 'Dermatologa interesuje ga ono sto radi.', null, 0);
insert into ComplaintEmployee(employee_id, id) values (4, 6);

insert into complaint (patient_id, system_admin_id, content, response, version) values (2, null, 'Zakasnio na pregled.', null, 0);
insert into ComplaintEmployee(employee_id, id) values (5, 7);

insert into complaint (patient_id, system_admin_id, content, response, version) values (2, 1, 'Farmaceut hteo da vrsi fizicko nasilje nadamnome.', 'Farmaceut kaze nije.', 0);
insert into ComplaintEmployee(employee_id, id) values (7, 8);

insert into therapy (patient_id, prescribedDate, code, status, pharmacy_id) values (2, '2021-05-14', 'code123456', 'COMPLETED', 1);
insert into therapy (patient_id, prescribedDate, code, status, pharmacy_id) values (2, '2021-04-10', 'code654321', 'COMPLETED', 1);

insert into therapyItem (quantity, medicationPrice, medication_id, therapyDuration, therapy_id) values (2, 350, 2, 7, 1);
insert into therapyItem (quantity, medicationPrice, medication_id, therapyDuration, therapy_id) values (5, 300, 3, 30, 1);
insert into therapyItem (quantity, medicationPrice, medication_id, therapyDuration, therapy_id) values (4, 450, 4, 20, 1);
insert into therapyItem (quantity, medicationPrice, medication_id, therapyDuration, therapy_id) values (1, 400, 3, 6, 2);
insert into therapyItem (quantity, medicationPrice, medication_id, therapyDuration, therapy_id) values (2, 250, 4, 10, 2);

--dermatologist ratings - Milica Milic
insert into rating (type, date, rating, employee_id, patient_id) values ('employee_rating', '2021-01-20', 3.0, 4, 2 );
insert into rating (type, date, rating, employee_id, patient_id) values ('employee_rating', '2021-02-12', 5.0, 4, 11 );

--pharmacist ratings - Mirko Visnjic
insert into rating (type, date, rating, employee_id, patient_id) values ('employee_rating', '2021-01-20', 3.0, 7, 2 );
insert into rating (type, date, rating, employee_id, patient_id) values ('employee_rating', '2021-01-20', 2.0, 7, 11 );

insert into absence (absenceStatus, absenceType, timePeriod, employee_id, pharmacy_id) values ('REQUESTED', 'VACATION', '{ "startDate":  [2021, 8, 1], "startTime": [0, 0, 0], "endDate": [2021, 8, 8], "endTime": [0, 0, 0]}', 4, 1);
insert into absence (absenceStatus, absenceType, timePeriod, employee_id, pharmacy_id) values ('REQUESTED', 'VACATION', '{ "startDate":  [2021, 12, 25], "startTime": [0, 0, 0], "endDate": [2022, 1, 15], "endTime": [0, 0, 0]}', 4, 1);
insert into absence (absenceStatus, absenceType, timePeriod, employee_id, pharmacy_id) values ('DENIED', 'ABSENCE', '{ "startDate":  [2021, 5, 5], "startTime": [0, 0, 0], "endDate": [2021, 5, 10], "endTime": [0, 0, 0]}', 4, 1);

insert into eprescription (code, patientfirstname, patientlastname, prescribeddate, price, pharmacy_id, patient_id) values ('code1', 'Patient', 'Patientic', '2021-05-31', 450, 1, 2);
insert into eprescriptionitem (quantity, e_prescription_id, medication_id) values (2, 1, 2);

insert into eprescription (code, patientfirstname, patientlastname, prescribeddate, price, pharmacy_id, patient_id) values ('code2', 'Patient', 'Patientic', '2021-05-31', 741, 1, 2);
insert into eprescriptionitem (quantity, e_prescription_id, medication_id) values (1, 2, 1);
insert into eprescriptionitem (quantity, e_prescription_id, medication_id) values (1, 2, 2);
insert into eprescriptionitem (quantity, e_prescription_id, medication_id) values (1, 2, 3);