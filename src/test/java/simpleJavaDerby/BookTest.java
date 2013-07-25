package simpleJavaDerby;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simpleJavaDerby.entity.Book;
import org.junit.Assert;

/**
 *
 * @author upkar
 */
public class BookTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static EntityTransaction tx;

    @BeforeClass
    public static void initEntityManger() throws Exception {
        emf = Persistence.createEntityManagerFactory("chapter02PUTest");
        em = emf.createEntityManager();
    }

    @AfterClass
    public static void closeEntityManager() throws SQLException {
        em.close();
        emf.close();
    }

    @Before
    public void initTransaction() {
        tx = em.getTransaction();
    }

    @Test
    public void shouldCreateABook() throws Exception {
        // Creates an instance of book
        Book book = new Book();
        final String bookName = "The Hitchhiker's Guide to the Galaxy";
        book.setTitle(bookName);
        book.setPrice(12.5F);
        book.setDescription("Science fiction comedy book");
        book.setIsbn("1-84023-742-2");
        book.setNbOfPages(354);
        book.setIllustrations(false);
        // Persists the book to the database
        tx.begin();
        em.persist(book);
        tx.commit();

        Assert.assertNotNull("ID should not be null", book.getId());
        // Retrieves all the books from the database
        List<Book> books = em.createNamedQuery("findAllBooks").getResultList();
        Assert.assertEquals(1, books.size());
        Assert.assertEquals(bookName, books.get(0).getTitle());
    }
}
