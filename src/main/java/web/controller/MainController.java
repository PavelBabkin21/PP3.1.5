package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.Role;
import web.model.User;
import web.repository.RoleRepository;
import web.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    private  UserService userService;
    private  RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MainController(UserService userService, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String allUsers(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "AllUsers";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String userPage(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.getUser(id));
        return "user";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String userPageName(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        model.addAttribute("user", user);
        return "user";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPageName(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "admin";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/";
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String editPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("roles", userService.listRoles());
        model.addAttribute("user", userService.getUser(id));
        return "UserEdit";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String editUser(@ModelAttribute("user") User user, @PathVariable("id") long id,
                           @RequestParam(value = "role") String role) {
        user.setRoles(userService.findRolesByName(role));
        userService.edit(id, user);
        return "redirect:/";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPage(Model model) {
        if (userService.listRoles().isEmpty()) {
            roleRepository.save(new Role("ROLE_ADMIN"));
            roleRepository.save(new Role("ROLE_USER"));
        }
        model.addAttribute("user", new User());
        model.addAttribute("roles", userService.listRoles());
        return "UserAdd";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("User") User user, @RequestParam(value = "role") String role) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(userService.findRolesByName(role));
        userService.add(user);
        return "redirect:/";
    }


}