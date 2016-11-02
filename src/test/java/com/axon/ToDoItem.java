package com.axon;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import com.axon.command.CreateToDoItemCommand;
import com.axon.command.MarkCompletedCommand;
import com.axon.event.ToDoItemCompletedEvent;
import com.axon.event.ToDoItemCreatedEvent;

public class ToDoItem extends AbstractAnnotatedAggregateRoot<ToDoItem> {

    private static final long serialVersionUID = 1L;

    @AggregateIdentifier
    private String id;

    public ToDoItem() {
    }

    @CommandHandler
    public ToDoItem(CreateToDoItemCommand command) {
        apply(new ToDoItemCreatedEvent(command.getTodoId(), command.getDescription()));
    }

    @EventHandler
    public void on(ToDoItemCreatedEvent event) {
        this.id = event.getTodoId();
    }

    @CommandHandler
    public void markCompleted(MarkCompletedCommand command) {
        apply(new ToDoItemCompletedEvent(id));
    }
}
