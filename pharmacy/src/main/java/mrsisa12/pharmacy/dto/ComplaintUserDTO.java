package mrsisa12.pharmacy.dto;

public abstract class ComplaintUserDTO {
	
	private Long id;
	private String content;

	public ComplaintUserDTO() { }

	public ComplaintUserDTO(Long id, String content) {
		super();
		this.id = id;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
