    package auth_service.config;

    import auth_service.entity.User;
    import auth_service.exception.ResourceNotFoundException;
    import auth_service.repository.UserRepository;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    @RequiredArgsConstructor
    @Slf4j
    public class MyUserDetailsService implements UserDetailsService {
        private final UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user  = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("Email Id Not Found"));
            List<GrantedAuthority> authorities =  List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()));
            log.info("Authority: {}",authorities);
            return  new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
        }
    }
