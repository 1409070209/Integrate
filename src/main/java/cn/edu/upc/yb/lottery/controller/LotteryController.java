package cn.edu.upc.yb.lottery.controller;

import cn.edu.upc.yb.common.dto.SwaggerParameter;
import cn.edu.upc.yb.common.util.QRcode;
import cn.edu.upc.yb.lottery.repository.LotteryListRepository;
import cn.edu.upc.yb.lottery.service.LotteryService;
import cn.edu.upc.yb.lottery.utils.ResponseBean;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * @author liliangbin dumpling1520@gmail.com
 * @date 2018/2/22  10:30
 */


@RestController
@RequestMapping("/lottery")
public class LotteryController {

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private LotteryListRepository lotteryListRepository;

    @Value("${lottery.fonturl}")
    private String fonturl;

    @GetMapping("")
    @ApiOperation(value = "", notes = "获取抽奖的列表")
    @ApiImplicitParams({

            @ApiImplicitParam(paramType = "query", name = SwaggerParameter.Authorization, value = "token", dataType = "String")
    })
    public Object lotteryIndex(HttpServletRequest request) {

        return lotteryService.getLotteryList();
    }

    @PostMapping("/userInfo")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = SwaggerParameter.Authorization, value = "token", dataType = "String")
    })
    public Object userInfo(HttpServletRequest request) throws IOException {

        return lotteryService.getUserInfo(request);
    }

    @PostMapping("/createLottery")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = SwaggerParameter.Authorization, value = "token", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "userName", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "lotteryName", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "lotteryIntro", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "lotteryTimeBegin", dataType = "Timestamp"),
            @ApiImplicitParam(paramType = "query", name = "lotteryTimeEnd", dataType = "Timestamp")

    })
    public Object createLottery(String Authorization,String userName, String lotteryName, String lotteryIntro, Timestamp lotteryTimeBegin,Timestamp lotteryTimeEnd) {

        return lotteryService.createLottery(Authorization,userName,lotteryName,lotteryIntro,lotteryTimeBegin,lotteryTimeEnd);
    }

    @PostMapping("/createPrizes")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = SwaggerParameter.Authorization, value = "token", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "prizeName", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "prizeIntro", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "prizePercentage", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "prizeTotalNumber", dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "creatorId", dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "lotteryId", dataType = "long")

    })
    public Object createPrizes(String prizeName,String prizeIntro,String prizePercentage,long prizeTotalNumber,long creatorId,long lotteryId) {

        return lotteryService.createPrizes(prizeName,prizeIntro,prizePercentage,prizeTotalNumber,creatorId,lotteryId);
    }

    @PostMapping("/ispass")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = SwaggerParameter.Authorization, value = "抽奖Id", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "lotteryId", value = "抽奖Id", dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "passcode", value = "passcode", dataType = "int")
    })
    public Object isPass(long lotteryId, int passcode) {
        return lotteryService.isPass(lotteryId, passcode);
    }


    @GetMapping("/dolottery")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = SwaggerParameter.Authorization, value = "抽奖Id", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "lotteryId", value = "抽奖Id", dataType = "long")

    })
    public Object doLottery(HttpServletRequest request) throws IOException {

        return lotteryService.doLottery(request);

    }

    @PostMapping("/prizeList")

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = SwaggerParameter.Authorization, value = "抽奖Id", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "lotteryId", value = "抽奖Id", dataType = "long")
    })
    public Object prizeList(long lotteryId) {

        return  new ResponseBean(1,"抽奖列表",lotteryService.message(lotteryId));
    }


    @PostMapping("/prizes")

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = SwaggerParameter.Authorization, value = "抽奖Id", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "lotteryId", value = "抽奖Id", dataType = "long")
    })
    public Object prizes(long lotteryId) {

        return  new ResponseBean(1,"奖项列表",lotteryService.prizes(lotteryId));
    }

    @PostMapping("/fuzzy")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = SwaggerParameter.Authorization, value = "抽奖Id", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "passcode", value = "passcode", dataType = "long")

    })


    public Object findall(int passcode) {

        return new ResponseBean(1,"通过passscode查找", lotteryService.findbycode(passcode));
    }


    @GetMapping("/share")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = SwaggerParameter.Authorization, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "lotteryId", dataType = "long")
    })
    public Object pic(long lotteryId) {

        QRcode qRcode = new QRcode();

                String url = qRcode.generateUrl( fonturl + "?id=" + lotteryId);
                return new ResponseBean(1,"分享成功",url);
    }

    @PostMapping("/findone")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = SwaggerParameter.Authorization, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "lotteryId", dataType = "long")
    })
    public Object findone(long lotteryId){
        return new ResponseBean(1,"抽奖信息",lotteryListRepository.findById(lotteryId));
    }

}
