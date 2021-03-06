package cz.muni.fi.pa165.dao.test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.pa165.dao.LoanDao;
import cz.muni.fi.pa165.entity.Book;
import cz.muni.fi.pa165.entity.Loan;
import cz.muni.fi.pa165.enums.BookState;
import cz.muni.fi.pa165.spring.LibrarySpringContext;

/**
 * Created by Juraj on 10/30/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LibrarySpringContext.class)
@Transactional
public class LoanDaoTest {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private LoanDao loanDao;

    private Book firstBook = new Book();
    private Book secondBook = new Book();
    private Loan firstLoan = new Loan();
    private Loan secondLoan = new Loan();
    private Loan thirdLoan = new Loan();

    private void createTestData() {
        firstBook.setName("Twilight");
        firstBook.setIsbn(1L);
        firstBook.setState(BookState.NEW);

        secondBook.setName("Fifty shades of gray");
        secondBook.setIsbn(1L);
        secondBook.setState(BookState.LIGHT_DAMAGE);

        em.persist(firstBook);
        em.persist(secondBook);
        em.flush();

        Calendar calendar = new GregorianCalendar(2015, 10, 27);
        Date date = calendar.getTime();
        firstLoan.setLoanDate(date);
        firstLoan.setBook(firstBook);
        firstLoan.setReturnBookState(BookState.NEW);
        firstLoan.setReturnDate(date);
        firstLoan.setReturned(true);

        secondLoan.setLoanDate(date);
        secondLoan.setBook(secondBook);
        secondLoan.setReturnBookState(BookState.LIGHT_DAMAGE);
        secondLoan.setReturnDate(date);
        secondLoan.setReturned(true);

        thirdLoan.setLoanDate(date);
        thirdLoan.setBook(firstBook);
        thirdLoan.setReturnBookState(BookState.HEAVY_DAMAGE);
        thirdLoan.setReturnDate(date);
        thirdLoan.setReturned(true);

        loanDao.create(firstLoan);
        loanDao.create(secondLoan);
        loanDao.create(thirdLoan);
    }

    @Test
    public void testCreate() {
        createTestData();
        assertNotNull(firstLoan.getId());
        assertNotNull(secondLoan.getId());
        assertNotNull(thirdLoan.getId());
    }

    @Test(expected = DataAccessException.class)
    public void testCreateNullDate() {
        Loan loan = new Loan();
        Calendar calendar = new GregorianCalendar(2015, 1, 1);
        Date date = calendar.getTime();
        loan.setReturnDate(date);
        loan.setReturnBookState(BookState.NEW);
        loanDao.create(loan);
    }

    @Test
    public void testUpdate() {
        createTestData();

        Calendar calendar = new GregorianCalendar(2015, 12, 24);
        Date newDate = calendar.getTime();

        firstLoan.setLoanDate(newDate);
        loanDao.update(firstLoan);

        Loan found = em.find(Loan.class, firstLoan.getId());
        assertEquals(found.getLoanDate(), firstLoan.getLoanDate());
    }

    @Test
    public void testDelete() {
        createTestData();
        loanDao.delete(firstLoan);
        Loan found = em.find(Loan.class, firstLoan.getId());
        assertNull(found);
    }

    @Test
    public void testFindById() {
        createTestData();
        Loan found = loanDao.findById(secondLoan.getId());
        assertNotNull(found);
        assertSame(found, secondLoan);
    }

    @Test
    public void testFindAll() {
        createTestData();
        List<Loan> loans = loanDao.findAll();
        assertEquals(loans.size(), 3);
    }

    @Test(expected = DataAccessException.class)
    public void testDeleteNonExistent() {
        Loan loan = new Loan();
        Calendar calendar = new GregorianCalendar(2015,1,1);
        Date date = calendar.getTime();
        loan.setLoanDate(date);
        loan.setReturnBookState(BookState.HEAVY_DAMAGE);
        loan.setReturnDate(date);
        loan.setBook(firstBook);
        loanDao.delete(loan);
    }
}
