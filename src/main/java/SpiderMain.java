import Modules.MyPageProccesser;
import Modules.MyPipeLine;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 * Created by Administrator on 2016/11/3.
 */
public class SpiderMain {
    public static void main(String args[]){
        Spider.create(new MyPageProccesser()).addUrl("http://news.ifeng.com/listpage/7837/20161113/1/rtlist.shtml").addPipeline(new MyPipeLine()).addPipeline(new ConsolePipeline()).thread(5).run();
    }
}
