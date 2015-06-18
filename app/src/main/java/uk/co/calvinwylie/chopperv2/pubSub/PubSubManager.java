package uk.co.calvinwylie.chopperv2.pubSub;

import android.util.Log;

/**
 * Created by Calvin on 15/05/2015.
 */
public class PubSubManager {

    private String tag = this.getClass().getSimpleName();

    Topic m_Test = new Topic();

    public Publisher createPublisher(TopicName topic){
        Topic returnedTopic = getTopic(topic);
        return new Publisher(returnedTopic);
    }

    public void subscribeTo(TopicName topic, Subscriber subscriber){
        getTopic(topic).subscribe(subscriber);
    }

    private Topic getTopic(TopicName topic){
        switch(topic){
            case Test:
                return m_Test;
            default:
                Log.e(tag, "Topic doesn't exist");
                break;
        }
        return null;
    }


   // private Publisher m_Publisher;

// to be put in publishing class constructor
//    m_Publisher = pubSubManager.createPublisher(TopicName.Test);
//    pubSubManager.subscribeTo(TopicName.Test, new TestSubscriber());

//to publish.
    //m_Publisher.publishToTopic();


    // to be put in subscribing class.
//    private class TestSubscriber extends Subscriber{
//
//        @Override
//        public void update() {
//            Log.e(tag, "DO THE THING");
//        }
//    }

}
