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

insert into pharmacystorageitem (quantity, medication_id) values (3, 2); /* tri brufena */
insert into pharmacystorageitem (quantity, medication_id) values (5, 3); /* pet paracetamola */
insert into pharmacystorageitem (quantity, medication_id) values (10, 5); /* deset panklava */
insert into pharmacystorageitem (quantity, medication_id) values (5, 3); /* pet paracetamola */
insert into pharmacystorageitem (quantity, medication_id) values (5, 2); /* pet brufena */
insert into pharmacystorageitem (quantity, medication_id) values (11, 4); /* jedanaest panadola */


insert into pharmacy_pharmacystorageitem values (1,1);
insert into pharmacy_pharmacystorageitem values (1,2);
insert into pharmacy_pharmacystorageitem values (1,3);
insert into pharmacy_pharmacystorageitem values (1,4);
insert into pharmacy_pharmacystorageitem values (2,5);
insert into pharmacy_pharmacystorageitem values (2,6);


insert into itemprice (price, current, timeperiod) values (430, true,'{ "startTimestamp": "2021-04-03T22:28:14.332", "endTimestamp": null }');
insert into itemprice (price, current, timeperiod) values (200, true, '{ "startTimestamp": "2021-04-02T22:28:14.332", "endTimestamp": null }');
insert into itemprice (price, current, timeperiod) values (550, true, '{ "startTimestamp": "2021-04-03T22:28:14.332", "endTimestamp": null }');
insert into itemprice (price, current, timeperiod) values (250, false, '{ "startTimestamp": "2021-04-01T23:28:14.332", "endTimestamp": "2021-04-02T22:28:14.332"}');
insert into itemprice (price, current, timeperiod) values (350, true, '{ "startTimestamp": "2021-04-03T00:28:14.332", "endTimestamp": null}');
insert into itemprice (price, current, timeperiod) values (430, true, '{ "startTimestamp": "2021-04-06T23:28:14.332", "endTimestamp": null}');
insert into itemprice (price, current, timeperiod) values (370, true, '{ "startTimestamp": "2021-04-06T00:28:14.332", "endTimestamp": null}');


insert into pharmacystorageitem_itemprice values (1,4);
insert into pharmacystorageitem_itemprice values (1,1);
insert into pharmacystorageitem_itemprice values (2,2);
insert into pharmacystorageitem_itemprice values (3,3);
insert into pharmacystorageitem_itemprice values (4,5);
insert into pharmacystorageitem_itemprice values (5,6);
insert into pharmacystorageitem_itemprice values (6,7);


