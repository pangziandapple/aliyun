package apple.pangzi.mns;

import apple.pangzi.config.MnsAccountConfig;
import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.Message;

public class ProducerDemo {

    public void work(String messageStr) {
        CloudAccount account = new CloudAccount(MnsAccountConfig.accessId, MnsAccountConfig.accessKey, MnsAccountConfig.endpoint);
        MNSClient client = account.getMNSClient(); // 在程序中，CloudAccount以及MNSClient单例实现即可，多线程安全
        try {
            CloudQueue queue = client.getQueueRef(MnsAccountConfig.queueName);
            Message message = new Message();
            message.setMessageBody(messageStr);
            Message putMsg = queue.putMessage(message);
        } catch (ServiceException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.close();
    }

    public static void main(String[] args) {
        new ProducerDemo().work("test");
    }
}
