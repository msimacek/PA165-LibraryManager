package cz.muni.fi.pa165.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Juraj Tomko on 11/23/2015.
 */
public class MemberDTO {

    private Long id;
    private String givenName;
    private String surname;
    private String email;
    private Date registrationDate;
    private Set<LoanDTO> loans = new HashSet<>();

    public MemberDTO() {
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

    public Set<LoanDTO> getLoans() {
        return loans;
    }

    public void setLoans(Set<LoanDTO> loans) {
        this.loans = loans;
    }

    public void addLoan(LoanDTO loan) {
        loans.add(loan);
    }

    @Override
    public int hashCode() {
        final int prime = 29;
        int result = 1;
        result = prime * result + (getGivenName() == null ? 0 : getGivenName().hashCode());
        result = prime * result + (getSurname() == null ? 0 : getSurname().hashCode());
        result = prime * result + (getEmail() == null ? 0 : getEmail().hashCode());
        result = prime * result + (getRegistrationDate() == null ? 0 : getRegistrationDate().hashCode());
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
        if (!(obj instanceof MemberDTO)) {
            return false;
        }
        MemberDTO other = (MemberDTO) obj;
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
