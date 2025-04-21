package com.recflix.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class GetWebController {

   @GetMapping("/user/{static_webpage}")
   public String getWebUser(@PathVariable("static_webpage") String webpage) {
      return webpage;
   }

   @GetMapping("/admin/{static_webpage}")
   public String getWebAdmin(@PathVariable("static_webpage") String webpage) {
      return webpage;
   }
}