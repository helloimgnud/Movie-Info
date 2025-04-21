package com.recflix.controller;

import com.recflix.model.*;
import com.recflix.dto.*;
import com.recflix.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Get an account by ID
    @GetMapping("/info")
    public ResponseEntity<AccountDTO> getAccount(){
        try{
            return ResponseEntity.ok(accountService.getCurrentAccount());
        } catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/info")
    public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO dto){
        try{
            return ResponseEntity.ok(accountService.changeInfo(dto));
        } catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/fav")
    public  ResponseEntity<Set<MovieShortDTO>> getFavorites() {
        try{ 
            return ResponseEntity.ok(accountService.getFavorites());
        } catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }   
    }

    @PostMapping("/fav/{id}")
    public  ResponseEntity<AccountDTO> setFavorites(@PathVariable Long id) {
        try{ 
            return ResponseEntity.ok(accountService.addFavorites(id));
        } catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }   
    }

    // Đổi mật khẩu (chỉ user mới được dùng)
    @PutMapping("/changePassword")
    public  ResponseEntity<UserDTO> changePassword(@RequestBody ChangePasswordDTO dto) {
        try{ 
            return ResponseEntity.ok(accountService.changePassword(dto));
        } catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }   
    }
}
