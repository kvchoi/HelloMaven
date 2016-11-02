package com.axon;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import com.axon.command.CreateToDoItemCommand;
import com.axon.command.MarkCompletedCommand;
import com.axon.event.ToDoItemCompletedEvent;
import com.axon.event.ToDoItemCreatedEvent;

/**
 * 
 * given-when-then单元测试
 * 
 * @author cairongfu.crf@alibaba-inc.com
 * @createDate 2016年11月2日
 */
public class ToDoItemTest {

    private FixtureConfiguration<ToDoItem> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture(ToDoItem.class);
    }

    @Test
    public void testCreateToDoItem() throws Exception {
        fixture.given().when(new CreateToDoItemCommand("todo1", "need to implement the aggregate"))
                .expectEvents(new ToDoItemCreatedEvent("todo1", "need to implement the aggregate"));
    }

    @Test
    public void testMarkToDoItemAsCompleted() throws Exception {
        fixture.given(new ToDoItemCreatedEvent("todo1", "need to implement the aggregate"))
                .when(new MarkCompletedCommand("todo1"))
                .expectEvents(new ToDoItemCompletedEvent("todo1"));
    }

}