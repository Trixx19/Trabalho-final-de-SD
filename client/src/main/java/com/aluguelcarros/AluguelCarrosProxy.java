package com.aluguelcarros;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class AluguelCarrosProxy {
    private Integer currentId = 0;
    private Gson gson;
    private UDPClient udpClient;

    AluguelCarrosProxy(){
        this.gson = new Gson();
        this.udpClient = new UDPClient();
    }

    private String packsMessage(String objectReference, String methodId, String arguments){
        Message requestMessage = new Message(0, currentId++, objectReference, methodId, arguments);

        requestMessage.messageType = 0;
        requestMessage.id = currentId++;
        requestMessage.objectReference = objectReference;
        requestMessage.methodId = methodId;
        requestMessage.arguments = arguments;

        return gson.toJson(requestMessage);
    }

    private String unpackMessage(String responseMessage){
        Message response = gson.fromJson(responseMessage, Message.class);
        return response.arguments;
    }

    private String doOperation(String objectReference, String method, String args){
        String jsonRequest = this.packsMessage(objectReference, method, args);
        udpClient.sendRequest(jsonRequest.getBytes());

        String response = udpClient.getReply();

        while(response == "TIMEOUTED"){
            udpClient.sendRequest(jsonRequest.getBytes());
            response = udpClient.getReply();
        }

        String responseArgs = this.unpackMessage(response);
        return responseArgs;        
    }

    public Boolean addCarro(Carro newCarro){
        String requestArgs = gson.toJson(newCarro);
        String responseArgs = this.doOperation("AluguelCarros", "addCarro", requestArgs);
        
        if (responseArgs.equals("sucess")){
            return true;
        } else {
            return false;
        }
    }

    public Carro searchCarro(/*String sectionNumber,*/ String CarroNumber) throws CarroNotExistsException{
        String[] argsList = {/*sectionNumber, */CarroNumber};
        String requestArgs = gson.toJson(argsList);
        String responseArgs = this.doOperation("AluguelCarros", "searchCarro", requestArgs);

        if (responseArgs.equals("CarroNotExists")){
            throw new CarroNotExistsException("Error: Carro buscado não existe.");
        } else {
            return gson.fromJson(responseArgs, Carro.class);
        }
    }

    public List<Carro> getAllCarros() throws CarroNotExistsException {
        String responseArgs = this.doOperation("AluguelCarros", "getAllCarros", "");
    
        if (responseArgs.equals("CarroNotExists")){
            throw new CarroNotExistsException("Error: Não existe carros cadastrados.");
        } else {
            Type listType = new TypeToken<List<Carro>>() {}.getType();
            List<Carro> listCarros = gson.fromJson(responseArgs, listType);
            return listCarros;
        }
    }

    public Carro removeCarro(Integer ID) throws CarroNotExistsException {
        String requestArgs = gson.toJson(ID);
        String responseArgs = this.doOperation("AluguelCarros", "removeCarro", requestArgs);

        if (responseArgs.equals("CarroNotExists")){
            throw new CarroNotExistsException("Error: O carro não existe.");
        } else {
            Carro carro = gson.fromJson(responseArgs, Carro.class);
            return carro;
        }
    }

    public void reserveCarro(Integer ID) throws CarroNotExistsException, InvalidOccupancyStatusException {
        String requestArgs = gson.toJson(ID);
        String responseArgs = this.doOperation("AluguelCarros", "reserveCarro", requestArgs);

        if (responseArgs.equals("CarroNotExists")){
            throw new CarroNotExistsException("Error:O carro não existe.");
        } else if (responseArgs.equals("InvalidOccupancyStatus")){
            throw new InvalidOccupancyStatusException("Error: Carro já alugado.");
        }
    }

    public void cancelReservation(Integer ID) throws CarroNotExistsException, InvalidOccupancyStatusException {
        String requestArgs = gson.toJson(ID);
        String responseArgs = this.doOperation("AluguelCarros", "cancelReservation", requestArgs);

        if (responseArgs.equals("CarroNotExists")){
            throw new CarroNotExistsException("Error: o carro não existe.");
        } else if (responseArgs.equals("InvalidOccupancyStatus")){
            throw new InvalidOccupancyStatusException("Error: carro não está reservado.");
        }
    }
}
