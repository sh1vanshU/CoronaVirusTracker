package io.CodeWithShivanshu.coronavirustracker.models;

public class LocationStats {
	private String  state;
	private String country;
	private long latestTotalCases;
	private long newCases;
	@Override
	public String toString() {
		return "LocationStats [state= " + state + ", country= " + country + ", latestTotalCases= " + latestTotalCases
				+ ", newCases= " + newCases +"]";
	}
	public long getNewCases() {
		return newCases;
	}
	public void setNewCases(long newCases) {
		this.newCases = newCases;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public long getLatestTotalCases() {
		return latestTotalCases;
	}
	public void setLatestTotalCases(long latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}
	
}
