package com.ev.momcalcboot.service.internal;

import com.ev.momcalcboot.Entity.MaterialsEntity;
import com.ev.momcalcboot.Entity.UserEntity;
import com.ev.momcalcboot.dao.MaterialsDao;
import com.ev.momcalcboot.repositoriy.UserDaoRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

private final MaterialsDao materialsDao;

private final UserDaoRepository userDaoRepository;

    public void saveMaterialRequestAdmin(HttpServletRequest request, int userId){

        boolean saveKey = false;

        List<MaterialsEntity> materals_entity = materialsDao.getMaterialsByUserId(userId);

        for (MaterialsEntity materal: materals_entity){

            String comments = null;

            if (Strings.isNotBlank(request.getParameter("comments" + materal.getId()))){

                comments = request.getParameter("comments" + materal.getId());

                if(! comments.equals(materal.getComments())){
                    materal.setComments(comments);
                    saveKey = true;
                }
            }

            int limit = 0;
            if (Strings.isNotBlank(request.getParameter("limit" + materal.getId()))){

               limit = Integer.parseInt(request.getParameter("limit" + materal.getId()));

               if(limit != materal.getLimitStrength()){
                    materal.setLimitStrength(limit);
                    saveKey = true;
                }

            }

            String materialName = null;

            if (Strings.isNotBlank(request.getParameter("material" + materal.getId()))){

              materialName = request.getParameter("material" + materal.getId());

              if(! materialName.equals(materal.getMaterials())){
                    materal.setMaterials(materialName);
                    saveKey = true;
                }
            }

            Double strengthClass = null;

            if(Strings.isNotBlank(request.getParameter("class" + materal.getId()))) {

                strengthClass = Double.parseDouble(request.getParameter("class" + materal.getId()));

                if(! strengthClass.equals(materal.getStrengthClass())){
                    materal.setStrengthClass(strengthClass);
                    saveKey = true;
                }
             }

            if(saveKey){
                materialsDao.save(materal);
            }
        }
    }

    public void saveUserRequestAdmin(HttpServletRequest request, int id) {

        UserEntity userEntity = userDaoRepository.getUserById(id);

        String userName = request.getParameter("userName");

        String roleUser = request.getParameter("roleUser");

        String passwordUser = request.getParameter("passwordUser");

        boolean saveKey = false;

    if(! userEntity.getUserName().equals(userName)){
        saveKey = true;

        userEntity.setUserName(userName);
    }
        if(! userEntity.getRoleUser().equals(roleUser)){
            saveKey = true;

            userEntity.setRoleUser(roleUser);
        }

        if(! userEntity.getPasswordUser().equals(passwordUser)){
            saveKey = true;

            userEntity.setPasswordUser(passwordUser);
        }
         if (saveKey){
             userDaoRepository.updateUser(userEntity);
         }
    }
//    public <P> boolean validInt(P parametr){
//
//    }
}