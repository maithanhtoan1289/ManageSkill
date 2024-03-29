package com.example.manageskill.controller;

import com.example.manageskill.model.Team;
import com.example.manageskill.model.Teammember;
import com.example.manageskill.model.User;
import com.example.manageskill.service.TeamService;
import com.example.manageskill.service.TeammemberService;
import com.example.manageskill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TeammemberController {
    @Autowired
    private TeammemberService teammemberService;
    @Autowired
    private TeamService teamService;

    @GetMapping("/teammembers")
    public String showViewTeammembersPage(Model model) {
        List<Teammember> teammembers = teammemberService.getAllTeammembers();
        model.addAttribute("teammembers", teammembers);
        return "teammembers";
    }
    @Autowired
    private UserService userService;
    @GetMapping("/teammembers/create")
    public String showCreateTeammemberForm(Model model) {
        List<String> usersWithoutTeam = userService.findUsersWithoutTeam();
        model.addAttribute("users", usersWithoutTeam);

        //model.addAttribute("teammember", new Teammember());
        List<Team> teams = teamService.getAllTeams(); // Lấy danh sách các team
        model.addAttribute("teams", teams); // Truyền danh sách các team đến form
        return "create-teammember";
    }

    @PostMapping("/teammembers/create")
    public String createTeammember(@RequestParam("username") String username,
                                   @RequestParam("teamId") Long teamId) {
        Team team = teamService.getTeamById(teamId);
        User user = userService.getUserByUsername(username);

        if (user != null && team != null) {
            Teammember teammember = new Teammember();
            teammember.setUser(user);
            teammember.setTeam(team);
            teammemberService.createTeammember(teammember);
        }
        return "redirect:/teammembers";
    }

    @GetMapping("/teammembers/view/edit/{id}")
    public String showEditTeammemberForm(@PathVariable Long id, Model model) {
        Teammember teamMember = teammemberService.getTeammemberById(id);
        List<Team> teams = teamService.getAllTeams();
        List<String> usersWithoutTeam = userService.findUsersWithoutTeam();
        model.addAttribute("teamMember", teamMember);
        model.addAttribute("teams", teams);
        model.addAttribute("users", usersWithoutTeam);
        return "edit-team-member";
    }

    @PostMapping("/teammembers/update")
    public String updateTeammember(@ModelAttribute Teammember teammember) {
        teammemberService.updateTeammember(teammember);
        return "redirect:/teammembers/view";
    }
        // Endpoint để xóa team member
        @GetMapping("/teammembers/delete/{id}")
        public String deleteTeamMember(@PathVariable("id") Long id) {
            teammemberService.deleteTeamMember(id);
            return "redirect:/teammembers/view"; // Chuyển hướng người dùng đến trang danh sách team members sau khi xóa thành công
        }
    }

