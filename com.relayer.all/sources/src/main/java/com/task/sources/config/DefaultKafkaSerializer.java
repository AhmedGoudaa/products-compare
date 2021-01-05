package com.task.sources.config;

import com.google.protobuf.Message;
import org.apache.kafka.common.serialization.Serializer;

public class DefaultKafkaSerializer<T extends Message> implements Serializer<T>, ProtoSerializer<T> {

	@Override
	public byte[] serialize(String s, T t) {
		return serialize(t);
	}
}
