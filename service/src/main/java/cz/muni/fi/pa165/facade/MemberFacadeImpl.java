package cz.muni.fi.pa165.facade;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.pa165.dto.LoanDTO;
import cz.muni.fi.pa165.dto.MemberAuthenticateDTO;
import cz.muni.fi.pa165.dto.MemberDTO;
import cz.muni.fi.pa165.dto.InputMemberDTO;
import cz.muni.fi.pa165.entity.Member;
import cz.muni.fi.pa165.service.MemberService;

/**
 * @author Juraj Tomko
 */
@Service
@Transactional
public class MemberFacadeImpl implements MemberFacade {

    @Inject
    private ListMapper mapper;

    @Inject
    private MemberService service;

    @Override
    public MemberDTO findById(Long id) {
        return mapper.map(service.findById(id), MemberDTO.class);
    }

    @Override
    public List<MemberDTO> findAll() {
        return mapper.map(service.findAll(), MemberDTO.class);
    }

    @Override
    public MemberDTO findByEmail(String email) {
        return mapper.map(service.findByEmail(email), MemberDTO.class);
    }

    @Override
    public List<MemberDTO> findByName(String name) {
        return mapper.map(service.findByName(name), MemberDTO.class);
    }

    @Override
    public void deleteMember(Long id) {
        service.deleteMember(service.findById(id));
    }

    @Override
    public List<LoanDTO> getAllLoans(Long id) {
        Member member = service.findById(id);
        return mapper.map(service.getAllLoans(member), LoanDTO.class);
    }

    @Override
    public boolean authenticateMember(MemberAuthenticateDTO memberAuth) {
        return service.authenticateMember(service.findByEmail(memberAuth.getMemberEmail()), memberAuth.getPassword());
    }

    @Override
    public Long registerMember(InputMemberDTO memberReg) {
        Member member = mapper.map(memberReg, Member.class);
        service.registerMember(member, memberReg.getPassword());
        return member.getId();
    }

    @Override
    public boolean isAdmin(Long id) {
        return service.findById(id).isAdmin();
    }

    @Override
    public void makeAdmin(Long id) {
        service.makeAdmin(service.findById(id));
    }

    @Override
    public InputMemberDTO findByIdForUpdate(Long id) {
        Member entity = service.findById(id);
        InputMemberDTO dto = mapper.map(entity, InputMemberDTO.class);
        return dto;
    }

    @Override
    public void updateMember(Long id, InputMemberDTO memberToUpdate) {
        Member member = service.findById(id);
        mapper.map(memberToUpdate, member);
        service.update(member, memberToUpdate.getPassword());
    }
}
