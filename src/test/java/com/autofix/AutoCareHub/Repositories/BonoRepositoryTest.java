package com.autofix.AutoCareHub.Repositories;
import com.autofix.AutoCareHub.Entities.BonoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class BonoRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BonoRepository bonoRepository;

    @Test
    public void whenFindListPagadoTrue_thenReturnBonoList(){
        // given
        BonoEntity bonoEntity = BonoEntity.builder()
                .marca("toyota")
                .usado(true)
                .amount(0)
                .build();
        entityManager.persistAndFlush(bonoEntity);

        //when
        ArrayList<BonoEntity> found = bonoRepository.findAllByUsadoIsTrueOrderByIdDesc();

        //then
        assertThat(found.size()).isEqualTo(1);
    }
    @Test
    public void whenFindListPagadoFalse_thenReturnBonoList(){
        // given
        BonoEntity bonoEntity = BonoEntity.builder()
                .marca("toyota")
                .usado(false)
                .amount(0)
                .build();
        entityManager.persistAndFlush(bonoEntity);

        //when
        ArrayList<BonoEntity> found = bonoRepository.findAllByUsadoIsFalseOrderByIdDesc();

        //then
        assertThat(found.size()).isEqualTo(1);
    }

    @Test
    public void whenGetAllByMarca_thenReturnBonoList(){
        // given
        BonoEntity bonoEntity = BonoEntity.builder()
                .marca("toyota")
                .usado(false)
                .amount(0)
                .build();
        entityManager.persist(bonoEntity);
        BonoEntity bonoEntity2 = BonoEntity.builder()
                .marca("toyota")
                .usado(false)
                .amount(0)
                .build();
        entityManager.persist(bonoEntity2);
        entityManager.flush();

        //when
        ArrayList<BonoEntity> found = bonoRepository.getAllByMarca("toyota");

        //then
        assertThat(found.size()).isEqualTo(2);
    }

    @Test
    public void whenGetAllByMarcaAndUsadoIsFalse_thenReturnBonoList(){
        // given
        BonoEntity bonoEntity = BonoEntity.builder()
                .marca("toyota")
                .usado(false)
                .amount(100)
                .build();
        entityManager.persist(bonoEntity);
        BonoEntity bonoEntity2 = BonoEntity.builder()
                .marca("toyota")
                .usado(false)
                .amount(0)
                .build();
        entityManager.persist(bonoEntity2);
        entityManager.flush();

        //when
        ArrayList<BonoEntity> found = bonoRepository.findAllByMarcaAndUsadoIsFalseOrderByAmountDesc("toyota");

        //then
        assertThat(found.size()).isEqualTo(2);
    }

    @Test
    public void whenGetFirstByMarcaAndUsadoIsFalse_thenReturnBonoList(){
        // given
        BonoEntity bonoEntity = BonoEntity.builder()
                .marca("toyota")
                .usado(false)
                .amount(100)
                .build();
        entityManager.persistAndGetId(bonoEntity);
        BonoEntity bonoEntity2 = BonoEntity.builder()
                .marca("toyota")
                .usado(false)
                .amount(0)
                .build();
        entityManager.persistAndGetId(bonoEntity2);
        entityManager.flush();

        //when
        Optional<BonoEntity> found = bonoRepository.getFirstByMarcaAndUsadoIsFalseOrderByAmountDesc("toyota");

        //then
        assertThat(found.get()).isEqualTo(bonoEntity);
    }
}
