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

--insert into pharmacystorageitem (quantity, medication_id, pharmacy_id) values (3, 2, 1); /* tri brufena */
--insert into pharmacystorageitem (quantity, medication_id, pharmacy_id) values (5, 3, 1); /* pet paracetamola */
--insert into pharmacystorageitem (quantity, medication_id, pharmacy_id) values (10, 5, 1); /* deset panklava */
--insert into pharmacystorageitem (quantity, medication_id, pharmacy_id) values (5, 3, 1); /* pet paracetamola */
--insert into pharmacystorageitem (quantity, medication_id, pharmacy_id) values (5, 2, 2); /* pet paracetamola */
--insert into pharmacystorageitem (quantity, medication_id, pharmacy_id) values (5, 4, 2); /* pet paracetamola */

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