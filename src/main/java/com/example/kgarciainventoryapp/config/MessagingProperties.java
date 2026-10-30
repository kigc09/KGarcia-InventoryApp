package com.example.kgarciainventoryapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="item.messaging")
public class MessagingProperties {
    @Value("${item.messaging.item-status-topic}")
    private String lowInventoryTopic;
    private String loggingGroup;
    private String emailGroup;
    private int partitions = 3;
    private int replicas = 1;

    public MessagingProperties() {}

    public String getLowInventoryTopic() {
        return lowInventoryTopic;
    }

    public void setLowInventoryTopic(String lowInventoryTopic) {
        this.lowInventoryTopic = lowInventoryTopic;
    }

    public String getLoggingGroup() {
        return loggingGroup;
    }

    public void setLoggingGroup(String loggingGroup) {
        this.loggingGroup = loggingGroup;
    }

    public String getEmailGroup() {
        return emailGroup;
    }

    public void setEmailGroup(String emailGroup) {
        this.emailGroup = emailGroup;
    }

    public int getPartitions() {
        return partitions;
    }

    public void setPartitions(int partitions) {
        this.partitions = partitions;
    }

    public int getReplicas() {
        return replicas;
    }

    public void setReplicas(int replicas) {
        this.replicas = replicas;
    }
}
