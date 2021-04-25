insert into medication (name, manufacturer, prescriptionReq, form, description, content) values ('Probiotik', 'Ivancic i sinovi', 'False', 'PILL', 'Neki opis 1', 'Neki sadrzaj 1');
insert into medication (name, manufacturer, prescriptionReq, form, description, content) values ('Brufen', 'Bosna lijek', 'True', 'PILL', 'Neki opis 2', 'Neki sadrzaj 2');
insert into medication (name, manufacturer, prescriptionReq, form, description, content) values ('Paracetamol', 'Krka', 'True', 'CAPSULE', 'Neki opis 3', 'Neki sadrzaj 3');
insert into medication (name, manufacturer, prescriptionReq, form, description, content) values ('Panadol', 'Jugoremedija', 'False', 'PASTE', 'Neki opis 4', 'Neki sadrzaj 4');
insert into medication (name, manufacturer, prescriptionReq, form, description, content) values ('Panklav', 'Krka', 'True', 'CAPSULE', 'Neki opis 5', 'Neki sadrzaj 5');

insert into medication_medication (medication_id, alternatives_id) values (1, 5);
insert into medication_medication (medication_id, alternatives_id) values (1, 3);

insert into location (latitude, longitude, street, city, zipcode, streetNum) values (30.30,20.20, 'Narodnog fronta','Novi Sad', '21000', 5);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (30.30,20.20, 'Bulevar Oslobođenja','Novi Sad', '21000', 88);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (30.30,20.20, 'Jevrejska','Novi Sad', '21000', 21);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (30.30,20.20, 'Ljube Nesice','Zajecar', '19000', 21);

insert into pharmacy (name, rating, location_id) values ('Apoteka Jankovic', 2.5, 1); --id 1
insert into pharmacy (name, rating, location_id) values ('Benu', 4.5, 2); --id 2
insert into pharmacy (name, rating, location_id) values ('Srbotrade', 4.8, 3); --id 3

insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, deleted) values (3, 2, 1, 'False'); /* tri brufena */
insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, deleted) values (5, 3, 1, 'False'); /* pet paracetamola */
insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, deleted) values (10, 5, 1, 'False'); /* deset panklava */
insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, deleted) values (5, 1, 1, 'False'); /* pet PROBIOTIKA */
insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, deleted) values (5, 2, 2, 'False'); /* pet brufena */
insert into pharmacystorageitem (quantity, medication_id, pharmacy_id, deleted) values (11, 4, 2, 'False'); /* jedanaest panadola */

insert into itemprice (price, current, timeperiod, pharmacystorageitem_id) values (430, true,'{ "startTimestamp": "2021-04-03T22:28:14.332", "endTimestamp": null }', 1);
insert into itemprice (price, current, timeperiod, pharmacystorageitem_id) values (200, true, '{ "startTimestamp": "2021-04-02T22:28:14.332", "endTimestamp": null }', 2);
insert into itemprice (price, current, timeperiod, pharmacystorageitem_id) values (550, true, '{ "startTimestamp": "2021-04-03T22:28:14.332", "endTimestamp": null }', 3);
insert into itemprice (price, current, timeperiod, pharmacystorageitem_id) values (250, false, '{ "startTimestamp": "2021-04-01T23:28:14.332", "endTimestamp": "2021-04-02T22:28:14.332"}', 1);
insert into itemprice (price, current, timeperiod, pharmacystorageitem_id) values (350, true, '{ "startTimestamp": "2021-04-03T00:28:14.332", "endTimestamp": null}', 4);
insert into itemprice (price, current, timeperiod, pharmacystorageitem_id) values (430, true, '{ "startTimestamp": "2021-04-06T23:28:14.332", "endTimestamp": null}', 5);
insert into itemprice (price, current, timeperiod, pharmacystorageitem_id) values (370, true, '{ "startTimestamp": "2021-04-06T00:28:14.332", "endTimestamp": null}', 6);

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

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted) values ('patient', '$2a$10$s9YM05JPaH8hZARhK1c0wex4g4FmqXbgNX1pKy8uvbyhhIbyWkIn2', 'patient@email.com', 'Patient', 'Patientic', 1, 'MALE', 'ACTIVATED', 'False');
insert into patient (id, penaltypoints) values (2, 0);

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, deleted) values ('padmin', '$2a$10$F.92g9Y99STKAL3TCExFYuboFCcgMlhH3dOZXa8XX0bKbc88Rzn26', 'padmin@email.com', 'Padmin', 'Padminic', 1, 'MALE', 'ACTIVATED', 'False');
insert into pharmacyadmin (id) values (3);



INSERT INTO user_role (user_id, role_id) VALUES (1, 2); --  ROLE_SYSTEM_ADMIN
INSERT INTO user_role (user_id, role_id) VALUES (2, 3); --  ROLE_PATIENT
INSERT INTO user_role (user_id, role_id) VALUES (3, 6); --  ROLE_PHARMACY_ADMIN

insert into reservation (patient_id, medication_id, pharmacy_id, quantity, duedate, status) values (2, 1, 1, 3, '1.25.2021.', 'COMPLETED'); --za sad americki format dok ne skontamo kako drugacije
insert into reservation (patient_id, medication_id, pharmacy_id, quantity, duedate, status) values (2, 2, 2, 10, '5.21.2021.', 'CREATED'); --za sad americki format dok ne skontamo kako drugacije
insert into reservation (patient_id, medication_id, pharmacy_id, quantity, duedate, status) values (2, 3, 1, 1, '6.13.2021.', 'CREATED'); --za sad americki format dok ne skontamo kako drugacije
