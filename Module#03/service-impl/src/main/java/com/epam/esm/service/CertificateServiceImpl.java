package com.epam.esm.service;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.CertificateSlimDto;
import com.epam.esm.dto.PatchCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Criteria;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CertificateAlreadyExistsException;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of the {@link CertificateService} interface.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    /**
     * Data Access Object for managing certificates.
     */
    private final CertificateDao certificateDao;
    /**
     * Mapper for mapping between certificate entities and DTOs.
     */
    private final CertificateMapper mapper;
    /**
     * Mapper for mapping between tag entities and DTOs.
     */
    private final TagMapper tagMapper;

    /**
     * Error message prefix for certificate not found.
     */
    private static final String MESSAGE = "Certificate not found with";

//    /**
//     * {@inheritDoc}
//     * <p>
//     * Retrieves a page of certificates based on tag names.
//     *
//     * @param criteria the list of tag names
//     * @return a page of certificate DTOs
//     */
//    @Override
//    @Transactional(readOnly = true)
//    public List<CertificateDto> getCertificatesByTags(
//            final Criteria criteria, Pageable pageable) {
//        return mapper.toDtoList(
//                certificateDao.findByCriteria(criteria, pageable));
//    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves a certificate by its ID.
     * <p>
     * Throws a {@link CertificateNotFoundException}
     * if the certificate is not found.
     */
    @Override
    @Transactional(readOnly = true)
    public CertificateDto getById(final Long id) {
        Objects.requireNonNull(id, "Id should not be null");
        Certificate certificate = certificateDao.getById(id)
                .orElseThrow(() -> new CertificateNotFoundException(
                        String.format("%s id: %d", MESSAGE, id)));
        return mapper.toDto(certificate);
    }

//    /**
//     * {@inheritDoc}
//     * <p>
//     * Retrieves all certificates with pagination.
//     * Returns a {@link Page} of {@link CertificateDto}.
//     */
//    @Override
//    @Transactional(readOnly = true)
//    public List<CertificateDto> getCertificates(
//            final Pageable pageable) {
//
//        List<Certificate> all = certificateDao.getAll(pageable);
////        for (Certificate certificate : all) {
////            log.info(certificate.getTags().toString());
////        }
//        return mapper.toDtoList(all);
//
//
//    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves all certificates with pagination.
     * Returns a {@link Page} of {@link CertificateDto}.
     */
    @Override
    public List<CertificateDto> getCertificates(
            final Pageable pageable) {
        log.info("pageable " + pageable);
        return mapper.toDtoList(certificateDao.getAllBy(pageable));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves a certificate by its name.
     *
     * @param name the name of the certificate
     * @return the {@link CertificateDto} with the specified name
     * @throws CertificateNotFoundException if the certificate
     *                                      with the given name does not exist
     */
    @Override
    @Transactional(readOnly = true)
    public CertificateDto getByName(final String name) {
        Objects.requireNonNull(name, "Name should not be null");
        Certificate certificate = certificateDao.getByName(name)
                .orElseThrow(() -> new CertificateNotFoundException(
                        String.format("%s name: %s", MESSAGE, name)));
        return mapper.toDto(certificate);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Deletes a certificate by its ID.
     *
     * @param id the ID of the certificate to delete
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(final Long id) {
        Objects.requireNonNull(id, "Id should not be null");
        certificateDao.delete(id);
    }

    /**
     * {@inheritDoc}
     * Updates a certificate with the provided {@link CertificateDto}.
     * Returns the updated {@link CertificateDto}.
     *
     * @param dto the {@link CertificateDto}
     *            containing the updated certificate information
     * @return the updated {@link CertificateDto}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CertificateDto update(
            final PatchCertificateDto dto) {
        log.debug(dto.toString());
        Certificate updated = certificateDao
                .update(mapper.toEntity(dto));
        return mapper.toDto(updated);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Saves a new certificate with the provided {@link CertificateSlimDto}.
     *
     * @param dto the {@link CertificateSlimDto}
     *            containing the new certificate information
     * @return the saved {@link CertificateDto}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CertificateDto save(
            final CertificateDto dto) {
        if (certificateDao.getByName(dto.getName()).isPresent()) {
            throw new CertificateAlreadyExistsException(
                    String.format("Certificate already exists with name %s",
                            dto.getName()));
        }
        Certificate saved = certificateDao
                .save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves the tags associated with a certificate identified by its ID.
     *
     * @param id the ID of the certificate
     * @return the set of {@link TagDto} associated with the certificate
     */
    @Override
    @Transactional(readOnly = true)
    public Set<TagDto> findTagsByCertificateId(
            final Long id) {
        HashSet<Tag> dtos = new HashSet<>(
                certificateDao.findTagsByCertificateId(id));
        return tagMapper.toDtoSet(dtos);
    }

    /**
     * {@inheritDoc}
     * <p>
     * If the tagNames parameter is null,
     * retrieves all certificates with pagination.
     * Returns a {@link Page} of {@link CertificateDto}.
     *
     * @param criteria the list of tag names to filter certificates by,
     *                 or null to retrieve all certificates
     * @param pageable
     * @return the {@link Page} of {@link CertificateDto}
     * matching the specified tag names or all certificates
     */
    @Override
    @Transactional(readOnly = true)
    public List<CertificateDto> findAllBy(
            final Criteria criteria, Pageable pageable) {
        log.info(criteria + " " + pageable);
        return mapper.toDtoList(certificateDao
                .findByCriteria(criteria, pageable));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves all certificates associated
     * with a user identified by their ID.
     *
     * @param id the ID of the user
     * @return the {@link Page}
     * of {@link CertificateDto} associated with the user
     */
    @Override
    public Page<CertificateDto> getCertificatesByUserId(
            final Long id) {
        List<CertificateDto> dtos = mapper.toDtoList(
                certificateDao.getCertificatesByUserId(id));
        return new PageImpl<>(dtos, Pageable.unpaged(), dtos.size());
    }

    /**
     * {@inheritDoc}
     *
     * @param ids the set of certificate IDs
     * @return the list of {@link CertificateDto}
     * matching the specified IDs
     */
    @Override
    public List<CertificateDto> getByIds(
            final Set<Long> ids) {
        return mapper.toDtoList(new ArrayList<>(
                certificateDao.findAllByIds(ids)));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves certificates by an order ID.
     *
     * @param id the ID of the order
     * @return the list of {@link CertificateDto}
     * associated with the order
     */
    @Override
    public List<CertificateDto> getByOrderId(
            final Long id) {
        return mapper.toDtoList(new ArrayList<>(
                certificateDao.findAllByOrderId(id)));
    }
}
