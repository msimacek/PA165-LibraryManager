package cz.muni.fi.pa165.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cz.muni.fi.pa165.dao.MemberDao;
import cz.muni.fi.pa165.entity.Loan;
import cz.muni.fi.pa165.entity.Member;
import cz.muni.fi.pa165.exceptions.LibraryServiceException;

/**
 * @author Juraj Tomko on 11/23/2015.
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Inject
    private PasswordEncoder encoder;

    @Inject
    private MemberDao memberDao;

    @Override
    public Member findById(Long id) {
        return memberDao.findById(id);
    }

    @Override
    public List<Member> findAll() {
        return memberDao.findAll();
    }

    @Override
    public List<Member> findByName(String name) {
        return memberDao.findByName(name);
    }

    @Override
    public Member findByEmail(String email) {
        List<Member> result = memberDao.findByEmail(email);
        if (!result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void deleteMember(Member member) {
        memberDao.delete(member);
    }

    @Override
    public List<Loan> getAllLoans(Member member) {
        Long id = member.getId();
        return memberDao.findById(id).getLoans();
    }

    @Override
    public boolean authenticateMember(Member member, String unencryptedPassword) {
        if (member == null) {
            return false;
        }
        return member.getPasswordHash().equals(encoder.encode(unencryptedPassword));
    }

    @Override
    public boolean isAdmin(Member member) {
        return member.isAdmin();
    }

    @Override
    public void registerMember(Member member, String password) {
        if (password.isEmpty()) {
            throw new LibraryServiceException("Password may not be empty");
        }
        member.setRegistrationDate(new Date());
        member.setPasswordHash(encoder.encode(password));
        memberDao.create(member);
    }

    @Override
    public void makeAdmin(Member member) {
        if (member == null) {
            throw new LibraryServiceException("Member doesn't exist");
        }
        member.setIsAdmin(true);
        memberDao.update(member);
    }

    @Override
    public void update(Member member, String newPasswd) {
        if (newPasswd != null) {
            member.setPasswordHash(encoder.encode(newPasswd));
        }
        memberDao.update(member);
    }
}
