package com.gimnasio.gimnasio.services;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferencePayerRequest;
import com.mercadopago.resources.preference.Preference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
public class PagoService {

    public String Pago() throws ErrorServiceException {

        MercadoPagoConfig.setAccessToken("APP_USR");

        PreferenceClient client = new PreferenceClient();

        Double precio1 = 10.00;

        PreferenceRequest request = PreferenceRequest.builder()
                .items(Arrays.asList(
                        PreferenceItemRequest.builder()
                                .title("Couta Mensual")
                                .description("Pago de la couta mensual del gimnasio")
                                .quantity(1)
                                .unitPrice(BigDecimal.valueOf(precio1))
                                .build()
                ))
                .backUrls(
                        PreferenceBackUrlsRequest.builder()
                                //url para cuando paga bien
                                .success("http://localhost:9000/inicio")
                                //url para cuando falla
                                .failure("http://localhost:9000/inicio")
                                //url para cuando queda pendiente
                                .pending("http://localhost:9000/inicio")
                                .build()
                )


                // esta es la URL donde Mercado Pago manda la info del pago con un POST
                .notificationUrl("https://tu-sitio.com/notifications")

                // esto es un identificador que tiene que ser unico
                .externalReference("orden_123456")
                .build();

        try {
            Preference preference = client.create(request);
            return preference.getInitPoint();
        } catch (com.mercadopago.exceptions.MPApiException e) {
            System.out.println("Error status: " + e.getStatusCode());
            System.out.println("Error message: " + e.getMessage());
            System.out.println("Error cause: " + e.getCause());

            if (e.getApiResponse() != null) {
                System.out.println("API Response body: " + e.getApiResponse().getContent());
            }
            throw new ErrorServiceException("error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorServiceException("error");
        }
    }
}
