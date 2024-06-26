package com.example.manageskill.service;

import com.example.manageskill.model.MemberSkill;
import com.example.manageskill.model.Skill;
import com.example.manageskill.repository.MemberSkillRepository;
import com.example.manageskill.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Optional<Skill> getSkillById(Long id) {
        return skillRepository.findById(id);
    }

    public List<Skill> getAllSkillsWithGroupName() {
        // Thực hiện truy vấn để lấy danh sách kỹ năng với thông tin skillGroupName
        // Ví dụ: sử dụng JPQL hoặc Criteria API để thực hiện truy vấn kết hợp
        return skillRepository.findAllWithSkillGroupName();
    }

    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public void updateSkill(Skill skill) {
        skillRepository.save(skill);
    }
    @Autowired
    private MemberSkillRepository memberSkillRepository;
    @Transactional
    public void deleteSkill(Long skillId) {
        List<MemberSkill> memberSkills = memberSkillRepository.findAllBySkillId(skillId);
        memberSkillRepository.deleteAll(memberSkills);
        skillRepository.deleteById(skillId);
    }

    public List<Object[]> getMySkills(String username) {
        return skillRepository.findMySkillsByUsername(username);
    }
}
