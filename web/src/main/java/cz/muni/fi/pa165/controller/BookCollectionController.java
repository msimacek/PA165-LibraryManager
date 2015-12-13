package cz.muni.fi.pa165.controller;

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

import cz.muni.fi.pa165.dto.BookCollectionDTO;
import cz.muni.fi.pa165.dto.CreateBookCollectionDTO;
import cz.muni.fi.pa165.dto.UpdateBookCollectionDTO;
import cz.muni.fi.pa165.facade.BookCollectionFacade;
import cz.muni.fi.pa165.facade.BookFacade;

@Controller
@RequestMapping("/collection")
public class BookCollectionController {

    @Inject
    private BookCollectionFacade collectionFacade;

    @Inject
    private BookFacade bookFacade;

    @RequestMapping("/list")
    public String listCollections(Model model) {
        model.addAttribute("collections", collectionFacade.findAll());
        return "collection/list";
    }

    @RequestMapping("/{id}")
    public String showCollection(@PathVariable long id, Model model) {
        BookCollectionDTO dto = collectionFacade.findById(id);
        if (dto == null)
            return "404";
        model.addAttribute("collection", dto);
        return "collection/show";
    }

    @RequestMapping(path = "/create", method = RequestMethod.GET)
    public String createCollectionView(Model model) {
        model.addAttribute("action", "Create");
        model.addAttribute("collection", new CreateBookCollectionDTO());
        model.addAttribute("allBooks", bookFacade.findAll());
        return "collection/create_or_update";
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String createCollection(@Valid @ModelAttribute CreateBookCollectionDTO dto, BindingResult result,
            Model model, UriComponentsBuilder uri) {
        if (result.hasErrors()) {
            return "collection/create_or_update";
        }
        Long id = collectionFacade.createBookCollection(dto);
        return "redirect:" + id;
    }

    @RequestMapping(path = "{id}/update", method = RequestMethod.GET)
    public String updateCollectionView(@PathVariable long id, Model model) {
        model.addAttribute("action", "Modify");
        model.addAttribute("collection", collectionFacade.findByIdForUpdate(id));
        model.addAttribute("allBooks", bookFacade.findAll());
        return "collection/create_or_update";
    }

    @RequestMapping(path = "{id}/update", method = RequestMethod.POST)
    public String updateCollection(@Valid @ModelAttribute UpdateBookCollectionDTO dto, BindingResult result,
            Model model, UriComponentsBuilder uri) {
        if (result.hasErrors()) {
            return "collection/create_or_update";
        }
        // TODO nonexistent id
        collectionFacade.updateBookCollection(dto);
        return "redirect:";
    }
}