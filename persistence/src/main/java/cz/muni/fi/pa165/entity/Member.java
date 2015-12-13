package cz.muni.fi.pa165.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity representing single member of the library
 * 
 * @author Juraj Tomko
 */
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String givenName;

    private String surname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date registrationDate;
    
    @Column(nullable = false)
    private String passwordHash;
    
    @OneToMany
    private List<Loan> loans = new ArrayList<>();

    @Column(nullable = false)
    private boolean isAdmin = false;

    public Member() {
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public List<Loan> getLoans() {
        return Collections.unmodifiableList(loans);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public int hashCode() {
        final int prime = 29;
        int result = 1;
        result = prime * result + (getGivenName() == null ? 0 : getGivenName().hashCode());
        result = prime * result + (getSurname() == null ? 0 : getSurname().hashCode());
        result = prime * result + (getEmail() == null ? 0 : getEmail().hashCode());
        result = prime * result + (getRegistrationDate() == null ? 0 : getRegistrationDate().hashCode());
        result = prime * result + (getPasswordHash() == null ? 0 : getPasswordHash().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Member)) {
            return false;
        }
        Member other = (Member) obj;
        if (getGivenName() == null) {
            if (other.getGivenName() != null) {
                return false;
            }
        } else if (!getGivenName().equals(other.getGivenName())) {
            return false;
        }
        if (getSurname() == null) {
            if (other.getSurname() != null) {
                return false;
            }
        } else if (!getSurname().equals(other.getSurname())) {
            return false;
        }
        if (getEmail() == null) {
            if (other.getEmail() != null) {
                return false;
            }
        } else if (!getEmail().equals(other.getEmail())) {
            return false;
        }
        if (getRegistrationDate() == null) {
            if (other.getRegistrationDate() != null) {
                return false;
            }
        } else if (!getRegistrationDate().equals(other.getRegistrationDate())) {
            return false;
        }
        return true;
    }
}
