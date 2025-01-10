package sitha.rupp.model;

public class CASADashboard {

	private String accountType;
	private String ccy;
	private double balance;
	private long no_of_client;
	private long no_of_casa;
	private long counts;
	public CASADashboard() {
		super();
	}
	public CASADashboard(String accountType, String ccy, double balance) {
		super();
		this.accountType = accountType;
		this.ccy = ccy;
		this.balance = balance;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getCcy() {
		return ccy;
	}
	public void setCcy(String ccy) {
		this.ccy = ccy;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public long getNo_of_client() {
		return no_of_client;
	}
	public void setNo_of_client(long no_of_client) {
		this.no_of_client = no_of_client;
	}
	public long getNo_of_casa() {
		return no_of_casa;
	}
	public void setNo_of_casa(long no_of_casa) {
		this.no_of_casa = no_of_casa;
	}
	public long getCounts() {
		return counts;
	}
	public void setCounts(long counts) {
		this.counts = counts;
	}
	
	
}
