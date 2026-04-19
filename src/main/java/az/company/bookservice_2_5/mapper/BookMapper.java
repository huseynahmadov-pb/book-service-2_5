package az.company.bookservice_2_5.mapper;

import az.company.bookservice_2_5.dao.entity.BookEntity;
import az.company.bookservice_2_5.model.request.CreateBookRequest;
import az.company.bookservice_2_5.model.response.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authors", ignore = true)
    BookEntity toEntity(CreateBookRequest request);

    @Mapping(source = "authors", target = "author")
    BookResponse toResponse(BookEntity entity);

    List<BookResponse> toResponse(List<BookEntity> entities);
}
