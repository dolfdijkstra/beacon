package com.dolfdijkstra.beacon.domain;




public class Hit {
	private Long id;

	private String uid;

	private String type;

	private String userAgent;

	private String oid;

	private String payload;

	private long timeStamp;

	public Long getId() {
		return id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public void setType(String type) {
		this.type=type;
		
	}

	public String getType() {
		return type;
	}

}
