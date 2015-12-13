package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.MemberDao;
import cz.muni.fi.pa165.entity.Loan;
import cz.muni.fi.pa165.entity.Member;
import cz.muni.fi.pa165.exceptions.LibraryServiceException;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
 * @author Juraj Tomko on 11/23/2015.
 */
@Service
public class MemberServiceImpl implements MemberService {

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
        return member.getPasswordHash().equals(makeSha1Hash(unencryptedPassword));
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
        member.setPasswordHash(makeSha1Hash(password));
        memberDao.create(member);
    }

    private String makeSha1Hash(String password) {
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            return new BigInteger(1, crypt.digest()).toString(16);
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
