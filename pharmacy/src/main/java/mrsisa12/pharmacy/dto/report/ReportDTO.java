package mrsisa12.pharmacy.dto.report;

import java.util.HashMap;

public class ReportDTO {
	
	public static enum ReportMonths {
		January,
		February,
		March,
		April,
		May,
		June,
		July,
		August,
		September,
		October,
		November,
		December
	}
	
	public static HashMap<String, String> quarters = new HashMap<String, String>(){{
		    put("Q1", "January, February, March");
		    put("Q2", "April, May, June");
		    put("Q3", "July, August, September");
		    put("Q4", "October, November, December");
	}};

	private HashMap<String, Integer> data;

	public ReportDTO() {}
	
	public ReportDTO(HashMap<String, Integer> data) {
		super();
		this.data = data;
	}

	public HashMap<String, Integer> getData() {
		return data;
	}

	public void setData(HashMap<String, Integer> data) {
		this.data = data;
	}
}
