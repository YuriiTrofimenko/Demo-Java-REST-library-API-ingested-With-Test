package ua.com.epam.tests.service;

import org.mockito.ArgumentCaptor;
import ua.com.epam.entity.dto.author.nested.NameDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
// import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.epam.entity.Author;
import ua.com.epam.entity.dto.author.AuthorDto;
import ua.com.epam.repository.AuthorRepository;
import ua.com.epam.repository.JsonKeysConformity;
import ua.com.epam.service.AuthorService;
import ua.com.epam.service.mapper.ModelToDtoMapper;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    // просьба к Мокито: создать объект-макет репозитория аторов
    // и сохранить ссылку на него в поле authorRepository
    @Mock
    private AuthorRepository authorRepositoryMock;
    @Mock
    private ModelToDtoMapper toDtoMapperMock;

    // просьба к Мокито: в классе AuthorService найти все поля,
    // в которые должны были производиться внедрения зависимостей (@Autowired)
    @InjectMocks
    private AuthorService authorService;

    // объект-заглушка сущности Author
    // для передачи в качестве аргумента метода,
    // когда неизвестно, какой конкретно (с какими данными внутри) объект Author
    // передавался в метод перед проверкой
    ArgumentCaptor<Author> authorArgument =
        ArgumentCaptor.forClass(Author.class);

    @Test
    // TODO Complete method's name
    void givenCorrectAuthorId_whenFindAuthor_then() {
        // System.out.println(authorService.toString());
        final Long id = 1L;
        final Long authorId = 4001L;
        final String firstName = "Desiree";
        final String secondName = "Hermann";
        // System.out.println(authorService.findAuthor(authorId));
        final Author author = new Author();
        author.setId(id);
        author.setAuthorId(authorId);
        author.setFirstName(firstName);
        author.setSecondName(secondName);
        final AuthorDto authorDto = new AuthorDto();
        authorDto.setAuthorId(authorId);
        authorDto.setAuthorName(new NameDto(firstName, secondName));

        // обучение макета: когда на объекте макета authorRepositoryMock
        // будет вызван метод findById с аргументом authorId -
        // этот метод вернет Optional-обертку с объектом автора внутри
        given(authorRepositoryMock.getOneByAuthorId(authorId))
            .willReturn(Optional.of(author));
        given(toDtoMapperMock.mapAuthorToAuthorDto(author))
            .willReturn(authorDto);
        // на тестируемом объекте службы вызываем метод
        AuthorDto result = authorService.findAuthor(authorId);
        // проверка: есть объект, который вернул тестируемый метод
        assertNotNull(result);
        // проверка: в этом объекте authorId равен переданному
        assertEquals(result.getAuthorId(), authorId);
        assertEquals(
            result.getAuthorName().getFirst(),
            authorDto.getAuthorName().getFirst()
        );
        assertEquals(
            result.getAuthorName().getSecond(),
            authorDto.getAuthorName().getSecond()
        );
        // проверка: в результате вызова тестируемого метода вызывался ли внутри него
        // хотя бы один раз метод репозитория authorRepository - getOneByAuthorId
        // с конкретным аргументом authorId
        verify(authorRepositoryMock, atLeastOnce()).getOneByAuthorId(authorId);
        // проверка: в результате вызова тестируемого метода вызывался ли внутри него
        // хотя бы один раз метод объекта toDtoMapper - mapAuthorToAuthorDto
        // с каким-либо (authorArgument.capture()) аргументом типа Author
        verify(toDtoMapperMock, atLeastOnce()).mapAuthorToAuthorDto(authorArgument.capture());
    }
}
