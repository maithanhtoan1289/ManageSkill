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

    @GetMapping("/teammembers/edit/{id}")
    public String showUpdateTeammemberForm(@PathVariable("id") Long id, Model model) {
        Teammember teammember = teammemberService.getTeammemberById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid team member Id:" + id));

        List<User> users = userService.getAllUsers();
        List<Team> teams = teamService.getAllTeams();

        model.addAttribute("teammember", teammember);
        model.addAttribute("users", users);
        model.addAttribute("teams", teams);

        return "edit-team-member"; // Thymeleaf template name
    }

    @PostMapping("/teammembers/edit")
    public String updateTeammember(@ModelAttribute("teammember") Teammember teammember) {
        teammemberService.save(teammember);
        return "redirect:/teammembers";
    }
        // Endpoint để xóa team member
        @GetMapping("/teammembers/delete/{id}")
        public String deleteTeamMember(@PathVariable("id") Long id) {
            teammemberService.deleteTeamMember(id);
            return "redirect:/teammembers"; // Chuyển hướng người dùng đến trang danh sách team members sau khi xóa thành công
        }
    }

