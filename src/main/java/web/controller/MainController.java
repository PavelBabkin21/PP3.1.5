package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/users")
public class MainController {
    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public void setRoleRepository(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String welcomeUsers() {
        return "index";
    }

    @GetMapping("/admin/allUsers")
    public String getUsers(Model model) {
        model.addAttribute("list", userService.listUsers());
        return "allUsers";
    }

    @GetMapping("/admin/{id}")
    public String userPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUser(id));
        return "user";
    }

    @GetMapping("/user")
    public String userPageForName(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String adminPageForName(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("list", userService.listUsers());
        model.addAttribute("user", user);
        return "admin";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.listRoles());
        return "new";
    }

    @PostMapping()
    public String userCreate(@ModelAttribute("user") User user, @RequestParam(value = "role") String role) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleService.getByName(role));
        userService.addUser(user);
        return "redirect:/";
    }

    @GetMapping(value = "/admin/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("roles", roleService.listRoles());
        model.addAttribute("user", userService.getUser(id));
        return "edit";
    }

    @PatchMapping(value = "/admin/edit/{id}")
    public String update(@PathVariable(value = "id", required = false) Long id, @ModelAttribute("user") User user,
                         @RequestParam(value = "role", required = false) String role) {
        if (role != null) {
            user.setRoles(roleService.getByName(role));
        }
        userService.edit(user, id);
        return "redirect:/";
    }


    @DeleteMapping("/admin/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/";
    }


}