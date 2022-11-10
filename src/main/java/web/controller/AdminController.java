package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class AdminController {
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


    @GetMapping("/admin")
    public String adminPageForName(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", new User());
        model.addAttribute("list", userService.listUsers());
        model.addAttribute("roles", roleService.listRoles());
        model.addAttribute("user", user);
        return "admin";
    }

    @PostMapping("/new")
    public String userCreate(@ModelAttribute("user") User user, @RequestParam(value = "role") String role) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleService.getByName(role));
        userService.addUser(user);
        return "redirect:/users/admin";
    }

    @PatchMapping(value = "/admin/edit/{id}")
    public String update(@PathVariable(value = "id", required = false) Long id, @ModelAttribute("user") User user,
                         @RequestParam(value = "role", required = false) String role) {
        if (role != null) {
            user.setRoles(roleService.getByName(role));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.edit(user, id);
        return "redirect:/users/admin";
    }


    @DeleteMapping("/admin/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/users/admin";
    }


}