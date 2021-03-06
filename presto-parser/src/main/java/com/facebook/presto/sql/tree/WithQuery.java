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
package com.facebook.presto.sql.tree;

import java.util.List;
import java.util.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

public class WithQuery
        extends Node
{
    private final String name;
    private final Query query;
    private final List<String> columnNames;

    public WithQuery(String name, Query query, List<String> columnNames)
    {
        this.name = QualifiedName.of(checkNotNull(name, "name is null")).getParts().get(0);
        this.query = checkNotNull(query, "query is null");
        this.columnNames = columnNames;
    }

    public String getName()
    {
        return name;
    }

    public Query getQuery()
    {
        return query;
    }

    public List<String> getColumnNames()
    {
        return columnNames;
    }

    @Override
    public <R, C> R accept(AstVisitor<R, C> visitor, C context)
    {
        return visitor.visitWithQuery(this, context);
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
                .add("name", name)
                .add("query", query)
                .add("columnNames", columnNames)
                .omitNullValues()
                .toString();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, query, columnNames);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        WithQuery o = (WithQuery) obj;
        return Objects.equals(name, o.name) &&
                Objects.equals(query, o.query) &&
                Objects.equals(columnNames, o.columnNames);
    }
}
