/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.mongodb;

import com.mongodb.ReadPreference;
import org.apache.camel.Endpoint;
import org.junit.Test;

public class MongoDbReadPreferenceOptionTest extends AbstractMongoDbTest {
    
    private MongoDbEndpoint endpoint;

    @Test
    public void testInvalidReadPreferenceOptionValue() throws Exception {
        try {
            createMongoDbEndpoint("mongodb:myDb?database={{mongodb.testDb}}&readPreference=foo");
            fail("Should have thrown exception");
        } catch (IllegalArgumentException iae) {
            assertTrue(iae.getMessage(), iae.getMessage().endsWith("No match for read preference of foo"));
        }
    }

    @Test
    public void testNoReadPreferenceOptionValue() throws Exception {
        endpoint = createMongoDbEndpoint("mongodb:myDb?database={{mongodb.testDb}}");
        assertNull(endpoint.getReadPreference());
        assertSame(ReadPreference.primary(), endpoint.getMongoConnection().getReadPreference()); // the default is primary
    }

    @Test
    public void testPrimaryReadPreferenceOptionValue() throws Exception {
        endpoint = createMongoDbEndpoint("mongodb:myDb?database={{mongodb.testDb}}&readPreference=primary");
        assertSame(ReadPreference.primary(), endpoint.getReadPreference());
        assertSame(ReadPreference.primary(), endpoint.getMongoConnection().getReadPreference());
    }

    @Test
    public void testSecondaryReadPreferenceOptionValue() throws Exception {
        endpoint = createMongoDbEndpoint("mongodb:myDb?database={{mongodb.testDb}}&readPreference=secondary");
        assertSame(ReadPreference.secondary(), endpoint.getReadPreference());
        assertSame(ReadPreference.secondary(), endpoint.getMongoConnection().getReadPreference());
    }

    @Test
    public void testNearestReadPreferenceOptionValue() throws Exception {
        endpoint = createMongoDbEndpoint("mongodb:myDb?database={{mongodb.testDb}}&readPreference=nearest");
        assertSame(ReadPreference.nearest(), endpoint.getReadPreference());
        assertSame(ReadPreference.nearest(), endpoint.getMongoConnection().getReadPreference());
    }

    private MongoDbEndpoint createMongoDbEndpoint(String uri) throws Exception {
        MongoDbComponent component = context().getComponent("mongodb", MongoDbComponent.class);
        Endpoint endpoint = component.createEndpoint(uri);
        endpoint.start();
        return (MongoDbEndpoint) endpoint;

    }

}
