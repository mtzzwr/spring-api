package com.mtzzwr.odonto.upload;


public class UploadInput {

	private String filename;
	private String mimetype;
	private String base64;
	
	public UploadInput() {}
	
	public UploadInput(String filename, String mimetype, String base64) {
		super();
		this.filename = filename;
		this.mimetype = mimetype;
		this.base64 = base64;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}
	
}
