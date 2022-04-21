package uz.yt.springdata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.yt.springdata.dao.User;
import uz.yt.springdata.dto.ResponseDTO;
import uz.yt.springdata.dto.UserDTO;
import uz.yt.springdata.mapping.UserMapping;
import uz.yt.springdata.repository.UserRepository;
import uz.yt.springdata.validation.UserValid;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserValid userValid;

    public ResponseDTO<Page<UserDTO>> getAllUsers(Integer size, Integer page) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<User> userList = userRepository.findAll(pageRequest);
            List<UserDTO> userDTOList = new ArrayList<>();
            for (User user : userList) {
                userDTOList.add(UserMapping.toDto(user));
            }
            Page<UserDTO> result = new PageImpl(userDTOList, userList.getPageable(), userList.getTotalPages());
            return new ResponseDTO<>(true, 0, "OK", result, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO<>(false, -2, "Ma'lumot qidirishda xatolik mavjud", null, null);
        }
    }

    public ResponseDTO<UserDTO> getUser(Integer id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                return new ResponseDTO<>(true, 0, "OK", UserMapping.toDto(user), null);
            }
            return new ResponseDTO<>(false, -1, String.format("UserId = %d bo'yicha ma'lumot topilmadi", id), new UserDTO(id), null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO<>(false, -2, "Ma'lumot izlashda xatolik mavjud", new UserDTO(id), null);
        }
    }

    public ResponseDTO<UserDTO> addAndUpdate(UserDTO userDTO){
        try {
            User user = UserMapping.toEntity(userDTO);
            userRepository.save(user);
            UserMapping.setDto(userDTO, user);
            return new ResponseDTO<>(true, 0, "OK", userDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO<>(false, -3, "Ma'lumot saqlashda xatolik!", userDTO);
        }
    }

    public ResponseDTO<UserDTO> addUser(UserDTO userDTO) {
        Map<String, String> errors = userValid.validPOST(userDTO);
        if (errors.size()>0) return new ResponseDTO<>(false, -1, "Validatsiyada xatolik", userDTO, errors);
        userDTO.setId(null);
        return addAndUpdate(userDTO);
    }

    public ResponseDTO<UserDTO> updateUser(UserDTO userDTO) {
        Map<String, String> errors = validUserId(userDTO);
        if (errors.size()>0) return new ResponseDTO<>(false, -1, "Validatsiyada xatolik", userDTO, errors);
        userValid.validPUT(userDTO, errors);
        if (errors.size()>0) return new ResponseDTO<>(false, -1, "Validatsiyada xatolik", userDTO, errors);

        return addAndUpdate(userDTO);
    }

    private Map<String, String> validUserId(UserDTO userDTO) {
        return null;
    }


    public ResponseDTO<UserDTO> deleteUser(UserDTO userDTO) {
        Map<String, String> errors = userValid.validDELETE(userDTO);
        if (errors.size()>0) return new ResponseDTO<>(false, -1, "Validatsiyada xatolik", userDTO, errors);
        Optional<User> userOptional = userRepository.findById(userDTO.getId());
        User user = userOptional.get();
        UserMapping.setDto(userDTO, user);
        try {
            userRepository.delete(user);
            return new ResponseDTO<>(true, 0, "OK", userDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO<>(false, -4, "Ma'lumot o'chirishda xatolik!", userDTO);
        }
    }
}
