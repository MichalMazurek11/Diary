package org.school.diary.config;

import lombok.RequiredArgsConstructor;
import org.school.diary.dao.UserRepository;
import org.school.diary.model.common.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByLogin(username);
        UserPrincipal userPrincipal = new UserPrincipal(user);
        System.out.println("Znaleziony uzytkownik: "+ userPrincipal.getUsername() + " haslo: "+ userPrincipal.getPassword());

        if (user == null) {
            throw new UsernameNotFoundException("Unauthorized");
        }
        return new UserPrincipal(user);
    }
}
