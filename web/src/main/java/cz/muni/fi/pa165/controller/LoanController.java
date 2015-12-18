package cz.muni.fi.pa165.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import cz.muni.fi.pa165.dto.BookDTO;
import cz.muni.fi.pa165.dto.CreateLoanDTO;
import cz.muni.fi.pa165.dto.LoanDTO;
import cz.muni.fi.pa165.dto.MemberDTO;
import cz.muni.fi.pa165.enums.BookState;
import cz.muni.fi.pa165.exceptions.LibraryServiceException;
import cz.muni.fi.pa165.exceptions.NotFoundException;
import cz.muni.fi.pa165.facade.BookFacade;
import cz.muni.fi.pa165.facade.LoanFacade;
import cz.muni.fi.pa165.facade.MemberFacade;

/**
 * Created by Juraj Tomko on 12/10/2015.
 */

@Controller
@RequestMapping("/loans")
public class LoanController {

    @Inject
    LoanFacade loanFacade;

    @Inject
    MemberFacade memberFacade;

    @Inject
    BookFacade bookFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listLoans(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        MemberDTO member = memberFacade.findByEmail(username);

        List<LoanDTO> loans;
        if (member.isAdmin()) {
            loans = loanFacade.findAll();
        } else {
            loans = memberFacade.getAllLoans(member.getId());
        }

        model.addAttribute("member", member);
        model.addAttribute("loans", loans);
        return "loan/list";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newLoan(@RequestParam(required = false) Long bookId, @RequestParam(required = false) Long memberId,
            Model model) {
        if (bookId != null) {
            BookDTO book = bookFacade.findById(bookId);
            model.addAttribute("book", book);
        }
        if (memberId != null) {
            MemberDTO member = memberFacade.findById(memberId);
            model.addAttribute("member", member);
        }

        model.addAttribute("createLoan", new CreateLoanDTO());
        return "loan/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createLoan(@ModelAttribute("createLoan") CreateLoanDTO createLoan, Model model,
            RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        if (createLoan.getMemberId() == null || createLoan.getBookId() == null) {
            redirectAttributes.addFlashAttribute("alert_warning", "Member or book has not been chosen");
            return "redirect:" + uriBuilder.path("/loans/create").toUriString();
        }

        loanFacade.createLoan(createLoan);
        redirectAttributes.addFlashAttribute("alert_success", "New loan was created");
        return "redirect:" + uriBuilder.path("/loans/list").toUriString();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteLoan(@PathVariable("id") Long id, RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriBuilder, @RequestParam String redir, HttpServletRequest request) {
        try {
            loanFacade.delete(id);
        } catch (LibraryServiceException e) {
            throw new NotFoundException("Loan with id = " + id + " doesn't exist");
        }
        redirectAttributes.addFlashAttribute("alert_success", "Loan was successfuly deleted");
        if (redir != null) {
            redir = redir.substring(request.getContextPath().length());
            return "redirect:" + redir;
        }
        return "redirect:/list";
    }

    @RequestMapping(value = "/return/{id}", method = RequestMethod.POST)
    public String returnLoan(@PathVariable Long id, @RequestParam BookState state, Model model,
            RedirectAttributes redirectAttrs, @RequestParam String redir, HttpServletRequest request) {
        loanFacade.returnLoan(id, state);
        redirectAttrs.addFlashAttribute("alert_success", "Loan was successfuly returned");
        if (redir != null) {
            // there's a trailing comma, dunno why
            redir = redir.substring(request.getContextPath().length(), redir.length() - 1);
            return "redirect:" + redir;
        }
        return "redirect:/list";
    }

    @RequestMapping(value = "/return/{id}", method = RequestMethod.GET)
    public String returnLoanView(@PathVariable Long id, Model model, RedirectAttributes redirectAtts,
            UriComponentsBuilder uriBuilder) {
        LoanDTO loan = loanFacade.findById(id);
        if (loan == null) {
            throw new NotFoundException("Loan with id = " + id + " doesn't exist");
        }
        model.addAttribute("loan", loan);
        return "loan/book_return_state";
    }

    @RequestMapping(value = "/find_book", method = RequestMethod.GET)
    public String findBook(@RequestParam String book, Model model) {
        List<BookDTO> allBooks = bookFacade.findAll();

        String regex = "[0-9]+";
        List<BookDTO> foundBooks = new ArrayList<>();
        for (BookDTO item : allBooks) {
            if (item.getName().toLowerCase().contains(book.toLowerCase())
                    || item.getAuthorName().toLowerCase().contains(book.toLowerCase())
                    || book.toLowerCase().contains(item.getName().toLowerCase())
                    || book.toLowerCase().contains(item.getAuthorName().toLowerCase())) {
                foundBooks.add(item);
            }
            if (book.matches(regex)) {
                if (item.getIsbn().equals(Long.valueOf(book))) {
                    foundBooks.add(item);
                }
            }
        }

        model.addAttribute("books", foundBooks);
        return "loan/show_book_results";
    }

    @RequestMapping(value = "/find_member", method = RequestMethod.GET)
    public String findMember(@RequestParam String member, Model model) {
        List<MemberDTO> allMembers = memberFacade.findAll();
        System.out.println("member = " + member);

        List<MemberDTO> foundMembers = new ArrayList<>();
        for (MemberDTO item : allMembers) {
            if (member.toLowerCase().contains(item.getSurname().toLowerCase())
                    || member.toLowerCase().contains(item.getGivenName().toLowerCase())
                    || item.getSurname().toLowerCase().contains(member.toLowerCase())
                    || item.getGivenName().toLowerCase().contains(member.toLowerCase())
                    || member.toLowerCase().equals(item.getEmail().toLowerCase())) {
                foundMembers.add(item);
            }
        }

        model.addAttribute("members", foundMembers);
        return "loan/show_member_results";
    }
}
