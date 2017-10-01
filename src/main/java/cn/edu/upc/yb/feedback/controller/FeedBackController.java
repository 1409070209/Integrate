package cn.edu.upc.yb.feedback.controller;

import cn.edu.upc.yb.common.dto.SwaggerParameter;
import cn.edu.upc.yb.feedback.service.FeedBackService;
import cn.edu.upc.yb.speaktoteacher.model.Message;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lylll on 2017/6/4.
 */


@RestController
@RequestMapping("/feedback")
public class FeedBackController {

    @Autowired
    private FeedBackService feedBackService;



    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = SwaggerParameter.Authorization, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "message", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "appname", dataType = "String"),

    })
    @PostMapping("/create")
    public ResponseEntity<?> createFeedback(HttpServletRequest request,String message, String appname){
        return ResponseEntity.ok(feedBackService.doFeedBack(request,message,appname));
    }
}
