insert into medication (name, manufacturer, prescriptionReq, form) values ('Probiotik', 'Ivancic i sinovi', 'False', 'PILL');
insert into medication (name, manufacturer, prescriptionReq, form) values ('Brufen', 'Bosna lijek', 'True', 'PILL');
insert into medication (name, manufacturer, prescriptionReq, form) values ('Paracetamol', 'Krka', 'True', 'CAPSULE');
insert into medication (name, manufacturer, prescriptionReq, form) values ('Panadol', 'Jugoremedija', 'False', 'PASTE');
insert into medication (name, manufacturer, prescriptionReq, form) values ('Panklav', 'Krka', 'True', 'CAPSULE');

insert into location (latitude, longitude, street, city, zipcode, streetNum) values (30.30,20.20, 'Narodnog fronta','Novi Sad', '21000', 5);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (30.30,20.20, 'Bulevar Oslobođenja','Novi Sad', '21000', 88);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (30.30,20.20, 'Jevrejska','Novi Sad', '21000', 21);
insert into location (latitude, longitude, street, city, zipcode, streetNum) values (30.30,20.20, 'Ljube Nesice','Zajecar', '19000', 21);

insert into pharmacy (name, rating, location_id) values ('Apoteka Jankovic', 2.5, 1);
insert into pharmacy (name, rating, location_id) values ('Benu', 4.5, 2);
insert into pharmacy (name, rating, location_id) values ('Srbotrade', 4.8, 3);

insert into pharmacystorageitem (quantity, medication_id) values (3, 2); /* tri brufena */
insert into pharmacystorageitem (quantity, medication_id) values (5, 3); /* pet paracetamola */
insert into pharmacystorageitem (quantity, medication_id) values (10, 5); /* deset panklava */

insert into pharmacy_pharmacystorageitem values (1,1);
insert into pharmacy_pharmacystorageitem values (2,3);
insert into pharmacy_pharmacystorageitem values (3,2);

insert into itemprice (price, current) values (430, false);
insert into itemprice (price, current) values (200, false);
insert into itemprice (price, current) values (550, true);
insert into itemprice (price, current) values (250, false);
