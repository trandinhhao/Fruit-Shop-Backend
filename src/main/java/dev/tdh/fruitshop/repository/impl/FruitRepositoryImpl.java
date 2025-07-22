package dev.tdh.fruitshop.repository.impl;

import dev.tdh.fruitshop.entity.FruitEntity;
import dev.tdh.fruitshop.repository.custom.FruitRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class FruitRepositoryImpl implements FruitRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    private boolean checkLike(String key) {
        String[] keyList = {"name"};
        for (String s : keyList) {
            if (s.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public List<FruitEntity> findAllByConditions(Map<String, Object> conditions) {
        // SQL Native with StringBuilder
        StringBuilder sql = new StringBuilder(" SELECT f.* FROM Fruit f WHERE 1=1 AND f.instock = true ");
        for (Map.Entry<String, Object> ets : conditions.entrySet()) {
            if (checkLike(ets.getKey())) { // LIKE = %...%
                sql.append(" AND f.").append(ets.getKey()).append(" LIKE :").append(ets.getKey()).append(" ");
            } else {
                sql.append(" AND f.").append(ets.getKey()).append(" = :").append(ets.getKey()).append(" "); // dung =: tranh sql injection
            }
        }
        Query query = entityManager.createNativeQuery(sql.toString(), FruitEntity.class);

        // set parameter
        for (Map.Entry<String, Object> ets : conditions.entrySet()) {
            if (checkLike(ets.getKey())) {
                query.setParameter(ets.getKey(), "%" + ets.getValue().toString() + "%");
            } else {
                query.setParameter(ets.getKey(), ets.getValue());
            }
        }
        return query.getResultList();
    }
}
