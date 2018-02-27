package uk.co.nstech.securitydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleRestController {

  @GetMapping("/guest/hello")
  public String getGuest() {
    return "hello guest";
  }

  @GetMapping("/user/hello")
  public String getUser() {
    return "hello user";
  }

  @GetMapping("/admin/hello")
  public String getAdmin() {
    return "hello admin";
  }
}
