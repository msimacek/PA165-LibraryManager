package cz.muni.fi.pa165.facade.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.muni.fi.pa165.dto.BookDTO;
import cz.muni.fi.pa165.dto.CreateLoanDTO;
import cz.muni.fi.pa165.dto.LoanDTO;
import cz.muni.fi.pa165.dto.MemberDTO;
import cz.muni.fi.pa165.entity.Book;
import cz.muni.fi.pa165.entity.Loan;
import cz.muni.fi.pa165.entity.Member;
import cz.muni.fi.pa165.enums.BookState;
import cz.muni.fi.pa165.facade.LoanFacade;
import cz.muni.fi.pa165.service.BookService;
import cz.muni.fi.pa165.service.LoanService;
import cz.muni.fi.pa165.service.MemberService;
import java.util.Arrays;


/**
 * Tests for LoanFacade
 * 
 * @author Michael Šimáček
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FacadeTestConfiguration.class)
public class LoanFacadeTest {
    // mock injected from FacadeTestConfiguration
    @Inject
    private LoanService loanServiceMock;
    @Inject
    private MemberService memberServiceMock;
    @Inject
    private BookService bookServiceMock;

    @Inject
    private LoanFacade facade;

    @Test
    public void testCreate() {
        ArgumentCaptor<Loan> captor = ArgumentCaptor.forClass(Loan.class);
        Member member = new Member();
        member.setId(1L);
        Book book = new Book();
        book.setId(1L);
        when(memberServiceMock.findById(1L)).thenReturn(member);
        when(bookServiceMock.findById(1L)).thenReturn(book);
        CreateLoanDTO loan = new CreateLoanDTO();
        loan.setBookId(new ArrayList(Arrays.asList(1L)));
        loan.setMemberId(1L);
        facade.createLoan(loan);
        verify(loanServiceMock).create(captor.capture());
        Loan entity = captor.getValue();
        assertNull(entity.getId());
        assertSame(member, entity.getMember());
        assertSame(book, entity.getBook());
    }

    @Test
    public void testReturnLoan() {
        facade.returnLoan(1L, BookState.HEAVY_DAMAGE);
        verify(loanServiceMock).returnLoan(1L, BookState.HEAVY_DAMAGE);
    }

    private Loan prepareLoan(Long id) {
        Date d = new Date(0);
        Book book = new Book();
        book.setId(id + 1);
        Member member = new Member();
        member.setId(id + 2);
        Loan loan = new Loan();
        loan.setId(id);
        loan.setBook(book);
        loan.setMember(member);
        loan.setReturnBookState(BookState.HEAVY_DAMAGE);
        loan.setLoanDate(d);
        loan.setReturnDate(d);
        return loan;
    }

    private void assertLoanDTO(long expectedId, LoanDTO dto) {
        Date d = new Date(0);
        assertEquals(Long.valueOf(expectedId), dto.getId());
        BookDTO bookDto = dto.getBook();
        assertNotNull(bookDto);
        assertEquals(Long.valueOf(expectedId + 1), bookDto.getId());
        MemberDTO memberDto = dto.getMember();
        assertNotNull(memberDto);
        assertEquals(Long.valueOf(expectedId + 2), memberDto.getId());
        assertEquals(BookState.HEAVY_DAMAGE, dto.getReturnBookState());
        assertEquals(d, dto.getLoanDate());
        assertEquals(d, dto.getReturnDate());
    }

    @Test
    public void testFindById() {
        Loan loan = prepareLoan(1L);
        when(loanServiceMock.findById(1L)).thenReturn(loan);
        LoanDTO dto = facade.findById(1L);
        assertLoanDTO(1L, dto);
    }

    @Test
    public void testFindAll() {
        Loan loan1 = prepareLoan(1L);
        Loan loan2 = prepareLoan(100L);
        List<Loan> allLoans = new ArrayList<>();
        allLoans.add(loan1);
        allLoans.add(loan2);
        when(loanServiceMock.findAll()).thenReturn(allLoans);
        List<LoanDTO> dtos = facade.findAll();
        assertEquals(2, dtos.size());
        assertLoanDTO(1L, dtos.get(0));
        assertLoanDTO(100L, dtos.get(1));
    }

    @Test
    public void testDelete() {
        Loan loan = prepareLoan(1L);
        when(loanServiceMock.findById(1L)).thenReturn(loan);
        facade.delete(1L);
        verify(loanServiceMock).delete(loan);
    }
}
