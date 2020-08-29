package io.github.th3c0d3r.tollbackend.repo;

import io.github.th3c0d3r.tollbackend.entity.TollPlaza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TollPlazaRepo extends JpaRepository<TollPlaza, Integer> {
    public TollPlaza findByTollPlazaId(Integer tollPlazaId);
    public List<TollPlaza> findAllByTollPlazaIdIn(List<Integer> tollPlazaIds);
}
