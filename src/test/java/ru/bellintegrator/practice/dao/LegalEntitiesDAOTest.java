package ru.bellintegrator.practice.dao;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.Application;
import ru.bellintegrator.practice.model.*;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
@Transactional
@DirtiesContext
public class LegalEntitiesDAOTest {
    @Autowired
    private LegalEntitiesDAO dao;

    private final Comparator<LegalEntity> legalEntityComparator = Comparator.comparing(LegalEntity::getName).thenComparing(LegalEntity::getInn);

    private boolean equals(LegalEntity left, LegalEntity right) {
        return legalEntityComparator.compare(left, right) == 0;
    }

    private LegalEntity createLegalEntity() {
        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setName("Google");
        legalEntity.setInn("5036032527");
        return legalEntity;
    }

    @Test
    public void addAndGetLegalEntities() {
        LegalEntity legalEntity = createLegalEntity();
        dao.addLegalEntities(Collections.singletonList(legalEntity));
        List<LegalEntity> legalEntities = dao.getLegalEntities();
        assert equals(legalEntity, legalEntities.get(legalEntities.size() - 1));
    }
}
