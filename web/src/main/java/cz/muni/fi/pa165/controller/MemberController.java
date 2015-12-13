package cz.muni.fi.pa165.controller;

import cz.muni.fi.pa165.dto.LoanDTO;
import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import cz.muni.fi.pa165.dto.MemberDTO;
import cz.muni.fi.pa165.dto.MemberRegisterDTO;
import cz.muni.fi.pa165.facade.MemberFacade;
import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Inject
    MemberFacade facade;
    
    @RequestMapping("/{id}")
    public String showMember(@PathVariable long id, Model model) {
        MemberDTO dto = facade.findById(id);
        if (dto == null)
            return "404";
        List<LoanDTO> allLoans = facade.getAllLoans(dto.getId());
        System.out.println("Number of loans: " + allLoans.size());
        model.addAttribute("member", dto);
        model.addAttribute("loans", allLoans);
        return "member/show";
    }

    @RequestMapping(path = "/create", method = RequestMethod.GET)
    public String createMemberView(Model model) {
        model.addAttribute("createMember", new MemberRegisterDTO());
        return "member/create";
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String createMember(@Valid @ModelAttribute MemberRegisterDTO dto, BindingResult result,
            Model model, UriComponentsBuilder uri) {
        if (result.hasErrors()) {
            return "member/create";
        }
        Long id = facade.registerMember(dto);
        return "redirect:" + id;
    }
}