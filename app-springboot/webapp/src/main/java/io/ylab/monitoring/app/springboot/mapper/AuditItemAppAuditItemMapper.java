package io.ylab.monitoring.app.springboot.mapper;

import io.ylab.monitoring.app.springboot.out.AppAuditItem;
import io.ylab.monitoring.domain.audit.model.AuditItem;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Преобразование в дто лога аудита
 *
 * <p>
 * <a href="https://www.baeldung.com/mapstruct">Quick Guide to MapStruct</a><br>
 * <a href="https://stackoverflow.com/questions/56849053/mapstruct-is-not-updating-its-getters-and-setters-in-the-generated-source-files">Mapstruct is not updating its getters and setters in the generated source files</a><br>
 * </p>
 */
@Mapper
public interface AuditItemAppAuditItemMapper {
    List<AppAuditItem> from(List<AuditItem> auditItemList);

    AppAuditItem from(AuditItem auditItem);
}
