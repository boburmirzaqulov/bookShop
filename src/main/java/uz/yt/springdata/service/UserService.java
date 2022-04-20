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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            return new ResponseDTO<>(true, 0, "OK", result);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO<>(false, -1, "ERROR", null);
        }
    }

    public ResponseDTO<UserDTO> getUser(Integer id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            return userOptional.map(
                            user -> new ResponseDTO<>(true, 0, "OK", UserMapping.toDto(user)))
                    .orElseGet(() -> new ResponseDTO<>(false, -2, String.format("ID = %d bo'yicha User topilmadi", id), null));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO<>(false, -1, "ERROR", null);
        }
    }

    public ResponseDTO<UserDTO> addUser(UserDTO userDTO, Integer id) {
        ResponseDTO<UserDTO> responseDTO = userValid.validAll(userDTO, id);
        if (!responseDTO.isSuccess()) return responseDTO;
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

    public ResponseDTO<UserDTO> updateUser(UserDTO userDTO) {
        return addUser(userDTO, userDTO.getId());
    }

    public ResponseDTO<UserDTO> deleteUser(UserDTO userDTO) {
        ResponseDTO<UserDTO> responseDTO = userValid.validUserId(userDTO);
        if (responseDTO != null) {
            return responseDTO;
        }
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
