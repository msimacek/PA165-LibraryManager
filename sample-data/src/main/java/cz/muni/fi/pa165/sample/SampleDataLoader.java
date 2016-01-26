package cz.muni.fi.pa165.sample;

import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Named;

import cz.muni.fi.pa165.dto.InputBookCollectionDTO;
import cz.muni.fi.pa165.dto.CreateBookDTO;
import cz.muni.fi.pa165.dto.CreateLoanDTO;
import cz.muni.fi.pa165.dto.InputMemberDTO;
import cz.muni.fi.pa165.facade.BookCollectionFacade;
import cz.muni.fi.pa165.facade.BookFacade;
import cz.muni.fi.pa165.facade.LoanFacade;
import cz.muni.fi.pa165.facade.MemberFacade;
import java.lang.reflect.Array;
import java.util.List;

@Named
public class SampleDataLoader {

    @Inject
    private BookCollectionFacade collectionFacade;

    @Inject
    private BookFacade bookFacade;

    @Inject
    private MemberFacade memberFacade;

    @Inject
    private LoanFacade loanFacade;

    public void createSampleData() {
        Long b1 = book("A Brief History of Time", "Stephen Hawking", 9780553380163L);
        Long b2 = book("Death by Black Hole: And Other Cosmic Quandaries", "Neil deGrasse Tyson", 9780393350388L);
        Long b3 = book("The Theory Of Everything ", "Stephen Hawking", 9788179925911L);
        Long b4 = book("The Elegant Universe: Superstrings, Hidden Dimensions, and the Quest for the Ultimate Theory", "Brian Greene", 9780393338102L);
        bookCollection("Cosmology", b1, b2, b3, b4);

        Long b5 = book("Tesla: Inventor of the Electrical Age", "W. Bernard Carlson", 9780691165615L);
        Long b6 = book("Steve Jobs", "Walter Isaacson", 9781501127625L);
        Long b7 = book("Elon Musk: Tesla, SpaceX, and the Quest for a Fantastic Future", "Ashlee Vance", 9780062301239L);
        Long b8 = book("Into the Wild", "Jon Krakauer", 9780385486804L);
        bookCollection("Biography", b5, b6, b7, b8);

        Long b9 = book("Between the World and Me", "Ta-Nehisi Coates", 9780812993547L);
        Long b10 = book("The Wright Brothers", "David McCullough", 9781476728742L);
        Long b11 = book("Dead Wake: The Last Crossing of the Lusitania", "Erik Larson", 9780307408860L);
        Long b12 = book("Humans of New York", "Brandon Stanton", 9781250038821L);
        Long b13 = book("Custer's Trials: A Life on the Frontier of a New America", "T.J. Stiles", 9780307592644L);
        Long b14 = book("Not in God's Name: Confronting Religious Violence", "Jonathan Sacks", 9780805243345L);
        bookCollection("History", b9, b10, b11, b12, b13, b14);

        Long b15 = book("A Knight of the Seven Kingdoms (A Song of Ice and Fire)", "George R. R. Martin", 9780345533487L);
        Long b16 = book("Six of Crows", "Leigh Bardugo", 9781627792127L);
        Long b17 = book("Seveneves: A Novel", "Neal Stephenson", 9780062190376L);
        Long b18 = book("The Martian", "Andy Weir", 9780553418026L);
        Long b19 = book("Doctor Who Mad Libs", "Price Stern Sloan", 9780843182460L);
        Long b20 = book("Leviathan Wakes", "James S.A. Corey", 9780316129084L);
        bookCollection("Science Fiction & Fantasy", b15, b16, b17, b18, b19, b20);

        Long b21 = book("The Nightingale", "Kristin Hannah", 9780312577223L);
        Long b22 = book("Fates and Furies: A Novel", "Lauren Groff", 9781594634475L);
        Long b23 = book("Beatlebone", "Kevin Barry", 9780385540292L);
        Long b24 = book("The Ice Storm: A Novel", "Rick Moody", 9780316706001L);
        Long b25 = book("All the Light We Cannot See", "Anthony Doerr", 9781476746586L);
        Long b26 = book("Cross Justice (Alex Cross)", "James Patterson", 9780316407045L);
        bookCollection("Literature & Fiction", b21, b22, b23, b24, b25, b26);

        Long b27 = book("The Power of Your Subconscious Mind", "Joseph Murphy", 9781604592016L);
        Long b28 = book("Mindset: The New Psychology of Success", "Carol Dweck", 9780345472328L);
        Long b29 = book("The Magic of Thinking Big", "David J. Schwartz", 9780671646783L);
        Long b30 = book("Why We Lie: The Evolutionary Roots of Deception and the Unconscious Mind", "David Livingstone Smith", 9780312310400L);
        bookCollection("Psychology & Counseling", b27, b28, b29, b30);

        Long b31 = book("The Bread Baker's Apprentice: Mastering the Art of Extraordinary Bread", "Peter Reinhart", 9781580082686L);
        Long b32 = book("Everyone Is Italian on Sunday", "Rachael Ray", 9781476766072L);
        bookCollection("Cookbooks, Food & Wine", b31, b32);

        Long m1 = member("Petr", "Cibula", "petr.cibula@email.cz", "cibula123");
        Long m2 = member("Honza", "Novotný", "honza.novotny@email.cz", "novotny123");
        Long m3 = member("Karel", "Mach", "karel.mach@email.cz", "mach123");
        Long m4 = member("Zdeněk", "Vyskočil", "zdenek.vyskocil@email.cz", "vyskocil23");
        Long m5 = member("František", "Babiš", "franta.babis@email.cz", "babis123");
        Long m6 = member("Jiří", "Mouka", "jiri.mouka@email.cz", "mouka123");
        Long m7 = member("Tereza", "Janáčková", "tereza.janackova@email.cz", "janackova123");
        Long m8 = member("Jana", "Mrachtilová", "jana.mrachtilova@email.cz", "mrachtilova123");

        Long m9 = member("Adam", "Všemocný", "adam.vsemocny@email.cz", "vsemocny123");
        memberFacade.makeAdmin(m9);

        loan(m1, b1);
        loan(m2, b2);
        loan(m3, b3);
        loan(m4, b4);
        loan(m5, b5);
        loan(m6, b6);
        loan(m7, b7);
        loan(m8, b8);
        loan(m1, b9);
        loan(m2, b10);
        loan(m3, b11);
        loan(m4, b12);
        loan(m5, b13);
    }

    private Long book(String name, String authorName, Long isbn) {
        CreateBookDTO dto = new CreateBookDTO();
        dto.setName(name);
        dto.setAuthorName(authorName);
        dto.setIsbn(isbn);
        return bookFacade.createBook(dto);
    }

    private Long bookCollection(String name, Long... ids) {
        InputBookCollectionDTO dto = new InputBookCollectionDTO();
        dto.setName(name);
        dto.setBookIds(Arrays.asList(ids));
        return collectionFacade.createBookCollection(dto);
    }

    private Long member(String givenName, String surname, String email, String password) {
        InputMemberDTO memberRegister = new InputMemberDTO();
        memberRegister.setGivenName(givenName);
        memberRegister.setSurname(surname);
        memberRegister.setEmail(email);
        memberRegister.setPassword(password);
        return memberFacade.registerMember(memberRegister);
    }

    private List<Long> loan(Long memberId, Long bookId) {
        CreateLoanDTO loan = new CreateLoanDTO();
        loan.setBookId(Arrays.asList(new Long[] {bookId}));
        loan.setMemberId(memberId);
        return loanFacade.createLoan(loan);
    }
}
