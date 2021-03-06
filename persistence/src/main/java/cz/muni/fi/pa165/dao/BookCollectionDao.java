package cz.muni.fi.pa165.dao;
import cz.muni.fi.pa165.entity.BookCollection;
import java.util.List;

/**
 * Data access object for BookCollection entity
 * 
 * @author xkubist
 */
public interface BookCollectionDao {
    /**
     * Creates the BookCollection in database.
     *
     * @param b BookCollection object to be persisted
     * */
    void create(BookCollection b);
    /**
     * delete the BookCollection from database
     * 
     * @param b BookCollection to be deleted
     */
    void delete(BookCollection b);
    /**
     * update the BookCollection from database
     * 
     * @param b BookCollection to be deleted
     */
    void update(BookCollection b);
    /**
     * return list of all BookCollection objects from database 
     * 
     * @return list of all BookCollection objects from database 
     */
    List<BookCollection> findAll();
    /**
     * return BookCollection object with target name 
     * 
     * @param name name to be searched
     * @return BookCollection object with target name 
     */
    BookCollection findByName(String name);
    /**
     * return BookCollection objects with target id 
     * 
     * @param id collection id
     * @return BookCollection objects with target id 
     */
    BookCollection findById(Long id);
    
}
