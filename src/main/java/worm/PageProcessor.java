package worm;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

public interface PageProcessor {
    public void process(Page page);
    public Site getSite();
}
