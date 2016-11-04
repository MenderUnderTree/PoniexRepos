package Modules;

import Modules.DAO.articalMapper;
import Modules.Model.artical;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.SimpleFormatter;

/**
 * Created by Administrator on 2016/11/3.
 */
public class MyPipeLine implements Pipeline {
    ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>();
    public void process(ResultItems resultItems, Task task) {
        SqlSession sqlSession = Util.getInstance().getSessionFactory().openSession();
        artical a = new artical();
        a.setAuthor(resultItems.get("author").toString());
        a.setCategory(resultItems.get("category").toString());
        a.setContemt(resultItems.get("content").toString());
        a.setUrl(resultItems.get("url").toString());
        a.setKeywords(resultItems.get("keywords").toString());
        a.setOrignalResource(resultItems.get("Original Source").toString());
        a.setTitle(resultItems.get("title").toString());

        StringBuffer stringBuffer = new StringBuffer(resultItems.get("time").toString().replace('年','-').replace('月','-'));
        stringBuffer.deleteCharAt(10);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            a.setTime(simpleDateFormat.parse(stringBuffer.toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        articalMapper AM = sqlSession.getMapper(articalMapper.class);
        try {
            AM.insert(a);
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }

    }
}
