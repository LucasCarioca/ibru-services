package net.lucasdesouza.ibru.repositories;

import net.lucasdesouza.ibru.models.Brew;
import net.lucasdesouza.ibru.models.Reading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingRepository extends JpaRepository<Reading, String> {
    List<Reading> findAllByBrew(Brew brew);
}
