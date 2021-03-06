/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.controller;

import cz.muni.fi.pa165.dto.BookDTO;
import cz.muni.fi.pa165.dto.CreateBookDTO;
import cz.muni.fi.pa165.enums.BookState;
import cz.muni.fi.pa165.exceptions.NotFoundException;
import cz.muni.fi.pa165.facade.BookFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * @author xkubist
 */

@Controller
@RequestMapping("/books")
public class BookController {
    final static Logger logger = LoggerFactory.getLogger(BookController.class);

    @Inject
    private BookFacade bookFacade;

    @RequestMapping(value = "/list")
    public String getAll(Model model) {

        logger.debug("rest getBooks");
        model.addAttribute("books", bookFacade.findAll());
        return "book/show_all";
    }

    @RequestMapping(value = "/{id}")
    public String getBook(@PathVariable long id, Model model) throws Exception {

        logger.debug("rest getBook({})", id);

        BookDTO book = bookFacade.findById(id);
        if (book == null) {
            throw new NotFoundException();
        }
        model.addAttribute("book", book);
        return "book/show_book";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createBook(@Valid @ModelAttribute("createBook") CreateBookDTO dto, BindingResult result,
                             Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAtrrs) {
        if (result.hasErrors()) {
            redirectAtrrs.addFlashAttribute("alert_warning", "Book couldn't be created. Invalid parameters.");
            return "redirect:" + uriBuilder.path("books/create").toUriString();
        }
        Long id = bookFacade.createBook(dto);
        redirectAtrrs.addFlashAttribute("alert_success", "Book was successfuly created");
        return "redirect:" + uriBuilder.path("/books/{id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createBook(Model model) {
        model.addAttribute("createBook", new CreateBookDTO());
        return "book/create_book";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String changeBookState(@PathVariable long id, String state,
                                  Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttrs) {
        bookFacade.setState(id, BookState.valueOf(state));
        redirectAttrs.addFlashAttribute("alert_success", "Book state was successfuly updated.");
        return "redirect:" + uriBuilder.path("/books/{id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String changeState(@PathVariable long id, Model model) {
        BookDTO book = bookFacade.findById(id);

        if (book == null) {
            throw new NotFoundException();
        }
        logger.debug("changeState({})", id);
        logger.debug("changeState(book={})", book);

        model.addAttribute("book", book);
        model.addAttribute("state", book.getState());
        return "book/change_state";

    }
    
    @RequestMapping(path = "delete/{id}", method = RequestMethod.POST)
    public String deleteCollection(@PathVariable long id) {
        bookFacade.delete(id);
        return "redirect:/books/list";
    }
}
