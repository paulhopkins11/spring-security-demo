package uk.co.nstech.securitydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsersController {

  @RequestMapping("/protectedLinks")
  public String getAnonymousPage() {
    return "index";
  }

  @RequestMapping("/user/welcome")
  public String getUserPage(Model model) {
    model.addAttribute("name", "USER");
    return "welcome";
  }

  @RequestMapping("/admin/welcome")
  public String getAdminPage(Model model) {
    model.addAttribute("name", "ADMIN");
    return "welcome";
  }

  @RequestMapping("/user/loginUser")
  public String userLoginPage() {
    return "login";
  }

  @RequestMapping("/admin/loginAdmin")
  public String adminLoginPage() {
    return "loginAdmin";
  }

  @RequestMapping("/guest/welcome")
  public String greeting(@RequestParam(value = "name", required = false, defaultValue = "Guest") String name, Model model) {
    model.addAttribute("name", name);
    return "welcome";
  }

  @RequestMapping("/403")
  public String getAccessDeniedPage() {
    return "403";
  }
}
