package cz.muni.fi.pa165.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.muni.fi.pa165.dto.InputMemberDTO;
import cz.muni.fi.pa165.dto.LoanDTO;
import cz.muni.fi.pa165.dto.MemberDTO;
import cz.muni.fi.pa165.exceptions.NotFoundException;
import cz.muni.fi.pa165.exceptions.WebSecurityException;
import cz.muni.fi.pa165.facade.MemberFacade;
import cz.muni.fi.pa165.security.MemberUserDetailsAdapter;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Inject
    MemberFacade facade;

    private void checkCanView(MemberUserDetailsAdapter currentUser, long id) {
        if (!(currentUser.getDto().getId() == id || currentUser.getDto().isAdmin())) {
            throw new WebSecurityException("Non-admin cannot view other members");
        }
    }

    private void checkCanSetAdmin(MemberUserDetailsAdapter currentUser, InputMemberDTO dto) {
        if (dto.isAdmin() && !currentUser.getDto().isAdmin()) {
            throw new WebSecurityException("Non-admin cannot set admin");
        }
    }

    @RequestMapping("/{id}")
    public String showMember(@AuthenticationPrincipal MemberUserDetailsAdapter currentUser, @PathVariable long id,
            Model model) {
        checkCanView(currentUser, id);
        MemberDTO dto = facade.findById(id);
        if (dto == null) {
            throw new NotFoundException();
        }
        List<LoanDTO> allLoans = facade.getAllLoans(dto.getId());
        List<LoanDTO> activeLoans = new ArrayList<>();
        List<LoanDTO> returnedLoans = new ArrayList<>();
        for (LoanDTO loan : allLoans) {
            if (loan.getReturned()) {
                returnedLoans.add(loan);
            } else {
                activeLoans.add(loan);
            }
        }
        model.addAttribute("member", dto);
        model.addAttribute("activeloans", activeLoans);
        model.addAttribute("returnedloans", returnedLoans);
        return "member/show";
    }

    @RequestMapping(path = "/create", method = RequestMethod.GET)
    public String createMemberView(Model model) {
        if (!model.containsAttribute("member")) {
            model.addAttribute("member", new InputMemberDTO());
        }
        model.addAttribute("action", "Create");
        return "member/create_or_update";
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String createMember(@AuthenticationPrincipal MemberUserDetailsAdapter currentUser,
            @Valid @ModelAttribute("member") InputMemberDTO dto, BindingResult result, Model model) {
        if (dto.getPassword() == null || dto.getPassword().length() <= 6 || dto.getPassword().length() > 60) {
            result.addError(new FieldError("member", "password", "Password must have between 6 and 50 characters."));
        }
        if (result.hasErrors()) {
            return createMemberView(model);
        }
        checkCanSetAdmin(currentUser, dto);

        Long id = facade.registerMember(dto);
        return "redirect:/member/" + id;
    }

    @RequestMapping(path = "/{id}/update", method = RequestMethod.GET)
    public String updateMemberView(@AuthenticationPrincipal MemberUserDetailsAdapter currentUser, @PathVariable long id,
            Model model) {
        checkCanView(currentUser, id);
        model.addAttribute("action", "Update");
        InputMemberDTO memberDTO = facade.findByIdForUpdate(id);
        model.addAttribute("member", memberDTO);
        return "member/create_or_update";
    }

    @RequestMapping(path = "/{id}/update", method = RequestMethod.POST)
    public String updateMember(@AuthenticationPrincipal MemberUserDetailsAdapter currentUser, @PathVariable long id,
            @Valid @ModelAttribute("member") InputMemberDTO dto, BindingResult result, Model model) {
        checkCanView(currentUser, id);
        if (result.hasErrors()) {
            return "member/create_or_update";
        }
        checkCanSetAdmin(currentUser, dto);
        facade.updateMember(id, dto);

        return "redirect:/member/" + id;
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String listView(Model model) {
        List<MemberDTO> members = facade.findAll();
        model.addAttribute("members", members);
        return "member/list";
    }
}
