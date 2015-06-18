package uk.co.calvinwylie.chopperv2.pubSub;

/**
 * Created by Calvin on 15/05/2015.
 */
public class Publisher {
    private Topic m_Topic;

    public Publisher(Topic topic){
        m_Topic = topic;
    }
    public void publishToTopic(){
        m_Topic.publish();
    }
}
