package net.lucasdesouza.ibru.repositories;

import net.lucasdesouza.ibru.models.Brew;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrewRepository extends JpaRepository<Brew, String> {
}
