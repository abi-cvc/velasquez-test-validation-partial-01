package ec.edu.epn.skyroute.service;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.InjectMocks;
import org.mockito.Mock;
public class BaggageFeeCalculatorTest {
    
    @Mock
    private PassengerService passengerService;

    @InsertMocks
    private BaggageFeeCalculator calculator;

    @Test
    void shouldThrow_whenWeightIsZero() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> calculator.calculateFee(0.0, 1, 1L));
    }

    //1. Prueba de Equipaje estándar
    @Test
    void shouldCalculateStandardBaggageFee() {
        when(passengerService.isFrequentFlyer(1L)).thenReturn(false);
        double fee = calculator.calculateFee(20.0, 1, 1L);
        assertThat(fee).isEqualTo(30.0);
    }

    //2. Prueba de Exceso de peso
    @Test
    void shouldCalculateExcessWeightFee() {
        when(passengerService.isFrequentFlyer(1L)).thenReturn(false);
        double fee = calculator.calculateFee(25.0, 1, 1L);
        assertThat(fee).isEqualTo(80.0); 
    }

    //3. Prueba de Beneficio VIP
    @Test
    void shouldCalculateVipBenefitForOneBag() {
        when(passengerService.isFrequentFlyer(1L)).thenReturn(true);
        double fee = calculator.calculateFee(15.0, 1, 1L);
        assertThat(fee).isEqualTo(0.0); 
    } 
    
    //4. Prueba caso limite VIP
    @Test
    void shouldCalculateVipBenefitForTwoBags() {
        when(passengerService.isFrequentFlyer(1L)).thenReturn(true);
        double fee = calculator.calculateFee(15.0, 2, 1L);
        assertThat(fee).isEqualTo(30.0); 
    }

    //5. Prueba de validación de excepción cuando el peso (weight = negativo)
    @Test
    void shouldThrow_whenWeightIsNegative() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> calculator.calculateFee(-5.0, 1, 1L));
    }

    //Prueba extra cuando la cantidad de maletas es menor a 1
    @Test
    void shouldThrow_whenBagCountIsLessThanOne() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> calculator.calculateFee(20.0, 0, 1L));
    }


}
