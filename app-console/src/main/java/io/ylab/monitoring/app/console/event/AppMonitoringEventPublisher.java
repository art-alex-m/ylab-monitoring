package io.ylab.monitoring.app.console.event;

import io.ylab.monitoring.domain.core.event.MonitoringEvent;
import io.ylab.monitoring.domain.core.event.MonitoringEventHandler;
import io.ylab.monitoring.domain.core.event.MonitoringEventPublisher;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AppMonitoringEventPublisher implements MonitoringEventPublisher {

    private final List<HandlerContainer> handlerContainers = new LinkedList<>();

    @Override
    public boolean publish(MonitoringEvent event) {

        for (HandlerContainer handlerContainer : handlerContainers) {
            try {
                if (handlerContainer.supports(event.getClass())) {
                    handlerContainer.handler.handle(event);
                }
            } catch (Exception ignored) {
            }
        }

        return true;
    }

    /**
     * Добавляет слушателя событий
     *
     * @param handler Обработчик
     * @param events  Список событий
     * @return Истина
     */
    @SafeVarargs
    public final AppMonitoringEventPublisher subscribe(MonitoringEventHandler handler,
            Class<? extends MonitoringEvent>... events) {

        handlerContainers.add(new HandlerContainer(handler, Arrays.stream(events).toList()));

        return this;
    }

    /**
     * Контейнер для хранения обработчиков
     */
    @AllArgsConstructor
    @Getter
    private static class HandlerContainer {
        private final MonitoringEventHandler handler;

        private final List<Class<? extends MonitoringEvent>> events;

        /**
         * Проверяет, что событие может быть обработано данным обработчиком
         *
         * @param eventObj Класс полученного события
         * @return Истина, если класс, привязанного к обработчику события, достижим из полученного
         */
        public boolean supports(Class<?> eventObj) {
            for (Class<?> supportEvent : events) {
                if (supportEvent.isAssignableFrom(eventObj)) {
                    return true;
                }
            }
            return false;
        }
    }
}
