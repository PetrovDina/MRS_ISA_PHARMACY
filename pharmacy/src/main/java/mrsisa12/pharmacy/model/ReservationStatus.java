package mrsisa12.pharmacy.model;

public enum ReservationStatus {
	CREATED, //inicijalno kad se kreira
	COMPLETED, //kada pacijent pokupi lekove
	CANCELLED, //kada pacijent otkaze rezervaciju
	EXPIRED //kada pacijent ne preuzme lek do naznacenog datuma
}
