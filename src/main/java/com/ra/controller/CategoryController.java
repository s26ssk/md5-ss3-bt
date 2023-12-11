package com.ra.controller;

import com.ra.entity.Category;
import com.ra.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/list")
    public String category(Model model, RedirectAttributes redirectAttributes){
        List<Category> list = categoryService.findAll();
        model.addAttribute("list", list);
        if (redirectAttributes.containsAttribute("error")) {
            model.addAttribute("error", redirectAttributes.getFlashAttributes().get("error"));
        }
        return "category";
    }

    @GetMapping("/add")
    private String addCategory(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "add-category";
    }
    @PostMapping("/add")
    private String storeCategory(@ModelAttribute("category") Category category){
        if(categoryService.saveOrUpdate(category) != null){
            return "redirect:/category/list";
        }
        return "add-category";
    }

    @GetMapping("/edit/{id}")
    private String editCategory(@PathVariable("id") Integer id, Model model){
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "edit-category";
    }
    @PostMapping("/edit")
    private String update(@ModelAttribute("category") Category category){
        if(categoryService.saveOrUpdate(category) != null){
            return "redirect:/category/list";
        }
        return "edit-category";
    }
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if (categoryService.hasProducts(id)) {
            redirectAttributes.addFlashAttribute("error", "Cannot delete category with associated products.");
            return "redirect:/category/list";
        }
        categoryService.deleteById(id);
        return "redirect:/category/list";
    }
}
