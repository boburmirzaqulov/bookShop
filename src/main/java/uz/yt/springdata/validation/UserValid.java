package uz.yt.springdata.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.yt.springdata.dto.ResponseDTO;
import uz.yt.springdata.dto.UserDTO;
import uz.yt.springdata.repository.UserRepository;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserValid {
    private final UserRepository userRepository;

    public ResponseDTO<UserDTO> validUserId(UserDTO userDTO) {
        Integer id = userDTO.getId();
        if (id == null || !userRepository.existsById(id)) {
            return new ResponseDTO<>(false, -7, String.format("Berilgan userID = %d mavjud emas", id), userDTO);
        }
        return null;
    }

    public ResponseDTO<UserDTO> validName(UserDTO userDTO) {
        if (userDTO.getFirstName() == null && userDTO.getLastName() == null) {
            return new ResponseDTO<>(false, -7, "Firstname va Lastname mavjud emas. Hech bo'lmasa bittasini kiriting", userDTO);
        }
        return null;
    }

    public ResponseDTO<UserDTO> validPhoneNumber(UserDTO userDTO) {
        if (userDTO.getPhoneNumber() != null && !Pattern.matches("[+]\\d{3} \\d{2} \\d{3} \\d{2} \\d{2}", userDTO.getPhoneNumber())) {
            return new ResponseDTO<>(false, -7, "Telefon nomer formati mos kelmadi. To'g'ri format +998 XX XXX XX XX", userDTO);
        }
        return null;
    }

    public ResponseDTO<UserDTO> validAccount(UserDTO userDTO) {
        if (userDTO.getAccount().intValue() < 0)
            return new ResponseDTO<>(false, -7, "User hisob raqami manfiy son bo'lishi mumkinmas", userDTO);
        return null;
    }

    public ResponseDTO<UserDTO> validUsername(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            return new ResponseDTO<>(false, -7, String.format("USERNAME = %s foydalanuvchi mavjud", userDTO.getUsername()), userDTO);
        }
        return null;
    }

    public ResponseDTO<UserDTO> validGET(Integer id) {
        if (id != null) {
            try {
                if (userRepository.existsById(id)) {
                    return new ResponseDTO<>(true, 0, "OK", new UserDTO());
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseDTO<>(false, -2, "Ma'lumot qidirishda xatolik mavjud", null);
            }
        }
        return new ResponseDTO<>(false, -1, "Bu ID bo'yicha user mavjud emas", null);
    }

    public ResponseDTO<UserDTO> validPOST(UserDTO userDTO) {
        ResponseDTO<UserDTO> responseDTO = validUsername(userDTO);
        if (responseDTO != null) return responseDTO;
        responseDTO = validName(userDTO);
        return validPOSTandPUT(userDTO, responseDTO);
    }

    private ResponseDTO<UserDTO> validPOSTandPUT(UserDTO userDTO, ResponseDTO<UserDTO> responseDTO) {
        if (responseDTO != null) return responseDTO;
        responseDTO = validPhoneNumber(userDTO);
        if (responseDTO != null) return responseDTO;
        responseDTO = validAccount(userDTO);
        if (responseDTO != null) return responseDTO;
        return new ResponseDTO<>(true, 0, "OK", userDTO);
    }

    public ResponseDTO<UserDTO> validPUT(UserDTO userDTO) {
        ResponseDTO<UserDTO> responseDTO = validUserId(userDTO);
        if (responseDTO != null) return responseDTO;
        responseDTO = validUsername(userDTO);
        return validPOSTandPUT(userDTO, responseDTO);
    }

    public ResponseDTO<UserDTO> validDELETE(UserDTO userDTO) {
        ResponseDTO<UserDTO> responseDTO = validUserId(userDTO);
        if (responseDTO != null) return responseDTO;
        return new ResponseDTO<>(true, 0, "OK", userDTO);
    }
}
