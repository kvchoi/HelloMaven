package com.protobuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * http://blog.csdn.net/xiao__gui/article/details/36643949<br>
 * http://masutangu.com/2016/09/talk-about-protobuf/<br>
 * http://www.cnblogs.com/shitouer/archive/2013/04/12/google-protocol-buffers-encoding.html<br>
 * 
 * @author cairongfu.crf@alibaba-inc.com
 * @createDate 2016年11月19日
 */
public class Main {

    public static void main(String[] args) throws IOException {

        // 按照定义的数据结构，创建一个Person
        PersonMsg.Person.Builder personBuilder = PersonMsg.Person.newBuilder();
        personBuilder.setId(1);
        personBuilder.setName("叉叉哥");
        personBuilder.setEmail("xxg@163.com");
        personBuilder.addFriends("Friend A");
        personBuilder.addFriends("Friend B");
        PersonMsg.Person xxg = personBuilder.build();

        // 将数据写到输出流，如网络输出流，这里就用ByteArrayOutputStream来代替
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        xxg.writeTo(output);

        // -------------- 分割线：上面是发送方，将数据序列化后发送 ---------------

        byte[] byteArray = output.toByteArray();

        // -------------- 分割线：下面是接收方，将数据接收后反序列化 ---------------

        // 接收到流并读取，如网络输入流，这里用ByteArrayInputStream来代替
        ByteArrayInputStream input = new ByteArrayInputStream(byteArray);

        // 反序列化
        PersonMsg.Person xxg2 = PersonMsg.Person.parseFrom(input);
        System.out.println("ID:" + xxg2.getId());
        System.out.println("name:" + xxg2.getName());
        System.out.println("email:" + xxg2.getEmail());
        System.out.println("friend:");
        List<String> friends = xxg2.getFriendsList();
        for (String friend : friends) {
            System.out.println(friend);
        }
    }

}
