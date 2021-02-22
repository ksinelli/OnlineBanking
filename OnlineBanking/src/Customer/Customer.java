package Customer;

import Utility.MyScanner;

public class Customer {
	
	private int customer_id;
	private String username;
	private String password;
	private String first_name;
	private String last_name;
	private String address_line_1;
	private String address_line_2;
	private String city;
	private String state;
	private String zip;
	private String phone;
	private String email;

	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getAddress_line_1() {
		return address_line_1;
	}
	public void setAddress_line_1(String address_line_1) {
		this.address_line_1 = address_line_1;
	}
	public String getAddress_line_2() {
		return address_line_2;
	}
	public void setAddress_line_2(String address_line_2) {
		this.address_line_2 = address_line_2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Customer updateProfile(Customer customer) {
		System.out.println("Please provide some additional information about yourself.");
		System.out.println("Only first name is required.  All other fields are optional.  Press Enter to leave a field blank.");
		System.out.println("NOTE: DO NOT USE YOUR REAL INFORMATION.  Be creative and make something up!\n");
	
		System.out.println("First Name: ");	
		customer.setFirst_name(MyScanner.getInput());

		System.out.println("Last Name: ");
		customer.setLast_name(MyScanner.getInput());
	
		System.out.println("Address (Line 1): ");
		customer.setAddress_line_1(MyScanner.getInput());
	
		System.out.println("Address (Line 2): ");
		customer.setAddress_line_2(MyScanner.getInput());
	
		System.out.println("City: ");
		customer.setCity(MyScanner.getInput());
	
		System.out.println("State (2 Letter Abbreviation): ");
		customer.setState(MyScanner.getInput());

		System.out.println("ZIP Code (5 Digits Only): ");
		customer.setZip(MyScanner.getInput());

		System.out.println("Phone (10 Digits and Numbers Only): ");
		customer.setPhone(MyScanner.getInput());
	
		System.out.println("Email Address: ");
		customer.setEmail(MyScanner.getInput());
		
		return customer;
	}	
}
