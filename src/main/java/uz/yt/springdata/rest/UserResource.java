//package uz.yt.springdata.rest;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.web.bind.annotation.*;
//import uz.yt.springdata.dto.ResponseDTO;
//import uz.yt.springdata.dto.UserDTO;
//import uz.yt.springdata.service.UserService;
//
//@RestController
//@RequiredArgsConstructor
//public class UserResource {
//    private final UserService userService;
//
//    @GetMapping("/users")
//    public ResponseDTO<Page<UserDTO>> getAllUsers(@RequestParam Integer size, @RequestParam Integer page){
//        return userService.getAllUsers(size, page);
//    }
//
//    @GetMapping("/user/{id}")
//    public ResponseDTO<UserDTO> getUser(@PathVariable Integer id){
//        return userService.getUser(id);
//    }
//
//    @PostMapping("/user")
//    public ResponseDTO<UserDTO> addUser(@RequestBody UserDTO userDTO){
//        return userService.addUser(userDTO);
//    }
//
//    @PutMapping("/user")
//    public ResponseDTO<UserDTO> updateUser(@RequestBody UserDTO userDTO){
//        return userService.updateUser(userDTO);
//    }
//
//    @DeleteMapping("/user")
//    public ResponseDTO<UserDTO> deleteUser(@RequestBody UserDTO userDTO){
//        return userService.deleteUser(userDTO);
//    }
//}
