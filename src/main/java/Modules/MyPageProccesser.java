package Modules;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public class MyPageProccesser implements PageProcessor {

    private Site site = Site.me().setRetryTimes(5).setSleepTime(1000).setCycleRetryTimes(3).setUseGzip(true);

    public void process(Page page) {
        if(page.getRequest().getUrl().contains("http://news.ifeng.com/listpage")){
            page.addTargetRequest(page.getHtml().xpath("//div[@id='backDay']").links().get());
            page.addTargetRequests(page.getHtml().xpath("//div[@class='newsList']/ul").links().all());
        }else {
            page.putField("title",page.getHtml().xpath("//div[@id='artical']/h1/text()").get().trim());
            page.putField("Original Source",page.getHtml().xpath("//div[@id='artical_sth']/p//span[@itemprop='name']/a/text()").get().trim());
            page.putField("author",page.getHtml().xpath("//div[@id='artical_sth2']/p[@class='zb_ph pc_none_new']").get().trim().split("：")[1].split(" ")[0]);
            page.putField("time",page.getHtml().xpath("//head/meta[@name='og:time']/@content").get().trim().trim());
            page.putField("category",page.getHtml().xpath("//div[@class='searchDiv02 js_titleName ss_none']/allText()"));
            page.putField("url",page.getUrl().get().trim());
            String keywords = page.getHtml().xpath("//head/meta[@name='keywords']/@content").get().trim();
            if(keywords.equals(""))
                keywords = null;
            page.putField("keywords",keywords);

            StringBuffer stringBuffer = new StringBuffer();
            List<String> lines = page.getHtml().xpath("//div[@id='artical']//div[@id='artical_real']//div[@id='main_content']/p/text(1)").all();
            for(String line : lines){
                if(line.equals(""))
                    continue;
                else
                    stringBuffer.append(line+"\n");
            }
            page.putField("content",stringBuffer);


        }
    }

    public Site getSite() {
        return site;
    }
}
