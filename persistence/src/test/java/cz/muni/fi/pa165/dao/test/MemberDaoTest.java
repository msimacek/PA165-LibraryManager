package cz.muni.fi.pa165.dao.test;

import cz.muni.fi.pa165.dao.MemberDao;
import cz.muni.fi.pa165.entity.Member;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import cz.muni.fi.pa165.spring.LibrarySpringContext;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author xkubist
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LibrarySpringContext.class)
@Transactional
public class MemberDaoTest {
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private MemberDao memberDao;
    
    private Member member1;
    private Member member2;
    @Before
    public void setUp() {
        member1 = new Member();
        member1.setGivenName("Blanka");
        member1.setSurname("Protrhla");
        member1.setEmail("BerUska15@pokec.sk");
        member1.setPasswordHash("hash");
        Date date = new Date(0);
        member1.setRegistrationDate(date);
        member2 = new Member();
        member2.setGivenName("Petr");
        member2.setSurname("Soustal");
        member2.setEmail("soustal@gmail.com");
        member2.setPasswordHash("hash");
        date = new Date(2);
        member2.setRegistrationDate(date);
        em.persist(member1);
        em.persist(member2);
        em.flush();
        memberDao.create(member1);
        memberDao.create(member2);
        
    }
    
    @Test
    @Transactional
    public void testCreate() {
        assertNotNull(member1.getId());
        assertNotNull(member2.getId());
    }
    
    
    @Test
    @Transactional
    public void testUpdate() {
        member1.setGivenName("Janka");
        memberDao.update(member1);
        assertEquals("Janka", em.find(Member.class, member1.getId())
                .getGivenName());
    }
    
    
    @Test
    public void testFindById() {
        assertSame(member1, memberDao.findById(member1.getId()));
        assertSame(member2, memberDao.findById(member2.getId()));
        assertNull(memberDao.findById(Long.MAX_VALUE));
    }
    @Test
    public void testFindAll() {
      
        Set<Member> members = new HashSet<>();
        members.add(member1);
        members.add(member2);
        Set<Member> result = new HashSet<>(memberDao.findAll());
        assertEquals(result, members);
    }
    
    @Test
    public void testDelete() {
        memberDao.delete(member1);
        assertNull(em.find(Member.class, member1.getId()));
    }

    @Test(expected = DataAccessException.class)
    public void testDeleteNonexistent() {
        Member myMember = new Member();
        memberDao.delete(myMember);
    }
    
}
