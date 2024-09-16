package com.projeto.shopee.util;

import java.util.List;
import org.springframework.stereotype.Component;

import com.projeto.shopee.dto.StatusDTO;
import com.projeto.shopee.entities.Status;
import java.util.stream.Collectors;

@Component
public class StatusMapper {

    public StatusDTO toDTO(Status status) {
        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setId(status.getId());
        statusDTO.setNomeStatus(status.getNomeStatus());
        return statusDTO;
    }

    public Status toEntity(StatusDTO statusDTO) {
        return new Status(
                statusDTO.getId(),
                statusDTO.getNomeStatus());
    }

    public List<StatusDTO> toDTOs(List<Status> statusList) {
        return statusList.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
