package com.example.demo;

import com.example.demo.CosasPatrones.Car;
import com.example.demo.CosasPatrones.CarDto;
import com.example.demo.CosasPatrones.CarMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}



    @Test
    public void shouldMapCarToDto() {
        //given
        Car car = new Car( "Morris", 5 );

        //when
        CarDto carDto = CarMapper.INSTANCE.carToCarDto( car );

        //then
        assertThat( carDto ).isNotNull();
        assertThat( carDto.getMake() ).isEqualTo( "Morris" );
        assertThat( carDto.getSeatCount() ).isEqualTo( 5 );

        System.out.println("se ha ejecutado el test correctamente");

    }



}
