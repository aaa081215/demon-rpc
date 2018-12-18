import com.alibaba.fastjson.JSONObject;
import com.demon.client.annotation.RemoteInvoke;
import com.demon.client.parame.Response;
import com.demon.user.bean.User;
import com.demon.user.remote.UserRemote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by wangpengpeng on 2018/12/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Tcp.class)
@ComponentScan("com.demon")
public class Tcp {

    @RemoteInvoke
    private UserRemote userRemote;

    @Test
    public void mytest() {
        User user = new User();
        user.setId(123123);
        user.setName("王鹏鹏");
        Response response = userRemote.saveUser(user);
        System.out.println(JSONObject.toJSONString(response));
    }
//    @Test
//    public void mytest2(){
//        List<User> list= new ArrayList<User>();
//        User user = new User();
//        user.setId(123123);
//        user.setName("王鹏鹏");
//        list.add(user);
//        ClientRequest request = new ClientRequest();
//        request.setCommand("com.demon.demo.controller.UserController.saveList");
//        request.setContent(list);
//        ClientResponse response = TcpClient.send(request);
//        System.out.println(response.getId()+"***"+response.getResult());
//    }
}
