package cz.muni.fi.pa165.sample;

import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Named;

import cz.muni.fi.pa165.dto.CreateBookCollectionDTO;
import cz.muni.fi.pa165.dto.CreateBookDTO;
import cz.muni.fi.pa165.dto.CreateLoanDTO;
import cz.muni.fi.pa165.dto.MemberDTO;
import cz.muni.fi.pa165.dto.MemberRegisterDTO;
import cz.muni.fi.pa165.facade.BookCollectionFacade;
import cz.muni.fi.pa165.facade.BookFacade;
import cz.muni.fi.pa165.facade.LoanFacade;
import cz.muni.fi.pa165.facade.MemberFacade;

@Named
public class SampleDataLoader {
    @Inject
    private BookCollectionFacade collectionFacade;

    @Inject
    private BookFacade bookFacade;

    @Inject
    MemberFacade memberFacade;
    
    @Inject
    LoanFacade loanFacade;

    public void createSampleData() {
        Long b1 = book("東方文花帖", "ZUN", 4758010374L);
        Long b2 = book("東方紫香花", "ZUN", 9780400101194L);
        Long b3 = book("東方求聞史紀", "ZUN", 4758010633L);
        bookCollection("Manga", b1, b2, b3);
        createMember();
    }

    private Long book(String name, String authorName, Long isbn) {
        CreateBookDTO dto = new CreateBookDTO();
        dto.setName(name);
        dto.setAuthorName(authorName);
        dto.setIsbn(isbn);
        return bookFacade.createBook(dto);
    }

    private Long bookCollection(String name, Long... ids) {
        CreateBookCollectionDTO dto = new CreateBookCollectionDTO();
        dto.setName(name);
        dto.setBookIds(Arrays.asList(ids));
        return collectionFacade.createBookCollection(dto);
    }

    private void createMember() {
        MemberDTO member = new MemberDTO();
        member.setEmail("email@email.com");
        member.setGivenName("peter");
        member.setSurname("cibula");

        MemberRegisterDTO memberRegister = new MemberRegisterDTO();
        memberRegister.setMember(member);
        memberRegister.setPassword("12345");       
        Long memberId = memberFacade.registerMember(memberRegister);
        createLoan(memberId);
    }
    
    private void createLoan(Long memberId){
        CreateLoanDTO loan = new CreateLoanDTO();
        loan.setBookId(1L);
        loan.setMemberId(memberId);
        loanFacade.createLoan(loan);
    }
}
