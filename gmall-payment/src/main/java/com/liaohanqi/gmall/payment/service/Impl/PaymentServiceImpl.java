package com.liaohanqi.gmall.payment.service.Impl;

import com.liaohanqi.gmall.bean.PaymentInfo;
import com.liaohanqi.gmall.payment.mapper.PaymentInfoMapper;
import com.liaohanqi.gmall.service.PaymentService;
import com.liaohanqi.gmall.util.ActiveMQUtil;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQMessage;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import javax.jms.*;
import java.util.Map;

public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentInfoMapper paymentInfoMapper;

    @Autowired
    ActiveMQUtil activeMQUtil;

    @Override
    public void sava(PaymentInfo paymentInfo) {

        paymentInfoMapper.insertSelective(paymentInfo);
    }

    @Override
    public void update(PaymentInfo paymentInfo) {

        Example example = new Example(PaymentInfo.class);
        example.createCriteria().andEqualTo("orderSn",paymentInfo.getOrderSn());
        paymentInfoMapper.updateByExampleSelective(paymentInfo,example);
    }

    //使用消息队列，此时作为消息的生产者，对支付成功的消息通知放在ActiveMQ中，等待别的模块调用
    @Override
    public void sendPaySuccessQueue(PaymentInfo paymentInfo) {

        //连接ativeMQ
        ConnectionFactory connectionFactory = activeMQUtil.getConnectionFactory();

        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        //将消息传入ActiveMQ队列中，此时是消息的生产者
        try {
            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
            //设置队列名：PAY_SUCEESS_QUEUE
            Queue pay_suceess_queue = session.createQueue("PAY_SUCEESS_QUEUE");
            MessageProducer producer = session.createProducer(pay_suceess_queue);

            //打包消息
            ActiveMQMapMessage map = new ActiveMQMapMessage();
            map.setString("out_trade_no", paymentInfo.getOrderSn());
            map.setString("status","success");
            //发送消息
            producer.send(map);
            //提交
            session.commit();
            //关闭
            session.close();
            //ActiveMQ关闭
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendPayCheckQueue(PaymentInfo paymentInfo, Long count) {

        ConnectionFactory connectionFactory = activeMQUtil.getConnectionFactory();

        Connection connection = null;

        try {
            connection = connectionFactory.createConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        try {
            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
            Queue pay_success_queue = session.createQueue("PAY_CHECK_QUEUE");
            MessageProducer producer = session.createProducer(pay_success_queue);

            ActiveMQMapMessage activeMQMapMessage = new ActiveMQMapMessage();
            activeMQMapMessage.setString("out_trade_no",paymentInfo.getOrderSn());
            activeMQMapMessage.setLong("count",count);

            activeMQMapMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,25*1000);

            producer.send(activeMQMapMessage);
            session.commit();
            session.close();
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
