package fr.insa.coffee.OrchestratorMS.service;

import fr.insa.coffee.OrchestratorMS.repository.OrchestratorMsRepository;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Service
public class OrchestratorMsService {
    private final OrchestratorMsRepository OrchestratorMsRepository;


    public OrchestratorMsService(OrchestratorMsRepository OrchestratorMsRepository) {
        this.OrchestratorMsRepository = OrchestratorMsRepository;
    }


    @Autowired
        private RestTemplate restTemplate;

        public String check() {

            List<Long> listOfIds;
            String res = "";
            try {
                listOfIds = this.OrchestratorMsRepository.getMachineIds();

                for (ListIterator<Long> iter = listOfIds.listIterator(); iter.hasNext(); )
            {
                ResponseEntity<String> cupResponse = restTemplate.exchange(
                "http://api/cup-Ms/{iter}",
                HttpMethod.GET,
                null,
                String.class,
                iter
                );
                ResponseEntity<String> presenceResponse = restTemplate.exchange(
                "http://api/presence-MS/{iter}",
                HttpMethod.GET,
                null,
                String.class,
                iter
                );

                String cupQuantity = cupResponse.getBody();
                String presenceValue = presenceResponse.getBody();

                ResponseEntity<String> ledResponse = restTemplate.exchange(
                "http://api/led-ms/update"+"?machineId={machineId}"+"&cupQuantity={cupQuantity}"+ "&presenceValue={presenceValue}",
                HttpMethod.POST,
                null,
                String.class,
                iter,
                cupQuantity,
                presenceValue
                );

                res = ledResponse.getBody() ;

            };
            } catch (SQLException e) {
                e.printStackTrace();
            }      
            
            return res ;
        }

}
