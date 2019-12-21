package pl.com.app.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.app.dto.ConstituencyDTO;
import pl.com.app.exceptions.ExceptionCode;
import pl.com.app.exceptions.MyException;
import pl.com.app.repository.ConstituencyRepository;
import pl.com.app.service.mappers.ModelMapper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ConstituencyService {
    private final ConstituencyRepository constituencyRepository;
    private final ModelMapper modelMapper;


    public List<ConstituencyDTO> getAllConstituencies() {
        try {
            return constituencyRepository
                    .findAll()
                    .stream()
                    .map(modelMapper::fromConstituencyToConstituencyDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public void saveOrUpdateAll(List<ConstituencyDTO> list) {
        try {
            if (list == null){
                throw new NullPointerException("LIST IS NULL");
            }

            constituencyRepository.saveOrUpdateAll(
                    list
                            .stream()
                            .map(modelMapper::fromConstituencyDTOToConstituency)
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public ConstituencyDTO findByName(String name) {
        try {
            if (name == null){
                throw new NullPointerException("NAME IS NULL");
            }

            return constituencyRepository.findByName(name)
                    .map(modelMapper::fromConstituencyToConstituencyDTO)
                    .orElseThrow(NullPointerException::new);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public void deleteAll() {
        try {
            constituencyRepository.deleteAll();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public ConstituencyDTO getOneConstituency(Long constituencyId) {
        try {
            if (constituencyId == null) {
                throw new NullPointerException("CONSTITUENCY ID IS NULL");
            }

            return constituencyRepository
                    .findById(constituencyId)
                    .map(modelMapper::fromConstituencyToConstituencyDTO)
                    .orElseThrow(NullPointerException::new);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }
}
