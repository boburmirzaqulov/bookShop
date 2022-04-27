//package uz.yt.springdata.validation;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import uz.yt.springdata.dao.User;
//import uz.yt.springdata.dto.ResponseDTO;
//import uz.yt.springdata.dto.UserDTO;
//import uz.yt.springdata.repository.UserRepository;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//import java.util.regex.Pattern;
//
//@Component
//@RequiredArgsConstructor
//public class UserValid {
//    private final UserRepository userRepository;
//
//    public ResponseDTO<UserDTO> validUserId(UserDTO userDTO) {
//        Optional<User> optionalUserDTO = userRepository.findById(userDTO.getId());
//        if (!optionalUserDTO.isPresent()) {
//            return new ResponseDTO<>(false, -7, String.format("Berilgan userID = %d mavjud emas", userDTO.getId()), userDTO);
//        }
//        String userName = optionalUserDTO.get().getUsername();
//        if (!userName.equals(userDTO.getUsername())){
//            return new ResponseDTO<>(false, -7, String.format("Berilgan userName = %s mos kelmadi", userDTO.getUsername()), userDTO);
//        }
//        return null;
//    }
//
//    public void validFirstName(UserDTO userDTO, Map<String, String> errors) {
//        String firstName = userDTO.getFirstName();
//        if (firstName==null){
//            errors.put("firstName", "FirstName null bo'lishi mumkinmas");
//            return;
//        }
//        if (firstName.isEmpty()){
//            errors.put("firstName", "FirstName bo'sh bo'lishi mumkinmas");
//        }
//    }
//
//    public void validPhoneNumber(UserDTO userDTO, Map<String, String> errors) {
//        if (userDTO.getPhoneNumber() != null && !Pattern.matches("[+]\\d{3} \\d{2} \\d{3} \\d{2} \\d{2}", userDTO.getPhoneNumber())) {
//            errors.put("phoneNumber", "Telefon nomer formati mos kelmadi. To'g'ri format +998 XX XXX XX XX");
//        }
//    }
//
//    public void validAccount(UserDTO userDTO, Map<String, String> errors) {
//        if (userDTO.getAccount().intValue() < 0)
//            errors.put("account", "User hisob raqami manfiy son bo'lishi mumkinmas");
//    }
//
//    public void validUsername(UserDTO userDTO, Map<String, String> errors) {
//        if (userRepository.existsByUsername(userDTO.getUsername())) {
//            errors.put("userName", String.format("USERNAME = %s foydalanuvchi mavjud", userDTO.getUsername()));
//        }
//    }
//
//    public Map<String, String> validPOST(UserDTO userDTO) {
//        Map<String, String> errors = new HashMap<>();
//        validUsername(userDTO, errors);
//        if (errors.size()>0) return errors;
//        validPostAndPut(userDTO, errors);
//        return errors;
//    }
//
//    private void validLastName(UserDTO userDTO, Map<String, String> errors) {
//        String lastName = userDTO.getLastName();
//        if (lastName==null){
//            errors.put("firstName", "FirstName null bo'lishi mumkinmas");
//            return;
//        }
//        if (lastName.isEmpty()){
//            errors.put("firstName", "FirstName bo'sh bo'lishi mumkinmas");
//        }
//    }
//
//    private void validPostAndPut(UserDTO userDTO, Map<String, String> errors) {
//        validFirstName(userDTO, errors);
//        validLastName(userDTO, errors);
//        validPhoneNumber(userDTO, errors);
//        validAccount(userDTO, errors);
//        validPassword(userDTO, errors);
//    }
//
//    private void validPassword(UserDTO userDTO, Map<String, String> errors) {
//        String password = userDTO.getPassword();
//        if (password == null) {
//            errors.put("password", "Password null bo'lishi mumkinmas");
//            return;
//        }
//        if (password.isEmpty()){
//            errors.put("password", "Pssword bo'sh bo'lishi mumkinmas");
//        }
//    }
//
//    public void validPUT(UserDTO userDTO, Map<String, String> errors) {
//        validPostAndPut(userDTO, errors);
//    }
//
//    public ResponseDTO<UserDTO> validDELETE(UserDTO userDTO) {
//        ResponseDTO<UserDTO> responseDTO = validUserId(userDTO);
//        if (responseDTO != null) return responseDTO;
//        return new ResponseDTO<>(true, 0, "OK", userDTO);
//    }
//}
