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
import vn.iotstar.dto.ProductDTO;
import vn.iotstar.dto.ProductFormDTO;
import vn.iotstar.security.CustomUserDetails;
import vn.iotstar.service.ProductService;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public String list(@RequestParam(defaultValue = "") String keyword,
                        @RequestParam(defaultValue = "0") int page,
                        @org.springframework.security.core.annotation.AuthenticationPrincipal CustomUserDetails currentUser,
                        Model model) {
        boolean isAdmin = currentUser.getRole().equals("ROLE_ADMIN");
        Pageable pageable = PageRequest.of(page, 8, Sort.by("id").descending());
        Page<ProductDTO> productPage = productService.search(keyword, currentUser.getId(), isAdmin, pageable);
        model.addAttribute("productPage", productPage);
        model.addAttribute("keyword", keyword);
        return "products/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("productFormDTO", new ProductFormDTO());
        return "products/form";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute ProductFormDTO productFormDTO,
                          BindingResult bindingResult,
                          @org.springframework.security.core.annotation.AuthenticationPrincipal CustomUserDetails currentUser,
                          Model model) {
        if (bindingResult.hasErrors()) {
            return "products/form";
        }
        try {
            productService.create(productFormDTO, currentUser.getId());
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "products/form";
        }
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("productFormDTO", productService.findFormById(id));
        return "products/form";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                          @Valid @ModelAttribute ProductFormDTO productFormDTO,
                          BindingResult bindingResult,
                          @org.springframework.security.core.annotation.AuthenticationPrincipal CustomUserDetails currentUser,
                          Model model) {
        productFormDTO.setId(id);
        if (bindingResult.hasErrors()) {
            return "products/form";
        }
        boolean isAdmin = currentUser.getRole().equals("ROLE_ADMIN");
        try {
            productService.update(productFormDTO, currentUser.getId(), isAdmin);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "products/form";
        }
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                          @org.springframework.security.core.annotation.AuthenticationPrincipal CustomUserDetails currentUser) {
        boolean isAdmin = currentUser.getRole().equals("ROLE_ADMIN");
        productService.delete(id, currentUser.getId(), isAdmin);
        return "redirect:/products";
    }
}
