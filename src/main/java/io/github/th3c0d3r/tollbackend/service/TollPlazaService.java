package io.github.th3c0d3r.tollbackend.service;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.th3c0d3r.tollbackend.converter.TollPlazaConverter;
import io.github.th3c0d3r.tollbackend.dto.TollPlazaDto;
import io.github.th3c0d3r.tollbackend.dto.TollPlazaJsonConversionType;
import io.github.th3c0d3r.tollbackend.entity.TollPlaza;
import io.github.th3c0d3r.tollbackend.repo.TollPlazaRepo;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TollPlazaService {

    @Autowired
    private TollPlazaRepo tollPlazaRepo;

    @Autowired
    private TollPlazaConverter tollPlazaConverter;

    public void populateTable(){

        try {
            URL url = new URL("http://tis.nhai.gov.in/TollPlazaService.asmx");
            String requestXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "    <soap:Body>\n" +
                    "        <GetTollPlazaInfo xmlns=\"http://tempuri.org/\" />\n" +
                    "    </soap:Body>\n" +
                    "</soap:Envelope>";

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "text/xml; charset=utf-8");
            connection.setRequestProperty("SOAPAction", "http://tempuri.org/GetTollPlazaInfo");
            connection.setRequestProperty("Content-Length", String.valueOf(requestXML.length()));
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setDoOutput(true);

            log.info("Sending Request for TollPlaza Data....");
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(requestXML);
            wr.flush();
            wr.close();
            log.info("Request Sent.");

            log.info("Receiving Response containing TollPlaza Data....");
            BufferedReader serverResponse = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer responseXML = new StringBuffer();
            while ((inputLine = serverResponse.readLine()) != null) {
                responseXML.append(inputLine);
            }
            serverResponse.close();
            String response = responseXML.toString();
            log.info("Response received.");

            log.info("Converting Response XML to JSON.");
            JSONObject responseJsonObject = XML.toJSONObject(response);
            JSONObject tollData = responseJsonObject.getJSONObject("soap:Envelope")
                    .getJSONObject("soap:Body").getJSONObject("GetTollPlazaInfoResponse").getJSONObject("GetTollPlazaInfoResult");

            log.info("Convert Response JSON to Objects.");
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting().serializeNulls();
            builder.setFieldNamingStrategy(new FieldNamingStrategy() {

                @Override
                public String translateName(Field field) {
                    if (field.getName().equals("tollPlazaId"))
                        return "TollPlazaID";
                    if (field.getName().equals("tollName"))
                        return "TollName";
                    if (field.getName().equals("costTable"))
                        return "HtmlPopup";
                    if (field.getName().equals("tollPlazaList"))
                        return "TollPlazaInfo";
                    else
                        return field.getName();
                }
            });

            Gson gson = builder.create();
            List<TollPlaza> tollPlazaList = gson.fromJson(tollData.toString(), TollPlazaJsonConversionType.class).getTollPlazaList();
            log.info("Conversion Success. Extracting CostTable from HTML.");
            tollPlazaList.parallelStream().forEach( value -> value.setCostTable(Jsoup.parse(value.getCostTable()).select("table").first().toString()));
            log.info("CostTable extracted successfully. Persisting TollPlaza Data in DB.");

            createAll(tollPlazaList);
            log.info("Success.");
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getCause().getMessage());
        }
    }

    private void createAll(List<TollPlaza> tollPlazaList){
        log.info("Preparing Data for Persist.");
        List<Integer> tollPlazaIds = tollPlazaList.parallelStream().map(TollPlaza::getTollPlazaId).collect(Collectors.toList());
        List<TollPlaza> tollPlazaListFromDB = tollPlazaRepo.findAllByTollPlazaIdIn(tollPlazaIds);
        Map<Integer, TollPlaza> tollPlazaIdToTollPlazaMap = tollPlazaListFromDB.parallelStream().collect(Collectors.toMap(k -> k.getTollPlazaId(),v -> v));

        log.info("Creating new records and updating existing entries.");
        for (TollPlaza tollPlaza : tollPlazaList){
            if (tollPlazaIdToTollPlazaMap.containsKey(tollPlaza.getTollPlazaId())){
                tollPlaza.setId(tollPlazaIdToTollPlazaMap.get(tollPlaza.getTollPlazaId()).getId());
                tollPlazaConverter.applyChanges(tollPlazaIdToTollPlazaMap.get(tollPlaza.getTollPlazaId()),tollPlaza);
            }
            else {
                tollPlaza.setDeleted(false);
                tollPlazaIdToTollPlazaMap.put(tollPlaza.getTollPlazaId(),tollPlaza);
            }
        }

        List<TollPlaza> tollPlazas = new ArrayList<>(tollPlazaIdToTollPlazaMap.values());
        tollPlazas.sort(Comparator.comparing(TollPlaza::getTollPlazaId));
        tollPlazaRepo.saveAll(tollPlazas);
    }

    public TollPlazaDto getByTollPlazaId(Integer tollPlazaId) throws Exception {
        TollPlaza tollPlaza = tollPlazaRepo.findByTollPlazaId(tollPlazaId);
        if (tollPlaza == null) {
            throw new Exception("No Record found for id: " + tollPlazaId);
        } else
            return tollPlazaConverter.convertEntityToDto(tollPlaza);
    }
}
