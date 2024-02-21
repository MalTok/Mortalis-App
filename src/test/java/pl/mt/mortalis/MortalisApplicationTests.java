package pl.mt.mortalis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.mt.mortalis.necrology.Necrology;

import java.time.LocalDate;

@SpringBootTest
class MortalisApplicationTests {

    //private NecrologyDisplayDtoMapper necrologyDisplayDtoMapper;
    private Necrology necrology;

    @BeforeEach
    public void init() {
        //necrologyDisplayDtoMapper = new NecrologyDisplayDtoMapper(condolencesDisplayDtoMapper);
        necrology = new Necrology();

    }

    @Test
    public void shoudReturn10() {
        // given
        necrology.setBirthDate(LocalDate.of(2014, 1, 23));
        necrology.setDeathDate(LocalDate.now());

        // when
       //NecrologyDisplayDto necrologyDisplayDto = necrologyDisplayDtoMapper.maptoDisplayDto(necrology);

        // then
        //assertThat(necrologyDisplayDto.getAgeString()).isEqualTo("10 lat");
    }
}
