package mrsisa12.pharmacy.model.enums;

public enum AppointmentStatus {
	AVAILABLE, //kada admin kreira nove termine za derm
	RESERVED, //kada pacijent rezervise a nije jos dosao datum 
	CONCLUDED, //ovo postavlja derm/pharm pri uspesno odrzanom pregledu
	PENALED, //ovo postavlja derm/pharm ako se pacijent ne pojavi
	EXPIRED //ovo bi trebalo da se dodeli automatski ako derm/pharm ne dodje na termin i prosao je datum termina
}
