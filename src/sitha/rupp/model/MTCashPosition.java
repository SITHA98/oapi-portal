package sitha.rupp.model;

public class MTCashPosition {
	
	private String reference;
	private String date;
	private String description;
	private double cashIn;
	private double cashOut;
	private double balance;
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getCashIn() {
		return cashIn;
	}
	public void setCashIn(double cashIn) {
		this.cashIn = cashIn;
	}
	public double getCashOut() {
		return cashOut;
	}
	public void setCashOut(double cashOut) {
		this.cashOut = cashOut;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	

}
