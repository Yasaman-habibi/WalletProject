package ir.freeland.springboot.entity;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Email;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;


@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "mobileNumber", "email" , "nationalId"})
})
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private long id;
    
    
    
    @NotNull(message = "National Id is required")
    @Pattern(regexp = "^\\d{10}$", message = "National id contains 10 number")
    @Column(name = "nationa_lId" , unique = true ,nullable = false)
    private String nationalId; 
    

    
    @OneToMany(mappedBy = "person" , cascade = CascadeType.ALL)
    private Set<Account> account;
    
    
    
    @MobileNumber
    @NotNull(message = "mobile number is required")
    @Column(unique = true)
    private String mobileNumber;
    
    
    
    @NotNull(message = "first Name is required")
    @Column(name = "first_Name", length = 50, nullable = false)
    private String firstName;
    
    
    
    @NotNull(message = "last Name is required")
    @Column(name = "last_Name", length = 50, nullable = false)
    private String lastName;
    
    
    
    @NotNull(message = "gender is required")
    @Enumerated(EnumType.STRING)
	private Gender gender;
    
    
    
    @NotNull(message = "birthDate is required")
    @Temporal(TemporalType.DATE)
	private Date birthDate;
    
    
    
    @NotNull(message = "email is required")
    @Email(message = "must be invalid email")
    @Column(name = "email" , unique = true)
    private String email;
    
    
    
    @Enumerated(EnumType.STRING)
   	private MilitaryStatus militaryStatus;
    
    
    
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
           message = "Password must be at least 8 characters long and contain at least one uppercase letter,"
    		+ " one lowercase letter, and one number")
    private String password;

    
    
    private boolean isActive = false; 
    
    
    
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Account> accounts;

   
    
    public void addAccount(Account account) {
        if (accounts == null) {
        	accounts = new HashSet<>();
        }
        accounts.add(account);
    }
    
    
    
    public Person() {
    }
    
    public Person(String nationalId, String mobileNumber, String email, String firstName,
                  String lastName, Date birthDate, Gender gender,
                   MilitaryStatus militaryStatus) {
        
    	this.nationalId = nationalId;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.militaryStatus = militaryStatus;    
    }
    
    
    public Person(String nationalId, String mobileNumber, String email, 
    		String firstName, String lastName, Date birthDate,
             Gender gender, MilitaryStatus militaryStatus, Set<Account> accounts) {
            
       this.nationalId = nationalId;
       this.mobileNumber = mobileNumber;
       this.email = email;
       this.firstName = firstName;
       this.lastName = lastName;
       this.birthDate = birthDate;
       this.gender = gender;
       this.militaryStatus = militaryStatus;
       this.accounts = accounts;
}
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    
    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    
    
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumbe(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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
    
    
    
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    
    
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    
    
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
    public MilitaryStatus getMilitaryStatus() {
        return militaryStatus;
    }

    public void setMilitaryStatus(MilitaryStatus militaryStatus) {
        this.militaryStatus = militaryStatus;
    } 
    
    
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
 



    @Override
    public String toString() {
        return "Person [id=" + id + ", mobileNumber=" + mobileNumber + ", firstName=" + firstName +
        		 ", lastName=" + lastName + ", email=" + email + ", gender=" + gender +
        		  ", militaryStatus=" + militaryStatus + ", birthDate=" + birthDate +"]";
    }

    
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
