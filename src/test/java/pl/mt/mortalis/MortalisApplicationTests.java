package pl.mt.mortalis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import pl.mt.mortalis.condolences.dto.mapper.CondolencesDisplayDtoMapper;
import pl.mt.mortalis.necrology.Necrology;
import pl.mt.mortalis.necrology.dto.NecrologyDisplayDto;
import pl.mt.mortalis.necrology.dto.mapper.NecrologyDisplayDtoMapper;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class MortalisApplicationTests {

    private NecrologyDisplayDtoMapper necrologyDisplayDtoMapper;
    private Necrology necrology;

    @BeforeEach
    public void init() {
        necrologyDisplayDtoMapper = new NecrologyDisplayDtoMapper(Mockito.mock(CondolencesDisplayDtoMapper.class));
        necrology = Mockito.mock(Necrology.class);
        when(necrology.getGender()).thenReturn(Gender.MALE);
    }

    @Test
    void shouldReturn34lat() {
        // given
        when(necrology.getBirthDate()).thenReturn(LocalDate.of(1990, 1, 1));
        when(necrology.getDeathDate()).thenReturn(LocalDate.of(2024, 1, 1));

        // when
        NecrologyDisplayDto necrologyDisplayDto = necrologyDisplayDtoMapper.mapEntityToDisplayDto(necrology);

        // then
        assertThat(necrologyDisplayDto.getAgeString()).isEqualTo("34 lat");
    }

    @Test
    void shouldReturn1roku() {
        // given
        when(necrology.getBirthDate()).thenReturn(LocalDate.of(2023, 1, 1));
        when(necrology.getDeathDate()).thenReturn(LocalDate.of(2024, 1, 1));

        // when
        NecrologyDisplayDto necrologyDisplayDto = necrologyDisplayDtoMapper.mapEntityToDisplayDto(necrology);

        // then
        assertThat(necrologyDisplayDto.getAgeString()).isEqualTo("1 roku");
    }

    @Test
    void shouldReturn3dni() {
        // given
        when(necrology.getBirthDate()).thenReturn(LocalDate.of(2024, 1, 1));
        when(necrology.getDeathDate()).thenReturn(LocalDate.of(2024, 1, 4));

        // when
        NecrologyDisplayDto necrologyDisplayDto = necrologyDisplayDtoMapper.mapEntityToDisplayDto(necrology);

        // then
        assertThat(necrologyDisplayDto.getAgeString()).isEqualTo("3 dni");
    }

    @Test
    void shouldReturn1dnia() {
        // given
        when(necrology.getBirthDate()).thenReturn(LocalDate.of(2024, 1, 1));
        when(necrology.getDeathDate()).thenReturn(LocalDate.of(2024, 1, 2));

        // when
        NecrologyDisplayDto necrologyDisplayDto = necrologyDisplayDtoMapper.mapEntityToDisplayDto(necrology);

        // then
        assertThat(necrologyDisplayDto.getAgeString()).isEqualTo("1 dnia");
    }

    @Test
    void shouldReturn0dni() {
        // given
        when(necrology.getBirthDate()).thenReturn(LocalDate.of(2024, 1, 1));
        when(necrology.getDeathDate()).thenReturn(LocalDate.of(2024, 1, 1));

        // when
        NecrologyDisplayDto necrologyDisplayDto = necrologyDisplayDtoMapper.mapEntityToDisplayDto(necrology);

        // then
        assertThat(necrologyDisplayDto.getAgeString()).isEqualTo("0 dni");
    }

    @Test
    void shouldReturn3tygodni() {
        // given
        when(necrology.getBirthDate()).thenReturn(LocalDate.of(2022, 1, 1));
        when(necrology.getDeathDate()).thenReturn(LocalDate.of(2022, 1, 25));

        // when
        NecrologyDisplayDto necrologyDisplayDto = necrologyDisplayDtoMapper.mapEntityToDisplayDto(necrology);

        // then
        assertThat(necrologyDisplayDto.getAgeString()).isEqualTo("3 tygodni");
    }

    @Test
    void shouldReturn1tygodnia() {
        // given
        when(necrology.getBirthDate()).thenReturn(LocalDate.of(2022, 1, 1));
        when(necrology.getDeathDate()).thenReturn(LocalDate.of(2022, 1, 8));

        // when
        NecrologyDisplayDto necrologyDisplayDto = necrologyDisplayDtoMapper.mapEntityToDisplayDto(necrology);

        // then
        assertThat(necrologyDisplayDto.getAgeString()).isEqualTo("1 tygodnia");
    }

    @Test
    void shouldReturn11miesiecy() {
        // given
        when(necrology.getBirthDate()).thenReturn(LocalDate.of(2024, 1, 1));
        when(necrology.getDeathDate()).thenReturn(LocalDate.of(2024, 12, 1));

        // when
        NecrologyDisplayDto necrologyDisplayDto = necrologyDisplayDtoMapper.mapEntityToDisplayDto(necrology);

        // then
        assertThat(necrologyDisplayDto.getAgeString()).isEqualTo("11 miesięcy");
    }

    @Test
    void shouldReturn1miesiaca() {
        // given
        when(necrology.getBirthDate()).thenReturn(LocalDate.of(2024, 1, 1));
        when(necrology.getDeathDate()).thenReturn(LocalDate.of(2024, 2, 1));

        // when
        NecrologyDisplayDto necrologyDisplayDto = necrologyDisplayDtoMapper.mapEntityToDisplayDto(necrology);

        // then
        assertThat(necrologyDisplayDto.getAgeString()).isEqualTo("1 miesiąca");
    }
}
