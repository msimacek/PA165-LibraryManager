package cz.muni.fi.pa165.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Inject
    private MemberFacade facade;

    @Inject
    private Validator validator;

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
            @Valid @ModelAttribute("member") InputMemberDTO dto, BindingResult result, Model model,
            RedirectAttributes redirectAttrs, UriComponentsBuilder uriBuilder) {
        if (result.hasErrors()) {
            return createMemberView(model);
        }
        checkCanSetAdmin(currentUser, dto);

        Long id;
        try {
            id = facade.registerMember(dto);
        } catch (DataIntegrityViolationException e) {
            redirectAttrs.addFlashAttribute("alert_warning", "Member with given email address already exists.");
            return "redirect:" + uriBuilder.path("/member/list").toUriString();
        }
        redirectAttrs.addFlashAttribute("alert_success", "Member with id = " + id + " was successfuly created.");
        return "redirect:" + uriBuilder.path("/member/{id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(path = "/{id}/update", method = RequestMethod.GET)
    public String updateMemberView(@AuthenticationPrincipal MemberUserDetailsAdapter currentUser, @PathVariable long id,
            Model model) {
        checkCanView(currentUser, id);
        model.addAttribute("action", "Update");
        if (!model.containsAttribute("member")) {
            InputMemberDTO memberDTO = facade.findByIdForUpdate(id);
            model.addAttribute("member", memberDTO);
        }
        return "member/create_or_update";
    }

    @RequestMapping(path = "/{id}/update", method = RequestMethod.POST)
    public String updateMember(@AuthenticationPrincipal MemberUserDetailsAdapter currentUser, @PathVariable long id,
            @ModelAttribute("member") InputMemberDTO dto, BindingResult result, Model model,
            RedirectAttributes redirectAttrs, UriComponentsBuilder uriBuilder) {
        checkCanView(currentUser, id);

        // placeholder to pass validation if we don't alter the password
        final String KEEP_PASSWORD = "__keep_existing__";
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            dto.setPassword(KEEP_PASSWORD);
        }
        validator.validate(dto, result);
        if (result.hasErrors()) {
            return updateMemberView(currentUser, id, model);
        }
        // intentionally comparing by identity
        if (dto.getPassword() == KEEP_PASSWORD) {
            dto.setPassword(null);
        }

        checkCanSetAdmin(currentUser, dto);
        try {
            facade.updateMember(id, dto);
        } catch (DataIntegrityViolationException e) {
            redirectAttrs.addFlashAttribute("alert_warning", "Member with given email address already exists.");
            return "redirect:" + uriBuilder.path("/member/{id}/update").buildAndExpand(id).encode().toUriString();
        }

        redirectAttrs.addFlashAttribute("alert_success", "Member with id = " + id + " was successfuly updated");
        return "redirect:" + uriBuilder.path("/member/{id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String listView(Model model) {
        List<MemberDTO> members = facade.findAll();
        model.addAttribute("members", members);
        return "member/list";
    }

    @RequestMapping(path = "/{id}/delete")
    public String deleteMember(@AuthenticationPrincipal MemberUserDetailsAdapter currentUser, @PathVariable long id,
            Model model) {
        checkCanView(currentUser, id);
        facade.deleteMember(id);
        return "redirect:/";
    }
}
