package com.ev.momcalcboot.service.internal;

import com.ev.momcalcboot.Entity.BoltEntity;
import com.ev.momcalcboot.Entity.MaterialsEntity;
import com.ev.momcalcboot.Entity.SqrewEntity;
import com.ev.momcalcboot.Entity.UserEntity;
import com.ev.momcalcboot.dao.MaterialsDao;
import com.ev.momcalcboot.repositoriy.BoltDaoRepository;
import com.ev.momcalcboot.repositoriy.SqrewDaoRepository;
import com.ev.momcalcboot.repositoriy.UserDaoRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ev.momcalcboot.service.internal.ParserNumber.toDouble;
import static com.ev.momcalcboot.service.internal.ParserNumber.toInt;

@Service
@AllArgsConstructor
@Slf4j
public class AdminService {

    private final MaterialsDao materialsDao;

    private final UserDaoRepository userDaoRepository;

    private final BoltDaoRepository boltDaoRepository;

    private final SqrewDaoRepository sqrewDaoRepository;

    public void saveMaterialRequestAdmin(HttpServletRequest request, int userId) {

        boolean saveKey = false;

        List<MaterialsEntity> materals_entity = materialsDao.getMaterialsByUserId(userId);

        for (MaterialsEntity materal : materals_entity) {
            String comments = null;

            if (Strings.isNotBlank(request.getParameter("comments" + materal.getId()))) {
                comments = request.getParameter("comments" + materal.getId());

                if (!comments.equals(materal.getComments())) {
                    materal.setComments(comments);
                    saveKey = true;
                }
            }

            int limit = 0;
            if (Strings.isNotBlank(request.getParameter("limit" + materal.getId()))) {
                limit = toInt(request.getParameter("limit" + materal.getId()));

                if (limit != materal.getLimitStrength()) {
                    materal.setLimitStrength(limit);
                    saveKey = true;
                }

            }

            String materialName = null;

            if (Strings.isNotBlank(request.getParameter("material" + materal.getId()))) {
                materialName = request.getParameter("material" + materal.getId());

                if (!materialName.equals(materal.getMaterials())) {
                    materal.setMaterials(materialName);
                    saveKey = true;
                }
            }

            Double strengthClass = null;

            if (Strings.isNotBlank(request.getParameter("class" + materal.getId()))) {
                strengthClass = toDouble(request.getParameter("class" + materal.getId()));

                if (!strengthClass.equals(materal.getStrengthClass())) {
                    materal.setStrengthClass(strengthClass);
                    saveKey = true;
                }
            }
            if (saveKey) {
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

        if (!userEntity.getUserName().equals(userName)) {
            saveKey = true;

            userEntity.setUserName(userName);
        }
        if (!userEntity.getRoleUser().equals(roleUser)) {
            saveKey = true;
            userEntity.setRoleUser(roleUser);
        }

        if (!userEntity.getPasswordUser().equals(passwordUser)) {
            saveKey = true;

            userEntity.setPasswordUser(passwordUser);
        }
        if (saveKey) {
            userDaoRepository.updateUser(userEntity);
        }
    }

    public List<BoltEntity> getBoltByUserId(int userId) {

        List<BoltEntity> bolts = boltDaoRepository.getBoltByUserId(userId);
        if (!bolts.isEmpty()) {
            List<BoltEntity> sortedBoltsList = bolts.stream().
                    sorted((bolt1, bolt2) -> Integer.compare(bolt1.getId(), bolt2.getId()))
                    .collect(Collectors.toList());
            return sortedBoltsList;
        } else {
            BoltEntity bolt = BoltEntity.builder().name("материал").build();
            bolts.add(bolt);
            return bolts;
        }
    }

    public List<SqrewEntity> getSqrewByUserId(int userId) {

        List<SqrewEntity> sqrews = sqrewDaoRepository.getSqrewByUserId(userId);

        if (sqrews.isEmpty()) {
            SqrewEntity sqrew = SqrewEntity.builder().name("материал").build();
            sqrews.add(sqrew);
        }
        return sqrews;
    }

    public void deleteBoltById(int boltId) {
        boltDaoRepository.deleteBolt(boltId);
    }

    public void deleteSqrewById(int sqrewId) {
        sqrewDaoRepository.deleteSqrew(sqrewId);
    }

}
