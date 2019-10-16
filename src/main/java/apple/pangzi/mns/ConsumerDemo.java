package apple.pangzi.mns;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.Message;

public class ConsumerDemo {

    public void work() {
        CloudAccount account = new CloudAccount(MnsAccount.accessId, MnsAccount.accessKey, MnsAccount.endpoint);
        MNSClient client = account.getMNSClient();
        try {
            CloudQueue queue = client.getQueueRef(MnsAccount.queueName);
            while (true){
                Message popMsg = queue.popMessage(30);
                if (popMsg != null) {
                    System.out.println("message body: " + popMsg.getMessageBodyAsString());
                    System.out.println("message id: " + popMsg.getMessageId());
                    queue.deleteMessage(popMsg.getReceiptHandle());
                } else {
                    System.out.println("message not exist in TestQueue");
                }
            }

        } catch (ServiceException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.close();
    }

    public static void main(String[] args) {
        new ConsumerDemo().work();
    }
}
