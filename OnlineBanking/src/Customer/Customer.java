package Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Utility.DatabaseConnection;
import Utility.MyScanner;

public class Customer {
	
	private int customerID;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zip;
	private String phone;
	private String email;
	
	private static ResultSet resultSet;
	private static PreparedStatement stmt;

	public int getCustomerID() {
		return customerID;
	}
	public void setCustomer_id(int customerID) {
		this.customerID = customerID;
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
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
	
	public Customer createOrUpdateProfile(Customer customer) {
		System.out.println("Please provide some additional information about yourself.");
		System.out.println("Only first name is required.  All other fields are optional.  Press Enter to leave a field blank.");
		System.out.println("NOTE: DO NOT USE YOUR REAL INFORMATION.  Be creative and make something up!\n");
	
		System.out.println("First Name: ");	
		customer.setFirstName(MyScanner.getInput());

		System.out.println("Last Name: ");
		customer.setLastName(MyScanner.getInput());
	
		System.out.println("Address (Line 1): ");
		customer.setAddressLine1(MyScanner.getInput());
	
		System.out.println("Address (Line 2): ");
		customer.setAddressLine2(MyScanner.getInput());
	
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
		
	public Customer pushProfileToDatabase(Customer customer) {
		try {
			stmt = DatabaseConnection.prepareStatement("Update customer set first_name = ?, last_name = ?, address_line_1 = ?, address_line_2 = ?, city = ?, state = ?, zip = ?, phone = ?, email = ? where customer_id = ?");
			stmt.setString(1, customer.getFirstName());
			stmt.setString(2, customer.getLastName());
			stmt.setString(3, customer.getAddressLine1());
			stmt.setString(4, customer.getAddressLine2());
			stmt.setString(5, customer.getCity());
			stmt.setString(6, customer.getState());
			stmt.setString(7, customer.getZip());
			stmt.setString(8, customer.getPhone());
			stmt.setString(9, customer.getEmail());
			stmt.setInt(10, customer.getCustomerID());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return customer;
	}
	
	public Customer pullProfileFromDatabase(Customer customer) {
		try {
			stmt = DatabaseConnection.prepareStatement("Select first_name, last_name, address_line_1, address_line_2, city, state, zip, phone, email, customer_id from customer where username = ?");
			stmt.setString(1, customer.getUsername());
			resultSet = stmt.executeQuery();
			
			while (resultSet.next()) {
				customer.setCustomer_id(resultSet.getInt("customer_id"));
				customer.setFirstName(resultSet.getString("first_name"));
				customer.setLastName(resultSet.getString("last_name"));
				customer.setAddressLine1(resultSet.getString("address_line_1"));
				customer.setAddressLine2(resultSet.getString("address_line_2"));
				customer.setCity(resultSet.getString("city"));
				customer.setState(resultSet.getString("state"));
				customer.setZip(resultSet.getString("zip"));
				customer.setPhone(resultSet.getString("phone"));
				customer.setEmail(resultSet.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}
}
