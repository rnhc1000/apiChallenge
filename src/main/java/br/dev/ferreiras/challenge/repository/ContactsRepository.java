package br.dev.ferreiras.challenge.repository;

import br.dev.ferreiras.challenge.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ContactsRepository extends JpaRepository<Contact, Long> {

    @Override
    Page <Contact> findAll(Pageable pageable);

    @Query(
            """
                SELECT r FROM Contact r WHERE r.company = ?1%
            """
    )
    Page<Contact> findAllByCompany(String company, Pageable pageable);

    @Query(
            """
                SELECT email FROM Contact c WHERE c.email = ?1%
            """
    )
    Optional<Contact> findByEmail(String email);
}
