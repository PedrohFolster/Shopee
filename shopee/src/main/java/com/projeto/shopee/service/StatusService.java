package com.projeto.shopee.service;

import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.shopee.dto.StatusDTO;
import com.projeto.shopee.entities.Status;
import com.projeto.shopee.repository.StatusRepository;
import com.projeto.shopee.util.StatusMapper;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private StatusMapper statusMapper;

    public List<StatusDTO> getAllStatus() {
        List<Status> statusList = statusRepository.findAll();
        return statusMapper.toDTOs(statusList);
    }

    public StatusDTO getStatusById(Long id) {
        Optional<Status> status = statusRepository.findById(id);
        return status.map(statusMapper::toDTO).orElse(null);
    }

    public StatusDTO createStatus(StatusDTO statusDTO) {
        Status status = statusMapper.toEntity(statusDTO);
        status = statusRepository.save(status);
        return statusMapper.toDTO(status);
    }

    public StatusDTO updateStatus(Long id, StatusDTO statusDTO) {
        if (!statusRepository.existsById(id)) {
            return null;
        }
        statusDTO.setId(id);
        Status status = statusMapper.toEntity(statusDTO);
        status = statusRepository.save(status);
        return statusMapper.toDTO(status);
    }

    public void deleteStatus(Long id) {
        statusRepository.deleteById(id);
    }
}
