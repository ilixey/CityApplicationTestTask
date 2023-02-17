package vaspiakou.citylistapplication.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import vaspiakou.citylistapplication.exception.notfound.NotFoundException;
import vaspiakou.citylistapplication.model.User;
import vaspiakou.citylistapplication.repository.RoleRepository;
import vaspiakou.citylistapplication.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            throw new NotFoundException(String.format("User with username %s is not found", username));
        });
        String[] authorities = roleRepository.findById(user.getRole_id()).orElseThrow(() -> {
            throw new NotFoundException("Role is not found");
        }).getAuthorityList();

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        for (String authority: authorities){
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), simpleGrantedAuthorities);
    }
}
