package cz.muni.fi.pa165.facade;

import java.util.List;

import cz.muni.fi.pa165.dto.CreateLoanDTO;
import cz.muni.fi.pa165.dto.LoanDTO;
import cz.muni.fi.pa165.enums.BookState;
import cz.muni.fi.pa165.exceptions.LibraryServiceException;

/**
 * Facade layer for Loan entity
 * 
 * @author Jakub Peschel
 */
public interface LoanFacade {
    /**
     * Persists loan into database
     *
     * @param loan loan object to be created
     * @throws LibraryServiceException when list of book ids is empty
     * @return new loans id
     */
    List <Long> createLoan(CreateLoanDTO loan) throws LibraryServiceException;

    /**
     * Returns loan with given id or null
     *
     * @param id loan id
     * @return loan or null
     */
    LoanDTO findById(Long id);

    /**
     * Obtains all persisted loans
     *
     * @return list of all loans
     */
    List<LoanDTO> findAll();

    /**
     * Removes loan from the database
     *
     * @param loanId loan id
     */
    void delete(Long loanId);

    
    /**
     * Return selected loan
     * 
     * @param id id of loan
     * @param returnState State of book when returned
     */
    void returnLoan(Long id, BookState returnState);
}
