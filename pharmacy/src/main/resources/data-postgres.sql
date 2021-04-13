insert into medication (name, manufacturer, prescriptionReq, form, description, content) values ('Probiotik', 'Ivancic i sinovi', 'False', 'PILL', 'Neki opis 1', 'Neki sadrzaj 1');
insert into medication (name, manufacturer, prescriptionReq, form, description, content) values ('Brufen', 'Bosna lijek', 'True', 'PILL', 'Neki opis 2', 'Neki sadrzaj 2');
insert into medication (name, manufacturer, prescriptionReq, form, description, content) values ('Paracetamol', 'Krka', 'True', 'CAPSULE', 'Neki opis 3', 'Neki sadrzaj 3');
insert into medication (name, manufacturer, prescriptionReq, form, description, content) values ('Panadol', 'Jugoremedija', 'False', 'PASTE', 'Neki opis 4', 'Neki sadrzaj 4');
insert into medication (name, manufacturer, prescriptionReq, form, description, content) values ('Panklav', 'Krka', 'True', 'CAPSULE', 'Neki opis 5', 'Neki sadrzaj 5');

insert into location (latitude, longitude, street, city, zipcode, streetNum) values (30.30,20.20, 'Narodnog fronta','Novi Sad', '21000', 5);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (30.30,20.20, 'Bulevar Oslobođenja','Novi Sad', '21000', 88);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (30.30,20.20, 'Jevrejska','Novi Sad', '21000', 21);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (30.30,20.20, 'Ljube Nesice','Zajecar', '19000', 21);

insert into pharmacy (name, rating, location_id) values ('Apoteka Jankovic', 2.5, 1); --id 1
insert into pharmacy (name, rating, location_id) values ('Benu', 4.5, 2); --id 2
insert into pharmacy (name, rating, location_id) values ('Srbotrade', 4.8, 3); --id 3

insert into pharmacystorageitem (quantity, medication_id) values (3, 2); /* id 1 - tri brufena */ 
insert into pharmacystorageitem (quantity, medication_id) values (5, 3); /* id 2 - pet paracetamola */
insert into pharmacystorageitem (quantity, medication_id) values (10, 5); /*id 3 - deset panklava */
insert into pharmacystorageitem (quantity, medication_id) values (5, 1); /* id 4 - pet PROBIOTIKA - dina promenila jer je brufen ovde bio a ne sme dvaput */
insert into pharmacystorageitem (quantity, medication_id) values (5, 2); /* id 5 - pet brufena */
insert into pharmacystorageitem (quantity, medication_id) values (11, 4); /*id 6 -  jedanaest panadola */


insert into pharmacy_pharmacystorageitem values (1,1);
insert into pharmacy_pharmacystorageitem values (1,2);
insert into pharmacy_pharmacystorageitem values (1,3);
insert into pharmacy_pharmacystorageitem values (1,4);
insert into pharmacy_pharmacystorageitem values (2,5);
insert into pharmacy_pharmacystorageitem values (2,6);

insert into itemprice (price, current ) values (430, true);
insert into itemprice (price, current ) values (200, true);
insert into itemprice (price, current ) values (550, true);
insert into itemprice (price, current ) values (250, false);
insert into itemprice (price, current ) values (350, true);
insert into itemprice (price, current) values (430, true);
insert into itemprice (price, current ) values (370, true);


insert into pharmacystorageitem_itemprice values (1,4);
insert into pharmacystorageitem_itemprice values (1,1);
insert into pharmacystorageitem_itemprice values (2,2);
insert into pharmacystorageitem_itemprice values (3,3);
insert into pharmacystorageitem_itemprice values (4,5);
insert into pharmacystorageitem_itemprice values (5,6);
insert into pharmacystorageitem_itemprice values (6,7);


insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, userrole, deleted) values ('a', 'a', 'a@gmail.com', 'Admin', 'Adminic', 1, 'MALE', 'ACTIVATED', 'SYSTEM_ADMIN', 'False');
insert into systemadmin (id) values (1);

insert into system_user (username, password, email, firstname, lastname, location_id, gender, activestatus, userrole, deleted) values ('p', 'p', 'p@gmail.com', 'Pac', 'Pacic', 1, 'MALE', 'ACTIVATED', 'PATIENT', 'False');
insert into patient (id, penaltypoints) values (2, 123);



insert into reservation (patient_id, medication_id, pharmacy_id, quantity, duedate, status) values (2, 1, 1, 3, '1.25.2021.', 'CREATED'); --za sad americki format dok ne skontamo kako drugacije























