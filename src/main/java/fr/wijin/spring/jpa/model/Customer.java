package fr.wijin.spring.jpa.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
@NamedQuery(name = "Customer.findByActive", query = "SELECT c FROM Customer c WHERE c.active=:active")
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5179286429559866248L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 100)
	private String lastname;

	@Column(length = 100)
	private String firstname;

	@Column(length = 200)
	private String company;

	@Column(length = 255)
	private String mail;

	@Column(length = 15)
	private String phone;

	@Column(length = 15)
	private String mobile;

	@Column(columnDefinition = "TEXT")
	private String notes;

	private Boolean active;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Order> orders;

	public Customer() {
		super();
	}

	public Customer(Integer id, String lastname, String firstname, String company, String mail, String phone,
			String mobile, String notes, Boolean active) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.company = company;
		this.mail = mail;
		this.phone = phone;
		this.mobile = mobile;
		this.notes = notes;
		this.active = active;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", lastname=" + lastname + ", firstname=" + firstname + ", company=" + company
				+ ", mail=" + mail + ", phone=" + phone + ", mobile=" + mobile + ", notes=" + notes + ", active="
				+ active + "]";
	}
}
