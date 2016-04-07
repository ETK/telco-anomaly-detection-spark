package com.mapr.cell.spark;


import com.mapr.cell.common.CDR;
import com.mapr.cell.common.Config;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.v09.KafkaUtils;
import org.ojai.Document;

import java.util.Set;

public class Spark {
    JavaStreamingContext jssc;

    public Spark(int duration) {
        SparkConf sparkConf = new SparkConf();
//        for(Map.Entry<String, String> entry : Config.getConfig().getPrefixedMap("spark.").entrySet()) {
//            sparkConf.set(entry.getKey(), entry.getValue());
//        }
        sparkConf.setAppName("CDR-Analytics");
        sparkConf.setMaster("local[4]");
        jssc = new JavaStreamingContext(sparkConf, new Duration(duration));
    }
    

    public JavaPairInputDStream<String, String> getKafkaInputStream(Set<String> topics){
        return KafkaUtils.createDirectStream(jssc, String.class, String.class,
                        Config.getConfig().getPrefixedMap("kafka."),
                        topics);
    }

    public void start(){
        jssc.start();
        jssc.awaitTermination();
    }


    public JavaPairDStream countStats(JavaPairDStream<String, CDR> stream){

        return stream;
    }

}