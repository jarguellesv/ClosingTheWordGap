package edu.gatech.wordgap.spring.jdbc.model;

public class Kid {
	private int id;
	private String name;
	private String icon;
	private long lastActivity;
	private String language = "english";
	private String strategy = "smart";
	private String activity = "fill";
	private String print_language = "english";
	private String speech_language = "english";

	public String getPrint_language() {
		return print_language;
	}
	public void setPrint_language(String print_language) {
		this.print_language = print_language;
	}
	public String getSpeech_language() {
		return speech_language;
	}
	public void setSpeech_language(String speech_language) {
		this.speech_language = speech_language;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public long getLastActivity() {
		return lastActivity;
	}
	public void setLastActivity(long lastActivity) {
		this.lastActivity = lastActivity;
	}
	public String getStrategy() {
		return strategy;
	}
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}

}
