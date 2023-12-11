package com.ra.controller;

import com.ra.entity.Category;
import com.ra.entity.Product;
import com.ra.service.CategoryService;
import com.ra.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/list")
    public String product(Model model){
        List<Product> productList = productService.findAll();
        model.addAttribute("productList", productList);
        return "product";
    }

    @GetMapping("/add")
    private String addProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);
        return "add-product";
    }

    @PostMapping("/add")
    private String storeProduct(@ModelAttribute("product") Product product) {
        if (productService.saveOrUpdate(product) != null) {
            return "redirect:/product/list";
        }
        return "add-product";
    }


    @GetMapping("/edit/{id}")
    private String editProduct(@PathVariable("id") Integer id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);
        return "edit-product";
    }
    @PostMapping("/edit")
    private String updateProduct(@ModelAttribute("product") Product product){
        if(productService.saveOrUpdate(product) != null){
            return "redirect:/product/list";
        }
        return "edit-product";
    }
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteById(id);
        return "redirect:/product/list";
    }
}
