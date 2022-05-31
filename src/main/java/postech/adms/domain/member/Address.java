package postech.adms.domain.member;


import postech.adms.common.persistence.FieldCopy;
import postech.adms.domain.codes.AddressType;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author miriet
 * @version 1.0
 * @created 20-2-2017  12:39:53
 */
   //@Embeddable
@Entity
@Table(name = "ADMS_ADDRESS")
@JsonIgnoreProperties({"alumniMember"})
public class Address implements Serializable {

	private static final long serialVersionUID = 8916964975062040095L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ADDRESS_ID")
	private Long id;

	/**
	 * 주소구분 
	 */
	@Column(name = "ADDR_TYPE")
	@FieldCopy(isCopy = true)
	private String addressType;

	/**
	 * 주소명 
	 */
	@Column(name = "NAME")
	@FieldCopy(isCopy = true)
	private String addressName;
	
	/**
	 * 우편번호 
	 */
	@Column(name = "ZIP_CODE")
	@FieldCopy(isCopy = true)
	private String zipCode;

	/**
	 * 주소1
	 */
	@Column(name = "ADDR1")
	@FieldCopy(isCopy = true)
	private String address1;

	/**
	 * 주소2 
	 */
	@Column(name = "ADDR2")
	@FieldCopy(isCopy = true)
	private String address2;
	
	/**
	 * 주소3 
	 */
	@Column(name = "ADDR3")
	@FieldCopy(isCopy = true)
	private String address3;
	
	/**
	 * 주소4 
	 */
	@Column(name = "ADDR4")
	@FieldCopy(isCopy = true)
	private String address4;

	/**
	 * 거주국가 
	 */
	@Column(name = "COUNTRY")
	@FieldCopy(isCopy = true)
	private String country = "KOR";

	/*
	@Column(name = "ADDR_TYPE")
	@Enumerated(EnumType.STRING)
	@FieldCopy(isCopy = true)
	private AddressType addressType;
    */
	
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@JoinColumn(name = "MEMBER_ID")
	private AlumniMember alumniMember;

	public Address() {

	}

	public Address(String address1, String address2,String address3,String address4, String addressName, String addressType, String country, String zipCode) {
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.address4 = address4;
		this.addressName = addressName;
		this.addressType = addressType;
		this.country = country;
		this.zipCode = zipCode;
	}

	public void finalize() throws Throwable {

	}

	public Long getId() {
		return id;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getAddressName() {
		return addressName;
	}

	public String getAddressType() {
		return addressType;
	}

	public String getCountry() {
		return country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setId(Long newVal) {
		id = newVal;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	
	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public AlumniMember getAlumniMember() {
		return alumniMember;
	}

	public void setAlumniMember(AlumniMember alumniMember) {
		this.alumniMember = alumniMember;
	}
}