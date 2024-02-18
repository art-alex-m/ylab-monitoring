package io.ylab.monitoring.app.jakartaee.mapper;

import io.ylab.monitoring.app.jakartaee.out.AppAuditItem;
import io.ylab.monitoring.domain.audit.model.AuditItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

/**
 * Преобразование в дто лога аудита
 *
 * <p>
 * <a href="https://www.baeldung.com/mapstruct">Quick Guide to MapStruct</a>
 * </p>
 */
@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface AuditItemAppAuditItemMapper {
    List<AppAuditItem> from(List<AuditItem> auditItemList);
}
