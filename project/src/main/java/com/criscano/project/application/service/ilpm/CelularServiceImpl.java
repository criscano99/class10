package com.criscano.project.application.service.ilpm;

import com.criscano.project.application.dto.CelularDto;
import com.criscano.project.application.service.CelularService;
import com.criscano.project.domain.Celular;
import com.criscano.project.infrastructure.persistence.CelularRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CelularServiceImpl implements CelularService {

    @Autowired
    private CelularRepository celularRepository;

    @Override
    public CelularDto guardar(CelularDto celularDto) {

        Celular celular = new Celular();

        celular.setMarca(celularDto.getMarca());
        celular.setModelo(celularDto.getModelo());
        celular.setColor(celularDto.getColor());
        celular.setAlmacenamiento(celularDto.getAlmacenamiento());
        celular.setPrecio(celularDto.getPrecio());

        Celular guardado = celularRepository.save(celular);

        return new CelularDto(
                guardado.getId(),
                guardado.getMarca(),
                guardado.getModelo(),
                guardado.getColor(),
                guardado.getAlmacenamiento(),
                guardado.getPrecio()
        );
    }

    @Override
    public List<CelularDto> listar() {

        return celularRepository.findByDeletedFalseOrderByMarcaAsc()
                .stream()
                .map(c -> new CelularDto(
                        c.getId(),
                        c.getMarca(),
                        c.getModelo(),
                        c.getColor(),
                        c.getAlmacenamiento(),
                        c.getPrecio()
                ))
                .collect(Collectors.toList());
    }
}
