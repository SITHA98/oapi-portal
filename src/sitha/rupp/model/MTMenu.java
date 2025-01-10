package sitha.rupp.model;

public class MTMenu {
	private int menu_id;
	private String menu_name;
	private String menu_description;
	private int menu_main;
	private String menu_icon;
	
	
	public String getMenu_icon() {
		return menu_icon;
	}
	public void setMenu_icon(String menu_icon) {
		this.menu_icon = menu_icon;
	}
	public int getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}
	public int getMenu_main() {
		return menu_main;
	}
	public void setMenu_main(int menu_main) {
		this.menu_main = menu_main;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getMenu_description() {
		return menu_description;
	}
	public void setMenu_description(String menu_description) {
		this.menu_description = menu_description;
	}	
	
}
