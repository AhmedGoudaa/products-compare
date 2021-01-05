package com.task.demo.config;

import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import org.apache.kafka.common.serialization.Deserializer;

public class DefaultKafkaDeserializer<T extends Message> implements Deserializer<T>, ProtoSerializer<T> {
	private final Parser<T> parser;

	public DefaultKafkaDeserializer(Parser<T> parser) {
		this.parser = parser;
	}

	@Override
	public T deserialize(String s, byte[] bytes) {
		return deserialize(parser, bytes);
	}
}
