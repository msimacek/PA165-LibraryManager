package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.*;

import java.util.List;

/**
 * Facade layer for Member entity
 * 
 * @author Juraj Tomko
 */

public interface MemberFacade {

    /**
     * Finds member by id
     * @param id member id
     * @return member dto or null
     */
    MemberDTO findById(Long id);

    /**
     * Returns all members
     * @return list of members
     */
    List<MemberDTO> findAll();

    /**
     * Finds members by name
     * @param name of member
     * @return list of members
     */
    List<MemberDTO> findByName(String name);

    /**
     * Removes member
     * @param id member id
     */
    void deleteMember(Long id);

    /**
     * Returns all loans made by member
     * @param id member id
     * @return list of loans
     */
    List<LoanDTO> getAllLoans(Long id);

    /**
     * Makes authentization of member
     * @param memberAuth DTO object containing authentication data
     * @return true if authorization passed, else false
     */
    boolean authenticateMember(MemberAuthenticateDTO memberAuth);

    /**
     * Registers new member
     * @param memberReg DTO object containing registration data
     * @return id of new member
     */
    Long registerMember(MemberRegisterDTO memberReg);

    /**
     * Checks if member has admin permissions
     * @param id member id
     * @return boolean
     */
    boolean isAdmin(Long id);
}
