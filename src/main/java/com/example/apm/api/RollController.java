package com.example.apm.api;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.apm.service.ApmService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RollController {
    private static final Logger logger = LoggerFactory.getLogger(RollController.class);

    @Resource
    ApmService apmService;

    /**
     * 处理"/rolldice"GET请求的控制器方法.
     * 
     * 该方法模拟玩家掷骰子的游戏场景如果玩家提供，则记录玩家的名字和掷出的骰子数；
     * 否则，记录为匿名玩家的方法首先运行两个与APM相关的服务方法，然后生成一个随机的骰子数，
     * 再运行另一个与APM相关的服务方法最后，根据玩家是否存在，记录不同的信息，
     * 并以字符串形式返回骰子数
     * 
     * @param player 可选参数，表示玩家的名字
     * @return 投掷的骰子数的字符串表示
     */
    @GetMapping("/rolldice")
    public String index(@RequestParam("player") Optional<String> player) {
        // 运行APM服务方法A
        String a = apmService.runA();
        log.debug("runA={}",a);
        // 运行APM服务方法B，并传入固定参数
        String b = apmService.runB("kkkkkk");
        log.debug("runB={}",b);
        // 生成1到6之间的随机数，模拟掷骰子
        int result = this.getRandomNumber(1, 6);
        // 运行APM服务方法C，并传入掷骰子的结果
        String c = apmService.runC(result);
        log.debug("runC={}",c);
        // 检查player是否存在，如果存在，则记录玩家掷骰子的信息
        if (player.isPresent()) {
            logger.info("{} is rolling the dice: {}", player.get(), result);
        } else {
            // 如果player不存在，则记录匿名玩家掷骰子的信息
            logger.info("Anonymous player is rolling the dice: {}", result);
        }
        // 返回掷骰子的结果，以字符串形式
        return Integer.toString(result);
    }

    public int getRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
