package org.helixcs.rmt.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/17/2020.
 * @Desc:
 */

@Controller
@RequestMapping("ws")
public class IndexController {

    @RequestMapping("index")
    public String index() {
        return "classpath:/rmt-support/index.html";
    }

}
