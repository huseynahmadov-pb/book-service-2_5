package az.company.bookservice_2_5.mapper;

import az.company.bookservice_2_5.dao.entity.AuthorEntity;
import az.company.bookservice_2_5.model.request.CreateAuthorRequest;
import az.company.bookservice_2_5.model.response.AuthorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    AuthorEntity toEntity(CreateAuthorRequest request);

    AuthorResponse toResponse(AuthorEntity entity);

    List<AuthorResponse> toResponse(List<AuthorEntity> entities);
}
