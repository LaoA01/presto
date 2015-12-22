/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.transaction;

import com.facebook.presto.spi.transaction.ConnectorTransactionHandle;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class LegacyTransactionHandle
        implements ConnectorTransactionHandle
{
    private final String connectorId;
    private final UUID uuid;

    @JsonCreator
    public LegacyTransactionHandle(
            @JsonProperty("connectorId") String connectorId,
            @JsonProperty("uuid") UUID uuid)
    {
        this.connectorId = requireNonNull(connectorId, "connectorId is null");
        this.uuid = requireNonNull(uuid, "uuid is null");
    }

    public static LegacyTransactionHandle create(String connectorId)
    {
        return new LegacyTransactionHandle(connectorId, UUID.randomUUID());
    }

    @JsonProperty
    public String getConnectorId()
    {
        return connectorId;
    }

    @JsonProperty
    public UUID getUuid()
    {
        return uuid;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(connectorId, uuid);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final LegacyTransactionHandle other = (LegacyTransactionHandle) obj;
        return Objects.equals(this.connectorId, other.connectorId)
                && Objects.equals(this.uuid, other.uuid);
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("connectorId", connectorId)
                .add("uuid", uuid)
                .toString();
    }
}