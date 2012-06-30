/**
 * Copyright 2012 Nikita Koksharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.corundumstudio.socketio.parser;

import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class DecoderEventPacketTest extends DecoderBaseTest {

    @Test
    public void testDecode() throws IOException {
        Packet packet = decoder.decodePacket("5:::{\"name\":\"woot\"}");
        Assert.assertEquals(PacketType.EVENT, packet.getType());
        Assert.assertEquals("woot", packet.getName());
    }

    @Test
    public void testDecodeWithMessageIdAndAck() throws IOException {
        Packet packet = decoder.decodePacket("5:1+::{\"name\":\"tobi\"}");
        Assert.assertEquals(PacketType.EVENT, packet.getType());
        Assert.assertEquals(1, (long)packet.getId());
        Assert.assertEquals("data", packet.getAck());
        Assert.assertEquals("tobi", packet.getName());
    }

    @Test
    public void testDecodeWithData() throws IOException {
        Packet packet = decoder.decodePacket("5:::{\"name\":\"edwald\",\"args\":[{\"a\": \"b\"},2,\"3\"]}");
        Assert.assertEquals(PacketType.EVENT, packet.getType());
        Assert.assertEquals("edwald", packet.getName());
        Assert.assertEquals(3, packet.getArgs().size());
        Map obj = (Map) packet.getArgs().get(0);
        Assert.assertEquals("b", obj.get("a"));
        Assert.assertEquals(2, packet.getArgs().get(1));
        Assert.assertEquals("3", packet.getArgs().get(2));
    }

}
