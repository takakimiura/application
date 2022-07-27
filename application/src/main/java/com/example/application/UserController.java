package com.example.application;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserRepository2 userRepository2;
  @RequestMapping("user/show")
  public String list(Model m,
  HttpServletResponse httpServletResponse
  ){

    httpServletResponse.addCookie(new Cookie("key1", "value1"));

    List<UserEntity> list = userRepository.findAll();
    m.addAttribute("userList", list); 

    return "user/show";

  }

  @RequestMapping("user/{id}/detail")
  public String detail(Model m,
  @PathVariable Integer id,
  @CookieValue(name = "key1", required = false) String cookieValue
  ) {

    System.out.println(cookieValue);

    UserEntity list = userRepository.findById(id).get();
    m.addAttribute("userInfo", list);

    return "user/detail";
  }

  // @RequestMapping("user/new")
  // public String create(Model m) {

  //   List<UserEntity> list = userRepository.findAll();
  //   m.addAttribute("user", list);

  //   return "/new";
  // }  

  @RequestMapping("user/new")
  public String usernew(Model m) {

    
    m.addAttribute("userData", new UserEntity2());
    m.addAttribute("userList", userRepository.findAll());

    return "user/new";
  }

  @RequestMapping("user/create")
  public String usercreate(Model m,
  @RequestParam(name = "store", required = false) String store,
  @RequestParam(name = "name1", required = false) String name1,
  @RequestParam(name = "date", required = false) String date,
  @RequestParam(name = "time", required = false) String time,
  @RequestParam(name = "people", required = false) Integer people,
  @RequestParam(name = "tel1", required = false) String tel1
 
  ) {
    m.addAttribute("store", store);
    m.addAttribute("name1", name1);
    m.addAttribute("date", date);
    m.addAttribute("time", time);
    m.addAttribute("people", people);
    m.addAttribute("tel1", tel1);
    m.addAttribute("countreserved", people);
    UserEntity userA = userRepository.findByName(store);
    UserEntity2 user = new UserEntity2();
    user.setStore(store);
    user.setName1(name1);
    user.setDate(date);
    user.setTime(time);
    user.setPeople(people);
    user.setTel1(tel1);
    userA.setCountreserved(people);

    System.out.println(user.getName1());
    userRepository2.save(user);
    userRepository.save(userA);

    // m.addAttribute("userData", user);
   return"redirect:/user/show";
  }


  // @GetMapping("user/save")
  // public String save(Model m,
  // @ModelAttribute UserEntity user
  // ) {
  //   m.addAttribute("user", user);
  //   userRepository.save(user);

  //   return "redirect:/user/show";
  // }

  @RequestMapping("user/{id}/save")
  public String save(Model m,
  @PathVariable(name = "id", required = false) int id){
    List<Integer> ids = new ArrayList<Integer>();

    ids.add(id);
    List<UserEntity2> list = userRepository2.findAllById(ids);

    m.addAttribute("userSave", list); 

    return "user/save";

  }

  @RequestMapping("/user/userinfo")
  public String info(Model m){

  List<UserEntity> list = userRepository.findAll();
    m.addAttribute("userList", list); 

    return "user/userinfo";

  }
}