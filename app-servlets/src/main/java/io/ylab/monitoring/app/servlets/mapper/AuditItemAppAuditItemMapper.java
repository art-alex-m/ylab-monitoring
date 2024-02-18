package io.ylab.monitoring.app.servlets.mapper;

import io.ylab.monitoring.app.servlets.out.AppAuditItem;
import io.ylab.monitoring.domain.audit.model.AuditItem;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Преобразование в дто лога аудита
 *
 * <p>
 * <a href="https://www.baeldung.com/mapstruct">Quick Guide to MapStruct</a>
 * </p>
 */
@Mapper
public interface AuditItemAppAuditItemMapper {
    List<AppAuditItem> from(List<AuditItem> auditItemList);

    AppAuditItem from(AuditItem auditItem);
}
