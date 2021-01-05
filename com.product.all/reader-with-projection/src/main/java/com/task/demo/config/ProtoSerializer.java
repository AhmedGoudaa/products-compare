package com.task.demo.config;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;


public interface ProtoSerializer<T extends Message> {

	default byte[] serialize(T obj) {
		return obj.toByteArray();
	}

	default T deserialize(Parser<T> parser, byte[] data) {
		try {
			return parser.parseFrom(data);
		}
		catch (InvalidProtocolBufferException e) {
			return null;
		}
	}

}
