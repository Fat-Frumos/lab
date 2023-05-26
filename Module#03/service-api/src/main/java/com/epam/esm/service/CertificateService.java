package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * Service interface for managing certificates.
 */
public interface CertificateService {

    /**
     * Retrieves a certificate by its ID.
     *
     * @param id the ID of the certificate.
     * @return the retrieved certificate.
     */
    CertificateDto getById(Long id);

    /**
     * Retrieves all certificates with pagination.

     * @param pageable the pagination information.
     * @return a List of certificates.
     */
    List<CertificateDto> getSlimCertificates(Pageable pageable);
    List<CertificateDto> getAll(Pageable pageable);

    /**
     * Retrieves a certificate by its name.
     *
     * @param name the name of the certificate.
     * @return the retrieved certificate.
     */
    CertificateDto getByName(String name);

    /**
     * Deletes a certificate by its ID.
     *
     * @param id the ID of the certificate to delete.
     */
    void delete(Long id);

    /**
     * Updates a certificate.
     *
     * @param dto the certificate DTO containing the updated information.
     * @return the updated certificate.
     */
    CertificateDto update(CertificateDto dto);

    /**
     * Saves a new certificate with slim information.
     *
     * @param dto the certificate DTO containing the information to save.
     * @return the saved certificate.
     */
    CertificateDto save(CertificateDto dto);

    /**
     * Retrieves the tags associated with a certificate.
     *
     * @param id the ID of the certificate.
     * @return a set of tag DTOs associated with the certificate.
     */
    Set<TagDto> findTagsByCertificateId(Long id);

    /**
     * Retrieves all certificates associated with a list of tag names.
     *
     * @param tagNames the list of tag names.
     * @return a page of certificates associated with the tags.
     */
    Page<CertificateDto> findAllByTags(List<String> tagNames);

    /**
     * Retrieves all certificates associated with a user by user ID.
     *
     * @param userId the ID of the user.
     * @return a page of certificates associated with the user.
     */
    Page<CertificateDto> getCertificatesByUserId(Long userId);

    /**
     * Retrieves certificates by their IDs.
     *
     * @param ids the set of certificate IDs.
     * @return a list of certificates matching the IDs.
     */
    List<CertificateDto> getByIds(Set<Long> ids);

    /**
     * Retrieves certificates associated with an order by order ID.
     *
     * @param id the ID of the order.
     * @return a list of certificates associated with the order.
     */
    List<CertificateDto> getByOrderId(Long id);
}
