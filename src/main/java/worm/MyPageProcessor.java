package worm;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

public class MyPageProcessor implements PageProcessor {
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    @Override
    public void process(Page page) {
// 部分二：定义如何抽取页面信息，并保存下来
        //未实现
       // List<String> links = page.getHtml().regex("(http://www.dytt8.net/html/gndy/^[A-Za-z]+$/^[1-9]d*$/^[1-9]d*$)").all();
        //抓取部分电影链接
        //List<String> links = page.getHtml().xpath("//div[@class='co_content8']/ul/table/tbody/tr//td[@class='inddline']/a/@href").all();
        //无差别抓取，但是受字段取值代码的约束
       // List<String> links = page.getHtml().links().all();
        List<String> links = page.getHtml().regex("/html/gndy/\\w+/\\d++/\\d+.html").all();
        page.addTargetRequests(links);
        page.putField("name",page.getHtml().xpath("//div[@class='title_all']/h1/font/text()").toString());
        page.putField("link",page.getHtml().xpath("//div[@class='co_content8']/ul/div/span/table/tbody/tr/td/a/@href").toString());
        if(page.getResultItems().get("link")==null){
            //skip this page
            page.setSkip(true);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new MyPageProcessor())
                .addUrl("http://www.dytt8.net/")
                .thread(5)
                .addPipeline(new ConsolePipeline())
                .run();
    }
}
