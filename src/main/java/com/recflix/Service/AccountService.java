package com.recflix.service;

import com.recflix.dto.*;
import com.recflix.model.*;
import com.recflix.Exception.*;
import com.recflix.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ConvertDTOService converter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieDetailRepository movieDetailRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            return authentication.getName(); 
        }
        return null; 
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account getAccountById(Long id) {
        Account returnAccount = accountRepository.findById(id)
                                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy id: "+id) );
        return returnAccount;
    }

    public AccountDTO getCurrentAccount() {
        Users users = userRepository.findByUsername(getCurrentUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Tên tài khoản không tồn tại"));
        Account returnAccount = accountRepository.findByUser_Id(users.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for User ID: " + users.getId()));
        return converter.convertToDTO(returnAccount);
    }

    public Set<MovieShortDTO> getFavorites(){
        Users users = userRepository.findByUsername(getCurrentUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Tên tài khoản không tồn tại"));
        Account returnAccount = accountRepository.findByUser_Id(users.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for User ID: " + users.getId()));
        Set<MovieShortDTO> returnData = new HashSet<>();
        System.out.println(returnAccount.getId());

        if(returnAccount.getFavouriteMovies().isEmpty()){
            System.out.println("empty");
        }
        else{
            System.out.println("not empty");
        }
        for(MovieDetail movie: returnAccount.getFavouriteMovies()){
            System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
            returnData.add(converter.convertToShort(movie));
        }
        return returnData;
    }


    public AccountDTO addFavorites(Long id){
        MovieDetail newMovie = movieDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Phim không tồn tại"));
        System.out.println(newMovie.getTitle());

        Users users = userRepository.findByUsername(getCurrentUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Tên tài khoản không tồn tại"));
        Account returnAccount = accountRepository.findByUser_Id(users.getId())
                .map(existingAccount -> {
                    existingAccount.getFavouriteMovies().add(newMovie);
                    existingAccount.setFavouriteMovies(existingAccount.getFavouriteMovies());
                    return accountRepository.save(existingAccount);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for User ID: " + users.getId()));
        return converter.convertToDTO(returnAccount);

    }

    public AccountDTO changeInfo(AccountDTO dto){
        Users users = userRepository.findByUsername(getCurrentUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Tên tài khoản không tồn tại"));
        Account returnAccount = accountRepository.findByUser_Id(users.getId())
                .map(existingAccount -> {
                    existingAccount.setName(dto.getName());
                    existingAccount.setDateOfBirth(dto.getDateOfBirth());
                    existingAccount.setEmail(dto.getEmail());
                    existingAccount.setPhoneNumber(dto.getPhoneNumber());
                    return accountRepository.save(existingAccount);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for User ID: " + users.getId()));
        return converter.convertToDTO(returnAccount);

    }

    public UserDTO changePassword(ChangePasswordDTO dto){
        Users users = userRepository.findByUsername(getCurrentUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Tên tài khoản không tồn tại"));
        Users updatedUsers;
        if(passwordEncoder.matches(dto.getCurrentPassword(), users.getPassword())){
            System.out.println("Matched!");
            updatedUsers = userRepository.findById(users.getId())
                .map(existingUser -> {
                    existingUser.setPassword(passwordEncoder.encode(dto.getNewPassword()));
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new ResourceNotFoundException("tài khoản không tồn tại"));
        } else{
            throw new ConflictException("Password not match !");
        }
        return converter.convertToDTO(updatedUsers);

    }
    
    // Get account by ID
    // public Optional<Account> getAccountById(Long id) {
    //     return accountRepository.findById(id);
    // }

    // Update an account
    // public Optional<Account> updateAccount(Long id, AccountDTO updatedAccount) {
    //     return accountRepository.findById(id).map(existingAccount -> {
    //         existingAccount.setName(updatedAccount.getName());
    //         existingAccount.setDateOfBirth(updatedAccount.getDateOfBirth());
    //         existingAccount.setEmail(updatedAccount.getEmail());
    //         existingAccount.setPhoneNumber(updatedAccount.getPhoneNumber());
    //         existingAccount.setUser(updatedAccount.getUser());
    //         return accountRepository.save(existingAccount);
    //     });
    // }

    // // Delete an account
    // public boolean deleteAccount(Long id) {
    //     if (accountRepository.existsById(id)) {
    //         accountRepository.deleteById(id);
    //         return true;
    //     }
    //     return false;
    // }
}
