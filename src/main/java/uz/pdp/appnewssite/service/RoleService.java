package uz.pdp.appnewssite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appnewssite.entity.Role;
import uz.pdp.appnewssite.payload.ApiResponse;
import uz.pdp.appnewssite.payload.RoleDto;
import uz.pdp.appnewssite.repository.RoleRepo;

@Service
public class RoleService {

    @Autowired
    RoleRepo roleRepo;
    public ApiResponse addRole(RoleDto roleDto) {
         if(roleRepo.existsByName(roleDto.getName()))
             return new ApiResponse("Bunday role mavjud",false);
            Role role = new Role(
                    roleDto.getName(),
                    roleDto.getPermissionList(),
                    roleDto.getDescription()
            );
            return new ApiResponse("Role qo'shildi!",true);

    }
}
