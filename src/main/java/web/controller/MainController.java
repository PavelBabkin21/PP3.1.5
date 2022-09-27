package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.repository.RoleRepository;
import web.service.UserService;


import javax.validation.Valid;
import java.security.Principal;

@Controller
public class MainController {
    private UserService userService;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String welcomeUsers() {
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("list", userService.listUsers());
        return "users";
    }

    @GetMapping("/user/{id}")
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
        model.addAttribute("user", user);
        return "admin";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        if (userService.listRoles().isEmpty()) {
            roleRepository.save(new Role("ROLE_ADMIN"));
            roleRepository.save(new Role("ROLE_USER"));
        }
        model.addAttribute("user", new User());
        model.addAttribute("roles", userService.listRoles());
        return "new";
    }

    @PostMapping()
    public String userCreate(@ModelAttribute("user") User user, @RequestParam(value = "role") String role) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userService.findRolesByName(role));
        userService.addUser(user);
        return "redirect:/";
    }

    @GetMapping(value = "/edit/{id}")
    public String editUser(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", userService.listRoles());
        return "edit";
    }

    @PatchMapping(value = "/edit/{id}")
    public String edit(@ModelAttribute("user") @Valid User user) {
        userService.editUser(user);
        return "redirect:/";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }


}