package uk.co.calvinwylie.chopperv2.pubSub;

import java.util.ArrayList;

/**
 * Created by Calvin on 22/05/2015.
 */
public class Topic {
    private ArrayList<Subscriber> m_SubscriberList = new ArrayList<>();

    public void subscribe(Subscriber subscriber){
        m_SubscriberList.add(subscriber);
    }
    public void unsubscribe(Subscriber subscriber){
        m_SubscriberList.remove(subscriber);
    }
    public void publish(){
        for (Subscriber sub: m_SubscriberList){
            sub.update();
        }
    }

}
