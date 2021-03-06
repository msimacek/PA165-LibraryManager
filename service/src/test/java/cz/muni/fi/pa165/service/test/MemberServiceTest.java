package cz.muni.fi.pa165.service.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import cz.muni.fi.pa165.dao.MemberDao;
import cz.muni.fi.pa165.entity.Book;
import cz.muni.fi.pa165.entity.Loan;
import cz.muni.fi.pa165.entity.Member;
import cz.muni.fi.pa165.enums.BookState;
import cz.muni.fi.pa165.exceptions.LibraryServiceException;
import cz.muni.fi.pa165.service.MemberService;
import cz.muni.fi.pa165.service.MemberServiceImpl;

/**
 * Tests for MemberService
 * 
 * @author xkubist
 */
public class MemberServiceTest {
    @Mock
    private MemberDao memberMock;
    
    @Mock
    private PasswordEncoder passwordMock;

    @InjectMocks
    MemberService service = new MemberServiceImpl();
    
    private Member member;

    @Before
    public void setUp() {
         MockitoAnnotations.initMocks(this);
        member = new Member();
        member.setGivenName("Blanka");
        member.setSurname("Protrhla");
        member.setEmail("BerUska15@pokec.sk");
        member.setIsAdmin(Boolean.TRUE);
        Date date = new Date(0);
        member.setRegistrationDate(date);
        when(passwordMock.encode("totoJeNajneprelomitelnejsieHesloNaSvete"))
            .thenReturn("fb9f91a6279185848d4d67fcf5e79d5dd8af8f0");
        when(passwordMock.encode("totoJeNajsieHesloNaSvete"))
            .thenReturn("f1d2d2f924e986ac86fdf7b36c94bcdf32beec1");
    }
    
    @Test
    public void testRegister() {
        service.registerMember(member, "totoJeNajneprelomitelnejsieHesloNaSvete");
        verify(memberMock).create(member);
        assertEquals("fb9f91a6279185848d4d67fcf5e79d5dd8af8f0", member.getPasswordHash());
    }
    
    @Test(expected = LibraryServiceException.class)
    public void testRegisterEmptyPassword() {
        service.registerMember(member, "");
    }
    
    @Test
    public void testFindById() {
        when(memberMock.findById(member.getId())).thenReturn(member);
        assertSame(member, service.findById(member.getId()));
    }

    @Test
    public void testFindAll() {
      
        List<Member> members = new ArrayList<>();
        members.add(member);
         when(memberMock.findAll()).thenReturn(members);
        assertEquals(members, service.findAll());
    }
    
    @Test
    public void testDelete() {
        service.deleteMember(member);
        verify(memberMock).delete(member);
    }
   
    @Test
    public void testGetAllLoansOfMember() {
        
        Book book = new Book();
        book.setName("Clean Code");
        book.setIsbn(1L);
        book.setState(BookState.NEW);
        
        Loan loan1=new Loan();
        loan1.setBook(book);
        loan1.setLoanDate(new Date(1));
        loan1.setReturnBookState(BookState.NEW);
        loan1.setReturnDate(new Date(3));
        loan1.setReturned(Boolean.TRUE);
        
        Loan loan2=new Loan();
        loan2.setBook(new Book());
        loan2.setLoanDate(new Date(10));
        loan2.setReturned(Boolean.FALSE);
        
        List<Loan> loans = new ArrayList<>();
        loans.add(loan1);
        loans.add(loan2);
        member.addLoan(loan1);
        member.addLoan(loan2);
        
        when(memberMock.findById(member.getId())).thenReturn(member);
        assertEquals(loans, service.getAllLoans(member));
    }

    @Test
    public void testCorrectAuthenticate() {
        member.setPasswordHash("fb9f91a6279185848d4d67fcf5e79d5dd8af8f0");
        assertTrue(service.authenticateMember(member, "totoJeNajneprelomitelnejsieHesloNaSvete"));
    }
    
    @Test
    public void testIncorrectAuthenticate() {
        member.setPasswordHash("fb9f91a6279185848d4d67fcf5e79d5dd8af8f0");
        assertFalse(service.authenticateMember(member, "totoJeNajsieHesloNaSvete"));
    }
}
