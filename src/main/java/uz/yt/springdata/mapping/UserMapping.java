package uz.yt.springdata.mapping;

import uz.yt.springdata.dao.User;
import uz.yt.springdata.dto.UserDTO;

public class UserMapping {
    public static UserDTO toDto(User user) throws NullPointerException{
        return user == null ? null : new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getAccount(),
                user.getUsername(),
                user.getPassword()
        );
    }

    public static User toEntity(UserDTO userDTO){
        return userDTO == null ? null : new User(
                userDTO.getId(),
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getPhoneNumber(),
                userDTO.getAccount(),
                userDTO.getUsername(),
                userDTO.getPassword()
        );
    }

    public static void setDto(UserDTO userDTO, User user){
        if (user == null){
            return;
        }
        userDTO.setId(user.getId());
        userDTO.setAccount(user.getAccount());
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPassword(user.getPassword());
        userDTO.setPhoneNumber(user.getPhoneNumber());
    }
}
