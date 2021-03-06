package cz.muni.fi.pa165.facade.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.muni.fi.pa165.dto.BookCollectionDTO;
import cz.muni.fi.pa165.dto.BookDTO;
import cz.muni.fi.pa165.dto.InputBookCollectionDTO;
import cz.muni.fi.pa165.entity.Book;
import cz.muni.fi.pa165.entity.BookCollection;
import cz.muni.fi.pa165.enums.BookState;
import cz.muni.fi.pa165.facade.BookCollectionFacade;
import cz.muni.fi.pa165.service.BookCollectionService;
import cz.muni.fi.pa165.service.BookService;

/**
 * Tests for BookCollectionFacade
 *
 * @author Jakub Peschel
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FacadeTestConfiguration.class)
public class BookCollectionFacadeTest {

    @Inject
    private BookCollectionService bookCollectionServiceMock;

    @Inject
    private BookCollectionFacade facade;
    
    @Inject
    private BookService bookService;
    
    @Test
    public void testCreate() {
        ArgumentCaptor<BookCollection> captor = ArgumentCaptor.forClass(BookCollection.class);
        InputBookCollectionDTO collection = new InputBookCollectionDTO();
        String name = "Test";
        collection.setName(name);
        List<Long> bookIds = new ArrayList<>();
        bookIds.add(1L);
        collection.setBookIds(bookIds);
        List<Book> bookList = prepareBookColl();
        when(bookService.findById(1L)).thenReturn(bookList.get(0));
        facade.createBookCollection(collection);
        verify(bookCollectionServiceMock).create(captor.capture());
        BookCollection entity = captor.getValue();
        assertNull(entity.getId());
        assertSame(name, entity.getName()); 
        assertEquals(1, entity.getBooks().size());
        assertEquals(bookList.get(0), entity.getBooks().iterator().next());
    }

    private List<Book> prepareBookColl() {
        List<Book> bookList = new ArrayList<>();
        Book book1 = new Book();
        book1.setName("Test1");
        book1.setId(1L);
        book1.setIsbn(1L);
        book1.setState(BookState.NEW);
        bookList.add(book1);
        return bookList;
    }

    private BookCollection prepareBookCollection(Long id) {
        BookCollection resultCollection = new BookCollection();
        resultCollection.setId(id);
        resultCollection.setName("Test");
        return resultCollection;
    }

    @Test
    public void testFindById() {
        BookCollection col = prepareBookCollection(1L);
        when(bookCollectionServiceMock.findById(any(Long.class))).thenReturn(col);
        BookCollectionDTO colDTO = facade.findById(col.getId());
        assertEquals(col.getName(), colDTO.getName());
        assertEquals(col.getId(), colDTO.getId());
        assertEquals(col.getBooks(), colDTO.getBooks());
    }

    @Test
    public void testFindAll() {
        BookCollection col = prepareBookCollection(1L);
        BookCollection col2 = prepareBookCollection(2L);
        List<BookCollection> cols = new ArrayList<>();
        cols.add(col);
        cols.add(col2);
        when(bookCollectionServiceMock.findAll()).thenReturn(cols);
        List<BookCollectionDTO> colDTO = facade.findAll();
        assertEquals(2, colDTO.size());
    }

    @Test
    public void testDelete() {
        BookCollection bookCollection = prepareBookCollection(1L);
        when(bookCollectionServiceMock.findById(1L)).thenReturn(bookCollection);
        facade.delete(1L);
        verify(bookCollectionServiceMock).delete(bookCollection);
    }
    
    @Test
    public void testAddBookToCollection(){
        Book book = new Book();
        book.setId(1L);
        book.setName("Test");
        book.setState(BookState.NEW);
        book.setIsbn(1L);
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setName("Test");
        bookDTO.setState(BookState.NEW);
        bookDTO.setIsbn(1L);
        facade.addBookToCollection(1L, bookDTO);
        verify(bookCollectionServiceMock).addBookToCollection(
                any(Long.class), 
                eq(book)
        );
        
    }
    
    @Test
    public void testRemoveBookToCollection(){
        Book book = new Book();
        book.setId(1L);
        book.setName("Test");
        book.setState(BookState.NEW);
        book.setIsbn(1L);
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setName("Test");
        bookDTO.setState(BookState.NEW);
        bookDTO.setIsbn(1L);
        facade.removeBookFromCollection(1L, bookDTO);
        verify(bookCollectionServiceMock).removeBookFromCollection(
                any(Long.class), 
                eq(book)
        );
    }
}
