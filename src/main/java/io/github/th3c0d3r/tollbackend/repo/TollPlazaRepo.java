package io.github.th3c0d3r.tollbackend.repo;

import io.github.th3c0d3r.tollbackend.entity.TollPlaza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public interface TollPlazaRepo extends JpaRepository<TollPlaza, Integer> {
    public TollPlaza findByTollPlazaIdAndDeleted(Integer tollPlazaId, Boolean deleted);
    public List<TollPlaza> findAllByTollPlazaIdInAndDeleted(Collection<Integer> tollPlazaId, Boolean deleted);
    public List<TollPlaza> findAllByTollNameAndDeleted(String tollName, Boolean deleted);
    public List<TollPlaza> findAllByStateAndDeleted(String state, Boolean deleted);
    public List<TollPlaza> findAllByStateAndTollNameLikeAndDeleted(String state, String tollName, Boolean deleted);
    public List<TollPlaza> findAllByDeleted(Boolean deleted);
}
