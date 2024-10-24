package br.dev.ferreiras.challenge.repository;

import br.dev.ferreiras.challenge.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsRepository extends JpaRepository<Contact, Long> {
}
