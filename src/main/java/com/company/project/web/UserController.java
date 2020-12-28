package com.company.project.web;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.User;
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
