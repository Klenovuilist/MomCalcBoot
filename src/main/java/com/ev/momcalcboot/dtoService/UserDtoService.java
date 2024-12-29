package com.ev.momcalcboot.dtoService;

import com.ev.momcalcboot.Entity.UserEntity;
import com.ev.momcalcboot.dao.MaterialsDao;

import com.ev.momcalcboot.repositoriy.UserDaoRepository;
import com.ev.momcalcboot.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
@Component

    public class UserDtoService {

    private final UserDaoRepository userDaoRepository;

    private  final MaterialsDao materals_dao;

    public List<UserDto> getAllUserDto(){

        List<UserEntity> allUserEntities = userDaoRepository.getAllUser();

        List<UserDto> allUsersDto= new ArrayList<>();

        allUserEntities.forEach(user -> {
            UserDto userDto = UserDto.builder()
                    .dataUser(user.getDataUser())
                    .roleUser(user.getRoleUser())
                    .passwordUser(user.getPasswordUser())
                    .userName(user.getUserName())
                    .id(user.getId())
                    .userBolts(user.getBoltEntities())
                    .userSqrews(user.getSqrewEntities())
                    .build();

            userDto.setUserInfo(Arrays.asList(user.getUserName(), user.getRoleUser()
                    , user.getPasswordUser()
                    , String.valueOf(user.getDataUser())
                    , String.valueOf(user.getId())));

            allUsersDto.add(userDto);

            });

        return allUsersDto;
    }

    public UserDto getUserDtoByIdWithMaterial(int UserId){

        UserEntity userEntity = userDaoRepository.getUserById(UserId);

//        List <Materals_db> materals_dbs = materals_db_dao.getMaterialsByUserId(UserId);

        return UserDto.builder()
                .userName(userEntity.getUserName())
                .roleUser(userEntity.getRoleUser())
                .dataUser(userEntity.getDataUser())
                .id(userEntity.getId())
                .passwordUser(userEntity.getPasswordUser())
//                .userMaterials(materals_dbs)
                .build();

    }

}
