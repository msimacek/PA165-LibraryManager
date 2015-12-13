package cz.muni.fi.pa165.dto;

/**
 * Data Transfer object for registeration of Member entity
 * 
 * @author Juraj Tomko
 */
public class MemberRegisterDTO {

    private MemberDTO member = new MemberDTO(); 
    private String password;
    
    public MemberRegisterDTO() {
    }

    public MemberRegisterDTO(MemberDTO member, String password) {
        this.member = member;
        this.password = password;
    }

    public MemberDTO getMember() {
        return member;
    }

    public void setMember(MemberDTO member) {
        this.member = member;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    }
