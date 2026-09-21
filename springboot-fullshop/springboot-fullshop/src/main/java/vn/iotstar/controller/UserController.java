package vn.iotstar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.dto.UserDTO;
import vn.iotstar.dto.UserFormDTO;
import vn.iotstar.repository.RoleRepository;
import vn.iotstar.service.UserService;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @GetMapping
    public String list(@RequestParam(defaultValue = "") String keyword,
                        @RequestParam(defaultValue = "0") int page,
                        Model model) {
        Pageable pageable = PageRequest.of(page, 8, Sort.by("id").descending());
        Page<UserDTO> userPage = userService.search(keyword, pageable);
        model.addAttribute("userPage", userPage);
        model.addAttribute("keyword", keyword);
        return "users/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("userFormDTO", new UserFormDTO());
        model.addAttribute("roles", roleRepository.findAll());
        return "users/form";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute UserFormDTO userFormDTO,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            return "users/form";
        }
        try {
            userService.create(userFormDTO);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("roles", roleRepository.findAll());
            return "users/form";
        }
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("userFormDTO", userService.findFormById(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "users/form";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                          @Valid @ModelAttribute UserFormDTO userFormDTO,
                          BindingResult bindingResult,
                          Model model) {
        userFormDTO.setId(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            return "users/form";
        }
        try {
            userService.update(userFormDTO);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("roles", roleRepository.findAll());
            return "users/form";
        }
        return "redirect:/users";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/users";
    }
}
