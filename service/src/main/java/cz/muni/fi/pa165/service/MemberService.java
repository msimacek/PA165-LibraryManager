package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Loan;
import cz.muni.fi.pa165.entity.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Service layer for Member entity
 *
 * Created by Juraj Tomko on 11/23/2015.
 */
@Service
public interface MemberService {

    /**
     * Returns member with given id or null
     *
     * @param id of requested member
     * @return member or null
     */
    Member findById(Long id);

    /**
     * Returns all persisted members
     *
     * @return list of all members
     */
    List<Member> findAll();

    /**
     * Removes member from database
     *
     * @param member entity to be removed
     */
    void delete(Member member);

    /**
     * Adds loan to member
     *
     * @param member
     * @param loan
     */
    void addLoanToMember(Member member, Loan loan);

    /**
     * Returns all loans of member
     *
     * @param member
     * @return list of all loans possessed by member or null
     */
    Set<Loan> getAllLoansOfMember(Member member);

    /**
     * Authenticates member if password matches the records
     *
     * @param member to authenticate
     * @param unhashedPassword hashed password to be matched
     * @return boolean
     */
    boolean authenticate(Member member, String unhashedPassword);

    /**
     * Checks if member is admin
     * @param member to check
     * @return boolean
     */
    boolean isAdmin(Member member);

    /**
     * Registers member
     * @param member to register
     * @param unhashedPassword
     */
    void register(Member member, String unhashedPassword);
}