/*
 * Copyright (c) 2010-2012. Axon Framework
 *
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

package org.axonframework.eventstore.jpa.criteria;

/**
 * Implementations of the NOT EQUALS operator for a JPA Event Store.
 *
 * @author Allard Buijze
 * @since 2.0
 */
public class NotEquals extends JpaCriteria {

    private final JpaProperty property;
    private final Object expression;

    /**
     * Initializes a NOT EQUALS operator where the given <code>property</code> must not be equal to the given
     * <code>expression</code>.
     *
     * @param property   The property to match
     * @param expression The expression to match against. May be <code>null</code>.
     */
    public NotEquals(JpaProperty property, Object expression) {
        this.property = property;
        this.expression = expression;
    }

    @Override
    public void parse(String entryKey, StringBuilder whereClause, ParameterRegistry parameters) {
        property.parse(entryKey, whereClause);
        if (expression == null) {
            whereClause.append(" IS NOT NULL");
        } else {
            whereClause.append(" <> ");
            if (expression instanceof JpaProperty) {
                ((JpaProperty) expression).parse(entryKey, whereClause);
            } else {
                whereClause.append(parameters.register(expression));
            }
        }
    }
}
