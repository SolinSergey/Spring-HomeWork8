package ru.gb.hibernate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.hibernate.Dao.ProductDao;
import ru.gb.hibernate.entities.Product;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    private ProductDao productDao;

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/findbyid")
    public String showFindById(Model model, @RequestParam Long id) {
        Product product = null;
        product = productDao.findById(id);
        model.addAttribute("result", product);
        return "byid";
    }

    @GetMapping("/findall")
    public String showFindAll(Model model) {
        List<Product> list = null;
        list = productDao.findAll();
        model.addAttribute("products", list);
        return "products";
    }

    @GetMapping("/deletebyid")
    public String deleteById(Model model, @RequestParam Long id) {
        Product product = null;
        product = productDao.findById(id);
        productDao.deleteById(id);
        model.addAttribute("product", product);
        return "deletebyid";
    }

    @GetMapping("/showForm")
    public String showSimpleForm(Model model) {
        Product product = new Product();
        product.setTitle("");
        product.setPrice(0);
        model.addAttribute("product", product);
        return "productform";
    }

    @PostMapping("/processForm")
    public String processForm(@ModelAttribute Product product, Model model) {
        product = productDao.saveOrUpdate(product);
        model.addAttribute("product", product);
        return "formresult";
    }
}
