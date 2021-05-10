package com.adrianomenezes.negativacao.model.repository;

import com.adrianomenezes.negativacao.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByNome(String nome);

    @Query("select c from Cliente c "
            + " where upper(c.nome) like upper(:nome) " )
    List<Cliente> findByNomes(@Param("nome") String nome);

    @Query("select c from Cliente c "
            + " where c.id = :id "
            + " and  c.negativar = true" )
    List<Cliente> findClientesANegativar(@Param("id") Integer id);

    @Query("select c from Cliente c "
            + " where  "
            + "   c.negativar = true" )
    List<Cliente> findClientesANegativar();

}
