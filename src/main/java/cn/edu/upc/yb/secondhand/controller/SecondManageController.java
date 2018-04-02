package cn.edu.upc.yb.secondhand.controller;

import cn.edu.upc.yb.secondhand.dto.Message;
import cn.edu.upc.yb.secondhand.model.Article;
import cn.edu.upc.yb.secondhand.model.Review;
import cn.edu.upc.yb.secondhand.model.User;
import cn.edu.upc.yb.secondhand.repository.ArticleRepository;
import cn.edu.upc.yb.secondhand.repository.ReviewRepository;
import cn.edu.upc.yb.secondhand.repository.UserRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/secondhand/manage")
public class SecondManageController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ReviewRepository reviewRepository;;

    @Autowired
    private UserRepository userRepository;


    /*
    删除非法用户
     */
    @ApiOperation("删除非法用户")
    @ApiImplicitParam(name = "userid",value = "用户id",dataType = "int",paramType = "query")
    @RequestMapping(value = "/deleteuser",method = RequestMethod.GET)
    public Object deleteUser(int userId){
        User user = userRepository.findByUserid(userId);
        user.setIsdelete(true);
        return new Message(1,"delete user success");
    }

    /*
    删除非法物品
     */
    @ApiOperation("删除非法物品")
    @ApiImplicitParam(name = "articleid",value = "物品id",dataType = "int",paramType = "query")
    @RequestMapping(value = "/deletearticle",method = RequestMethod.GET)
    public Object deleteArticle(int articleid){
        Article article=articleRepository.findOne(articleid);
        article.setIsdeal(-2);
        return new Message(1,"delete article success");
    }

    /*
    删除非法评论
     */
    @ApiOperation("删除非法评论")
    @ApiImplicitParam(name = "reviewid",value = "评论id",dataType = "int",paramType = "query")
    @RequestMapping(value = "/deletereview",method = RequestMethod.GET)
    public Object deleteReview(int reviewid){
        Review review=reviewRepository.findOne(reviewid);
        review.setIsdelete(-2);
        return new Message(1,"delete review success");
    }

    /*
    查看所有用户信息
     */
    @ApiOperation("查看所有用户信息")
    @RequestMapping(value = "/alluser",method = RequestMethod.GET)
    public Object allUser(){
        return userRepository.findAll();
    }

    /*
    查看所有物品
     */
    @ApiOperation("查看所有物品")
    @RequestMapping(value = "/allarticle",method = RequestMethod.GET)
    public Object allArticle(){
        return articleRepository.findAll();
    }

    /*
    查看所有评论
     */
    @ApiOperation("查看所有评论")
    @RequestMapping(value = "/allreview",method = RequestMethod.GET)
    public Object allReview(){
        return reviewRepository.findAll();
    }
}
