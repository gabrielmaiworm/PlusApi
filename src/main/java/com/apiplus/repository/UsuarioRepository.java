package com.apiplus.repository;

import com.apiplus.domain.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Usuario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("select usuario from Usuario usuario where usuario.user.login = ?#{principal.username}")
    List<Usuario> findByUserIsCurrentUser();
}
