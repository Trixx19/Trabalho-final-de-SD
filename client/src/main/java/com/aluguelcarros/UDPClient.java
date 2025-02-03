package com.aluguelcarros;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class UDPClient {
    private DatagramSocket SocketUDP;
    private InetAddress serverInetAddress;
    private String serverAdress;
    private Integer serverPort;

    UDPClient(){
        try{
            this.SocketUDP = new DatagramSocket();
            this.serverAdress = "localhost";
            this.serverPort = 3456;
            this.serverInetAddress = InetAddress.getByName(serverAdress);
            SocketUDP.setSoTimeout(3000);

        } catch (SocketException err){
            System.out.println(err.getMessage());
        } catch (UnknownHostException err) {
            System.out.println(err.getMessage());
        }
    }

    public void sendRequest(byte[] requestMessage){
        try{
            DatagramPacket requestPacket = new DatagramPacket(requestMessage, requestMessage.length, serverInetAddress, serverPort);
            SocketUDP.send(requestPacket);
        } catch (IOException err){
            System.out.println(err.getMessage());
        } 
    }

    public String getReply() {
        String serverResponse = "";
        byte[] bufferResponse = new byte[1024];
            
        DatagramPacket responsePacket = new DatagramPacket(bufferResponse, bufferResponse.length);
        try {
            SocketUDP.receive(responsePacket);
            serverResponse = new String(responsePacket.getData(), 0, responsePacket.getLength());
        
        } catch (SocketTimeoutException err){
            return "TIMEOUTED";
        } catch (IOException err){
            System.out.println(err.getMessage());
        }

        return serverResponse;
    }

    public void finish(){
        this.SocketUDP.close();
    }
}
