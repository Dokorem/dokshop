package dok.dokshop.managerapp.service;

import dok.dokshop.managerapp.entity.Authority;
import dok.dokshop.managerapp.repository.DokShopUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DokShopUserDetailsService implements UserDetailsService {

    private final DokShopUserRepository dokShopUserRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.dokShopUserRepository.findByUsername(username)
                .map(user -> User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .authorities(user.getAuthorities().stream()
                                .map(Authority::getAuthority)
                                .map(SimpleGrantedAuthority::new)
                                .toList())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
