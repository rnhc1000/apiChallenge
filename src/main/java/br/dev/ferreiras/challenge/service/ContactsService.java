package br.dev.ferreiras.challenge.service;

import br.dev.ferreiras.challenge.dto.*;
import br.dev.ferreiras.challenge.entity.Contact;
import br.dev.ferreiras.challenge.repository.ContactsRepository;
import br.dev.ferreiras.challenge.service.exceptions.ContactAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class ContactsService {

    private final ContactsRepository contactsRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(ContactsService.class);

    public ContactsService(ContactsRepository contactsRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.contactsRepository = contactsRepository;

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<Contact> getContacts() {

        return contactsRepository.findAll();
    }

    public PagedContactsDto getPagedContacts(int page, int size) {

        Pageable paging = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<ContactsDto> pageContacts = contactsRepository
                .findAll(paging)
                .map(contacts -> new ContactsDto(contacts.getId(), contacts.getName(),
                        contacts.getEmail(), contacts.getCompany(), contacts.getCreatedAt(),
                        contacts.getUpdateAt(), contacts.getDeleted())

        );
        return new PagedContactsDto(pageContacts.getContent(), page, size,
                pageContacts.getTotalPages(), pageContacts.getTotalElements());
    }

    public PagedContactsDto getPagedContactsByCompany(int page, int size, String company) {
        Pageable paging = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<ContactsDto> pageContacts = contactsRepository.findAllByCompany(company,paging ).map(
                contacts -> new ContactsDto(contacts.getId(), contacts.getName(),
                        contacts.getEmail(), contacts.getCompany(), contacts.getCreatedAt(),
                        contacts.getUpdateAt(), contacts.getDeleted()));

        return new PagedContactsDto(pageContacts.getContent(), page, size,
                pageContacts.getTotalPages(), pageContacts.getTotalElements());
    }


    public Optional<Contact> getEmail(String email) {
        return contactsRepository.findByEmail(email);
    }

    public void saveContact(Contact contact) {
        this.contactsRepository.save(contact);
    }

    public ResponsePagedContactsDto saveNewContact(RequestPagedContactsDto requestPagedContactsDto) {
        if(this.getEmail(requestPagedContactsDto.email()).isPresent()) {
            throw new ContactAlreadyExistsException("Contact already exists");
        } else {
            var contact = new Contact();
            contact.setName(requestPagedContactsDto.name());
            contact.setEmail(requestPagedContactsDto.email());
            contact.setCompany(requestPagedContactsDto.company());
            contact.setPassword(this.bCryptPasswordEncoder.encode(requestPagedContactsDto.password()));
            logger.info("Password -> {}, ", this.bCryptPasswordEncoder.encode(requestPagedContactsDto.password()));
            logger.info("Contact -> {}", contact);
            this.saveContact(contact);
        }
        return new ResponsePagedContactsDto(requestPagedContactsDto.name(), requestPagedContactsDto.email());
    }

    @Transactional
    public BulkContactsResponseDto insertBulkContacts(List<Contact> contacts) {
        for (int i = 0; i < contacts.size(); i++) {
            contactsRepository.save(contacts.get(i));
            if (i % 50 == 0) {
                contactsRepository.flush();
            }
        }
        return new BulkContactsResponseDto(contacts.size());
    }
}
