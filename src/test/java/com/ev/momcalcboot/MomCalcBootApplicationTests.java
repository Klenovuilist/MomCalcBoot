package com.ev.momcalcboot;


import com.ev.momcalcboot.Entity.BoltEntity;
import com.ev.momcalcboot.Entity.SqrewEntity;
import com.ev.momcalcboot.Entity.UserEntity;
import com.ev.momcalcboot.dao.UserDao;
import com.ev.momcalcboot.repositoriy.SqrewDaoRepository;
import com.ev.momcalcboot.repositoriy.UserDaoRepository;
import com.ev.momcalcboot.service.internal.BoltService;
import com.ev.momcalcboot.service.internal.SqrewService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD) // объект класса MomCalcBootApplicationTests будет сщздан перед каждым методом тест


class MomCalcBootApplicationTests {

    @InjectMocks
    SqrewService sqrewService;

    @Mock
    BoltService boltService;

    @Mock
    UserEntity user1;

    @Mock
    SqrewDaoRepository sqrewDaoRepository;

    @Mock
    UserDaoRepository userDaoRepository;

    @Mock
    UserDao userDao;

    @BeforeEach
    public void setup() { // Инит-моки перед каждым тестом
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Tag("sqrew")// для определения какие тесты запустяться
    @DisplayName("вызов метода получения списка sqrew")
    @RepeatedTest(value = 2, name = "2 раза запустится")
    void serviceSqrewG() {

        List<SqrewEntity> sqrewEntityList = new ArrayList<>(List.of(new SqrewEntity(), new SqrewEntity()));

        Mockito.doReturn(sqrewEntityList).when(sqrewDaoRepository).getSqrewByUserId(Mockito.anyInt());

//        if (! sqrewEntityList.isEmpty()){
//
//            SqrewEntity sqrew = sqrewEntityList.get(0);
//            assertInstanceOf(String.class, sqrew.getName(), "Имя гайки не String");
//            assertInstanceOf(Integer.class, sqrew.getId(), "id гайки не int");
//        }
        assertFalse(sqrewEntityList.isEmpty(), "список гаек не получен");

    }

    @Test
    @Disabled("не запускать")
    void serviceBolt(){
        List<BoltEntity> boltEntityList = boltService.getBoltByUserId(1);
        if (! boltEntityList.isEmpty()){

            BoltEntity bolt = boltEntityList.get(0);
            assertInstanceOf(String.class, bolt.getName(), "Имя bolt не String");
            assertInstanceOf(Integer.class, bolt.getId(), "id bolt не int");

//            Assertions.assertThat(bolt).as();
        }
        assertFalse(boltEntityList.isEmpty(), "список bolt не получен");



    }


    @Test
    @DisplayName("проверка Id user")
    void testService(){


        UserEntity user = Mockito.mock(UserEntity.class); // прокси объект(оболочка) над User
        Mockito.doReturn(1).when(user).getId(); // вернуть 1 при вызове метода getId()

        int i = user.getId();

        org.assertj.core.api.Assertions.assertThat(i).isEqualTo(1);
//        org.assertj.core.api.Assertions.assertThat()

    }
@Test
@DisplayName("получение списка гаек по User Id")
    public void sqrewByUserIdTest(){

        List<SqrewEntity> sqrewEntityList = new ArrayList<>(List.of(new SqrewEntity(), new SqrewEntity()));

        Mockito.doReturn(sqrewEntityList).when(sqrewDaoRepository).getSqrewByUserId(Mockito.anyInt());

        Mockito.doReturn(1).when(user1).getId();

        List<SqrewEntity> sqrews = sqrewDaoRepository.getSqrewByUserId(user1.getId());

    Assertions.assertThat(!sqrews.isEmpty()).isTrue();

        }

    }


