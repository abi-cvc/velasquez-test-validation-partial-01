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

    //Prueba de Equipaje estándar
    @Test
    void shouldCalculateStandardBaggageFee() {
        when(passengerService.isFrequentFlyer(1L)).thenReturn(false);
        double fee = calculator.calculateFee(20.0, 1, 1L);
        assertThat(fee).isEqualTo(30.0);
    }

    //Prueba de Exceso de peso
    @Test
    void shouldCalculateExcessWeightFee() {
        when(passengerService.isFrequentFlyer(1L)).thenReturn(false);
        double fee = calculator.calculateFee(25.0, 1, 1L);
        assertThat(fee).isEqualTo(80.0); 
    }

    //Prueba de Beneficio VIP
    @Test
    void shouldCalculateVipBenefitForOneBag() {
        when(passengerService.isFrequentFlyer(1L)).thenReturn(true);
        double fee = calculator.calculateFee(15.0, 1, 1L);
        assertThat(fee).isEqualTo(0.0); 
    } 
    


}
