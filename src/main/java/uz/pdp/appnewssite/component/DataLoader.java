package uz.pdp.appnewssite.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.appnewssite.entity.Role;
import uz.pdp.appnewssite.entity.User;
import uz.pdp.appnewssite.entity.enums.Permission;
import uz.pdp.appnewssite.repository.RoleRepo;
import uz.pdp.appnewssite.repository.UserRepo;
import uz.pdp.appnewssite.utils.AppConstants;

import java.lang.reflect.Array;
import java.util.Arrays;

import static uz.pdp.appnewssite.entity.enums.Permission.*;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.datasource.initialization-mode}")
    private String initialMode;

    @Override
    public void run(String... args) throws Exception {
        if(initialMode.equals("always")){
            Role admin = roleRepo.save(new Role(
                    AppConstants.ADMIN,
                    Arrays.asList(values()),
                    "Sistema egasi"
            ));
            Role user = roleRepo.save(new Role(
                    AppConstants.USER,
                    Arrays.asList(ADD_COMMENT, EDIT_COMMENT, DELETE_MY_COMMENT),
                    "Oddiy foydalanuvchi"
            ));

            userRepo.save(new User(
                    "admin",
                    "admin",
                    passwordEncoder.encode("admin123"),
                    admin,
                    true
            ));
            userRepo.save(new User(
                    "user",
                    "user",
                    passwordEncoder.encode("user123"),
                    user,
                    true
            ));
        }
    }
}
