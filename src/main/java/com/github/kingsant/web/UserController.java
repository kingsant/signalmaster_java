package com.github.kingsant.web;
import com.github.kingsant.core.Result;
import com.github.kingsant.core.ResultGenerator;
import com.github.kingsant.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* Created by CodeGenerator on 2018/09/03.
*/
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/add")
    public Result add(User user) {
        return ResultGenerator.genSuccessResult();
    }

}
